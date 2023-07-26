package org.dizena.modules.master.service.impl;

import org.dizena.modules.master.bean.JobInfo;
import org.dizena.modules.master.service.JobTaskService;
import org.dizena.modules.master.yiled.TaskRegister;
import org.dizena.modules.master.yiled.TaskRunnable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JobTaskServiceImpl implements JobTaskService
{
    @Resource
    private TaskRegister taskRegister;

    /**
     * 立即执行
     */
    @Override
    public void executeNow(JobInfo job)
    {
        TaskRunnable task = new TaskRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParam());
        task.run();
    }

    /**
     * 停止所有任务
     */
    @Override
    public void stopAllJob()
    {
        taskRegister.destroy();
    }

    /**
     * 开启一个任务
     */
    @Override
    public void startJob(JobInfo job)
    {
        taskRegister.addJob(job);
    }

    /**
     * 停止一个任务
     */
    @Override
    public void stopJob(JobInfo job)
    {
        taskRegister.delJob(job);
    }
}
