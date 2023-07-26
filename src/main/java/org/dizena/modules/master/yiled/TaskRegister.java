package org.dizena.modules.master.yiled;

import org.dizena.modules.master.bean.JobInfo;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskRegister implements DisposableBean
{
    private final Map<Runnable, TaskScheduled> scheduledTasks = new ConcurrentHashMap<>(16);
    @Resource
    private TaskScheduler taskScheduler;

    public void addJob(JobInfo job)
    {
        TaskRunnable task = new TaskRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParam());
        this.addCronTask(task, job.getCron());
    }

    public void delJob(JobInfo job)
    {
        TaskRunnable task = new TaskRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParam());
        this.removeCronTask(task);
    }

    private void addCronTask(Runnable task, String cronExpression)
    {
        addCronTask(new CronTask(task, cronExpression));
    }

    private void addCronTask(CronTask cronTask)
    {
        if (cronTask != null)
        {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task))
            {
                removeCronTask(task);
            }

            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
        }
    }

    private void removeCronTask(Runnable task)
    {
        TaskScheduled scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask != null)
        {
            scheduledTask.cancel();
        }
    }

    private TaskScheduled scheduleCronTask(CronTask cronTask)
    {
        TaskScheduled scheduledTask = new TaskScheduled();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    /**
     * 取消所有job
     */
    @Override
    public void destroy()
    {
        for (TaskScheduled task : this.scheduledTasks.values())
        {
            task.cancel();
        }
        this.scheduledTasks.clear();
    }

}
