package org.dizena.modules.guest.service;

import com.google.code.kaptcha.Producer;
import org.dizena.common.enums.RedisKeyEnum;
import org.dizena.common.realm.JwtUtil;
import org.dizena.modules.guest.bean.dto.LoginAccountDTO;
import org.dizena.modules.guest.bean.dto.LoginEmailDTO;
import org.dizena.modules.guest.bean.dto.LoginMobileDTO;
import org.dizena.modules.guest.bean.vo.ImgCodeVO;
import org.dizena.modules.guest.bean.vo.UserVO;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.service.KvConfigService;
import org.dizena.modules.master.service.LogService;
import org.dizena.modules.master.service.UserService;
import org.dizena.modules.master.zload.ConstantMaster;
import org.dizena.utils.IdGen;
import org.dizena.utils.RedisMultiUtil;
import org.dizena.utils.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class AuthService {
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private KvConfigService kvConfigService;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Resource
    private RedisMultiUtil redisMultiUtil;

    public ImgCodeVO image() throws IOException {
        //生成文字验证码
        String text = captchaProducerMath.createText();
        //个位数字相加
        String s1 = text.substring(0, 1);
        String s2 = text.substring(1, 2);
        Integer code = Integer.parseInt(s1) + Integer.parseInt(s2);

        String uuid = IdGen.get().nextSid();
        redisMultiUtil.set(RedisKeyEnum.CODE_IMAGE.key(uuid), code + "", 60);
        BufferedImage image = captchaProducerMath.createImage(s1 + "+" + s2 + "=?");
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        ImgCodeVO vo = new ImgCodeVO();
        vo.setUuid(uuid);
        vo.setImage("data:image/gif;base64," + Base64Utils.encodeToString(os.toByteArray()));
        return vo;
    }


    public UserVO login(LoginAccountDTO dto) {
        String tmp = redisMultiUtil.get(RedisKeyEnum.CODE_IMAGE.key(dto.getUuid()));
        if (tmp == null) {
            throw new RuntimeException("验证码已经过期");
        }
        if (!tmp.equals(dto.getCode())) {
            throw new RuntimeException("验证码输入错误");
        }
        redisMultiUtil.del(RedisKeyEnum.CODE_IMAGE.key(dto.getUuid()));
        User user = userService.login(dto.getAccount());
        logService.addLoginLog(user.getId(), dto.getAccount());
        return loginHandle(user, dto.getPassword());
    }


    public UserVO login(LoginEmailDTO dto) {
        User user = userService.login(dto.getEmail());
        if (user == null) {
            String code = redisMultiUtil.get(RedisKeyEnum.CODE_EMAIL.key(dto.getEmail()));
            if (code == null) {
                throw new RuntimeException("验证码过期");
            }
            if (!dto.getPassword().equals(code)) {
                throw new RuntimeException("验证码错误");
            }

            user = new User();
            user.setAccount(dto.getEmail());
            user.setNickname(dto.getEmail().substring(0, dto.getEmail().indexOf("@")));
            user.setPlatform("email");
            user.setRoles("user");
            user.setAuths("none");
            user.setEmail(dto.getEmail());
            user.setPasswd(dto.getPassword());
            userService.add(user);
            user = userService.login(dto.getEmail());
        }
        logService.addLoginLog(user.getId(), dto.getEmail());
        return loginHandle(user, dto.getPassword());
    }


    public String checkMobile(LoginMobileDTO dto) {
        User user = userService.login(dto.getMobile());
        if (user == null) {
            String code = StrUtil.makeNumCode();
            redisMultiUtil.set(RedisKeyEnum.CODE_MOBILE.key(dto.getMobile()), code, 300);
            //todo send sms cdoe
            return "密码已经发送到您手机";
        }
        return "请输入密码";
    }


    public UserVO login(LoginMobileDTO dto) {
        User user = userService.login(dto.getMobile());
        if (user == null) {
            String code = redisMultiUtil.get(RedisKeyEnum.CODE_MOBILE.key(dto.getMobile()));
            if (code == null) {
                throw new RuntimeException("验证码过期");
            }
            if (!dto.getPassword().equals(code)) {
                throw new RuntimeException("验证码错误");
            }

            user = new User();
            user.setAccount(dto.getMobile());
            user.setNickname("#" + dto.getMobile().substring(7, 11));
            user.setPlatform("mobile");
            user.setRoles("user");
            user.setAuths("none");
            user.setMobile(dto.getMobile());
            user.setPasswd(dto.getPassword());
            userService.add(user);
            user = userService.login(dto.getMobile());
        }
        logService.addLoginLog(user.getId(), dto.getMobile());
        return loginHandle(user, dto.getPassword());
    }


    public UserVO findUserById(String id) {
        User user = userService.get(id);
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }


    public String getConfig(String key) {
        return kvConfigService.getConfigByKey(key);
    }

    private UserVO loginHandle(User user, String password) {
        if (user == null) {
            throw new RuntimeException("账户或者密码错误");
        }
        if (user.getLocked().equals(ConstantMaster.LOCKED)) {
            throw new RuntimeException("账户被锁定了");
        }
        if (user.getLocked().equals(ConstantMaster.CLOSE)) {
            throw new RuntimeException("账户被注销了");
        }

        String passwd = DigestUtils.md5DigestAsHex((user.getSalt() + password).getBytes(StandardCharsets.UTF_8));
        if (passwd.equals(user.getPasswd())) {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            vo.setToken(JwtUtil.sign(user.getAccount(), user.getPasswd()));
            return vo;
        }
        throw new RuntimeException("账户或者密码错误");
    }
}
