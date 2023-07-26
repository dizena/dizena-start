package org.dizena.modules.master.yiled;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dizena.utils.SpringContextUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class TaskRunnable implements Runnable
{
    /**
     * 执行程序类
     */
    private final String beanName;
    /**
     * 执行程序方法
     */
    private final String methodName;
    /**
     * 参数
     */
    private final String methodParam;

    public TaskRunnable(String beanName, String methodName)
    {
        this(beanName, methodName, null);
    }

    public TaskRunnable(String beanName, String methodName, String methodParam)
    {
        this.beanName = beanName;
        this.methodName = methodName;
        this.methodParam = methodParam;
    }

    @Override
    public void run()
    {
        long startTime = System.currentTimeMillis();
        try
        {
            Object target = SpringContextUtils.getBean(beanName);

            Method method;
            if (StringUtils.isNotEmpty(methodParam))
            {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            } else
            {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(methodParam))
            {
                method.invoke(target, methodParam);
            } else
            {
                method.invoke(target);
            }
        } catch (Exception ex)
        {
            log.error(String.format("定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, methodParam), ex);
        }

        long times = System.currentTimeMillis() - startTime;
        log.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, methodParam, times);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskRunnable that = (TaskRunnable) o;
        if (methodParam == null)
        {
            return beanName.equals(that.beanName) && methodName.equals(that.methodName) && that.methodParam == null;
        }

        return beanName.equals(that.beanName) && methodName.equals(that.methodName) && methodParam.equals(that.methodParam);
    }

    @Override
    public int hashCode()
    {
        if (methodParam == null)
        {
            return Objects.hash(beanName, methodName);
        }

        return Objects.hash(beanName, methodName, methodParam);
    }
}
