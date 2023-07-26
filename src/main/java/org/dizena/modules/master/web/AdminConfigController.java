package org.dizena.modules.master.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.KvConfig;
import org.dizena.modules.master.service.KvConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "系统管理")
@ApiSort(200)
@RestController
@RequestMapping("/admin/master/config")
public class AdminConfigController {
    @Resource
    private KvConfigService service;

    @ApiOperation(value = "配置新增")
    @ApiOperationSupport(order = 501)
    @PostMapping("/add")
    public void add(KvConfig m) {
        service.add(m);
    }

    @ApiOperation(value = "配置修改")
    @ApiOperationSupport(order = 502)
    @PostMapping("/edit")
    public void edit(KvConfig m) {
        service.edit(m);
    }

    @ApiOperation(value = "配置删除")
    @ApiOperationSupport(order = 503)
    @GetMapping("/del/{id}")
    public void del(@PathVariable("id") String id) {
        service.del(id);
    }

    @ApiOperation(value = "配置枚举")
    @ApiOperationSupport(order = 504)
    @PostMapping("/list")
    public PageResponse<KvConfig> list(PageRequest req) {
        return service.list(req);
    }

}
