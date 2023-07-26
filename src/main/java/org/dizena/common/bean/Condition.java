package org.dizena.common.bean;

import lombok.Data;

@Data
public class Condition {
    private String k;
    //str,int,long,float,double,bool,list-int
    private String t = "str";
    private String v;
    //=,!=,>,>=,<=,<,in,out,like,start,end
    private String c = "=";
}
