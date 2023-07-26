package org.dizena.modules.master.service.impl;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.service.KvConfigService;
import org.dizena.modules.master.bean.Res;
import org.dizena.modules.master.bean.Role;
import org.dizena.modules.master.bean.vo.RoleVO;
import org.dizena.modules.master.bean.vo.TreeVO;
import org.dizena.modules.master.dao.ResDao;
import org.dizena.modules.master.dao.RoleDao;
import org.dizena.modules.master.service.ResService;
import org.dizena.modules.master.service.RoleService;
import org.dizena.modules.master.zload.ConstantMaster;
import org.dizena.utils.IdGen;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao dao;
    @Resource
    private ResDao resDao;
    @Resource
    private KvConfigService baseConfigService;
    @Resource
    private ResService resService;

    @Override
    public PageResponse<RoleVO> list(PageRequest req) {
        PageQuery byPageQuery = new PageQuery(req);
        List<Role> roles = dao.find(byPageQuery);
        long count = dao.count(byPageQuery);
        List<RoleVO> list = new ArrayList<>();
        roles.forEach(role ->
        {
            List<TreeVO> treeVOList = resService.getRoleTreeData("0", 1, role.getResIds());
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            vo.setMenu(treeVOList);
            list.add(vo);
        });
        return new PageResponse<>(req.getPage(), req.getSize(), count, list);
    }


    @Override
    public String add(Role m) {
        if ("master".equals(m.getRole())) {
            throw new RuntimeException("Role is reserved");
        }
        Role tmp = dao.getByRole(m.getRole());
        if (tmp != null) {
            throw new RuntimeException("Role is exist");
        }
        m.setId(IdGen.get().nextSid());
        List<String> resIds = m.getResIds();
        if (resIds != null) {
            resIds.remove("0");
            m.setResIds(resIds);
        }
        dao.insert(m);
        return "ok";
    }

    @Override
    public String edit(Role t) {
        if ("master".equals(t.getRole())) {
            throw new RuntimeException("Role is reserved");
        }
        List<String> resIds = t.getResIds();
        if (resIds != null) {
            resIds.remove("0");
            t.setResIds(resIds);
        }
        //新的角色名称查询一下有无
        Role tmp = dao.getByRole(t.getRole());

        if (tmp == null) {
            dao.update(t);
        } else {
            if (tmp.getId().equals(t.getId())) {
                dao.update(t);
            }
        }
        return "ok";
    }

    @Override
    public String del(String ids) {
        for (String s : ids.split(",")) {
            dao.delete(s);
        }
        return "ok";
    }

    public Role get(String id) {
        return dao.findOne(id);
    }


    public Collection<Res> queryRoleRes(String role) {
        List<Res> res = new LinkedList<Res>();
        Role r = dao.getByRole(role);
        List<String> lists = r.getResIds();
        if (lists != null && lists.size() > 0) {
            for (String rid : lists) {
                Res tmp = resDao.findOne(rid);
                if (null != tmp) {
                    res.add(tmp);
                }
            }
        }
        return res;
    }

    @Override
    public List<String> all() {
        List<String> ary = new ArrayList<>();
        List<Role> list = dao.find(null);
        for (Role role : list) {
            ary.add(role.getRole());
        }
        return ary;
    }

    @Override
    public List<String> auth() {
        return Arrays.asList(baseConfigService.getConfigByKey(ConstantMaster.AUTH_All).split(","));
    }


}
