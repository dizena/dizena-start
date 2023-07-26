package org.dizena.modules.master.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.common.anno.CheckRoleAdvice;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.Res;
import org.dizena.modules.master.bean.vo.ResVO;
import org.dizena.modules.master.bean.vo.TreeVO;
import org.dizena.modules.master.service.ResService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "系统管理")
@ApiSort(200)
@CheckRoleAdvice(roles = {"master", "admin"}, type = CheckRoleAdvice.OR)
@RestController
@RequestMapping("/admin/master/res")
public class AdminResController {

    @Resource
    private ResService service;

    @ApiOperation(value = "资源新增")
    @ApiOperationSupport(order = 301)
    @PostMapping("/add")
    public void add(@Validated Res m) {
        service.add(m);
    }

    @ApiOperation(value = "资源删除")
    @ApiOperationSupport(order = 302)
    @GetMapping("/del/{id}")
    public void del(@PathVariable("id") String id) {
        service.del(id);
    }

    @ApiOperation(value = "资源修改")
    @ApiOperationSupport(order = 303)
    @PostMapping("/edit")
    public void edit(Res m) {
        service.edit(m);
    }

    @ApiOperation(value = "资源明细")
    @ApiOperationSupport(order = 304)
    @GetMapping("/get/{id}")
    public Res get(@PathVariable("id") String id) {
        return service.get(id);
    }

    @ApiOperation(value = "资源枚举")
    @ApiOperationSupport(order = 305)
    @PostMapping("/list")
    public PageResponse<ResVO> list(PageRequest req) {
        return service.list(req);
    }

    @ApiOperation(value = "分级资源")
    @ApiOperationSupport(order = 306)
    @GetMapping("/level/{v}")
    public List<Res> level(@PathVariable("v") Integer level) {
        return service.level(level);
    }

    @ApiOperation(value = "资源展示")
    @ApiOperationSupport(order = 307)
    @GetMapping("/view")
    public List<TreeVO> view() {
        return service.getTree();
    }


}
