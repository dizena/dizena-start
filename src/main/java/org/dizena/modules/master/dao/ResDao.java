package org.dizena.modules.master.dao;

import org.dizena.common.mongo.CommonRepository;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.Res;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ResDao extends CommonRepository<Res> {

    @Resource
    private MongoTemplate mt;

    @Override
    public Res insert(Res m) {
        return mt.insert(m);
    }

    @Override
    public void delete(String id) {
        mt.remove(idQuery(id), Res.class);
    }

    @Override
    public void delete(String key, Object value) {
        mt.remove(kvQuery(key, value), Res.class);
    }

    @Override
    public void delete(String[] keys, Object[] values) {
        mt.remove(kvQuery(keys, values), Res.class);
    }

    @Override
    public void update(Res m) {
        mt.updateFirst(idQuery(m.getId()), mongoUpdate(m), Res.class);
    }

    @Override
    public Res findOne(String id) {
        return mt.findOne(idQuery(id), Res.class);
    }

    @Override
    public Res findOne(String key, Object value) {
        return mt.findOne(kvQuery(key, value), Res.class);
    }

    @Override
    public Res findOne(String[] keys, Object[] values) {
        return mt.findOne(kvQuery(keys, values), Res.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value), Res.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values), Res.class);
    }

    @Override
    public List<Res> findMany(String key, Object value) {
        return mt.find(kvQuery(key, value), Res.class);
    }

    @Override
    public List<Res> findMany(String[] keys, Object[] values) {
        return mt.find(kvQuery(keys, values), Res.class);
    }

    @Override
    public List<Res> find(PageQuery pageQuery) {
        return mt.find(pageQuery(pageQuery), Res.class);
    }

    @Override
    public long count(PageQuery pageQuery) {
        return mt.count(countQuery(pageQuery), Res.class);
    }


    public Integer queryMaxSort(String pid) {
        Query query = kvQuery("pid", pid);
        Sort.Direction dire = Sort.Direction.DESC;
        Sort sort = Sort.by(dire, "sort");
        query.with(sort);
        Res res = mt.findOne(query, Res.class);
        if (res == null) {
            return null;
        }
        return res.getSort();
    }

    public List<Res> querySub(String pid) {
        Query query = kvQuery("pid", pid);
        Sort.Direction dire = Sort.Direction.ASC;
        Sort sort = Sort.by(dire, "sort");
        query.with(sort);
        return mt.find(query, Res.class);
    }

    public List<Res> queryLevel(Integer level) {
        Query query = kvQuery("level", level - 1);
        Sort.Direction dire = Sort.Direction.ASC;
        Sort sort = Sort.by(dire, "sort");
        query.with(sort);
        return mt.find(query, Res.class);
    }

    public List<Res> queryPidLevel(String pid, Integer level) {
        Query query = kvQuery(new String[]{"level", "pid"}, new Object[]{level, pid});
        Sort.Direction dire = Sort.Direction.ASC;
        Sort sort = Sort.by(dire, "sort");
        query.with(sort);

        return mt.find(query, Res.class);
    }

}
