package org.dizena.modules.guest.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.BlackUser;
import org.dizena.modules.master.bean.vo.RelationVO;
import org.dizena.modules.master.service.RelationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户信息中心")
@ApiSort(102)
@RestController
@RequestMapping("/center/relation")
public class CenterRelationController {
    @Resource
    private RelationService service;

    @ApiOperation(value = "枚举管理关注粉丝好友0-3")
    @ApiOperationSupport(order = 201)
    @PostMapping("/list/{flag}")
    public PageResponse<RelationVO> list(@RequestBody PageRequest dto, @PathVariable("flag") String flag) {
        return service.list(dto, flag);
    }

    @ApiOperation(value = "黑名单")
    @ApiOperationSupport(order = 202)
    @GetMapping("/list-black")
    public List<BlackUser> listBlack() {
        return service.listBlack();
    }

    @ApiOperation(value = "关注")
    @ApiOperationSupport(order = 203)
    @GetMapping("/attention/{toUid}")
    public void attention(@PathVariable("toUid") String toUid) {
        service.attention(toUid);
    }

    @ApiOperation(value = "取消关注粉丝好友")
    @ApiOperationSupport(order = 204)
    @GetMapping("/cancel/{flag}/{id}")
    public void cancel(@PathVariable("flag") String flag, @PathVariable("id") String id) {
        service.cancel(flag, id);
    }


}
