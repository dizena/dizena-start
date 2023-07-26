package org.dizena.modules.guest.service;

import org.dizena.common.realm.JwtUtil;
import org.dizena.modules.guest.bean.dto.UpdateInfoDTO;
import org.dizena.modules.guest.bean.dto.UploadPicDTO;
import org.dizena.modules.guest.bean.vo.UserVO;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.bean.vo.MenuVO;
import org.dizena.modules.master.service.MenuService;
import org.dizena.modules.master.service.UserService;
import org.dizena.utils.FileUtil;
import org.dizena.utils.IdGen;
import org.dizena.utils.ImageZip;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class CenterService {
    @Resource
    private UserService service;
    @Resource
    private MenuService menuService;
    @Value("${config.upload.dir}")
    private String uploadDir;
    @Value("${config.oss.dir}")
    private String dir;
    @Value("${config.cdn.domain}")
    private String domain;

    public UpdateInfoDTO info() {
        User user = service.info();
        UpdateInfoDTO vo = new UpdateInfoDTO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    public UserVO who() {
        User user = service.info();
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        vo.setToken(JwtUtil.sign(user.getAccount(), user.getPasswd()));
        return vo;
    }

    public UserVO upFile(MultipartFile file) throws IOException {
        byte[] bs = file.getBytes();
        return modifyAvatar(bs);
    }

    public UserVO upFile(UploadPicDTO dto) throws IOException {
        byte[] bs = Base64.getDecoder().decode(dto.getBase64());
        return modifyAvatar(bs);
    }

    public String uploadFile(UploadPicDTO dto) {
        byte[] bs = Base64.getDecoder().decode(dto.getBase64());
        String name = uploadDir + "/" + IdGen.get().nextSid() + ".jpg";
        FileUtil.createFile(bs, name);
        return name;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        byte[] bs = file.getBytes();
        String name = uploadDir + "/" + IdGen.get().nextSid() + suffix;
        FileUtil.createFile(bs, name);
        return name;
    }

    private UserVO modifyAvatar(byte[] bs) throws IOException {
        User user = service.info();
        assert user != null;
        String pic = dir + "/pic/" + user.getId() + ".jpg";
        // 大于0.25MB
        if (bs.length > 1024 * 256) {
            ByteArrayInputStream bis = new ByteArrayInputStream(bs);
            bs = ImageZip.compressIcon(bis);
        }
        //todo 存储云服务
        UpdateInfoDTO m = new UpdateInfoDTO();
        m.setPic(domain + "/" + pic);
        return service.modifyInfo(m);
    }

    public UserVO modifyInfo(UpdateInfoDTO dto) {
        return service.modifyInfo(dto);
    }

    public String modifyPwd(String old, String p1, String p2) {
        return service.modifyPwd(old, p1, p2);
    }

    public List<MenuVO> menu() {
        return menuService.getUserMenu();
    }


}
