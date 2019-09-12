package com.caine.platform.common.exception;

import com.caine.platform.common.constant.Constant;

/**
 * @Author: CaineZhu
 * @Description: 自定义异常
 * @Date: Created in 10:10 2019/9/4
 * @Modified By:
 */
public class CustomException extends Exception {
    private int level;

    private Exception exception;

    public CustomException error(Exception exception) {
        return this.common(Constant.EXCEPTION_LEVEL_1, exception);
    }

    public CustomException normal(Exception exception) {
        return this.common(Constant.EXCEPTION_LEVEL_2, exception);
    }

    public CustomException ignore(Exception exception) {
        return this.common(Constant.EXCEPTION_LEVEL_3, exception);
    }

    public CustomException common(int level, Exception exception) {
        this.level = level;
        this.exception = exception;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
