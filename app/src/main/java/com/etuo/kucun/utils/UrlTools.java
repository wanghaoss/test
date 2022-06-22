package com.etuo.kucun.utils;

import com.etuo.kucun.storage.PreferenceCache;

import java.net.URLEncoder;

/**
 * ================================================
 *
 * @author :haining.yang
 * @version :V 1.0.0
 * @date :2019/2/23/9:53
 * @ProjectNameDescribe :UrlTools接口类
 * 修订历史：增加枚举(vip)
 * ================================================
 */
public class UrlTools {
    //--------------------------------打包变量注意(三部曲)-------------------------------------------*
    /**
     * 1.baseUrl
     **/
    private static String BASE_URL = "";
    /**
     * 2.当前选择的版本(发版前切换switchApiType.release即可)
     **/
    private static final switchApiType SWITCH_API_TYPE = switchApiType.test;

    /**
     * 3.打版本标记
     **/    
    private enum switchApiType {
        //正式，测试,开发，本地
        release, beta, dev, test
    }

    static {
        switch (SWITCH_API_TYPE) {
            //外网测试阿里云
            case release:
                BASE_URL = "http://e-tuo.com/boot/";
                break;
            //本地测试服务器
            case beta:
                BASE_URL = "http://192.168.1.20:8080/boot/";
                break;
            case dev:
                BASE_URL = "http://192.168.1.142:8080/boot/";
                break;
            case test:

                // 嘉哥
//                BASE_URL = "http://192.168.1.67:8080/boot/";
                //冬冬
//                BASE_URL = "http://192.168.1.185:8050/boot/";
//                //孙浩
//                BASE_URL = "http://192.168.1.55:8080/boot/";
//                //于鹏
//                BASE_URL = "http://192.168.1.211:8080/boot/";
//                //孙晓东
//                BASE_URL = "http://192.168.1.142:8080/boot/";
                //浩亮
//                BASE_URL = "http://192.168.1.66:8888/boot/";
                //客户
//                BASE_URL="http://116.3.204.85:235/boot/";
                BASE_URL="http://www.e-tuo.com/test/";
                //http://e-tuo.com/test/
//                BASE_URL="http://42.84.41.237:235/boot/";

//                BASE_URL="http://e-tuo.com/test/";
                break;
            default:
                break;

        }
    }


    //--------------------------------------方法接口------------------------------------------------*


    /**
     * 获取验证码
     **/
    public static String CODE = "api/sms/branch/send";
    /**
     * 检查验证码是否正确或失效
     **/
    public static String CHECK_VERIFY_CODE = "api/sms/verify";
    /**
     * 找回密码
     **/
    public static String BACKPASSWORD = "api/branch/pwd/forget";
    /**
     * 设置 修改密码
     **/
    public static String SETTINGPASSWORD = "api/branch/pwd/change";

    /**
     * 检测版本更新
     **/
    public static String UPDATEVERSION = "api/branch/version";
    /**
     * 上传图片
     **/
    public static String IMAGE_UPLOAD = "api/imageUpload";

    /**
     * 扫码报损查询
     **/
    public static String DAMAGED_SCAN_SEARCH = "api/branch/damaged/scanSearch";

    /**
     * 消息列表
     **/
    public static String MASSAGE_LIST = "api/branch/message/list";
    /**
     * 报损列表
     **/
    public static String DAMAGED_LIST = "api/branch/damaged/list";
    /**
     * 报损详情
     **/
    public static String DAMAGED_DETAILS = "api/branch/damaged/info";
    /**
     * 新增报损
     **/
    public static String ADD_DAMAGED = "api/branch/damaged/add";
    /**
     * 报损详情修改
     **/
    public static String EDIT_DAMAGED_DETAILS = "api/branch/damaged/upd";

    /**
     * 新版出库扫码查询
     **/
    public static String OUT_BOUND_SCAN_CODE = "api/branch/outStock/scanSearch";
    /**
     * 新版出库扫码确定
     **/
    public static String OUT_BOUND_SCAN_CODE_DETERMINE = "api/branch/outStock/scanConfirm";

    /**
     * 新版入库扫码查询
     **/
    public static String IN_BOUND_SCAN_CODE = "api/branch/enterStock/scanSearch";
    /**
     * 新版入库扫码确定
     **/
    public static String IN_BOUND_SCAN_CODE_DETERMINE = "api/branch/enterStock/scanConfirm";

    /**
     * 收托扫码查询
     **/
    public static String RECEIVE_SCAN_CODE = "api/branch/order/scanSearch";
    /**
     * 收托扫码确定
     **/
    public static String RECEIVE_SCAN_CODE_DETERMINE = "api/branch/order/scanConfirm";
    /**
     * 收托确定未提交
     **/
    public static String RECEIVE_NO_CONFRIM = "api/branch/order/colseConfirm";
    /**
     * 扫码查询
     **/
    public static String PALLET_ID = "orderCenterApi/palletId";

    /**
     * 注册
     **/
    public static String REGIST = "registApi/regist";

    /**
     * 退出登录
     **/
    public static String LOG_OUT = "logout";


    /********************仓储系统*************************************/

    /**
     * 登录
     **/
    public static String LOGIN = "api/user/appLogin";
    /**
     * 首页数据
     **/
    public static String HOMEDATA = "api/goodsStorage/home";


    /**
     * 备货信息列表
     **/
    public static String BILL_LIST = "api/goodsPrepare/billList";
    /**
     * 备货详情
     **/
    public static String GET_BILL = "api/goodsPrepare/getBill";

    /**
     * 批量更新备货状态
     */
    public static String MOBILE_BINDING_GOODS = "api/goodsPrepare/mobileBindingGoods";

    /**
     * 更新备货单状态
     */
    public static String UPDATE_BILL = "api/goodsPrepare/updateBill";


    /**
     * 托盘验收信息列表
     **/
    public static String TP_CHECK_LIST = "api/pallet/checkBill/list";


    /**
     * 验收托盘详情
     **/
    public static String TP_CHECK_INFO = "api/pallet/checkBill/detail";
    /**
     * 批量更新托盘验收状态
     */
    public static String TP_PALLET_CHECK = "api/pallet/checkBill/palletCheck";

    /**
     * 更新托盘验收单状态
     */
    public static String CHECK_BILL_CHECK = "api/pallet/checkBill/checkBillUpd";


    /**
     * 还托盘详情
     **/
    public static String TP_RECEIVE_ORDER_INFO = "api/receiveOrder/detail";
    /**
     * 批量更新还托盘状态
     */
    public static String TP_RECEIVE_ORDER_CHECK = "api/receiveOrder/palletCheck";

    /**
     * 更新托盘验收单状态
     */
    public static String RECEIVE_ORDER_CHECK_BILL_UPD = "api/receiveOrder/checkBillUpd";


    /**
     * 采购验收详情
     **/
    public static String GOODS_BUY_CHECK_GETBILL = "api/acceptance/getCheckBillDetail";

    /**
     * 更新采购验收单状态
     */
    public static String UPD_CHECK_BILL = "api/acceptance/updCheckBill";

    /**
     * 批量更新采购货物状态
     */
    public static String BATCH_ACCEPTANCE = "api/acceptance/batchAcceptance";


    /**
     * 采购入库详情
     **/
    public static String GOODS_BUY_IN_GETBILL = "api/storage/in/goodsStorageInBill/detail";


    /**
     * 批量更新采购入库状态
     */
    public static String GOODS_BATCH_ACCEPTANCE = "api/storage/in/goodsStorageInBill/mobileBatchOperation";

    /**
     * 更新采购入库单状态
     */

    public static String GOODS_SINGLE_OPERATION = "api/storage/in/goodsStorageInBill/storageInOperation";


    /**
     * 托盘入库详情
     **/
    public static String TP_IN_STORAGE_GETBILL = "api/storage/in/palletStorageInBill/detail";

    /**
     * 批量更新托盘入库状态
     **/
    public static String PALLET_BATCH_OPERATION = "api/storage/in/palletStorageInBill/mobileBatchOperation";

    /**
     * 更新托盘入库单状态
     */
    public static String PALLET_SINGLE_OPERATION = "api/storage/in/palletStorageInBill/storageInOperation";


    /**
     * 出库详情(bom和销售出库)
     **/
    public static String GOODS_STORAGE_OUT_GETBILL = "api/goodsStorageOut/getBill";

    /**
     * 批量更新托盘出库状态
     **/
    public static String MOBILE_GOODS_STORAGEOUT = "api/goodsStorageOut/mobileGoodsStorageOut";

    /**
     * 更新托盘出库单状态
     */
    public static String GOODS_STORAGEOUT_UPDATEBILL = "api/goodsStorageOut/updateBill";


    /**
     * 转库详情
     **/
    public static String GOODS_STORAGE_TRANSFER_GETBILL = "api/goodsStorageTransfer/getBill";

    /**
     * 转库 批量更新状态
     **/
    public static String GOODS_STORAGE_TRANSFER_MOBILE_TRANSFER = "api/goodsStorageTransfer/mobileTransfer";

    /**
     * 更新 转库单状态  (未修改)
     */
    public static String GOODS_CHANGE_AREA_UPDATEBILL = "api/goodsStorageTransfer/updateBill";


    /**
     * 发货详情
     **/
    public static String GOODS_SEND_OUT_GETBILL = "api/goodsDispatch/getBill";


    /**
     * 获取托盘图片list
     **/
    public static String GET_TP_BIG_PIC = "api/acceptance/getPalletImgList";

    //--------------------------------------测试------------------------------------------------*
    /**
     * 认证
     **/

    public static String GET_IN_RENZHENG_TEST = "http://47.95.42.132/userWap/certificationCompanyOnePage.html?urlType=1";
    /**
     * 注册服务协议
     **/
    public static String RegistInfoLink = "http://47.95.42.132/userWap/regist/agreement.html?backFlg=1";
    /**
     * 隐私条款
     **/
    public static String privacyAgreementPageLink = "userWap/pages/protocol/policy.html";



    // --------------------------------------新的发收数据---------------------------
    /**
     * 发拖数据
     **/
    public static String FATSJ = "api/orderPallet/getOrderReceivePallet";

    /**
     * 发拖详情
     **/
    public static String FTdetails = "api/driver/order-info";

    /**
     * 发拖提交
     **/
    public static String FTSubmit = "api/orderPallet/addOrderPallet";

    /**
     * 发拖提交
     **/
    public static String FTSubmitt = "api/orderPallet/addOrderPalletData";

    /**
     * 收拖数据
     **/
    public static String SATSJ = "api/branch/pallet/distribution/wxNetList";

    /**
     * 获取库存数量接口
     **/
    public static String PDSL = "api/yard/stock/model/detail";
    /**
     * 保存盘点信息接口
     **/
    public static String PDBC = "api/yard/stock/save";



    /**
     * @param method 方法名
     *               获得接口url
     * @return BASE_URL + method
     */
    public static String getInterfaceUrl(String method) {
        return BASE_URL + method;
    }

    /**
     * 获得html地址
     *
     * @param html
     * @return BASE_URL + html
     */
    public static String getHTMLUrl(String html) {

        return BASE_URL + html;
    }


    /**
     * 根据经纬度获取详细的地址细心
     * log : 经度  lat 维度
     */
    public static String getAddress(String log, String lat) {

        return "http://gc.ditu.aliyun.com/regeocoding?l=" + lat + "," + log + "&type=100";
    }

    /**
     * 获取 链接地址
     *
     * @param url
     * @return url + url_last + URLEncoder.encode(PreferenceCache.getToken())
     */
    public static String getAllWebUrl(String url) {
        String urlLast = "&token=";
        if (!StringUtil.isEmpty(url) && !url.contains("?")) {
            urlLast = "?token=";
        }
        return url + urlLast + URLEncoder.encode(PreferenceCache.getToken());
    }

    public static String getGoodsInfo(String goodsId) {

        String url = "userPC/pages/goods/show.html?goodsId=" + goodsId;
        return getAllWebUrl(url);
    }


}
