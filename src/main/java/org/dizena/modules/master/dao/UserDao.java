package org.dizena.modules.master.dao;

import org.dizena.common.mongo.PageQuery;
import org.dizena.common.mongo.CommonRepository;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.zload.ConstantMaster;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDao extends CommonRepository<User>
{

    @Resource
    private MongoTemplate mt;

    @Override
    public User insert(User m)
    {
        return mt.insert(m);
    }

    @Override
    public void delete(String id)
    {
        mt.remove(idQuery(id), User.class);
    }

    @Override
    public void delete(String key, Object value)
    {
        mt.remove(kvQuery(key, value), User.class);
    }

    @Override
    public void delete(String[] keys, Object[] values)
    {
        mt.remove(kvQuery(keys, values), User.class);
    }

    @Override
    public void update(User m)
    {
        mt.updateFirst(idQuery(m.getId()), mongoUpdate(m), User.class);
    }

    @Override
    public User findOne(String id)
    {
        return mt.findOne(idQuery(id), User.class);
    }

    @Override
    public User findOne(String key, Object value)
    {
        return mt.findOne(kvQuery(key, value), User.class);
    }

    @Override
    public User findOne(String[] keys, Object[] values)
    {
        return mt.findOne(kvQuery(keys, values), User.class);
    }

    @Override
    public long count(String key, Object value) {
        return mt.count(kvQuery(key, value), User.class);
    }

    @Override
    public long count(String[] keys, Object[] values) {
        return mt.count(kvQuery(keys, values), User.class);
    }

    @Override
    public List<User> findMany(String key, Object value)
    {
        return mt.find(kvQuery(key, value), User.class);
    }

    @Override
    public List<User> findMany(String[] keys, Object[] values)
    {
        return mt.find(kvQuery(keys, values), User.class);
    }

    @Override
    public List<User> find(PageQuery pageQuery)
    {
        return mt.find(pageQuery(pageQuery), User.class);
    }

    @Override
    public long count(PageQuery pageQuery)
    {
        return mt.count(countQuery(pageQuery), User.class);
    }


    public long count()
    {
        return mt.count(new Query(), User.class);
    }

    public List<User> findCancelUser()
    {
        long ts = System.currentTimeMillis() - 7 * 24 * 3600 * 1000;
        Query query = new Query(Criteria.where("locked").is(ConstantMaster.CLOSE).and("updateTime").lt(ts));
        return mt.find(query, User.class);
    }

    public List<User> findUserNoMobile()
    {
        Query query = new Query(Criteria.where("roles").is("user").and("mobile").exists(false));
        return mt.find(query, User.class);
    }

    //关于聚合计算的示例
    public void aggregate()
    {
        //构造$redact的条件
        Aggregation aggregation = Aggregation.newAggregation(
                //先筛选
                Aggregation.match(Criteria.where("math").gt(30).and("language").lt(100)),
                //在分组
                Aggregation.group("classes").sum("$math").as("mathCount").last("_id").as("id").last("name").as("name"),
                //排除字段
                Aggregation.project().andExclude("name", "gender"));
        //查询
        AggregationResults<User> results = mt.aggregate(aggregation, User.class, User.class);
        List<User> data = results.getMappedResults();
    }


}
