package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.vo.ResVO;
import org.dizena.modules.master.bean.vo.TreeVO;
import org.dizena.modules.master.bean.Res;

import java.util.List;

public interface ResService
{
    PageResponse<ResVO> list(PageRequest req);

    String add(Res m);

    String edit(Res m);

    String del(String ids);

    Res get(String id);

    List<Res> queryResSubs(String pid);

    List<Res> level(Integer level);

    List<TreeVO> getTree();

    List<TreeVO> getRoleTreeData(String pid, Integer level, List<String> resIds);

    void init();

    List<Res> getAll();
}
