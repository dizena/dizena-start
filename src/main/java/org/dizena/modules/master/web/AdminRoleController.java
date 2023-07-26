package org.dizena.modules.master.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.common.anno.CheckRoleAdvice;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.Role;
import org.dizena.modules.master.bean.vo.RoleVO;
import org.dizena.modules.master.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "系统管理")
@ApiSort(200)
@CheckRoleAdvice(roles = {"master", "admin"}, type = CheckRoleAdvice.OR)
@RestController
@RequestMapping("/admin/master/role")
public class AdminRoleController {
    @Resource
    private RoleService service;

    @ApiOperation(value = "角色新增")
    @ApiOperationSupport(order = 201)
    @PostMapping("/add")
    public void add(@Validated Role m) {
        service.add(m);
    }

    @ApiOperation(value = "角色删除")
    @ApiOperationSupport(order = 202)
    @GetMapping("/del/{id}")
    public void del(@PathVariable("id") String id) {
        service.del(id);
    }

    @ApiOperation(value = "角色修改")
    @ApiOperationSupport(order = 203)
    @PostMapping("/edit")
    public void edit(Role m) {
        service.edit(m);
    }

    @ApiOperation(value = "角色明细")
    @ApiOperationSupport(order = 204)
    @GetMapping("/get/{id}")
    public Role get(@PathVariable("id") String id) {
        return service.get(id);
    }

    @ApiOperation(value = "角色枚举")
    @ApiOperationSupport(order = 205)
    @PostMapping("/list")
    public PageResponse<RoleVO> list(PageRequest req) {
        return service.list(req);
    }

    @ApiOperation(value = "所有角色")
    @ApiOperationSupport(order = 206)
    @GetMapping("/all")
    public List<String> all() {
        return service.all();
    }

    @ApiOperation(value = "权限枚举")
    @ApiOperationSupport(order = 207)
    @GetMapping("/auth")
    public List<String> auth() {
        return service.auth();
    }
}
