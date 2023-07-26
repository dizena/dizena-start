package org.dizena.common.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageRequest implements Serializable {

    private int page = 1;
    private int size = 10;
    private String sort;
    private String dire;

    private String searchField;
    private String searchContent;
    private List<Condition> conditions;

    public long getSkip() {
        if (this.getPage() < 1) {
            this.page = 1;
        }
        return (long) (this.getPage() - 1) * this.getSize();
    }

    public int getLimit() {
        return this.getSize();
    }


}
