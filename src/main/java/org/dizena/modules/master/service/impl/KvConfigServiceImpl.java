package org.dizena.modules.master.service.impl;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.common.enums.RedisKeyEnum;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.KvConfig;
import org.dizena.modules.master.dao.KvConfigDao;
import org.dizena.modules.master.service.KvConfigService;
import org.dizena.utils.IdGen;
import org.dizena.utils.RedisMultiUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KvConfigServiceImpl implements KvConfigService {

    @Resource
    private KvConfigDao dao;
    @Resource
    private RedisMultiUtil redisMultiUtil;

    @Override
    public String getConfigByKey(String key) {
        KvConfig c = redisMultiUtil.get(RedisKeyEnum.CONFIG_INFO.key(key));
        if (c == null) {
            KvConfig m = dao.findOne("key", key);
            if (m != null) {
                redisMultiUtil.set(RedisKeyEnum.CONFIG_INFO.key(key), m, 600);
                return m.getValue();
            } else {
                return null;
            }
        } else {
            return c.getValue();
        }
    }

    @Override
    public PageResponse<KvConfig> list(PageRequest req) {
        PageQuery byPageQuery = new PageQuery(req);
        long count = dao.count(byPageQuery);
        List<KvConfig> list = dao.find(byPageQuery);
        return new PageResponse<>(req.getPage(), req.getSize(), count, list);
    }

    @Override
    public void add(KvConfig m) {
        m.setId(IdGen.get().nextSid());
        m.setCreateTime(System.currentTimeMillis());
        dao.insert(m);
        //redis
        redisMultiUtil.set(RedisKeyEnum.CONFIG_INFO.key(m.getKey()), m, 300);
    }

    @Override
    public void del(String key) {
        dao.deleteByKey(key);
        redisMultiUtil.del(RedisKeyEnum.CONFIG_INFO.key(key));
    }

    @Override
    public void edit(KvConfig m) {
        KvConfig old = dao.findOne(m.getId());
        redisMultiUtil.del(RedisKeyEnum.CONFIG_INFO.key(old.getKey()));
        dao.update(m);
        redisMultiUtil.set(RedisKeyEnum.CONFIG_INFO.key(m.getKey()), m, 300);
    }

    @Override
    public void add(String key, String value, String description) {
        KvConfig m = dao.findOne("key", key);
        if (null == m) {
            m = new KvConfig();
            m.setId(IdGen.get().nextSid());
            m.setKey(key);
            m.setValue(value);
            m.setCreateTime(System.currentTimeMillis());
            m.setDescription(description);
            dao.insert(m);
        }
        //redis
        redisMultiUtil.set(RedisKeyEnum.CONFIG_INFO.key(m.getKey()), m, 300);
    }
}
