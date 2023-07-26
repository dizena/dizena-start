package org.dizena.modules.guest.web;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dizena.modules.guest.bean.dto.UpdateInfoDTO;
import org.dizena.modules.guest.bean.dto.UploadPicDTO;
import org.dizena.modules.guest.bean.vo.UserVO;
import org.dizena.modules.guest.service.CenterService;
import org.dizena.modules.master.bean.vo.MenuVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Api(tags = "用户信息中心")
@ApiSort(102)
@RestController
@RequestMapping("/center/first")
public class CenterFirstController {

    @Resource
    private CenterService centerService;

    @ApiOperation(value = "当前登录用户明细")
    @ApiOperationSupport(order = 101)
    @GetMapping("/info")
    public UpdateInfoDTO info() {
        return centerService.info();
    }

    @ApiOperation(value = "当前登录用户信息")
    @ApiOperationSupport(order = 102)
    @GetMapping("/who")
    public UserVO who() {
        return centerService.who();
    }

    @ApiOperation(value = "客户端修改头像")
    @ApiOperationSupport(order = 104)
    @PostMapping("/up-file")
    public UserVO upFile(@RequestParam("file") MultipartFile file) throws IOException {
        return centerService.upFile(file);
    }

    @ApiOperation(value = "客户端上传头像")
    @ApiOperationSupport(order = 105)
    @PostMapping("/up-pic")
    public UserVO upPic(@RequestBody UploadPicDTO dto) throws IOException {
        return centerService.upFile(dto);
    }

    @ApiOperation(value = "客户端上传文件")
    @ApiOperationSupport(order = 106)
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return centerService.uploadFile(file);
    }

    @ApiOperation(value = "客户端上传图片")
    @ApiOperationSupport(order = 107)
    @PostMapping("/upload-pic")
    public String uploadPic(@RequestBody UploadPicDTO dto) throws IOException {
        return centerService.uploadFile(dto);
    }

    @ApiOperation(value = "客户端修改信息")
    @ApiOperationSupport(order = 108)
    @PostMapping("/modify-info")
    public UserVO modifyInfo(@RequestBody UpdateInfoDTO dto) {
        return centerService.modifyInfo(dto);
    }

    @ApiOperation(value = "客户端修改密码")
    @ApiOperationSupport(order = 109)
    @PostMapping("/modify-pwd")
    public String modifyPwd(String old, String p1, String p2) {
        return centerService.modifyPwd(old, p1, p2);
    }

    @ApiOperation(value = "管理端用户菜单")
    @ApiOperationSupport(order = 201)
    @GetMapping("/menu")
    public List<MenuVO> menu() {
        return centerService.menu();
    }

}
