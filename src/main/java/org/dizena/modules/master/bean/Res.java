package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@Document(collection = "t_master_res")
@NoArgsConstructor
@Data
public class Res implements Serializable, Comparable<Res>
{
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String uri;
    private Integer sort;
    private Integer level;
    @NotEmpty
    private String pid;
    private String icon;

    public Res(String id, String title, String uri, Integer sort, Integer level, String pid, String icon)
    {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.sort = sort;
        this.level = level;
        this.pid = pid;
        this.icon = icon;
    }

    @Override
    public int compareTo(Res o)
    {
        if (this.getLevel() < o.getLevel())
        {
            return -1;
        }
        else if (Objects.equals(this.getLevel(), o.getLevel()))
        {
            if (this.getSort() < o.getSort())
            {
                return -1;
            }
            else if (Objects.equals(this.getSort(), o.getSort()))
            {
                return 0;
            }
            else if (this.getSort() > o.getSort())
            {
                return 1;
            }
        }
        else if (this.getLevel() > o.getLevel())
        {
            return 1;
        }
        return 0;
    }

}
