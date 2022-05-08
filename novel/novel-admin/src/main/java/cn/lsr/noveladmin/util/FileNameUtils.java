package cn.lsr.noveladmin.util;

import java.util.UUID;

/**
 * 文件名生成类
 */

public class FileNameUtils {
    /**
     * 获取文件后缀
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 利用UUID生成新的文件名
     */
    public static String getFileName(String fileOriginName){
        return UUID.randomUUID().toString().replace("-", "") + FileNameUtils.getSuffix(fileOriginName);
    }
}
