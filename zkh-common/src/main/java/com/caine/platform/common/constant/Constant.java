package com.caine.platform.common.constant;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 10:19 2019/9/4
 * @Modified By:
 */
public class Constant {

    /**
     * 1:严重异常
     */
    public static final int EXCEPTION_LEVEL_1 = 1;

    /**
     * 2:一般异常
     */
    public static final int EXCEPTION_LEVEL_2 = 2;

    /**
     * 3:可忽略异常
     */
    public static final int EXCEPTION_LEVEL_3 = 3;

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String TOKEN_PREFIX = "PLATFORM-TOKEN-{0}-";

    /**
     *  RESPONSE_SUCCESS 成功
     *  RESPONSE_FAILED 失败
     *  RESPONSE_ERROR 错误
     */
    public static final int RESPONSE_SUCCESS = 200;
    public static final int RESPONSE_FAILED = 400;
    public static final int RESPONSE_ERROR = 500;

    public static final String SHA256 = "SHA-256";

    /**
     * 请求延迟时间
     */
    public static final long REQ_LIMITS = -2L;

    /**
     * 项目redis key的前缀
     */
    public static final String REDIS_KEY_PREFIX = "platform-key-prefix-";

    /**
     * 请求随机数前缀
     */
    public static final String REQ_RANDOM_PREFIX = REDIS_KEY_PREFIX + "req-random-code-";

}
