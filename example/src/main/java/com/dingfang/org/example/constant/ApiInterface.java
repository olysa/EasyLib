package com.dingfang.org.example.constant;

/**
 * 定义接口常量
 * Created by zuoqing on 2017/10/12.
 */

public class ApiInterface {

    /**
     * 基址
     */
    public static String BASE_URL = "http://www.ytzhihui.com";
    /**
     * 后缀名
     */
    public static String PLUS = "/ecp/rest/xxx/";
    /**
     * 端口号
     */
    public static String PORT = "";


    /**
     * 可用地址
     */
    public static String URL = BASE_URL + PORT + PLUS;

    /**
     * 上传服务器
     */
    public static final String SERVER = "http://server.jeasonlzy.com/OkHttpUtils/";


    /************************************************    以下是api接口   *****************************************************************/

    /**
     * 测试接口
     */
    public static String TEST_INTERFACE = "com.cloud.app.service.IAppBookService.queryNewEbook(RequestParameterMap)";

    /**
     * 云田智慧app下载
     */
//    public static String APP_DOWNLOAD_PATH = "/files/download/apk_reader/yuntianzhihui.apk";
    public static String APP_DOWNLOAD_PATH = "http://121.29.10.1/f5.market.mi-img.com/download/AppStore/0b8b552a1df0a8bc417a5afae3a26b2fb1342a909/com.qiyi.video.apk";

    /**
     * 上传接口
     */
    public static final String URL_FORM_UPLOAD = SERVER + "upload";
}
