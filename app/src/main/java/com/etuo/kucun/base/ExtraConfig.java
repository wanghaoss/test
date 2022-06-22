package com.etuo.kucun.base;


import com.etuo.kucun.TuoPanConfig;

public class ExtraConfig {

    public static final int DEFAULT_PAGE_COUNT = 10;

    public static boolean isRefreshByHome = false;//首页

    public static boolean isRefreshByShopping = false;//购物车

    public static boolean isRefreshByOrder = false;//订单

    public static boolean isRefreshByMy = false;//我的

    public static class BaseReceiverAction {
        public static final String ACTION_PREFIX = TuoPanConfig.ACTION_BASE_PREFIX;

        /**
         * token过期
         */
        public static final String ACTION_TOKEN_EXPIRE = ACTION_PREFIX
                + "token.expire";
    }

    public static class RequestCode {


        public static final int REQUEST_CODE_FOR_LOGIN = 0x01;// 登录
        public static final int REQUEST_CODE_FOR_REGISTERED = 0x02;// 注册
        public static final int REQUEST_CODE_FOR_REGISTERED_SUCCESS = 0x03;// 注册成功
        public static final int REQUEST_CODE_FOR_WITHDRAWAL = 0x04;// 提现
        public static final int REQUEST_CODE_FOR_PERFECT = 0x05;// 完善信息

        public static final int REQUEST_CODE_FOR_GESTURE = 0x06;// 设置手势
        public static final int REQUEST_CODE_FOR_GESTURE_DOWN = 0x07;// 关闭手势

        public static final int REQUEST_CODE_FOR_RESET_PASS = 0x08;//修改密码

        public static final int REQUEST_CODE_FOR_RESET_PAY_PASS_FLAG = 0x09;//是否免密的状态


    }


    /**
     * 类型码
     */
    public static class TypeCode {

//        public static final String GET_TYPE_REGIST = "REGIST";//注册
//        public static final String GET_TYPE_LOGIN = "LOGIN";//登录
//        public static final String GET_TYPE_BACK_PW = "BRANCH_FORGET";//找回登录密码
//        public static final String GET_STAFF_MANAGE = "EMPLOYEE";//员工管理页

        public static final String CHECK_TYPE_REGIST = "01";//注册
        public static final String CHECK_TYPE_LOGIN = "02";//登录
        public static final String CHECK_TYPE_BACK_PW = "03";//找回登录密码
        public static final String CHECK_BANK_CARD = "05";//绑定银行卡


        public static final String FROM_INT_LOGIN = "from_intent_login";//从哪跳到登录界面的,从哪来回哪去

        public static final String FROM_INTTENT_TYPE = "from_intent_type";//tab 类型
        public static final String FROM_INTTENT = "from_intent";//从哪跳到WEB界面的

        public static final String FROM_INTTENT_F = "from_intent_f";//第1层的值

        public static final String FROM_INTTENT_S = "from_intent_s";//第2层的值

        public static final String IMAGE_INTENT = "image_intent";//从轮播图跳进去的

        public static final String WEB_INTENT_BY_OUT = "web_intent_out";//从web 的 出库 跳进去

        public static final String WEB_INTENT_BY_IN = "web_intent_in";//从web 的 入库 跳进去

        public static final String WEB_INTENT_BY_SHOU = "web_intent_shou";//从收托盘 跳进去

        public static final String WEB_INTENT_BY_BREAKAGE = "web_intent_breakage";//从报损 跳进去

        public static final String SCAN_ORDER_TYPE = "scan_order_type";//订单类型

        public static final String SCAN_COMPANYID = "scan_companyId";//公司ID

        public static final String SCAN_QUERY_TYPE = "scan_query_type";//操作类型


        //recordId
        public static final String SCAN_RECORD_ID = "recordId";//RECORD
        public static final String SCAN_OREDER_ID = "orderId";//订单ID
        public static final String SCAN_OREDER_NUM = "orderNum";//订单num 编号

        /***********************tab*************************/

        public static final String TYPE_INTENT_BY_ZU = "1";//tab by 租托

        public static final String TYPE_INTENT_BY_HUAN = "2";//tab by 还托

        public static final String TYPE_INTENT_BY_ZHUAN = "3";//tab by 转托

        public static final String TYPE_INTENT_TAB_1 = "";//tab by 全部
        public static final String TYPE_INTENT_TAB_2 = "6";//tab by 已支付
        public static final String TYPE_INTENT_TAB_3 = "0";//tab by 待配送
        public static final String TYPE_INTENT_TAB_4 = "1";//tab by 待收托
        public static final String TYPE_INTENT_TAB_5 = "2";//tab by 已收托

        public static final String TYPE_INTENT_TAB_HUAN_1 = "";//tab by 全部
        public static final String TYPE_INTENT_TAB_HUAN_2 = "1";//tab by 待配送
        public static final String TYPE_INTENT_TAB_HUAN_3 = "2";//tab by 待收托
        public static final String TYPE_INTENT_TAB_HUAN_4 = "3";//tab by 已收托
        public static final String TYPE_INTENT_TAB_HUAN_5 = "5";//tab by 已入库

        /***************************扫码的type**************************/

        public static final String TYPE_CODE_BY_SHOUTUO = "shoutuo";// 二维码扫描从收托进入 成功后进入详情
        public static final String TYPE_CODE_BY_SHOUYE = "shouYe";// 二维码扫描从首页进入 成功后显示托盘内容详情
        public static final String TYPE_SCAN_TYPE1 = "0";// 查看
        public static final String TYPE_SCAN_TYPE2 = "1";// 收
        public static final String TYPE_SCAN_TYPE3 = "2";// 转
        public static final String TYPE_SCAN_TYPE4 = "3";// 还

        /*************************检测token 时候,从哪页进入的Login 界面***************************/
        public static final String HOME_FRAGMENT = "home_fragment";//从主界面
        public static final String SHOPPING_CARD_FRAGMENT = "shopping_fragment";//从购物车面进入
        public static final String TWO_CODE_FRAGMENT = "two_fragment";//从二维码扫描界面
        public static final String ORDER_FRAGMENT = "order_fragment";//从订单中心界面进入
        public static final String MY_FRAGMENT = "my_fragment";//从我的界面

        /*************************界面之间intent传递值得KEY****************************/
        public static final String INTENT_ID = "id";//界面之间传递的id
        public static final String INTENT_CAR_FROM = "carFrom";//车辆管理跳转编辑和新增车辆的判断KEY
        public static final String INTENT_CAR_BRANCHID = "branchId";//车辆管理传到新增车辆编辑车辆的branchId
        public static final String INTENT_DAMAGED_FROM = "from";//报损列表跳转新增报损和报损详情的判断key
        public static final String INTENT_DAMAGED_TPORCN = "tpOrContainer";//报损详情页面判断是集装箱还是托盘的key
        public static final String INTENT_DAMAGED_TPMODEL = "damagedNum";//传递到报损详情页面的托盘model
        public static final String INTENT_CONFIGURATION_VEHICLE_TYPE = "type";//配车需要的type
        public static final String INTENT_ORDER_ID = "orderId";//orderId
        public static final String INTENT_ORDER_BANCH_ID = "orderBatchId";//orderBatchId
        public static final String INTENT_RECODE_ID = "recodeId";//出入库id
        public static final String INTENT_RECORD_ID = "recordId";//出入库id
        public static final String INTENT_ORDER_NUM = "orderNum";//订单编号
        public static final String INTENT_STORK_TYPE = "stockType";//出入库类型
        public static final String INTENT_PALLET_MODEL = "palletModel";//托盘的数据
        public static final String INTENT_FROM_INTENT = "from_intent";
        public static final String INTENT_STOCK_TYPE = "storkType";
        
        /*************************弹出框的文言**********************/
        public static final String DIALOG_CONFIRM_TO_DELETE = "您确定要删除吗?";
        public static final String DIALOG_CONFRIM_TO_CONFIGURATON_VEHICLE = "您确定要配车吗?";
        public static final String DIALOG_CONFIRM_TO_EXIT = "确认要退出吗?";
        public static final String DIALOG_CAOZUO_TO_EXIT = "确认要操作吗?";
        public static final String DIALOG_CANCEL_TO_EXIT = "确认要取消操作吗?";


        
    }

    public static class IntentType {
        public static final String KET_GESTURE_FLAG = "gestureFlg";// 手势密码 值  1表示修改密码 2表示关闭手势校验一下 3表示从闪屏页跳入
        /***********************传递值*************************************/
        public static final String FROM_INTENT_BY_BEIHUO = "from_intent_by_beihuo";//  //从首页备货
        public static final String FROM_INTENT_BY_SHOU = "from_intent_by_shou";//  //从首页收托盘
        public static final String FROM_INTENT_BY_HUAN = "from_intent_by_huan";//  //从首页还托盘
        public static final String FROM_INTENT_BOM = "from_intent_by_bom";//  //从首页Bom
        public static final String FROM_INTENT_MARKET_OUT = "from_intent_by_market_out";//  //从首页销售出库

        public static final String FROM_INTENT_CHANGE_AREA_BY_TP = "from_intent_change_area_by_tp";//  //从首页托盘转库

        public static final String FROM_INTENT_CHANGE_AREA_BY_SHENGCHAN = "from_intent_change_area_by_shengchan";//  //从首页生成转库
        public static final String FROM_INTENT_CHANGE_AREA_BY_CHENGPIN = "from_intent_change_area_by_chengpin";//  //从首页成品转库
    }
}
