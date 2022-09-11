package com.client;

import cn.hutool.core.util.StrUtil;
import java.util.Arrays;

public class TftpUtil {
    static final String TFTP_SHELL = "tftclient -d{} -h{} -r{} {} -t{}";
    /**
     * 文件路径：根目录/用户/tftp文件目录/日期/批次流水号/
     */
    static final String FILE_PATH = "/{}/{}/data/{}/{}/";

    public static String getShell(String dType, String hHostNo, String srvFilenName, String clientFileName, String tradeCode) {
        String shell = StrUtil.format(TFTP_SHELL, dType, hHostNo, srvFilenName, clientFileName, tradeCode);
        return shell;
    }

    public String getPath(String addPath1, String addPath2) {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        /**
         * ex:file:/home/kpms/task-online/task-online-0.0.1-SNAPSHOT.jar!/BOOT-INF/lib/batch-common-0.0.1-SNAPSHOT.jar!/
         */
//        log.info("路径：：{}",path);
        String[] units = path.split("/");
        log.info(Arrays.toString(units));
        String finalPath = StrUtil.format(FILE_PATH, units[1], units[2], addPath1, addPath2);
        return finalPath;
    }
}
