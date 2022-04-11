

package com.waminet.common.tools.constant;

/**
 * 共通常量
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public interface Constant {

    /**
     * 成功
     */
    int SUCCESS = 1;

    /**
     * 失败
     */
    int FAIL = 0;

    /**
     * OK
     */
    String OK = "OK";

    /**
     * 用户标识
     */
    String USER_KEY = "userId";

    /**
     * 菜单根节点标识
     */
    Long MENU_ROOT = 0L;

    /**
     * 数据字典根节点标识
     */
    Long DICT_ROOT = 0L;

    /**
     * 升序
     */
    String ASC = "asc";

    /**
     * 降序
     */
    String DESC = "desc";

    /**
     * 删除字段名
     */
    String DEL_FLAG = "del_flag";

    /**
     * 状态  0：停用    1：正常
     */
    String status = "status";

    /**
     * 创建时间字段名
     */
    String CREATE_DATE = "create_date";

    /**
     * 数据权限过滤
     */
    String SQL_FILTER = "sqlFilter";

    /**
     * 当前页码
     */
    String PAGE = "page";

    /**
     * 每页显示记录数
     */
    String LIMIT = "limit";

    /**
     * 排序字段
     */
    String ORDER_FIELD = "orderField";

    /**
     * 排序方式
     */
    String ORDER = "order";

    /**
     * token header
     */
    String TOKEN_HEADER = "token";

    /**
     * 当前店铺id
     */
    String CURRENT_MALL_ID = "currentMallId";

    /**
     * 版本标识 管理后台
     */
    String BUNDLE_ADMIN = "admin";

    /**
     * 版本标识 八诀打单
     */
    String BUNDLE_WAYBILL = "waybill";

    /**
     * 八诀打单短信签名
     */
    String SMS_SIGN_NAME = "八诀打单";

    /**
     * 消息过期时间KEY
     */
    String MQ_INCR_LIVETIME_KEY = "mq_incr_livetime_key";

    /**
     * RPC CLIENT ID
     */
    String RPC_CLIENT_ID = "clientId";

    /**
     * RPC SIGN
     */
    String RPC_SIGN = "sign";

    /**
     * 常量
     */
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int FOUR = 4;
    int FIVE = 5;
    int SIX = 6;
    int SEVEN = 7;
    int EIGHT = 8;
    int NINE = 9;
    int ELEVEN = 11;
    int NEGATIVE_ONE = -1;

}
