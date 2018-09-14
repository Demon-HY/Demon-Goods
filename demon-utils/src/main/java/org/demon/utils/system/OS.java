package org.demon.utils.system;

public class OS {
    /** 
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等. 
     */  
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();  
    }

    /**
     * 根据操作系统自动获取CPU和主板的ID信息并返回
     */
    public static String getSystemInfo() {
        String os = getOSName().toLowerCase();
        if (os.startsWith("windows")) {
            String cpuWindows = Windows.getWindowsCPUID();
            String biosWindows = Windows.getWindowsBoardID();

            return String.format("%s%s", cpuWindows, biosWindows);
        } else {
            String cpuLinux = Linux.getLinuxCPUID();
            String biosLinux = Linux.getLinuxBoardID();

            return String.format("%s%s", cpuLinux, biosLinux);
        }
    }
}