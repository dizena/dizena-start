package org.dizena.modules.master.service.impl;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.master.bean.LogLogin;
import org.dizena.modules.master.dao.LogLoginDao;
import org.dizena.modules.master.service.LogService;
import org.dizena.utils.IdGen;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogLoginDao logLoginDao;

    @Override
    public void addLoginLog(String uid, String account) {
        LogLogin s = new LogLogin();
        s.setId(IdGen.get().nextSid());
        s.setUid(uid);
        s.setUseAccount(account);
        s.setCreateTime(System.currentTimeMillis());
        logLoginDao.insert(s);
    }

    @Override
    public PageResponse<LogLogin> list(PageRequest dto) {
        PageQuery query = new PageQuery(dto);
        List<LogLogin> list = logLoginDao.find(query);
        long count = logLoginDao.count(query);
        return new PageResponse<>(dto, count, list);
    }
}
