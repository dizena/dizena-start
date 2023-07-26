package org.dizena.common.bean;

import lombok.Data;
import org.dizena.common.enums.ResultCode;

import java.io.Serializable;

@Data
public class ResultVO implements Serializable {
    private int code;

    private String msg;

    private Object content;

    public ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(ResultCode rc) {
        this.code = rc.code();
        this.msg = rc.msg();
    }

    public static ResultVO success() {
        return new ResultVO(ResultCode.SUCCESS);
    }

    public static ResultVO success(Object content) {
        ResultVO vo = new ResultVO(ResultCode.SUCCESS);
        vo.setContent(content);
        return vo;
    }

    public static ResultVO fail(int code, String msg) {
        return new ResultVO(code, msg);
    }

    public static ResultVO fail(ResultCode rc) {
        return new ResultVO(rc);
    }

    public static ResultVO fail(ResultCode rc, String msg) {
        ResultVO vo = new ResultVO(rc);
        vo.setMsg(msg);
        return vo;
    }

}
