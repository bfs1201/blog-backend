package com.mojian.utils;

import cn.hutool.json.JSONUtil;
import com.mojian.common.Constants;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@Component
public class IpUtil {
    @Value("${location.api.format-url}")
    private String FORMAT_URL;

    @Value("${location.api.key}")
    private String API_KEY;

    private final static String DEFAULT_LOCAL_IP = "127.0.0.1";

    /**
     * 获取当前公网 IP
     *
     * @return
     */
    public String getIp() {
        try {
            URL url = new URL("https://ipinfo.io/ip");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String ip = in.readLine();
            in.close();
            return ip;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "未知"; // 如果发生异常，返回 "未知"
        }
    }

    /**
     * 解析ip地址
     *
     * @param ip ip地址
     * @return 解析后的ip地址
     */
    public String getCityInfo(String ip) {
        //解析ip地址，获取省市区
        String s = analyzeIp(ip);
        Map map = JSONUtil.toBean(s, Map.class);
        Integer status = (Integer) map.get("status");
        String address = Constants.UNKNOWN;
        if (status == 0) {
            Map result = (Map) map.get("result");
            Map addressInfo = (Map) result.get("ad_info");
            String nation = (String) addressInfo.get("nation");
            String province = (String) addressInfo.get("province");
            String city = (String) addressInfo.get("city");
            if (province.isEmpty()) {
                address = "|" + nation;
            } else {
                address = nation + "|" + province + "|" + city;
            }
        }
        return address;
    }

    /**
     * 获取访问设备
     *
     * @param request 请求
     * @return {@link UserAgent} 访问设备
     */
    public UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    /**
     * 获取IP地址
     *
     * @return 本地IP地址
     */
    public String getHostIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                // 跳过回环接口和未激活的接口
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    // 过滤掉 IPv6 地址
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf(':') == -1) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DEFAULT_LOCAL_IP; // 如果无法获取地址，返回默认值

    }

    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }

    /**
     * 调用腾讯地图API
     * 根据在腾讯位置服务上申请的key进行请求解析ip
     *
     * @param ip ip地址
     * @return
     */
    public String analyzeIp(String ip) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URLConnection connection = getUrlConnection(ip);
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！异常信息为:{}", e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error(String.valueOf(e2));
            }
        }
        return result.toString();
    }

    @NotNull
    private URLConnection getUrlConnection(String ip) throws IOException {
        String url = FORMAT_URL.replace("{}", ip) + API_KEY;
        URL realUrl = new URL(url);
        // 打开和URL之间的链接
        URLConnection connection = realUrl.openConnection();
        // 设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 创建实际的链接
        connection.connect();
        return connection;
    }
}
