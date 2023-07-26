package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.Res;
import org.dizena.modules.master.bean.Role;
import org.dizena.modules.master.bean.vo.RoleVO;

import java.util.Collection;
import java.util.List;

public interface RoleService
{

    PageResponse<RoleVO> list(PageRequest req);

    String add(Role m);

    String edit(Role m);

    String del(String ids);

    Role get(String id);

    Collection<Res> queryRoleRes(String role);

    List<String> all();

    List<String> auth();
}
