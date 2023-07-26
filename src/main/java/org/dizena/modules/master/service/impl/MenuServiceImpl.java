package org.dizena.modules.master.service.impl;

import org.dizena.modules.master.bean.vo.MenuVO;
import org.dizena.modules.master.bean.Res;
import org.dizena.modules.master.bean.User;
import org.dizena.modules.master.service.MenuService;
import org.dizena.modules.master.service.ResService;
import org.dizena.modules.master.service.RoleService;
import org.dizena.modules.master.service.UserService;
import org.dizena.utils.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService
{

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private ResService resService;

    @Override
    public List<MenuVO> getUserMenu()
    {
        // 1,获得用户的角色
        User loginUser = userService.info();
        assert loginUser != null;
        List<String> roles = StrUtil.str2List(loginUser.getRoles(), ",");
        // 2,根据角色得到角色的资源
        Set<Res> allRes = new HashSet<>();
        // master所有菜单
        if (roles.contains("master"))
        {
            allRes.addAll(resService.getAll());
        } else
        {
            for (String role : roles)
            {
                allRes.addAll(roleService.queryRoleRes(role));
            }
        }
        // 3,分类按照级别排序
        List<Res> nowRes = new ArrayList<>(allRes);
        Collections.sort(nowRes);
        // 4,构建菜单数据结构
        return getMenus(nowRes, 1, "0");
    }

    @Override
    public void initMenu()
    {
        long cnt = userService.countAll();
        if (cnt == 0)
        {
            userService.init();
            resService.init();
        }
    }


    private List<MenuVO> getMenus(List<Res> nowRes, Integer level, String pid)
    {
        List<MenuVO> menus = new ArrayList<>();
        for (Res res : nowRes)
        {
            if (level.equals(res.getLevel()) && pid.equals(res.getPid()))
            {
                MenuVO m = new MenuVO(res.getId(),res.getTitle(), res.getIcon(), res.getUri());
                List<MenuVO> subs = getMenus(nowRes, res.getLevel() + 1, res.getId());
                if (subs.size() > 0)
                {
                    m.setSubs(subs);
                }
                menus.add(m);
            }
        }
        return menus;
    }
}
