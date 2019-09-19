package com.capinfo.core.utils;

public class Constants {
    public static final String DATE_PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";
    public static final String HASH_ALGORITHM = "MD5";
    public static final int HASH_INTERATIONS = 4;
    public static final int SALT_SIZE = 8;
    // 短信验证码 secret key
    public static final String VERIFICATIONCODE_SECRET = "CAPINFO_SMS_SECRET";
    // 短信验证码过期时间
    public static final Integer VERIFICATIONCODE_EXPIRED = 5;

    // App列表页pageNum、pageSize
    public static final Integer DEFAULT_PAGE_NUM = 1;
    public static final Integer LIST_PAGE_SIZE = 20;

    // 消息提醒
    public static final String OBJECT_IS_EMPTY = "未找到相关信息！";

    public static final String PHONE_IS_EMPTY = "请填写手机号码！";
    public static final String PHONE_IS_INCORRECT = "填写手机号码不正确！";
    public static final String PHONE_IS_REGISTED = "该手机号码已被注册！";

    public static final String VERIFICATIONCODE_GET_FAILED = "短信验证码获取失败！";
    public static final String VERIFICATIONCODE_IS_EMPTY = "请填写短信验证码！";
    public static final String VERIFICATIONCODE_IS_INCORRECT = "短信验证码不正确，请重新获取！";
    public static final String VERIFICATIONCODE_IS_EXPIRED = "短信验证码已失效，请重新获取！";

    public static final String USER_INFO_ERROR = "用户信息错误！";
    public static final String USER_LOGOUT_SUCCESS = "用户退出成功！";
    public static final String USER_LOGOUT_FAILED = "用户退出失败！";
    public static final String USER_LOGIN_FAILED = "用户名或密码不正确！";
    public static final String USER_TOKEN_FAILED = "无效的token！";
    public static final String USER_AUTH_FAILED = "用户认证失败！";
    public static final String USER_NOT_LOGIN = "用户未登录！";
    public static final String USER_NOT_REGISTED = "该用户未注册！";

    public static final String REQUEST_MISSING_PARAM_EXCEPTION = "请求缺失参数！";
    public static final String REQUEST_PARAM_ERROR_EXCEPTION = "请求参数错误！";
    public static final String REQUEST_URL_NOT_FOUND_EXCEPTION = "请求URL不存在！";
    public static final String REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION = "请求方式不支持！";
    public static final String REQUEST_RESOURCE_UNAUTHORIZED = "请求资源未授权，不允许访问！";
    public static final String REQUEST_METHOD_ARGUMENT_TYPE_MISMATCHEXCEPTION = "请求方法参数类型不匹配！";
    public static final String REQUEST_INTERFACE_EXCEEDS_THE_LIMIT_WITHIN_THE_SPECIFIED_TIME = "请求接口在规定时间内超出限定次数！";

    public static final String APP_NO_NEW_VERSION = "App暂未发布新版本！";


    //文件上传标识
    public static final String CAP_HANDLE_DETAIL_FORM = "cap_handle_detail_form";
    public static final String CAP_BUSI_RECORD_FORM = "cap_busi_record_form";
    public static final String CAP_SHOP_INFO_YYZZ_FORM = "cap_shop_info_yyzz_form";
    public static final String CAP_SHOP_INFO_WSXKZ_FORM = "cap_shop_info_wsxkz_form";
    public static final String CAP_INSPECTION_BOOK_FROM="cap_inspection_book_form";
    public static final String CAP_SYS_AREA_FROM="sys_area_form";
    //多媒体类型
    //音频
    public static final String AUTIO = "audio";
    //图片
    public static final String IMAGE = "image";
    //视频
    public static final String VIDEO = "video";



    /**
     * access_token生成的密钥
     */
    public static final String AUTHENTICATION_ID_SECRET = "AcCeSS_tOkEN_asdaJDKHJH_dasda";
}
