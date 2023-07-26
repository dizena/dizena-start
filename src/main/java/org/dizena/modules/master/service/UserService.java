package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.guest.bean.vo.UserVO;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.guest.bean.dto.UpdateInfoDTO;
import org.dizena.modules.master.bean.dto.UserDTO;

public interface UserService {

    PageResponse<User> list(PageRequest req);

    String add(UserDTO m);

    String add(User m);

    String edit(UserDTO dto);

    String del(String ids);

    User get(String id);

    String lockUser(String id);

    long countAll();

    User info();

    void init();

    //业务

    User login(String account);

    User wxOpen(String openId);

    UserVO modifyInfo(UpdateInfoDTO dto);

    String modifyPwd(String old, String p1, String p2);
}
