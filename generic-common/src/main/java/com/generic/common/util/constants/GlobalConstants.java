package com.generic.common.util.constants;

/**
 * @author liuyuzhu
 * @description: 枚举常量
 * @date 2018/1/22 10:56
 */
public class GlobalConstants {

    /**
     * 系统默认密码
     */
    public static final String DEF_PASSWORD="123456";

    /**
     * 分页条数
     */
    public static int pageSize=10;

    /**
     * 用户缓存
     */
    public static final String USER_CACHE="userCache";

    /**
     * 数据字典缓存
     */
    public static final String CODE_CACHE="dictionaryCache";

    /**
     * 请求前缀
     */
    public static final String PREFIX = "api/";

    public static final String TOKEN = "access_token";

    /**
     * 代码生成方式
     */
    public enum genType {
        /**
         * 本地项目
         */
        local(0),
        /**
         * web下载
         */
        webDown(1);

        private int value;

        private genType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 机构类型
     */
    public enum OrganType {
        /**
         * 根节点
         */
        CATALOG(0),
        /**
         * 机构
         */
        ORGAN(1),
        /**
         * 部门
         */
        DEPART(2);

        private int value;

        private OrganType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 返回状态值
     */
    public enum RESULT{
        /**
         * 成功
         */
        CODE_YES("0"),
        /**
         * 失败
         */
        CODE_NO("-1"),
        /**
         * 失败msg
         */
        MSG_YES("操作成功"),
        /**
         * 失败msg
         */
        MSG_NO("操作失败");
        private String value;

        private RESULT(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }
    /**
     * 系统用户状态
     */
    public enum ABLE_STATUS{
        /**
         * 正常
         */
        YES(0),
        /**
         * 禁用
         */
        NO(-1);
        private int value;

        ABLE_STATUS(int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }
    /**
     * 菜单类型
     */
    public enum MenuType{
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2),
        /**
         * 目录
         */
        CATALOG(0);
        private int value;

        private MenuType(int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }

    /**
     * 是否类型
     */
    public enum YESNO{
        /**
         * 是
         */
        YES(1),
        /**
         * 否
         */
        NO(0);
        private int value;

        private YESNO(int value){
            this.value=value;
        }
        public int getValue(){
            return value;
        }
    }
}
