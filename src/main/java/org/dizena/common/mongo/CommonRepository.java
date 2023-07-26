package org.dizena.common.mongo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.dizena.common.bean.Condition;
import org.dizena.utils.ObjUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public abstract class CommonRepository<T> implements CommonDao<T> {

    public Query idQuery(String id) {
        return new Query(Criteria.where("id").is(id));
    }

    public Query kvQuery(String key, Object val) {
        return new Query(Criteria.where(key).is(val));
    }

    public Query kvQuery(String[] keys, Object[] vales) {
        if (keys != null && keys.length > 0 && keys.length == vales.length) {
            Criteria c = Criteria.where(keys[0]).is(vales[0]);
            for (int i = 1; i < vales.length; i++) {
                c = c.and(keys[i]).is(vales[i]);
            }
            return new Query(c);
        }
        return new Query();
    }

    public Update mongoUpdate(Object obj) {
        Update update = new Update();
        Map<String, Object> map = ObjUtil.beanAttrToMapKV(obj);
        for (Entry<String, Object> e : map.entrySet()) {
            if ("id".equals(e.getKey())) {
                continue;
            }
            update.set(e.getKey(), e.getValue());
        }
        return update;
    }

    public Query pageQuery(PageQuery pageQuery) {
        Query query = countQuery(pageQuery);
        if (pageQuery != null) {
            if (pageQuery.getSkip() != 0) {
                query.skip(pageQuery.getSkip());
            }
            if (pageQuery.getLimit() != 0) {
                query.limit(pageQuery.getLimit());
            }
            if (StringUtils.isNotEmpty(pageQuery.getDirection()) && StringUtils.isNotEmpty(pageQuery.getSortField())) {
                Sort sort = Sort.by(Sort.Direction.fromString(pageQuery.getDirection()), pageQuery.getSortField()).and(Sort.by(Sort.Order.asc("id")));
                query.with(sort);
            }
        }
        return query;
    }

    public Query countQuery(PageQuery pageQuery) {
        if (pageQuery != null) {
            Criteria criteria = getCriteria(pageQuery);
            if (criteria != null) {
                return new Query(criteria);
            }
        }
        return new Query();
    }

    public Criteria getCriteria(PageQuery pageQuery) {
        Criteria criteria = getCriteria(pageQuery.getParam(), pageQuery.getSearchField());
        List<Condition> conditions = pageQuery.getConditions();
        if (conditions != null && conditions.size() > 0) {
            if(criteria == null){
                criteria = new Criteria();
            }
            Criteria cc =criteria;
            conditions.forEach(p -> {
                String k = p.getK();
                String t = p.getT();
                String v = p.getV();
                String c = p.getC();
                if (StringUtils.isNotEmpty(k) && StringUtils.isNotEmpty(v)) {
                    if ("str".equalsIgnoreCase(t)) {
                        if ("=".equalsIgnoreCase(c)) {
                            cc.and(k).is(v);
                        }
                        if ("!=".equalsIgnoreCase(c)) {
                            cc.and(k).ne(v);
                        }
                        if ("like".equalsIgnoreCase(c)) {
                            cc.and(k).regex(v, "i");
                        }
                        if ("start".equalsIgnoreCase(c)) {
                            cc.and(k).regex("^" + v);
                        }
                        if ("end".equalsIgnoreCase(c)) {
                            cc.and(k).regex(v + "$");
                        }
                    }
                    if ("int".equalsIgnoreCase(t)) {
                        Integer vi = Integer.parseInt(v);
                        if ("=".equalsIgnoreCase(c)) {
                            cc.and(k).is(vi);
                        }
                        if ("!=".equalsIgnoreCase(c)) {
                            cc.and(k).ne(vi);
                        }
                        if (">".equalsIgnoreCase(c)) {
                            cc.and(k).gt(vi);
                        }
                        if (">=".equalsIgnoreCase(c)) {
                            cc.and(k).gte(vi);
                        }
                        if ("<".equalsIgnoreCase(c)) {
                            cc.and(k).lt(vi);
                        }
                        if ("<=".equalsIgnoreCase(c)) {
                            cc.and(k).lte(vi);
                        }
                    }
                    if ("long".equalsIgnoreCase(t)) {
                        Long vi = Long.parseLong(v);
                        if ("=".equalsIgnoreCase(c)) {
                            cc.and(k).is(vi);
                        }
                        if ("!=".equalsIgnoreCase(c)) {
                            cc.and(k).ne(vi);
                        }
                        if (">".equalsIgnoreCase(c)) {
                            cc.and(k).gt(vi);
                        }
                        if (">=".equalsIgnoreCase(c)) {
                            cc.and(k).gte(vi);
                        }
                        if ("<".equalsIgnoreCase(c)) {
                            cc.and(k).lt(vi);
                        }
                        if ("<=".equalsIgnoreCase(c)) {
                            cc.and(k).lte(vi);
                        }
                    }
                    if ("float".equalsIgnoreCase(t)) {
                        Float vi = Float.parseFloat(v);
                        if ("=".equalsIgnoreCase(c)) {
                            cc.and(k).is(vi);
                        }
                        if ("!=".equalsIgnoreCase(c)) {
                            cc.and(k).ne(vi);
                        }
                        if (">".equalsIgnoreCase(c)) {
                            cc.and(k).gt(vi);
                        }
                        if (">=".equalsIgnoreCase(c)) {
                            cc.and(k).gte(vi);
                        }
                        if ("<".equalsIgnoreCase(c)) {
                            cc.and(k).lt(vi);
                        }
                        if ("<=".equalsIgnoreCase(c)) {
                            cc.and(k).lte(vi);
                        }
                    }
                    if ("double".equalsIgnoreCase(t)) {
                        Double vi = Double.parseDouble(v);
                        if ("=".equalsIgnoreCase(c)) {
                            cc.and(k).is(vi);
                        }
                        if ("!=".equalsIgnoreCase(c)) {
                            cc.and(k).ne(vi);
                        }
                        if (">".equalsIgnoreCase(c)) {
                            cc.and(k).gt(vi);
                        }
                        if (">=".equalsIgnoreCase(c)) {
                            cc.and(k).gte(vi);
                        }
                        if ("<".equalsIgnoreCase(c)) {
                            cc.and(k).lt(vi);
                        }
                        if ("<=".equalsIgnoreCase(c)) {
                            cc.and(k).lte(vi);
                        }
                    }
                    if ("bool".equalsIgnoreCase(t)) {
                        Boolean vi = Boolean.parseBoolean(v);
                        if ("=".equalsIgnoreCase(c)) {
                            cc.and(k).is(vi);
                        }
                        if ("!=".equalsIgnoreCase(c)) {
                            cc.and(k).ne(vi);
                        }
                    }
                    if ("list-str".equalsIgnoreCase(t)) {
                        String[] vs = v.split(",");
                        if (vs.length > 0) {
                            if ("in".equalsIgnoreCase(c)) {
                                cc.and(k).in(vs);
                            }
                            if ("out".equalsIgnoreCase(c)) {
                                cc.and(k).nin(vs);
                            }
                        }
                    }
                    if ("list-int".equalsIgnoreCase(t)) {
                        String[] vs = v.split(",");
                        if (vs.length > 0) {
                            List<Integer> list = Arrays.stream(vs)
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList());
                            if ("in".equalsIgnoreCase(c)) {
                                cc.and(k).in(list);
                            }
                            if ("out".equalsIgnoreCase(c)) {
                                cc.and(k).nin(list);
                            }
                        }
                    }
                    if ("list-long".equalsIgnoreCase(t)) {
                        String[] vs = v.split(",");
                        if (vs.length > 0) {
                            List<Long> list = Arrays.stream(vs)
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                            if ("in".equalsIgnoreCase(c)) {
                                cc.and(k).in(list);
                            }
                            if ("out".equalsIgnoreCase(c)) {
                                cc.and(k).nin(list);
                            }
                        }
                    }
                    if ("list-float".equalsIgnoreCase(t)) {
                        String[] vs = v.split(",");
                        if (vs.length > 0) {
                            List<Float> list = Arrays.stream(vs)
                                    .map(Float::parseFloat)
                                    .collect(Collectors.toList());
                            if ("in".equalsIgnoreCase(c)) {
                                cc.and(k).in(list);
                            }
                            if ("out".equalsIgnoreCase(c)) {
                                cc.and(k).nin(list);
                            }
                        }
                    }
                    if ("list-double".equalsIgnoreCase(t)) {
                        String[] vs = v.split(",");
                        if (vs.length > 0) {
                            List<Double> list = Arrays.stream(vs)
                                    .map(Double::parseDouble)
                                    .collect(Collectors.toList());
                            if ("in".equalsIgnoreCase(c)) {
                                cc.and(k).in(list);
                            }
                            if ("out".equalsIgnoreCase(c)) {
                                cc.and(k).nin(list);
                            }
                        }
                    }
                    if ("list-bool".equalsIgnoreCase(t)) {
                        String[] vs = v.split(",");
                        if (vs.length > 0) {
                            List<Boolean> list = Arrays.stream(vs)
                                    .map(Boolean::parseBoolean)
                                    .collect(Collectors.toList());
                            if ("in".equalsIgnoreCase(c)) {
                                cc.and(k).in(list);
                            }
                            if ("out".equalsIgnoreCase(c)) {
                                cc.and(k).nin(list);
                            }
                        }
                    }
                }
            });
        }
        return criteria;
    }

    public Criteria getCriteria(Map<String, Object> map, String searchField) {
        Criteria criteria = null;
        if (map != null && map.size() > 0) {
            Set<Map.Entry<String, Object>> sets = map.entrySet();
            int i = 0;
            for (Map.Entry<String, Object> entry : sets) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if (key.equals(searchField)) {
                    String seal = (String) val;
                    if (i == 0) {
                        criteria = Criteria.where(searchField).regex(seal);
                    } else {
                        criteria = criteria.and(searchField).regex(seal);
                    }
                } else {
                    // other
                    if (i == 0) {
                        criteria = Criteria.where(key).is(val);
                    } else {
                        criteria = criteria.and(key).is(val);
                    }
                }
                i++;
            }
        }
        return criteria;
    }

}
