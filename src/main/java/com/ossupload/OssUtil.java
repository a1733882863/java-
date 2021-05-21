//package com.ossupload;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class OssUtil {
//    public void upload(SysPlugin sysPlugin,String path, MultipartFile file, String contentType) throws IOException {
//        if (sysPlugin != null) {
//            String accessId = sysPlugin.getAttribute("accessId");
//            String accessKey = sysPlugin.getAttribute("accessKey");
//            String bucketName = sysPlugin.getAttribute("bucketName");
//            String endpoint = sysPlugin.getAttribute("endpoint");
//            InputStream inputStream = file.getInputStream();
//            try {
//                OSSClient ossClient = new OSSClient(endpoint,accessId, accessKey);
//                ObjectMetadata objectMetadata = new ObjectMetadata();
//                objectMetadata.setContentType(contentType);
//                objectMetadata.setContentLength(file.getSize());
//                ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
//            } catch (Exception e) {
//                throw new IOException(e.getMessage());
//            } finally {
//                inputStream.close();
//            }
//        } else {
//            throw new IOException("无效插件");
//        }
//    }
//}
//
//
//
////if (mchId==null) {
////        SysUser sysUser = sysUserService.getCurrent();
////        mchId = sysUser.getMchId();
////        }
////
////        SysPlugin sysPlugin = sysPluginService.findByPlugin(mchId, "ossPlugin");
////        if (sysPlugin==null) {
////        SysUser sysUser = sysUserService.findByUsername("admin");
////        mchId = sysUser.getMchId();
////        sysPlugin = sysPluginService.findByPlugin(mchId, "ossPlugin");
////        }
////        if (sysPlugin != null) {
////        return CommResult.error("插件没安装");
////        }