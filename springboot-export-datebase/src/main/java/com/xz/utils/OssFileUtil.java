package com.xz.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.poi.util.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OssFileUtil {
//    public String uploadAliyun(MultipartFile file,String fileName) throws IOException {
//        // 1 获取上传需要的固定值
//        String endpoint ="oss-cn-shanghai.aliyuncs.com";      //你的站点
//        String accessKeyId = "<yourAccessKeyId>";  //你的acess_key_id
//        String accessKeySecret = "<yourAccessKeySecret>"; //你的acess_key_secret
//        String bucketName = "<yourBucketName>";       //你的bucket_name
//        //外面获取文件输入流，最后方便关闭
//        InputStream in = file.getInputStream();
//        try {
//            //2 创建OssClient对象
//            OSS ossClient =new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
//            //3 获取文件信息，为了上传
//            // meta设置请求头
//            ObjectMetadata meta = new ObjectMetadata();
//            meta.setContentType("image/jpg");
//            //4 设置知道文件夹
//            ossClient.putObject(bucketName,fileName,in, meta);
//            //5 关闭ossClient
//            ossClient.shutdown();
//            //6 返回上传之后地址，拼接地址
//            String uploadUrl = "https://"+bucketName+"."+endpoint+"/"+fileName;
//            return uploadUrl;
//        }catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }finally {
//            in.close();
//        }
//    }

    public static InputStream getInputStreamByUrl(String strUrl) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    public static void uploadAliyun(String sysPlugin, String path, InputStream inputStream, String contentType) throws IOException {

        String accessId = "LTA";
        String accessKey = "FcGiKBKy4WS4dt";
        String bucketName = "ptcb";
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        try {
            OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
//				objectMetadata.setContentLength();
            ossClient.putObject(bucketName, removeStart(path, "/"), inputStream, objectMetadata);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            inputStream.close();
        }
    }

    public static String uploadAliyun2(String sysPlugin, String path, InputStream inputStream, String contentType) throws IOException {

        String accessId = "LTAI5tFHK";
        String accessKey = "FcGiKBKy4WSaT";
        String bucketName = "ptcb";
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        try {
            //2 创建OssClient对象
            OSS ossClient =new OSSClientBuilder().build(endpoint,accessId,accessKey);
            //3 获取文件信息，为了上传
            // meta设置请求头
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            //4 设置知道文件夹
            ossClient.putObject(bucketName,"testmarkimg/imgmark.png",inputStream, meta);
            //5 关闭ossClient
            ossClient.shutdown();
            //6 返回上传之后地址，拼接地址
            String uploadUrl = "https://"+bucketName+"."+endpoint+"/"+path;
            return uploadUrl;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            inputStream.close();
        }
    }

    private static String removeStart(String str, String remove) {
        if (!isEmpty(str) && !isEmpty(remove)) {
            return str.startsWith(remove) ? str.substring(remove.length()) : str;
        } else {
            return str;
        }
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static InputStream getImageStream(BufferedImage bimage) {
        InputStream is = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bimage, "png", imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;

    }
}