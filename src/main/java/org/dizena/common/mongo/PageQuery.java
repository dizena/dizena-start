package org.dizena.common.mongo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dizena.common.bean.Condition;
import org.dizena.common.bean.PageRequest;
import org.dizena.utils.ObjUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PageQuery implements Serializable {
    private long skip;
    private int limit;
    private String sortField;
    private String direction;

    private String searchField;
    private String searchContent;
    private List<Condition> conditions;

    private Map<String, Object> param;

    public PageQuery(PageRequest req) {
        this.skip = req.getSkip();
        this.limit = req.getLimit();
        this.sortField = req.getSort();
        this.direction = req.getDire();

        this.searchField = req.getSearchField();
        this.searchContent = req.getSearchContent();
        this.conditions = req.getConditions();
        this.param = new HashMap<>();
        if (StringUtils.isNotEmpty(searchField) && StringUtils.isNotEmpty(searchContent)) {
            put(searchField, searchContent);
        }
    }

    public void put(String key, Object value) {
        this.param.put(key, value);
    }

    public void put(Object objFilter) {
        this.param.putAll(ObjUtil.beanAttrToMapKV(objFilter));
    }

    public void put(Map<String, Object> mapFilter) {
        this.param.putAll(mapFilter);
    }

}
