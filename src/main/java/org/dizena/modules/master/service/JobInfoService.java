package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.JobInfo;

import java.util.List;

public interface JobInfoService
{
    List<JobInfo> getNeedExecuteJob();

    void add(JobInfo m);

    void del(String id);

    void edit(JobInfo m);

    JobInfo get(String id);

    PageResponse<JobInfo> list(PageRequest req);

    void start(String id);

}
