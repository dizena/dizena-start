package org.dizena.modules.master.dao;

import org.dizena.common.mongo.CommonRepository;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.LogLogin;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class LogLoginDao extends CommonRepository<LogLogin> {
    @Resource
    private MongoTemplate mt;

    @Override
    public LogLogin insert(LogLogin m) {
        return mt.insert(m);
    }

    @Override
    public void delete(String id) {
        mt.remove(idQuery(id), LogLogin.class);
    }

    @Override
    public void delete(String key, Object value) {
        mt.remove(kvQuery(key, value), LogLogin.class);
    }

    @Override
    public void delete(String[] keys, Object[] values) {
        mt.remove(kvQuery(keys, values), LogLogin.class);
    }

    @Override
    public void update(LogLogin m) {
        Update update = mongoUpdate(m);
        mt.updateFirst(idQuery(m.getId()), update, LogLogin.class);
    }

    @Override
    public LogLogin findOne(String id) {
        return mt.findOne(idQuery(id), LogLogin.class);
    }

    @Override
    public LogLogin findOne(String key, Object value) {
        return mt.findOne(kvQuery(key, value), LogLogin.class);
    }

    @Override
    public LogLogin findOne(String[] keys, Object[] values) {
        return mt.findOne(kvQuery(keys, values), LogLogin.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value), LogLogin.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values), LogLogin.class);
    }

    @Override
    public List<LogLogin> findMany(String key, Object value) {
        return mt.find(kvQuery(key, value), LogLogin.class);
    }

    @Override
    public List<LogLogin> findMany(String[] keys, Object[] values) {
        return mt.find(kvQuery(keys, values), LogLogin.class);
    }

    @Override
    public List<LogLogin> find(PageQuery pageQuery) {
        return mt.find(pageQuery(pageQuery), LogLogin.class);
    }

    @Override
    public long count(PageQuery pageQuery) {
        return mt.count(countQuery(pageQuery), LogLogin.class);
    }


}
