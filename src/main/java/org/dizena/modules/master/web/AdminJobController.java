package org.dizena.modules.master.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.common.anno.CheckRoleAdvice;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.JobInfo;
import org.dizena.modules.master.service.JobInfoService;
import org.dizena.modules.master.service.JobTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "系统管理")
@ApiSort(200)
@CheckRoleAdvice(roles = {"master", "admin"}, type = CheckRoleAdvice.OR)
@RestController
@RequestMapping("/admin/master/job")
public class AdminJobController {
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private JobTaskService taskService;

    @ApiOperation(value = "任务新增")
    @ApiOperationSupport(order = 401)
    @PostMapping("/add")
    public void add(@Validated JobInfo m) {
        jobInfoService.add(m);
    }

    @ApiOperation(value = "任务删除")
    @ApiOperationSupport(order = 402)
    @GetMapping("/del/{id}")
    public void del(@PathVariable("id") String id) {
        jobInfoService.del(id);
    }

    @ApiOperation(value = "任务修改")
    @ApiOperationSupport(order = 403)
    @PostMapping("/edit")
    public void edit(@Validated JobInfo m) {
        jobInfoService.edit(m);
    }

    @ApiOperation(value = "任务枚举")
    @ApiOperationSupport(order = 404)
    @PostMapping("/list")
    public PageResponse<JobInfo> list(PageRequest req) {
        return jobInfoService.list(req);
    }

    @ApiOperation(value = "任务启动")
    @ApiOperationSupport(order = 405)
    @GetMapping("/start/{id}")
    public void start(@PathVariable("id") String id) {
        jobInfoService.start(id);
    }

    @ApiOperation(value = "立即执行")
    @ApiOperationSupport(order = 406)
    @GetMapping("/execute/{id}")
    public void execute(@PathVariable("id") String id) {
        taskService.executeNow(jobInfoService.get(id));
    }

}
