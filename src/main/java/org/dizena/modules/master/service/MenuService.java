package org.dizena.modules.master.service;

import org.dizena.modules.master.bean.vo.MenuVO;

import java.util.List;

public interface MenuService
{
    List<MenuVO> getUserMenu();

    void initMenu();
}
