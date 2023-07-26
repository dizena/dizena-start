package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Document(collection = "t_master_job")
@Data
@NoArgsConstructor
public class JobInfo implements Serializable
{
    @Id
    private String id;
    /**
     * 标题
     */
    @NotEmpty
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 执行程序类
     */
    @NotEmpty
    private String beanName;
    /**
     * 执行程序方法
     */
    @NotEmpty
    private String methodName;
    /**
     * 参数
     */
    private String methodParam;
    /**
     * 执行时间
     */
    @NotEmpty
    private String cron;
    /**
     * 执行状态,0 不执行；1 开启执行；
     */
    private Integer status;

}
