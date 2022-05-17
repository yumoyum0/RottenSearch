package searchEngine.exception;

import searchEngine.enums.ErrorEnum;

/**
 * @author yumo
 */
public class LocalRunTimeException extends RuntimeException {

    private ErrorEnum errorEnum;

    //默认错误
    public LocalRunTimeException(String message) {
        super(message);
    }

    public LocalRunTimeException(ErrorEnum errorEnum) {
        super(errorEnum.getErrMsg());
        this.errorEnum = errorEnum;
    }
}
