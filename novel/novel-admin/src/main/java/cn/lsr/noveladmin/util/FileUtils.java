package cn.lsr.noveladmin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传工具包
 */
public class FileUtils {
    //生成新的文件名或者原文件名和其对应的绝对路径
    public static List<String> upload(String path, String fileName){
        String ansName = (fileName == null || fileName.equals("")) ? "" : FileNameUtils.getFileName(fileName);
        String realPath = path + "/" + ansName;
        List<String> res = new ArrayList<>();
        //使用原文件名
//        String ansName = fileName;
//        String realPath = path + "/" + ansName;
        res.add(ansName);
        res.add(realPath);
        return res;
    }

    //生成图片
    public static void genPic(MultipartFile file, String realPath){
        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //删除图片
    public static boolean deletePic(String realPath){
        File dest = new File(realPath);
        return dest.delete();
    }

    //仅保留图片名称
    public static String retainPicName(String mapperPath){
        int n = mapperPath.length();
        if(mapperPath == null || n == 0) return null;
        int index = mapperPath.lastIndexOf('/');
        return mapperPath.substring(index + 1, n);
    }

    //根据两个文件的真实路径将其中的一个文件的名字和内容都改为另一个文件的样式（本地只有一个新的文件(由旧的变化而来)）
    public static boolean oldToNewFileByRealPath(String oldRealPath, String newRealPath){
        File oldFile = new File(oldRealPath);
        File newFile = new File(newRealPath);
        return oldFile.renameTo(newFile);
    }

}
