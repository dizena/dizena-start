package org.dizena.modules.master.zload;

import org.dizena.modules.master.bean.JobInfo;
import org.dizena.modules.master.service.JobInfoService;
import org.dizena.modules.master.service.KvConfigService;
import org.dizena.modules.master.service.JobTaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MasterInitRunner implements CommandLineRunner {

    @Resource
    private KvConfigService baseConfigService;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private JobTaskService taskService;

    @Override
    public void run(String... args) {
        // 基础配置
        baseConfigService.add(ConstantMaster.AUTH_All, "user:*,client:*,master:*,*", "角色权限选择");
        //任务调度
        List<JobInfo> list = jobInfoService.getNeedExecuteJob();
        list.forEach(m ->
        {
            taskService.startJob(m);
        });
        //end
    }

}
