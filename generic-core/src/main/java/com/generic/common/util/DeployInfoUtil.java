package com.generic.common.util;

import java.io.*;
import java.util.Properties;

/**
 * @author liuyuzhu
 * @description: ${todo}(这里用一句话描述这个类的作用)
 * @date 2018/1/21 0:01
 */
public class DeployInfoUtil {

    static Properties applicationPro = new Properties();
    static {
        try {
            applicationPro.load(DeployInfoUtil.class.getClassLoader().getResourceAsStream("config/deploy.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAppProParam(String key) {
        return applicationPro.get(key).toString();
    }

    public static void setAppProParam(String key, String value) {
        applicationPro.setProperty(key, value);
        OutputStream fos = null;
        try {
            fos = new BufferedOutputStream(new FileOutputStream(DeployInfoUtil.class.getClassLoader().getResource("deploy.properties").getPath()));
            fos.flush();
            applicationPro.store(fos, "写入到propertise文件");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>概要：</b> WEB应用的访问地址根路径 <b>作者：</b>suxh </br> <b>日期：</b>2015-1-8 </br>
     *
     * @return deploy.properties文件上配置的host
     */
    /*
     * @escription 获取redis的地址
     * @author liuyuzhu
     * @date 2018/1/21 0:11
     * @param []
     * @return java.lang.String
    */
    public static String getRedisHost(){
        return applicationPro.get("redis.host").toString();
    }
    /*
     * @escription 获取redis的端口号
     * @author liuyuzhu
     * @date 2018/1/21 0:11
     * @param []
     * @return java.lang.String
     */
    public static String getRedisPort(){
        return applicationPro.get("redis.port").toString();
    }
    /*
     * @escription 获取redis的密码
     * @author liuyuzhu
     * @date 2018/1/21 0:11
     * @param []
     * @return java.lang.String
     */
    public static String getRedisPassword(){
        return applicationPro.get("redis.password").toString();
    }

    /*
     * @escription 最大活动数
     * @author liuyuzhu
     * @date 2018/1/21 0:13
     * @param
     * @return
    */
    public static String getRedisMaxActive() {
        return applicationPro.get("redis.maxActive").toString();
    }

    /*
     * @escription 对象最大空闲时间
     * @author liuyuzhu
     * @date 2018/1/21 0:13
     * @param
     * @return
    */
    public static String getRedisMaxIdle() {
        return applicationPro.get("redis.maxIdle").toString();
    }

    /*
     * @escription 获取对象时最大等待时间
     * @author liuyuzhu
     * @date 2018/1/21 0:14
     * @param
     * @return
    */
    public static String getRedisMaxWait() {
        return applicationPro.get("redis.maxWait").toString();
    }

    public static Boolean getRedisTestOnBorrow() {
        return Boolean.parseBoolean(applicationPro.get("redis.testOnBorrow").toString());
    }

    /**
     * 获取token过期时间
     * @return 过期时间
     */
    public static String getTokenExpire() {
        return applicationPro.get("token.expire").toString();
    }

    /**
     * 获取token加密方式
     * @return 加密方式
     */
    public static String getTokenCrypt() {
        return applicationPro.get("token.crypt").toString();
    }

    /**
     * 获取登录地址
     * @return 登录地址
     */
    public static String getLoginUrl() {
        return applicationPro.get("login.url").toString();
    }
}
