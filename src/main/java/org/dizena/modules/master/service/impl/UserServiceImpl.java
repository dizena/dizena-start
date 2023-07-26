package org.dizena.modules.master.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.common.mongo.PageQuery;
import org.dizena.modules.guest.bean.vo.UserVO;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.guest.bean.dto.UpdateInfoDTO;
import org.dizena.modules.master.bean.dto.UserDTO;
import org.dizena.modules.master.dao.UserDao;
import org.dizena.modules.master.service.UserService;
import org.dizena.modules.master.zload.ConstantMaster;
import org.dizena.utils.IdGen;
import org.dizena.utils.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;

    @Override
    public PageResponse<User> list(PageRequest req) {
        PageQuery byPageQuery = new PageQuery(req);
        List<User> list = dao.find(byPageQuery);
        long count = dao.count(byPageQuery);
        return new PageResponse<>(req.getPage(), req.getSize(), count, list);
    }

    @Override
    public String add(UserDTO vo) {
        User user = new User();
        user.setPlatform("mgr");
        BeanUtils.copyProperties(vo, user);
        return add(user);
    }


    @Override
    public String add(User user) {
        user.setId(IdGen.get().nextSid());
        User old = login(user.getAccount());
        if (old != null) {
            return "account exist";
        }
        //默认密码 12345678
        String salt = StrUtil.makeNumCode();
        String p1 = StringUtils.isEmpty(user.getPasswd()) ? "12345678" : user.getPasswd();
        String passwd = org.springframework.util.DigestUtils.md5DigestAsHex((salt + p1).getBytes(StandardCharsets.UTF_8));

        user.setPasswd(passwd);
        user.setSalt(salt);
        user.setLocked(ConstantMaster.NORMAL);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        dao.insert(user);
        return "ok";
    }

    @Override
    public String edit(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setSalt(null);
        user.setPasswd(null);
        user.setCreateTime(null);
        user.setUpdateTime(System.currentTimeMillis());
        dao.update(user);
        return "ok";
    }

    @Override
    public String del(String ids) {
        String[] ss = ids.split(",");
        for (String id : ss) {
            dao.delete(id);
        }
        return "ok";
    }

    @Override
    public User get(String id) {
        return dao.findOne(id);
    }

    @Override
    public String lockUser(String id) {
        String b = "locked";
        User user = dao.findOne(id);
        User upUser = new User();
        upUser.setId(id);
        if (ConstantMaster.LOCKED.equals(user.getLocked())) {
            upUser.setLocked(ConstantMaster.NORMAL);
            b = "normal";
        } else {
            upUser.setLocked(ConstantMaster.LOCKED);
        }
        upUser.setUpdateTime(System.currentTimeMillis());
        dao.update(upUser);
        return b;
    }

    @Override
    public long countAll() {
        return dao.count();
    }

    @Override
    public User info() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Object principal = subject.getPrincipal();
            if (principal != null) {
                String account = principal.toString();
                return dao.findOne("account", account);
            }
        }
        return null;
    }

    @Override
    public void init() {
        Long ts = System.currentTimeMillis();
        User admin = new User("1", "admin", "管理员", "master", "*", ConstantMaster.NORMAL, ts);
        String password = "admin";
        admin.setSalt(StrUtil.makeNumCode());
        String passwd = DigestUtils.md5DigestAsHex((admin.getSalt() + password).getBytes(StandardCharsets.UTF_8));
        admin.setPasswd(passwd);
        admin.setPlatform("xxx");
        admin.setEmail("admin@xxxx.com");
        dao.insert(admin);
    }


    @Override
    public User login(String account) {
        return dao.findOne("account", account);
    }

    @Override
    public User wxOpen(String openId) {
        return dao.findOne("openIdWx", openId);
    }

    @Override
    public UserVO modifyInfo(UpdateInfoDTO dto) {
        String id = info().getId();
        User tmp = new User();
        BeanUtils.copyProperties(dto, tmp);
        tmp.setId(id);
        dao.update(tmp);
        //重新查询
        User newUser = dao.findOne(id);
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(newUser, vo);
        return vo;
    }

    @Override
    public String modifyPwd(String old, String p1, String p2) {
        String id = info().getId();
        return updatePassword(id, old, p1, p2);
    }

    private String updatePassword(String id, String oldPassword, String newPassword, String newPassword2) {
        User user = get(id);
        if (user != null) {
            String passwd = org.springframework.util.DigestUtils.md5DigestAsHex((user.getSalt() + oldPassword).getBytes(StandardCharsets.UTF_8));
            if (passwd.equals(user.getPasswd())) {
                if (newPassword.equals(newPassword2)) {
                    User upUser = new User();
                    upUser.setId(id);
                    String salt = StrUtil.makeNumCode();
                    String pwd = org.springframework.util.DigestUtils.md5DigestAsHex((salt + newPassword).getBytes(StandardCharsets.UTF_8));
                    upUser.setSalt(salt);
                    upUser.setPasswd(pwd);
                    upUser.setUpdateTime(System.currentTimeMillis());
                    dao.update(upUser);
                    return "ok";
                }
                return "两次密码输入不一致";
            }
            return "旧密码错误";
        }
        return "用户不存在";

    }

}
