package com.pjj.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class OssFileUtil {
    public String uploadAliyun(MultipartFile file, String fileName) throws IOException {
        // 1 获取上传需要的固定值
        String endpoint ="oss-cn-beijing.aliyuncs.com";      //你的站点
        String accessKeyId = "LTAI4GFyeVmJiifVpFQshDKh";  //你的acess_key_id
        String accessKeySecret = "DujfRYOI4aY5HwkbhguB0xIG1MxwID"; //你的acess_key_secret
        String bucketName = "pujunjuan";       //你的bucket_name
        String endpointAlias ="pujunjuan.oss-cn-beijing.aliyuncs.com"; //返回域名
        //外面获取文件输入流，最后方便关闭
        InputStream in = file.getInputStream();
        try {
            //2 创建OssClient对象
            OSS ossClient =new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
            //3 获取文件信息，为了上传
            // meta设置请求头
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            //4 设置知道文件夹
            ossClient.putObject(bucketName,fileName,in, meta);
            //5 关闭ossClient
            ossClient.shutdown();
            //6 返回上传之后地址，拼接地址
            String uploadUrl = "https://"+endpointAlias+"/"+fileName;
            return uploadUrl;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            in.close();
        }
    }


    public String showPic(String fileName){
        String endpointAlias ="oss-cn-beijing.aliyuncs.com"; //返回域名
        String uploadUrl = "https://"+endpointAlias+"/"+fileName;
        return uploadUrl;

    }


    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")){
            return "application/pdf";
        }
        return "images/jpg";
    }

}
