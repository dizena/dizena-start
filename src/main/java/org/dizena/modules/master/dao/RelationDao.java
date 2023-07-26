package org.dizena.modules.master.dao;

import org.dizena.common.mongo.CommonRepository;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.Relation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class RelationDao extends CommonRepository<Relation> {
    @Resource
    private MongoTemplate mt;

    @Override
    public Relation insert(Relation m) {
        return mt.insert(m);
    }

    @Override
    public void delete(String id) {
        mt.remove(idQuery(id), Relation.class);
    }

    @Override
    public void delete(String key, Object value) {
        mt.remove(kvQuery(key, value), Relation.class);
    }

    @Override
    public void delete(String[] keys, Object[] values) {
        mt.remove(kvQuery(keys, values), Relation.class);
    }

    @Override
    public void update(Relation m) {
        Update update = mongoUpdate(m);
        mt.updateFirst(idQuery(m.getId()), update, Relation.class);
    }

    @Override
    public Relation findOne(String id) {
        return mt.findOne(idQuery(id), Relation.class);
    }

    @Override
    public Relation findOne(String key, Object value) {
        return mt.findOne(kvQuery(key, value), Relation.class);
    }

    @Override
    public Relation findOne(String[] keys, Object[] values) {
        return mt.findOne(kvQuery(keys, values), Relation.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value), Relation.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values), Relation.class);
    }

    @Override
    public List<Relation> findMany(String key, Object value) {
        return mt.find(kvQuery(key, value), Relation.class);
    }

    @Override
    public List<Relation> findMany(String[] keys, Object[] values) {
        return mt.find(kvQuery(keys, values), Relation.class);
    }

    @Override
    public List<Relation> find(PageQuery pageQuery) {
        return mt.find(pageQuery(pageQuery), Relation.class);
    }

    @Override
    public long count(PageQuery pageQuery) {
        return mt.count(countQuery(pageQuery), Relation.class);
    }


}
