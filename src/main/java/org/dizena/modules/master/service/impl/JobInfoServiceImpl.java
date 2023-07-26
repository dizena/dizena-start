package org.dizena.modules.master.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dizena.common.mongo.PageQuery;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.JobInfo;
import org.dizena.modules.master.dao.JobInfoDao;
import org.dizena.modules.master.service.JobInfoService;
import org.dizena.modules.master.service.JobTaskService;
import org.dizena.utils.IdGen;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobInfoServiceImpl implements JobInfoService
{
    @Resource
    private JobInfoDao dao;
    @Resource
    private JobTaskService taskService;

    @Override
    public List<JobInfo> getNeedExecuteJob()
    {
        return dao.findMany("status", 1);
    }

    //增加
    @Override
    public void add(JobInfo m)
    {
        m.setStatus(0);
        if (StringUtils.isEmpty(m.getId()))
        {
            m.setId(IdGen.get().nextSid());
        }
        dao.insert(m);
    }

    //删除
    @Override
    public void del(String id)
    {
        JobInfo m = dao.findOne(id);
        taskService.stopJob(m);
        dao.delete(id);
    }

    //修改
    @Override
    public void edit(JobInfo m)
    {
        JobInfo job = dao.findOne(m.getId());
        taskService.stopJob(job);
        dao.update(m);
    }

    //查询
    @Override
    public JobInfo get(String id)
    {
        return dao.findOne(id);
    }

    //列表查询
    @Override
    public PageResponse<JobInfo> list(PageRequest req)
    {
        PageQuery byPageQuery = new PageQuery(req);
        List<JobInfo> list = dao.find(byPageQuery);
        long count = dao.count(byPageQuery);
        return new PageResponse<>(req.getPage(), req.getSize(), count, list);
    }

    @Override
    public void start(String id)
    {
        JobInfo m = get(id);
        JobInfo tmp = new JobInfo();
        tmp.setId(id);
        if (m.getStatus().equals(0))
        {
            tmp.setStatus(1);
            taskService.startJob(m);
        } else
        {
            tmp.setStatus(0);
            taskService.stopJob(m);
        }
        dao.update(tmp);
    }
}
