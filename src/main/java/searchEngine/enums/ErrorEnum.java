package searchEngine.enums;

/**
 * @Description: 错误枚举类(未完成)
 **/
public enum ErrorEnum {
    ;

    /**
     * 错误状态码
     */
    private Integer errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    ErrorEnum(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
