package org.dizena.modules.master.dao;

import org.dizena.common.mongo.CommonRepository;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.BlackUser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class BlackUserDao extends CommonRepository<BlackUser> {
    @Resource
    private MongoTemplate mt;

    @Override
    public BlackUser insert(BlackUser m) {
        return mt.insert(m);
    }

    @Override
    public void delete(String id) {
        mt.remove(idQuery(id), BlackUser.class);
    }

    @Override
    public void delete(String key, Object value) {
        mt.remove(kvQuery(key, value), BlackUser.class);
    }

    @Override
    public void delete(String[] keys, Object[] values) {
        mt.remove(kvQuery(keys, values), BlackUser.class);
    }

    @Override
    public void update(BlackUser m) {
        Update update = mongoUpdate(m);
        mt.updateFirst(idQuery(m.getId()), update, BlackUser.class);
    }

    @Override
    public BlackUser findOne(String id) {
        return mt.findOne(idQuery(id), BlackUser.class);
    }

    @Override
    public BlackUser findOne(String key, Object value) {
        return mt.findOne(kvQuery(key, value), BlackUser.class);
    }

    @Override
    public BlackUser findOne(String[] keys, Object[] values) {
        return mt.findOne(kvQuery(keys, values), BlackUser.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value), BlackUser.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values), BlackUser.class);
    }

    @Override
    public List<BlackUser> findMany(String key, Object value) {
        return mt.find(kvQuery(key, value), BlackUser.class);
    }

    @Override
    public List<BlackUser> findMany(String[] keys, Object[] values) {
        return mt.find(kvQuery(keys, values), BlackUser.class);
    }

    @Override
    public List<BlackUser> find(PageQuery pageQuery) {
        return mt.find(pageQuery(pageQuery), BlackUser.class);
    }

    @Override
    public long count(PageQuery pageQuery) {
        return mt.count(countQuery(pageQuery), BlackUser.class);
    }


}
