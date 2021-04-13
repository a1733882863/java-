package com.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


//订单结算金额
public class excel2 {
    public static SXSSFWorkbook workbook = null;
    public static Connection conn = null;
    public static Statement pst = null;
    public static OutputStream outputStream = null;
    public static List<ReportDO> reportDOList = null;

    public static void main(String[] args) throws Exception {
        conn = getConnection("rm-bp1j8qv14g33prh8e8o.mysql.rds.aliyuncs.com","ptcb","Ptcb1234");
        String basedir = "C:\\Users\\xz\\Desktop\\报表2";
        //在指定目录下生成文件夹
        //List<String> dateDirName = createXlsDirByDate(basedir, "2021-02-04", "2021-02-14");
        //查询商家名称
        String date1 = "2021-02-04";
        String date2 = "2021-02-14";
        List<String> enterprise_ids = queryShopNames(date1,date2);
        //循环生成xls,根据商家的名字
        generationXls(basedir,enterprise_ids,date1,date2);

    }

    //
    //添加表头内容
    public static void addHeadContent(SXSSFSheet sheet, CellStyle cellStyle, Integer rowNum) {

        SXSSFRow xssfRow = sheet.createRow(0);
        //添加表头内容
        SXSSFCell headCell = xssfRow.createCell(0);
        headCell.setCellValue("商家名称 ");
        headCell.setCellStyle(cellStyle);

        //设置每列的参数
        headCell = xssfRow.createCell(1);
        headCell.setCellValue("订单日期");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(2);
        headCell.setCellValue("订单号");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(3);
        headCell.setCellValue("包装费");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(4);
        headCell.setCellValue("商家补贴");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(5);
        headCell.setCellValue("政府补助");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(6);
        headCell.setCellValue("平台补贴");
        headCell.setCellStyle(cellStyle);
        //商户名	订单日期	订单序号	订单类型	订单号	结算金额	商品费用	包装费用	平台补贴	服务费用	配送费用	商家补贴
        headCell = xssfRow.createCell(7);
        headCell.setCellValue("顾客实付金额");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(8);
        headCell.setCellValue("商品金额");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(9);
        headCell.setCellValue("结算金额");
        headCell.setCellStyle(cellStyle);

        headCell = xssfRow.createCell(10);
        headCell.setCellValue("配送费用");
        headCell.setCellStyle(cellStyle);




    }
    private static List<String> createXlsDirByDate(String basedir, String s, String s1) throws ParseException {
        //输入想要查询的日期范围, startDate  endDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startDate1 = sdf.parse(s);
        java.util.Date endDate1 = sdf.parse(s1);
        String startDate = sdf.format(startDate1);
        String endDate = sdf.format(endDate1);
        int diffrenceNum = differentDays(startDate1, endDate1);
        Calendar calendar = new GregorianCalendar();

        //java.util.Date tempDate = new Date(startDate.getTime());
        java.util.Date tempDate = sdf.parse(String.valueOf(startDate));
        //先for日期
        for (int i = 0;i <= diffrenceNum; i++){

            //根据日期创建文件夹
            String finaldir = basedir +"\\"+ sdf.format(tempDate) + "\\";
            File file = new File(finaldir);
            if (!file.exists()) {
                file.mkdirs();
            }
            calendar.setTime(tempDate);
            calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
            tempDate = calendar.getTime();
        }
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        tempDate = calendar.getTime();
        System.out.println("日期操作正确否?" + tempDate.compareTo(endDate1));

        List<String> dirNames = new ArrayList();
        File fileParent = new File(basedir);
        File fa[] = fileParent.listFiles();//用数组接收  F:\笔记总结\C#, F:\笔记总结\if语句.txt
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            if (fs.isDirectory()) {
                dirNames.add(fs.getName());
                System.out.println(fs.getName() + " [目录]");//如果是目录就输出
            }
        }
        return dirNames;
    }


    private static void generationXls(String basedir, List<String> shopNames,String date1,String date2) throws Exception {

        //开始循环创建xls
        for (String id: shopNames) {

            //设置表头行对象

            //创建一个表格
            //SXSSFWorkbook workbook = new SXSSFWorkbook();
            workbook = tableFactory();
            //创建一个工作表
            SXSSFSheet sheet = sheetFactory(workbook,"sheet1");
            //设置工作表宽度等
            setSheet(sheet);
            //设置单元格格式居中
            CellStyle cellStyle = styleFactory(workbook);
            //设置格式
            setStyle(cellStyle);
            addHeadContent(sheet,cellStyle,0);

            String tempSql = getReportDOSql(id,date1,date2);
            ResultSet resultSet = pst.executeQuery(tempSql);
            List<ReportDO2> reportDOList = new ArrayList<ReportDO2>();

            while (resultSet.next()){
                ReportDO2 reportDO2 = new ReportDO2();
                reportDO2.setName1(resultSet.getString(1));
                reportDO2.setOrder_date2(resultSet.getString(2));
                reportDO2.setOrder_num3(resultSet.getString(3));
                reportDO2.setPackaging_fee4(resultSet.getString(4));
                reportDO2.setShop_fee5(resultSet.getString(5));
                reportDO2.setCoupon_swj6(resultSet.getString(6));
                reportDO2.setCoupon_pla7(resultSet.getString(7));
                reportDO2.setReal_pay8(resultSet.getString(8));
                reportDO2.setProduct_pay9(resultSet.getString(9));
                reportDO2.setDistribute10(resultSet.getString(10));
                reportDO2.setRider_fee11(resultSet.getString(11));

                reportDOList.add(reportDO2);
                //System.out.println(resultSet.getString(1) + "/t" +    resultSet.getString(2));
            }

            String xlsFilePath = null;
            if (reportDOList != null && reportDOList.size() > 1){
                xlsFilePath = basedir +"\\" + reportDOList.get(0).getName1() + ".xlsx";
            } else {
                System.out.println("1");
                continue;
            }
            //换行
            int b = 1;
            for (ReportDO2 re : reportDOList) {


                SXSSFRow xssfRow = sheet.createRow(b++);
                //创建单元格,并添加值
                SXSSFCell cell = xssfRow.createCell(0);
                cell.setCellValue(re.getName1());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(1);
                cell.setCellValue(re.getOrder_date2());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(2);
                cell.setCellValue(re.getOrder_num3());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(3);
                cell.setCellValue(re.getPackaging_fee4());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(4);
                cell.setCellValue(re.getShop_fee5());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(5);
                cell.setCellValue(re.getCoupon_swj6());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(6);
                cell.setCellValue(re.getCoupon_pla7());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(7);
                cell.setCellValue(re.getReal_pay8());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(8);
                cell.setCellValue(re.getProduct_pay9());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(9);
                cell.setCellValue(re.getDistribute10());
                cell.setCellStyle(cellStyle);

                cell = xssfRow.createCell(10);
                cell.setCellValue(re.getRider_fee11());
                cell.setCellStyle(cellStyle);

            }
            outputStream = new FileOutputStream(xlsFilePath);
            workbook.write(outputStream);
        }

        //查询所有这一天有订单的商家名称和id
    }

    private static String getReportDOSql(String id, String date1, String date2) {
//        return "SELECT\n" +
//                " d. NAME 商家名称,\n" +
//                " a.create_date 订单日期,\n" +
//                " a.sn 订单号,\n" +
//                " a.packaging_fee 包装费,\n" +
//                " a.discount 商家补贴,\n" +
//                "IF (c.amount, c.amount, 0) 优惠券金额,\n" +
//                " amount_paid 顾客实付金额,\n" +
//                " a.sub_price 商品金额,\n" +
//                " a.distribution_amount 结算金额,\n" +
//                " a.rider_fee 配送费用\n" +
//                "FROM\n" +
//                " wx_order a\n" +
//                "LEFT JOIN wx_coupon_code b ON a.coupon_code_id = b.id\n" +
//                "LEFT JOIN wx_coupon c ON b.coupon_id = c.id\n" +
//                "LEFT JOIN wx_enterprise d ON a.enterprise_id = d.id\n" +
//                "WHERE\n" +
//                " a.order_status IN (1, 2, 4)\n" +
//                "AND DATE_FORMAT(a.create_date, '%Y-%m-%d') >= STR_TO_DATE('"+date1+"', '%Y-%m-%d')\n" +
//                "AND DATE_FORMAT(a.create_date, '%Y-%m-%d') <= STR_TO_DATE('"+date2+"', '%Y-%m-%d')\n" +
//                "AND a.payment_status =1\n" +
//                "AND a.enterprise_id = "+ id;
        return "SELECT\n" +
                " d. NAME 商家名称,\n" +
                " a.create_date 订单日期,\n" +
                " a.sn 订单号,\n" +
                " a.packaging_fee 包装费,\n" +
                " a.discount 商家补贴,\n" +
                " IF (c.amount, c.amount, 0) 政府补助,\n" +
                " 0 平台补贴,\n" +
                " amount_paid 顾客实付金额,\n" +
                " a.sub_price 商品金额,\n" +
                " a.sub_price+a.rider_fee+a.packaging_fee-a.discount 结算金额,\n" +
                " a.rider_fee 配送费用\n" +
                "FROM\n" +
                " wx_order a\n" +
                "LEFT JOIN wx_coupon_code b ON a.coupon_code_id = b.id\n" +
                "LEFT JOIN wx_coupon c ON b.coupon_id = c.id\n" +
                "LEFT JOIN wx_enterprise d ON a.enterprise_id = d.id\n" +
                "left join wx_payment e on a.sn = e.order_sn and e.tran_sn is not null and STRCMP(e.tran_sn,'null') != 0\n" +
                "WHERE\n" +
                " a.order_status IN (1, 2, 4)\n" +
                "AND DATE_FORMAT(a.create_date, '%Y-%m-%d') >= STR_TO_DATE('2021-02-04', '%Y-%m-%d')\n" +
                "AND DATE_FORMAT(a.create_date, '%Y-%m-%d') <= STR_TO_DATE('2021-02-14', '%Y-%m-%d')\n" +
                "AND a.payment_status =1\n" +
                "AND c.amount > 10\n" +
                "AND a.enterprise_id = " + id +
                "\n union all\n" +
                "\n" +
                "SELECT\n" +
                " d. NAME 商家名称,\n" +
                " a.create_date 订单日期,\n" +
                " a.sn 订单号,\n" +
                " a.packaging_fee 包装费,\n" +
                " a.discount 商家补贴,\n" +
                " 0 政府补助,\n" +
                " IF (c.amount, c.amount, 0) 平台补贴,\n" +
                " amount_paid 顾客实付金额,\n" +
                " a.sub_price 商品金额,\n" +
                " a.distribution_amount 结算金额,\n" +
                " a.rider_fee 配送费用\n" +
                "FROM\n" +
                " wx_order a\n" +
                "LEFT JOIN wx_coupon_code b ON a.coupon_code_id = b.id\n" +
                "LEFT JOIN wx_coupon c ON b.coupon_id = c.id\n" +
                "LEFT JOIN wx_enterprise d ON a.enterprise_id = d.id\n" +
                "left join wx_payment e on a.sn = e.order_sn and e.tran_sn is not null and STRCMP(e.tran_sn,'null') != 0\n" +
                "WHERE\n" +
                " a.order_status IN (1, 2, 4)\n" +
                "AND DATE_FORMAT(a.create_date, '%Y-%m-%d') >= STR_TO_DATE('2021-02-04', '%Y-%m-%d')\n" +
                "AND DATE_FORMAT(a.create_date, '%Y-%m-%d') <= STR_TO_DATE('2021-02-14', '%Y-%m-%d')\n" +
                "AND a.payment_status =1\n" +
                "AND (c.amount is null or c.amount <= 10)\n" +
                "AND a.enterprise_id = "+ id;
    }

    private static String getEnterpriseIdSqlByDate(String date1, String date2) {
        return "SELECT distinct enterprise_id from wx_order where STR_TO_DATE('" + date1 + "','%Y-%m-%d') <= DATE_FORMAT(create_date,'%Y-%m-%d') " +
                "and STR_TO_DATE('"+date2+"','%Y-%m-%d') >= DATE_FORMAT(create_date,'%Y-%m-%d') and order_status in (1,2,4)";
//        return "select enterprise_id from wx_order";
    }



    private static List<String> queryShopNames(String date1, String date2) throws Exception {
        //获取查询sql
        List<String> shopNames = new ArrayList<>();
        String sql = getEnterpriseIdSqlByDate(date1,date2);
//        String sql = "SELECT enterprise_id from wx_order where STR_TO_DATE('2021-02-14','%Y-%m-%d') = DATE_FORMAT(create_date,'%Y-%m-%d') \n" +
//                "and order_status in (1,2,4) GROUP BY enterprise_id";
        pst = conn.createStatement();
        ResultSet rs = pst.executeQuery(sql);

        while(rs.next()) {
            shopNames.add(rs.getString(1));
        }
        return shopNames;
    }


    public static Connection getConnection(String ipdress,String user, String password) {

        String url = "jdbc:mysql://"+ipdress+"/ptcb?"
                + "user="+user+"&password="+password+"&useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=UTC";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动

            //System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int differentDays(java.util.Date date1,java.util.Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    //创建一个表格
    //public static SXSSFWorkbook workbook = null;
    //创建一个工作表
    //SXSSFSheet sheet = null;
    //生成表头
    public static SXSSFWorkbook tableFactory() {
        return  new SXSSFWorkbook();
    }

    //创建一个工作表
    public static SXSSFSheet sheetFactory(SXSSFWorkbook workbook,String sheetName) {
        return workbook.createSheet(sheetName);
    }

    //获取单元格格内格式对象
    public static CellStyle  styleFactory(SXSSFWorkbook workbook) {
        return workbook.createCellStyle();
    }

    //设置单元格内格式
    public static void setStyle(CellStyle  cellStyle) {
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
    }

    //设置sheet的样式大小等
    public static void setSheet(SXSSFSheet sheet) {
        // 单元格列宽 0代表列位置
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 3000);
        sheet.setColumnWidth(9, 3000);
        sheet.setColumnWidth(10, 3000);
        sheet.setColumnWidth(12, 3000);
        sheet.setColumnWidth(13, 3000);
        sheet.setColumnWidth(14, 3000);
    }


}

