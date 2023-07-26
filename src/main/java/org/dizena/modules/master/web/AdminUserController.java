package org.dizena.modules.master.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.common.anno.CheckAuthAdvice;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.bean.dto.UserDTO;
import org.dizena.modules.master.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "系统管理")
@ApiSort(200)
@CheckAuthAdvice(auth = "master:user:*")
@RestController
@RequestMapping("/admin/master/user")
public class AdminUserController {
    @Resource
    private UserService service;

    @ApiOperation(value = "用户新增")
    @ApiOperationSupport(order = 101)
    @PostMapping("/add")
    public void add(@Validated UserDTO vo) {
        service.add(vo);
    }

    @ApiOperation(value = "用户删除")
    @ApiOperationSupport(order = 102)
    @GetMapping("/del/{id}")
    public void del(@PathVariable("id") String id) {
        service.del(id);
    }

    @ApiOperation(value = "用户编辑")
    @ApiOperationSupport(order = 103)
    @PostMapping("/edit")
    public void edit(UserDTO dto) {
        service.edit(dto);
    }

    @ApiOperation(value = "用户明细")
    @ApiOperationSupport(order = 104)
    @GetMapping("/get/{id}")
    public User get(@PathVariable("id") String id) {
        return service.get(id);
    }

    @ApiOperation(value = "用户枚举")
    @ApiOperationSupport(order = 105)
    @PostMapping("/list")
    public PageResponse<User> list(@RequestBody PageRequest req) {
        return service.list(req);
    }

    @ApiOperation(value = "用户锁定")
    @ApiOperationSupport(order = 106)
    @GetMapping("/lock/{id}")
    public String lock(@PathVariable("id") String id) {
        return service.lockUser(id);
    }


}
