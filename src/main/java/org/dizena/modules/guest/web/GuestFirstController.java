package org.dizena.modules.guest.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.modules.guest.bean.dto.LoginAccountDTO;
import org.dizena.modules.guest.bean.dto.LoginEmailDTO;
import org.dizena.modules.guest.bean.dto.LoginMobileDTO;
import org.dizena.modules.guest.bean.vo.ImgCodeVO;
import org.dizena.modules.guest.bean.vo.UserVO;
import org.dizena.modules.guest.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@Api(tags = "游客注册登录")
@ApiSort(101)
@RestController
@RequestMapping("/guest/first")
public class GuestFirstController {
    @Resource
    private AuthService authService;

    @ApiOperation(value = "无权限")
    @ApiOperationSupport(order = 100)
    @GetMapping("/none")
    public String none() {
        return "none auth";
    }

    @ApiOperation(value = "获取图片验证码")
    @ApiOperationSupport(order = 101)
    @GetMapping("/image")
    public ImgCodeVO image() throws IOException {
        return authService.image();
    }

    @ApiOperation(value = "账户密码登录")
    @ApiOperationSupport(order = 102)
    @PostMapping("/login")
    public UserVO login(@RequestBody LoginAccountDTO dto) {
        return authService.login(dto);
    }

    @ApiOperation(value = "邮箱密码登录")
    @ApiOperationSupport(order = 104)
    @PostMapping("/login-email")
    public UserVO loginByEmail(@RequestBody LoginEmailDTO dto) {
        return authService.login(dto);
    }

    @ApiOperation(value = "检验手机号是否可用")
    @ApiOperationSupport(order = 105)
    @PostMapping("/check-mobile")
    public String checkMobile(@RequestBody LoginMobileDTO dto) {
        return authService.checkMobile(dto);
    }

    @ApiOperation(value = "手机号密码登录")
    @ApiOperationSupport(order = 106)
    @PostMapping("/login-mobile")
    public UserVO loginSms(@RequestBody LoginMobileDTO dto) {
        return authService.login(dto);
    }

    @ApiOperation(value = "查看用户")
    @ApiOperationSupport(order = 108)
    @GetMapping("/find/{id}")
    public UserVO findUserById(@PathVariable("id") String id) {
        return authService.findUserById(id);
    }

    @ApiOperation(value = "配置拉取")
    @ApiOperationSupport(order = 109)
    @GetMapping("/get-config/{key}")
    public String getConfig(@PathVariable("key") String key) {
        return authService.getConfig(key);
    }

}
