package org.dizena.modules.master.service.impl;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.BlackUser;
import org.dizena.modules.master.bean.Relation;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.bean.vo.RelationVO;
import org.dizena.modules.master.dao.BlackUserDao;
import org.dizena.modules.master.dao.RelationDao;
import org.dizena.modules.master.service.RelationService;
import org.dizena.modules.master.service.UserService;
import org.dizena.utils.IdGen;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {
    @Resource
    private RelationDao relationDao;
    @Resource
    private BlackUserDao blackUserDao;
    @Resource
    private UserService userService;

    @Override
    public PageResponse<RelationVO> list(PageRequest dto, String flag) {
        PageQuery query = new PageQuery(dto);
        if ("1".equals(flag)) {
            query.put("uid", userService.info().getId());
        }
        if ("2".equals(flag)) {
            query.put("toUid", userService.info().getId());
        }
        if ("3".equals(flag)) {
            query.put("uid", userService.info().getId());
            query.put("isFriend", 1);
        }
        long count = relationDao.count(query);
        List<RelationVO> list = new ArrayList<>();
        if (count > 0) {
            List<Relation> data = relationDao.find(query);
            data.forEach(m -> {
                User u;
                if ("2".equals(flag)) {
                    u = userService.get(m.getUid());
                } else {
                    u = userService.get(m.getToUid());
                }
                RelationVO vo = new RelationVO();
                BeanUtils.copyProperties(u, vo);
                BeanUtils.copyProperties(m, vo);
                list.add(vo);
            });
        }
        return new PageResponse<>(dto, count, list);
    }

    @Override
    public List<BlackUser> listBlack() {
        return blackUserDao.findMany("uid", userService.info().getId());
    }

    @Override
    public void attention(String toUid) {
        String uid = userService.info().getId();
        if (toUid.equals(uid)) {
            throw new RuntimeException("不能对自己执行此操作哦");
        }
        String[] keys = {"uid", "toUid"};
        Object[] values = {uid, toUid};
        Relation s = relationDao.findOne(keys, values);
        if (s == null) {
            s = new Relation();
            s.setId(IdGen.get().nextSid());
            s.setUid(uid);
            s.setToUid(toUid);
            s.setIsFriend(0);
            s.setCreateTime(System.currentTimeMillis());
            relationDao.insert(s);
        }
        s = relationDao.findOne(keys, values);
        Object[] values2 = {toUid, uid};
        Relation m = relationDao.findOne(keys, values2);
        if (m != null) {
            Relation tmp = new Relation();
            tmp.setId(s.getId());
            tmp.setIsFriend(1);
            relationDao.update(tmp);
            tmp.setId(m.getId());
            tmp.setIsFriend(1);
            relationDao.update(tmp);
        }
    }

    //取消关注粉丝好友
    @Override
    public void cancel(String flag, String id) {
        Relation r = relationDao.findOne(id);
        if (r != null) {
            if (!"2".equals(flag)) {
                if (r.getIsFriend() == 1) {
                    String[] keys = {"uid", "toUid"};
                    Object[] values = {r.getToUid(), r.getUid()};
                    Relation m = relationDao.findOne(keys, values);
                    if (m != null) {
                        Relation tmp = new Relation();
                        tmp.setId(m.getId());
                        tmp.setIsFriend(0);
                        relationDao.update(tmp);
                    }
                }
                relationDao.delete(id);
            } else {
                //拉黑
                User other = userService.get(r.getUid());
                BlackUser s = new BlackUser();
                s.setId(IdGen.get().nextSid());
                s.setUid(r.getToUid());
                s.setToUid(r.getUid());
                s.setNickname(other.getNickname());
                s.setPic(other.getPic());
                s.setCreateTime(System.currentTimeMillis());
                blackUserDao.insert(s);
                String[] keys = {"uid", "toUid"};
                Object[] values = {r.getUid(), r.getToUid()};
                Object[] values2 = {r.getToUid(), r.getUid()};
                relationDao.delete(keys, values);
                relationDao.delete(keys, values2);
            }
        }
    }

}
