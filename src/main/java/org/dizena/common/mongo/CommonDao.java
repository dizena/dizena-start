package org.dizena.common.mongo;

import java.util.List;

public interface CommonDao<T> {

    T insert(T m);

    void delete(String id);

    void delete(String k, Object v);

    void delete(String[] k, Object[] v);

    void update(T m);

    T findOne(String id);

    T findOne(String k, Object v);

    T findOne(String[] k, Object[] v);

    long count(String k, Object v);

    long count(String[] k, Object[] v);

    long count(PageQuery pq);

    List<T> findMany(String k, Object v);

    List<T> findMany(String[] k, Object[] v);

    List<T> find(PageQuery pq);

}