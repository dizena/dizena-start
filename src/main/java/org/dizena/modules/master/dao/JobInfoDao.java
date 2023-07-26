package org.dizena.modules.master.dao;

import org.dizena.common.mongo.CommonRepository;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.JobInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JobInfoDao extends CommonRepository<JobInfo> {
    @Resource
    private MongoTemplate mt;

    @Override
    public JobInfo insert(JobInfo m) {
        return mt.insert(m);
    }

    @Override
    public void delete(String id) {
        mt.remove(idQuery(id), JobInfo.class);
    }

    @Override
    public void delete(String key, Object value) {
        mt.remove(kvQuery(key, value), JobInfo.class);
    }

    @Override
    public void delete(String[] keys, Object[] values) {
        mt.remove(kvQuery(keys, values), JobInfo.class);
    }

    @Override
    public void update(JobInfo m) {
        Update update = mongoUpdate(m);
        mt.updateFirst(idQuery(m.getId()), update, JobInfo.class);
    }

    @Override
    public JobInfo findOne(String id) {
        return mt.findOne(idQuery(id), JobInfo.class);
    }

    @Override
    public JobInfo findOne(String key, Object value) {
        return mt.findOne(kvQuery(key, value), JobInfo.class);
    }

    @Override
    public JobInfo findOne(String[] keys, Object[] values) {
        return mt.findOne(kvQuery(keys, values), JobInfo.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value), JobInfo.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values), JobInfo.class);
    }

    @Override
    public List<JobInfo> findMany(String key, Object value) {
        return mt.find(kvQuery(key, value), JobInfo.class);
    }

    @Override
    public List<JobInfo> findMany(String[] keys, Object[] values) {
        return mt.find(kvQuery(keys, values), JobInfo.class);
    }

    @Override
    public List<JobInfo> find(PageQuery pageQuery) {
        return mt.find(pageQuery(pageQuery), JobInfo.class);
    }

    @Override
    public long count(PageQuery pageQuery) {
        return mt.count(countQuery(pageQuery), JobInfo.class);
    }


}
