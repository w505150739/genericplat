package com.generic.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author liuyuzhu
 * @description: ${todo}(这里用一句话描述这个类的作用)
 * @date 2018/1/20 22:49
 */
public class IPUtil {

    protected LogUtil log = LogUtil.getLogger(IPUtil.class);
    // 允许的IP访问列表

    private static Pattern pattern = Pattern.compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");

    public static boolean checkIP(String ip, String ips) {
        // 初始化IP白名单
        Set<String> ipList = init(ips);
        // 获取请求IP
        InetAddress inet = null;

        try {
            inet = InetAddress.getLocalHost();
            if (ip.equals("127.0.0.1"))
                ip = inet.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 对访问的IP进行过滤
        return checkLoginIP(ipList, ip);
    }

    /**
     *
     * @Description: 初始化IP白名单
     * @param @param allowIp
     * @param @return
     * @return Set<String>
     * @throws
     * @author WUH
     * @date 2016-5-17
     */
    private static Set<String> init(String allowIp) {
        Set<String> ipList = new HashSet<String>();
        for (String allow : allowIp.replaceAll("\\s", "").split(";")) {
            if (allow.indexOf("*") > -1) {
                String[] ips = allow.split("\\.");
                String[] from = new String[] { "0", "0", "0", "0" };
                String[] end = new String[] { "255", "255", "255", "255" };
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++)
                    if (ips[i].indexOf("*") > -1) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }

                StringBuffer fromIP = new StringBuffer();
                StringBuffer endIP = new StringBuffer();
                for (int i = 0; i < 4; i++)
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]", s.split(";")[0]) + "-" + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip))
                        ipList.add(ip);
                }
            } else {
                if (validate(allow))
                    ipList.add(allow);
            }
        }
        return ipList;
    }

    /**
     * 对单个IP节点进行范围限定
     *
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        if (arg.length() == 1) {
            com.add("0;255");
        } else if (arg.length() == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
            String s2 = complete(arg, 2);
            if (s2 != null)
                com.add(s2);
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null)
                com.add(s1);
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255)
            return null;
        if (Integer.valueOf(end) > 255)
            end = "255";
        return from + ";" + end;
    }

    /**
     * 在添加至白名单时进行格式校验
     *
     * @param ip
     * @return
     */
    private static boolean validate(String ip) {
        for (String s : ip.split("-"))
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        return true;
    }

    /**
     *
     * @Description: 判断Ip是否在白名单中
     * @param @param ipList
     * @param @param ip
     * @param @return
     * @return boolean
     * @throws
     * @author WUH
     * @date 2016-5-17
     */
    private static boolean checkLoginIP(Set<String> ipList, String ip) {
        if (ipList.isEmpty() || ipList.contains(ip))
            return true;
        else {
            for (String allow : ipList) {
                if (allow.indexOf("-") > -1) {
                    String[] from = allow.split("-")[0].split("\\.");
                    String[] end = allow.split("-")[1].split("\\.");
                    String[] tag = ip.split("\\.");

                    // 对IP从左到右进行逐段匹配
                    boolean check = true;
                    for (int i = 0; i < 4; i++) {
                        int s = Integer.valueOf(from[i]);
                        int t = Integer.valueOf(tag[i]);
                        int e = Integer.valueOf(end[i]);
                        if (!(s <= t && t <= e))
                            check = false;
                    }
                    if (check)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @Description: 获取客户端真实IP地址
     * @param @param request
     * @param @return
     * @return String
     * @throws
     * @author WUH
     * @date 2016-5-17
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index == -1) {
                return ip;
            } else {
                return ip.substring(0, index);
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }
}
