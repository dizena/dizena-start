package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.BlackUser;
import org.dizena.modules.master.bean.vo.RelationVO;

import java.util.List;

public interface RelationService {

    PageResponse<RelationVO> list(PageRequest dto, String flag);

    List<BlackUser> listBlack();

    void attention(String toUid);

    void cancel(String flag, String id);


}
