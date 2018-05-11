package com.test;

import java.io.*;

/**
 * Created by books on 2017/9/14.
 */
public class GetMac {

    /**
     * java获取客户端网卡的MAC地址
     *
     * @param args
     */
    public static void main(String[] args) {
        GetMac get = new GetMac();
        System.out.println("1 = "+get.getMAC());
        System.out.println("2 = "+get.getMAC("127.0.0.1"));
    }

    // 1.获取客户端ip地址( 这个必须从客户端传到后台)：
    // jsp页面下，很简单，request.getRemoteAddr() ;
    // 因为系统的VIew层是用JSF来实现的，因此页面上没法直接获得类似request，在bean里做了个强制转换

    // public String getMyIP() {
    // try {
    // FacesContext fc = FacesContext.getCurrentInstance();
    // HttpServletRequest request = (HttpServletRequest) fc
    // .getExternalContext().getRequest();
    // return request.getRemoteAddr();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return "";
    // }

    // 2.获取客户端mac地址
    // 调用window的命令，在后台Bean里实现 通过ip来获取mac地址。方法如下：

    // 运行速度【快】
    public String getMAC() {
        String mac = null;
        try {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c ipconfig/all");

            InputStream is = pro.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String message = br.readLine();

            int index = -1;
            while (message != null) {
                if ((index = message.indexOf("Physical Address")) > 0) {
                    mac = message.substring(index + 36).trim();
                    break;
                }
                message = br.readLine();
            }
//            System.out.println(mac);
            br.close();
            pro.destroy();
        } catch (IOException e) {
            System.out.println("Can't get mac address!");
            return null;
        }
        return mac;
    }

    // 运行速度【慢】
    public String getMAC(String ip) {
        String str = null;
        String macAddress = null;
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; true;) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str
                                .substring(str.indexOf("MAC Address") + 14);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
        return macAddress;
    }
}
