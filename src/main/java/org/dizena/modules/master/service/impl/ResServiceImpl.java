package org.dizena.modules.master.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dizena.common.mongo.PageQuery;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.vo.ResVO;
import org.dizena.modules.master.bean.vo.TreeVO;
import org.dizena.modules.master.bean.Res;
import org.dizena.modules.master.dao.ResDao;
import org.dizena.modules.master.service.ResService;
import org.dizena.utils.IdGen;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ResServiceImpl implements ResService
{

    @Resource
    private ResDao dao;

    @Override
    public PageResponse<ResVO> list(PageRequest req)
    {
        PageQuery byPageQuery = new PageQuery(req);
        byPageQuery.put("pid", "0");
        List<Res> list = dao.find(byPageQuery);
        long count = dao.count(byPageQuery);
        List<ResVO> data = new ArrayList<>();
        Collections.sort(list);//排序
        list.forEach(res ->
        {
            ResVO vo = new ResVO();
            BeanUtils.copyProperties(res, vo);
            vo.setChildren(getChildren(res.getId()));
            data.add(vo);
        });
        return new PageResponse<>(req.getPage(), req.getSize(), count, data);
    }

    private List<ResVO> getChildren(String pid)
    {
        List<ResVO> data = new ArrayList<>();
        List<Res> list = dao.findMany("pid", pid);
        Collections.sort(list);//排序
        list.forEach(res ->
        {
            ResVO vo = new ResVO();
            BeanUtils.copyProperties(res, vo);
            vo.setChildren(getChildren(res.getId()));
            data.add(vo);
        });
        return data;
    }

    @Override
    public String add(Res m)
    {
        m.setId(IdGen.get().nextSid());
        if (StringUtils.isEmpty(m.getPid()))
        {
            m.setPid("0");
            m.setLevel(1);
        }
        dao.insert(m);
        return "ok";
    }

    @Override
    public String edit(Res m)
    {
        dao.update(m);
        return "ok";
    }

    @Override
    public String del(String ids)
    {
        for (String id : ids.split(","))
        {
            List<Res> subs = queryResSubs(id);
            subs.forEach(r ->
            {
                dao.delete(r.getId());
            });
            dao.delete(id);
        }
        return "ok";
    }

    @Override
    public Res get(String id)
    {
        return dao.findOne(id);
    }

    public List<Res> queryResSubs(String pid)
    {
        return dao.querySub(pid);
    }

    public Integer queryMaxSort(String pid)
    {
        Integer res = dao.queryMaxSort(pid);
        if (res == null)
        {
            return dao.findOne(pid).getSort() * 100 + 1;
        }
        return res + 1;
    }

    public List<Res> level(Integer level)
    {
        List<Res> list = new ArrayList<Res>();
        if (1 == level)
        {
            Res r = new Res("0", "根目录", null, null, null, null, null);
            list.add(r);
        } else
        {
            list = dao.queryLevel(level);
        }
        return list;
    }

    @Override
    public List<TreeVO> getTree()
    {
        TreeVO tree = new TreeVO();
        tree.setId("0");
        tree.setLabel("所有资源");
        tree.setChildren(getTreeData("0", 1));
        List<TreeVO> treeData = new ArrayList<>();
        treeData.add(tree);
        return treeData;
    }

    @Override
    public void init()
    {

        Res r1 = new Res("1", "系统首页", "index", 1, 1, "0", "el-icon-lx-home");
        Res r2 = new Res("2", "系统配置", "#", 2, 1, "0", "el-icon-lx-settings");
        Res r21 = new Res("21", "用户管理", "User", 201, 2, "2", "el-icon-lx-people");
        Res r22 = new Res("22", "角色管理", "Role", 202, 2, "2", "el-icon-lx-service");
        Res r23 = new Res("23", "资源管理", "Res", 203, 2, "2", "el-icon-lx-global");
        Res r24 = new Res("24", "配置管理", "Config", 204, 2, "2", "el-icon-orange");
        Res r25 = new Res("25", "任务管理", "Job", 205, 2, "2", "el-icon-loading");

        dao.insert(r1);
        dao.insert(r2);
        dao.insert(r21);
        dao.insert(r22);
        dao.insert(r23);
        dao.insert(r24);
        dao.insert(r25);
    }

    @Override
    public List<Res> getAll()
    {
        return dao.find(null);
    }


    private List<TreeVO> getTreeData(String pid, Integer level)
    {
        List<TreeVO> lists = new ArrayList<>();
        List<Res> res = queryPidLevel(pid, level);
        for (Res r : res)
        {
            if (r != null)
            {
                TreeVO m = new TreeVO();
                m.setId(r.getId());
                m.setLabel(r.getTitle());
                m.setChildren(getTreeData(r.getId(), r.getLevel() + 1));
                lists.add(m);
            }
        }
        return lists;
    }

    private List<Res> queryPidLevel(String pid, Integer level)
    {
        return dao.queryPidLevel(pid, level);
    }

    @Override
    public List<TreeVO> getRoleTreeData(String pid, Integer level, List<String> resIds)
    {
        List<TreeVO> lists = new ArrayList<>();
        List<Res> res = queryPidLevel(pid, level);
        for (Res r : res)
        {
            resIds.forEach(resId ->
            {
                if (resId.equals(r.getId()))
                {
                    TreeVO m = new TreeVO();
                    m.setId(r.getId());
                    m.setLabel(r.getTitle());
                    m.setChildren(getRoleTreeData(r.getId(), r.getLevel() + 1, resIds));
                    lists.add(m);
                }
            });

        }
        return lists;
    }
}
