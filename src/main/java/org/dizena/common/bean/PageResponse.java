package org.dizena.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> implements Serializable {
    /**
     * 数据内容
     */
    private List<T> list;
    /**
     * 页数
     */
    private int pages;
    /**
     * 总数
     */
    private long count;
    /**
     * 页码
     */
    private transient int page;
    /**
     * 页大小
     */
    private transient int size;

    public PageResponse(int page, int size, long count, List<T> list) {
        this.page = page;
        this.size = size;
        this.count = count;
        this.list = list;
    }

    public PageResponse(PageRequest req, long count, List<T> list) {
        this.page = req.getPage();
        this.size = req.getSize();
        this.count = count;
        this.list = list;
    }

    public int getPages() {
        int pages = (int) (this.count / this.size);
        if (this.count % this.size != 0) {
            pages++;
        }
        return pages;
    }
}
