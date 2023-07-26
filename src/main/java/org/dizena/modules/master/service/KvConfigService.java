package org.dizena.modules.master.service;

import org.dizena.common.bean.PageRequest;
import org.dizena.common.bean.PageResponse;
import org.dizena.modules.master.bean.KvConfig;

public interface KvConfigService
{

    String getConfigByKey(String key);

    PageResponse<KvConfig> list(PageRequest req);

    void add(KvConfig m);

    void del(String key);

    void edit(KvConfig m);

    void add(String key, String value, String description);

}
