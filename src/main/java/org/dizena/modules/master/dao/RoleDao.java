package org.dizena.modules.master.dao;

import org.dizena.common.mongo.CommonRepository;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.Role;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class RoleDao extends CommonRepository<Role>
{

    @Resource
    private MongoTemplate mt;

    @Override
    public Role insert(Role m)
    {
        return mt.insert(m);
    }

    @Override
    public void delete(String id)
    {
        mt.remove(idQuery(id), Role.class);
    }

    @Override
    public void delete(String key, Object value)
    {
        mt.remove(kvQuery(key, value), Role.class);
    }

    @Override
    public void delete(String[] keys, Object[] values)
    {
        mt.remove(kvQuery(keys, values), Role.class);
    }

    @Override
    public void update(Role m)
    {
        Update update = mongoUpdate(m);
        update.set("resIds",m.getResIds());
        mt.updateFirst(idQuery(m.getId()), mongoUpdate(m), Role.class);
    }

    @Override
    public Role findOne(String id)
    {
        return mt.findOne(idQuery(id), Role.class);
    }

    @Override
    public Role findOne(String key, Object value)
    {
        return mt.findOne(kvQuery(key, value), Role.class);
    }

    @Override
    public Role findOne(String[] keys, Object[] values)
    {
        return mt.findOne(kvQuery(keys, values), Role.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value),Role.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values),Role.class);
    }

    @Override
    public List<Role> findMany(String key, Object value)
    {
        return mt.find(kvQuery(key, value), Role.class);
    }

    @Override
    public List<Role> findMany(String[] keys, Object[] values)
    {
        return mt.find(kvQuery(keys, values), Role.class);
    }

    @Override
    public List<Role> find(PageQuery pageQuery)
    {
        return mt.find(pageQuery(pageQuery), Role.class);
    }

    @Override
    public long count(PageQuery pageQuery)
    {
        return mt.count(countQuery(pageQuery), Role.class);
    }


    public Role getByRole(String role)
    {
        return mt.findOne(kvQuery("role", role), Role.class);
    }
}
