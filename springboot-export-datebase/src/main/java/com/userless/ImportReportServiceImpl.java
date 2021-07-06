//package com.xz.utils;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.FutureTask;
//
///**
// * 类功能描述：报表导入服务实现
// *
// * @author WangXueXing create at 19-3-19 下午1:43
// * @version 1.0.0
// */
//@Service
//public class ImportReportServiceImpl extends ReaderFileListener<Object> {
//    private final Logger LOGGER = LoggerFactory.getLogger(ImportReportServiceImpl.class);
//    //@Value("${READ_COL_NUM_ONCE}")
//    private String readColNum = "100";
//    //@Value("${REPORT_IMPORT_RECEIVER}")
//    private String reportImportReceiver;
//    /**
//     * 财务报表导入接口
//     */
//    //private ImportService service = new ImportServiceClient();
//
//    /**
//     * 读取文件内容
//     * @param file
//     */
//    public void readTxt(File file, Object importRequest) throws Exception {
//        this.setOtherParams(importRequest);
//        //ReadFile readFile = new ReadFile();
//        BatchReadFile readFile = new BatchReadFile();
//
//        try(FileInputStream fis = new FileInputStream(file)){
//            int available = fis.available();
//            long maxThreadNum = 3L;
//            // 线程粗略开始位置
//            long i = available / maxThreadNum;
//
//            this.setRowList(new ArrayList<>());
//            //StaffSimpInfo staffSimpInfo = ((StaffSimpInfo)UserSessionHelper.getCurrentUserInfo().getData());
//            //String finalReportReceiver = getEmail(staffSimpInfo.getEmail(), reportImportReceiver);
//            this.setReadColNum(Integer.parseInt(readColNum));
//            this.setEncode(readFile.CHARSET_UTF8);
//            //这里单独使用一个线程是为了当maxThreadNum大于1的时候，统一管理这些线程
//            new Thread(()->{
//                Thread preThread = null;
//                FutureTask futureTask = null ;
//                try {
//                    for (long j = 0; j < maxThreadNum; j++) {
//                        //计算精确开始位置
//                        long startNum = j == 0 ? 0 : readFile.getStartNum(file, i * j);
//                        long endNum = j + 1 < maxThreadNum ? readFile.getStartNum(file, i * (j + 1)) : -2L;
//
//                        //具体监听实现
//                        preThread = new ReadFileThread(this, startNum, endNum, file.getPath(), preThread);
//                        futureTask = new FutureTask(preThread, new Object());
//                        futureTask.run();
//                    }
//                    if(futureTask.get() != null) {
//                        //EmailUtil.sendEmail(EmailUtil.REPORT_IMPORT_EMAIL_PREFIX, finalReportReceiver, "导入报表成功", "导入报表成功" );  //todo 等文案
//                    }
//                } catch (Exception e){
//                    futureTask.cancel(true);
////                    try {
////                        EmailUtil.sendEmail(EmailUtil.REPORT_IMPORT_EMAIL_PREFIX, finalReportReceiver, "导入报表失败", e.getMessage());
////                    } catch (Exception e1){
////                        //ignore
////                        LOGGER.error("发送邮件失败", e1);
////                    }
//                    //LOGGER.error("导入报表类型:"+importRequest.getReportType()+"失败", e);
//                } finally {
//                    futureTask.cancel(true);
//                }
//            }).start();
//        } catch (Exception e) {
//            throw new Exception();
//        }
//    }
//
//    private String getEmail(String infoEmail, String reportImportReceiver){
//        if(StringUtils.isEmpty(infoEmail)){
//            return reportImportReceiver;
//        }
//        return infoEmail;
//    }
//
//    /**
//     * 每批次调用导入接口
//     * @param stringList
//     * @throws Exception
//     */
//    @Override
//    public void output(List<String> stringList) throws Exception {
//        System.out.println(stringList);
//
////
////
////        ImportRequest importRequest = this.getOtherParams();
////        List<List<String>> dataList = stringList.stream()
////                .map(x->Arrays.asList(x.split(ReadFile.SEPARATOR_COMMA)).stream().map(String::trim).collect(Collectors.toList()))
////                .collect(Collectors.toList());
////        LOGGER.info("上传数据:{}", dataList);
////        importRequest.setDataList(dataList);
//////        LOGGER.info("request对象：{}",importRequest, "request增加请求字段：{}", importRequest.data);
////        ImportResponse importResponse = service.batchImport(importRequest);
////        LOGGER.info("===========SUCESS_CODE======="+importResponse.getCode());
////        //导入错误,输出错误信息
////        if(!Constants.SUCESS_CODE.equals(importResponse.getCode())){
////            LOGGER.error("导入报表类型:"+importRequest.getReportType()+"失败","返回码为：", importResponse.getCode() ,"返回信息：",importResponse.getMessage());
////            throw new RuntimeException("导入报表类型:"+importRequest.getReportType()+"失败"+"返回码为："+ importResponse.getCode() +"返回信息："+importResponse.getMessage());
////        }
//////        if(importResponse.data != null && importResponse.data.get().get("batchImportFlag")!=null) {
//////            LOGGER.info("eywa-service请求batchImportFlag不为空");
//////        }
////        importRequest.setData(importResponse.data);
//    }
//}