package org.dizena.modules.master.service;

import org.dizena.modules.master.bean.JobInfo;


public interface JobTaskService
{
    void executeNow(JobInfo job);

    void stopAllJob();

    void startJob(JobInfo job);

    void stopJob(JobInfo job);

}
