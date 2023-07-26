package org.dizena.modules.master.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("demoJob")
public class DemoJob
{
    public void withParam(String param)
    {
        log.info("执行有参示例任务：" + param);
    }

    public void noneParam()
    {
        log.info("执行无参示例任务");
    }

}
