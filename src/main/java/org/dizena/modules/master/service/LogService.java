package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.LogLogin;

public interface LogService {

    void addLoginLog(String uid,String account);

    PageResponse<LogLogin> list(PageRequest dto);
}
