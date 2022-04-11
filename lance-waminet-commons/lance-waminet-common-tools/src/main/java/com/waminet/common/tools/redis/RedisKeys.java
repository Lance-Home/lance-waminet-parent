

package com.waminet.common.tools.redis;

/**
 * Redis通用Key常量
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class RedisKeys {

    // ------------------------------------------------------ //
    // ---                    基础配置                    --- //
    // ------------------------------------------------------ //

    /**
     * 系统参数Key
     */
    public static String getSysParamsKey() {
        return "waybill:sys:params";
    }

    /**
     * 发送短信验证码Key
     */
    public static String getSmsCaptchaKey(String uuid) {
        return "waybill:sms:captcha:" + uuid;
    }

    /**
     * 发送短信获取ip发送短信数量
     */
    public static String getSmsCountByIPKey(String ip) {
        return "waybill:sms:ip:" + ip;
    }

    /**
     * 发送短信获取手机号码发送短信数量
     */
    public static String getSmsCountByMobileKey(String mobile) {
        return "waybill:sms:mobile:" + mobile;
    }

    /**
     * 电子面单共享码Key
     */
    public static String getShareCaptchaKey(String uuid) {
        return "waybill:share:captcha:" + uuid;
    }

    /**
     * 登录验证码Key
     */
    public static String getLoginCaptchaKey(String uuid) {
        return "waybill:sys:captcha:" + uuid;
    }

    /**
     * 登录用户Key
     */
    public static String getSecurityUserKey(Long id) {
        return "waybill:sys:security:user:" + id;
    }

    /**
     * 登录店铺Key
     */
    public static String getSecurityMallKey(Long id) {
        return "waybill:sys:security:mall:" + id;
    }

    /**
     * 登录店铺授权Key
     */
    public static String getSecurityMallTokenKey(Long mallId, String platform) {
        return "waybill:sys:security:token:" + platform + "_" + mallId;
    }

    /**
     * 系统日志Key
     */
    public static String getSysLogKey() {
        return "waybill:sys:log";
    }

    /**
     * 系统资源Key
     */
    public static String getSysResourceKey() {
        return "waybill:sys:resource";
    }

    /**
     * 用户菜单导航Key
     */
    public static String getUserMenuNavKey(Long userId, String language) {
        return "waybill:sys:user:nav:" + userId + "_" + language;
    }

    /**
     * 用户菜单导航Key（通配符）
     */
    public static String getUserMenuNavKey(Long userId) {
        return "waybill:sys:user:nav:" + userId + "_*";
    }

    /**
     * 用户权限标识Key
     */
    public static String getUserPermissionsKey(Long userId) {
        return "waybill:sys:user:permissions:" + userId;
    }

    /**
     * RPC签名Key
     */
    public static String getRpcSignKey(String clientId) {
        return "waybill:rpc:sign:" + clientId;
    }

    /**
     * RPC品牌授权Key
     */
    public static String getRpcBoundKey(String clientId, Long brandId, String platform, Long deptId, Long userId, Long mallId) {
        return "waybill:rpc:bound:" + clientId + ":" + brandId + ":" + platform + "_" + deptId + "_" + userId + "_" + mallId;
    }

    /**
     * RPC品牌授权Key（通配符）
     */
    public static String getRpcBoundPatternKey(String clientId, Long brandId, String platform) {
        return "waybill:rpc:bound:" + clientId + ":" + brandId + ":" + platform + "_*";
    }

    /**
     * 消息队列防止重复用Key
     */
    public static String getRocketMQKey(String topic, String tags, String keys) {
        return "waybill:rocketmq:" + topic + ":" + tags + ":" + keys;
    }

    // ------------------------------------------------------ //
    // ---                   拼多多配置                    --- //
    // ------------------------------------------------------ //

    /**
     * 拼多多系统时间差Key
     */
    public static String getPddTimeDifferenceKey() {
        return "waybill:pdd:time:difference";
    }

    /**
     * 拼多多商品类目信息Key
     */
    public static String getPddGoodsCatsKey(Long catId, Integer level) {
        return "waybill:pdd:goods-cats:" + level + ":" + catId;
    }

    /**
     * 拼多多标准地址库Key
     */
    public static String getPddLogisticsAddressKey(Integer regionType, Long parentId, Long addressId) {
        return "waybill:pdd:logistics-address:" + regionType + ":" + parentId + ":" + addressId;
    }

    /**
     * 拼多多标准地址库Key
     */
    public static String getPddLogisticsAddressPatternKey() {
        return "waybill:pdd:logistics-address:*";
    }

    /**
     * 拼多多标准地址库Key
     */
    public static String getPddLogisticsAddressKeyByRegionType(Integer regionType) {
        return "waybill:pdd:logistics-address:" + regionType + ":*";
    }

    /**
     * 拼多多标准地址库Key
     */
    public static String getPddLogisticsAddressKeyByParentId(Long parentId) {
        return "waybill:pdd:logistics-address:*:" + parentId + ":*";
    }

    /**
     * 拼多多标准地址库Key
     */
    public static String getPddLogisticsAddressKeyByAddressId(Long addressId) {
        return "waybill:pdd:logistics-address:*:*:" + addressId;
    }

    /**
     * 拼多多快递公司Key
     */
    public static String getPddLogisticsCompanyKey(String companyCode) {
        return "waybill:pdd:logistics-company:" + companyCode;
    }

    /**
     * 拼多多标准电子面单模板Key
     */
    public static String getPddStandardTemplateKey(Long templateId) {
        return "waybill:pdd:standard-template:" + templateId;
    }

    /**
     * 拼多多标准电子面单模板Key
     */
    public static String getPddStandardTemplatePatternKey() {
        return "waybill:pdd:standard-template:*";
    }

    /**
     * 拼多多商品SKU信息Key
     */
    public static String getPddGoodsItemKey(Long mallId, Long skuId) {
        return "waybill:pdd:goods-item:" + mallId + ":" + skuId;
    }

    /**
     * 拼多多商品SKU信息Key
     */
    public static String getPddGoodsItemPatternKey(Long mallId) {
        return "waybill:pdd:goods-item:" + mallId + ":*";
    }

    /**
     * 拼多多订单增量接口上次调用时间Key
     */
    public static String getPddOrderTimerKey(Long mallId) {
        return "waybill:pdd:order-timer:" + mallId;
    }

    /**
     * 拼多多代打订单接口上次调用时间Key
     */
    public static String getPddFdsOrderTimerKey(Long mallId) {
        return "waybill:pdd:fds-order-timer:" + mallId;
    }

    /**
     * 拼多多同步店铺消息Key
     */
    public static String getPddMallSyncKey(Long mallId) {
        return "waybill:pdd:mall-sync:" + mallId;
    }

    /**
     * 拼多多代打订单同步Key
     */
    public static String getPddFdsOrderSyncKey(Long mallId) {
        return "waybill:pdd:fds-order-sync:" + mallId;
    }

    /**
     * 拼多多增量订单同步消息Key
     */
    public static String getPddIncrementOrderSyncKey(Long mallId, String taskId) {
        return "waybill:pdd:increment-order-sync:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多物流跟踪轨迹同步Key
     */
    public static String getPddLogisticsTraceSyncKey(Long mallId, String taskId) {
        return "waybill:pdd:logistics-trace-sync:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多订阅消息Key
     */
    public static String getPddMessageKey(String messageType, Long mallId, String orderSn) {
        return "waybill:pdd:message:" + messageType + ":" + mallId + ":" + orderSn;
    }

    /**
     * 拼多多订阅消息Key
     */
    public static String getPddMessagePatternKey(String messageType) {
        return "waybill:pdd:message:" + messageType + ":*";
    }

    /**
     * 拼多多快递单号申请Key
     */
    public static String getPddWaybillApplyKey(Long mallId, String taskId) {
        return "waybill:pdd:waybill-apply:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多快递单号申请Key失败
     */
    public static String getPddWaybillApplyErrorKey(Long mallId, String taskId) {
        return "waybill:pdd:waybill-apply-error:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多快递单号回收Key
     */
    public static String getPddWaybillRecycleKey(Long mallId, String taskId) {
        return "waybill:pdd:waybill-recycle:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多物流推荐规则Key
     */
    public static String getPddLogisticsRecommendRuleKey(Long mallId) {
        return "waybill:pdd:logistics-recommend-rule:" + mallId;
    }

    /**
     * 拼多多物流偏远地区规则Key
     */
    public static String getPddLogisticsRemoteRuleKey(Long mallId) {
        return "waybill:pdd:logistics-remote-rule:" + mallId;
    }

    /**
     * 拼多多物流禁运地区规则Key
     */
    public static String getPddLogisticsEmbargoRuleKey(Long mallId) {
        return "waybill:pdd:logistics-embargo-rule:" + mallId;
    }

    /**
     * 拼多多订单忽略规则Key
     */
    public static String getPddCostIgnoreRuleKey(Long mallId) {
        return "waybill:pdd:cost-ignore-rule:" + mallId;
    }

    /**
     * 拼多多物流成本规则Key
     */
    public static String getPddCostPostageRuleKey(Long mallId) {
        return "waybill:pdd:cost-postage-rule:" + mallId;
    }

    /**
     * 拼多多成本退回规则Key
     */
    public static String getPddCostRefundRuleKey(Long mallId) {
        return "waybill:pdd:cost-refund-rule:" + mallId;
    }

    /**
     * 拼多多代打订单回传Key
     */
    public static String getPddFdsOrderReturnKey(Long mallId, String taskId) {
        return "waybill:pdd:fds-order-return:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多代打订单取消回传Key
     */
    public static String getPddFdsOrderCancelKey(Long mallId, String taskId) {
        return "waybill:pdd:fds-order-cancel:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多代打订单电子面单取号Key
     */
    public static String getPddFdsOrderWaybillApplyKey(Long mallId, String taskId) {
        return "waybill:pdd:fds-order-waybill-apply:" + mallId + ":" + taskId;
    }

    /**
     * 拼多多电子面单模板Key
     */
    public static String getPddWaybillTemplateKey(Long mallId, Long templateId) {
        return "waybill:pdd:waybill-template:" + mallId + ":" + templateId;
    }

    /**
     * 拼多多电子面单模板Key
     */
    public static String getPddWaybillTemplatePatternKey(Long mallId) {
        return "waybill:pdd:waybill-template:" + mallId + ":*";
    }

    /**
     * 拼多多电子面单订购账户Key
     */
    public static String getPddWaybillBranchAccountKey(Long mallId, Long branchAccountId) {
        return "waybill:pdd:waybill-branch-account:" + mallId + ":" + branchAccountId;
    }

    /**
     * 拼多多电子面单订购账户Key
     */
    public static String getPddWaybillBranchAccountPatternKey(Long mallId) {
        return "waybill:pdd:waybill-branch-account:" + mallId + ":*";
    }

    /**
     * 拼多多店铺默认物流发货地址Key
     */
    public static String getPddShipmentAddressKey(Long mallId) {
        return "waybill:pdd:shipment-address:" + mallId;
    }

    /**
     * 拼多多电子面单共享关系Key
     */
    public static String getPddWaybillShareKey(Long id) {
        return "waybill:pdd:waybill-share:" + id;
    }

    /**
     * 拼多多电子面单模板自定义区域Key
     */
    public static String getPddWaybillTemplateCustomAreaKey(Long mallId, Long templateId) {
        return "waybill:pdd:waybill-template-custom-area:" + mallId + ":" + templateId;
    }

    /**
     * 拼多多电子面单模板自定义区域Key
     */
    public static String getPddWaybillTemplateCustomAreaPatternKey(Long mallId) {
        return "waybill:pdd:waybill-template-custom-area:" + mallId + ":*";
    }

    /**
     * 拼多多电子面单 - 多单号
     */
    public static String getPddWaybillMultipleOrderNumbersKey(String taskId) {
        return "waybill:pdd:waybill-multiple-order-numbers:" + taskId;
    }

}
