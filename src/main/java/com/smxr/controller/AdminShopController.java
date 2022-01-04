package com.smxr.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.primitives.Ints;
import com.smxr.dao.GoodsDao;
import com.smxr.dao.OrdersDao;
import com.smxr.myInterface.InJect;
import com.smxr.pojo.*;
import com.smxr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

/**
 * @author smxr
 * @date 2020/10/19
 * @time 16:32
 */
@RestController
@RequestMapping("/vue")
@Api(tags = "用户商城接口")//说明该类的作用，可以在UI界面上看到的注解，如果tags多个值，会生成多个list
public class AdminShopController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private OrdersDao ordersMapper;
    @Autowired
    private GoodsDao goodsMapper;

    @GetMapping("/info")
    @ApiOperation("vue初始化测试接口")
    public ResponseBean vueTest(){
        return ResponseUtils.Success(null,"admin-shop is for vue test!");
    }
    @GetMapping("/signIn")
    @ApiOperation(value = "vue初始化登录接口",notes = "说明该接口，提示内容") //接口说明
    public ResponseBean vueLogin(@ApiParam(name = "user",value = "账户ID， 参数说明",required = true) @RequestParam String account,
                                 @ApiParam(value = "密码",required = true) @RequestParam String pass){//@ApiParam() 接口数据说明  属性required 是否必须
        if (account==null||pass==null){
            return ResponseUtils.Error(null,"账户密码不能为空！");
        }
        if(!account.equals("772519606@qq.com")||!pass.equals("123456")) return ResponseUtils.Error(null,"账户密码不能为空！");
        System.out.println("账户密码:"+account+pass);
        byte[] bytes = (account + pass).getBytes();
        String string = Base64.getEncoder().encodeToString(bytes);
        System.out.println("string:"+string);
        return ResponseUtils.Success(string);
    }
    @InJect
    @PostMapping("/test")
    @ApiOperation(value = "AOP测试接口",notes = "无返回值，仅供测试使用")
    void spring_test(){
        System.out.println(userService.selectUserByPhoneNumber("123"));
        System.out.println("spring_test() 方法 执行了。。。。。。");
    }
    public void spring_ioc(){
        userService.getUser();
    }
    /*
    * 1.0
    * 简单下载excel
    */
    @InJect
    @RequestMapping("easyexcel")
    public void downLoadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.getParameter() //是获得相应名的数据，如果有重复的名，则返回第一个的值. 一般用于接收一般变量 ，如text类型
//        request.getParameterValues()//是获得如checkbox类(名字相同，但值有多个)的数据。 可以用于接收数组变量 ，如checkobx类型
        Enumeration<String> parameterNames = request.getParameterNames();//获取所有参数名字
        request.getParameterMap();//
        // 设置响应体相关信息
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        String fileName = URLEncoder.encode("用户信息文档","utf-8");
        response.setHeader("Content-disposition","attachment;filename="+ fileName + ".xlsx");
        //输出
        EasyExcel.write(response.getOutputStream(), User.class).sheet("用户模板页").doWrite(userService.getUserList());
    }
    /*
    *1.0
    * 文件上传
    */
    @InJect
    @RequestMapping("uploadexcel") //MultipartFile file
    public void uploadExcel() throws IOException {
        File file = new File("D:\\chromelog\\用户信息文档.xlsx");
        EasyExcel.read(file, User.class, new AnalysisEventListener() {
            @Override
            public void invoke(Object o, AnalysisContext analysisContext) {
                System.out.println("解析数据："+o);//o是表模型数据
                User user=o instanceof User ? ((User) o) : null;
                if (user!=null){
                    System.out.println(user.toString());
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("解析完成");
            }
        }).sheet().doRead();
    }
    @InJect
    @RequestMapping("/placeorder")
    public boolean getPlaceOrder(String commodity,String userID){
        //过滤和预防

        //开启redis事务
        redis.setEnableTransactionSupport(true);
        //watch某个key,当该key被其它客户端改变时,则会中断当前的操作
        redis.watch("");//监视某个key如果这个key被改变是就会中断这个事务

        redis.multi();//事务
        redis.boundValueOps("").decrement(1);//下单成功，商品数量减1
        redis.opsForSet().add("orderNum","");
        List<Object> exec = redis.exec();//执行事务
        if (exec==null||exec.size()==0){
            return false;//执行失败
        }else {
            return true;//执行成功
        }
    }

    /*
    * commodity 商品ID
    * userID   用户ID
    * num 数量
    */
    @Transactional
    @RequestMapping("/seckill")
    public  boolean getSeckill(){
//1.0  20件商品 100人去抢 57个人抢到了
//        update goods set goodsNum=#{goodsNum} where goodsId=#{goodsId}
//2.0  修改sql 减去的地方再SQL语句中而不是再代码上更新数量数据；//20件商品12个抢到了，每人两个，多了2个人的订单goodsNum=-4
//        update goods set goodsNum=goodsNum-#{goodsNum} where goodsId=#{goodsId}
//3.0  修改sql再where语句上做限制
//        update goods set goodsNum=goodsNum-#{goodsNum} where goodsId=#{goodsId} and goodsNum > 0
//        mysqlSql在写入的时候设备是加行级锁的，也就是写入允许一个连接写入，在加上后面的数据限制也就可以预防商品数据为负了

//      上面的1.0和2.0可以通过  synchronized 字段 解决，但会有性能问题
        //第三种为佳

        int commodity=11;
        int num=2;
        String userID="17513234581";
        //1.从数据库获取商品数量
        Goods goods = goodsMapper.selectGoodsById(commodity);
        //2.判断商品是否还有库存
        if (goods.getGoodsNum()>0){
            boolean b = goodsMapper.updateGoodsNumById(goods.getGoodsId(),num);
            if (b){
                Orders orders = new Orders();
                orders.setGoodsId(goods.getGoodsId());
                orders.setGoodsMoney(goods.getGoodsMoney());
                orders.setGoodsNum(num);
                orders.setOrderUser(userID);
                orders.setOrderStatus(0);
                orders.setOrderTime(LocalDateTime.now().toString());
                boolean b1 = ordersMapper.insertOrder(orders);
                if (b1){
                    System.out.println("====== 秒杀成功，请及时完成支付订单");
                    return true;
                }
                else{
                    System.out.println("====== 秒杀成功，订单添加失败！");
                    return false;
                }
            }
            System.out.println("====== 秒杀失败！ ");
            return false;
        }else {
            System.out.println("====== 秒杀失败！ ");
            return false;
        }
    }
    /*
     * commodity 商品ID
     * userID   用户ID
     * num 数量
     */
    @Transactional
    @RequestMapping("/seckills")
    public  boolean getSeckills(){
        int commodity=11;
        int num=1;
        String userID="17513234581";
        //1.从redis中获取商品数量 如果还有商品就减少1个
        if (!redis.hasKey("goods:11"))
            return false;
        //返回减过的值
        Long decrement = redis.opsForValue().decrement("goods:" + "11");
        //2.判断商品是否已被秒杀，是 同步数据库
        if (decrement>=0){
            boolean b = goodsMapper.updateGoodsNumById(11,num);
            if (b){
                Orders orders = new Orders();
                orders.setGoodsId(11);
                orders.setGoodsMoney(BigDecimal.valueOf(234.00));
                orders.setGoodsNum(num);
                orders.setOrderUser(userID);
                orders.setOrderStatus(0);
                orders.setOrderTime(LocalDateTime.now().toString());
                boolean b1 = ordersMapper.insertOrder(orders);
                if (b1){
                    System.out.println("====== 秒杀成功，请及时完成支付订单");
                    return true;
                }
                else{
                    System.out.println("====== 秒杀成功，订单添加失败！");
                    return false;
                }
            }
            System.out.println("====== 秒杀失败！ ");
            return false;
        }else {
            System.out.println("====== 秒杀失败！ ");
            return false;
        }
    }

    public void speak(){
        System.out.println(" chenggongdiaoyong!!! haha  ");
    }
    /*
    *POI处理Excel
    */
    @InJect
    @RequestMapping("poi")
    public void downLoadPoiExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //默认值是100
        //创建07版super实例
        //xls/03版,xlsx/07版,
        SXSSFWorkbook workbook=new SXSSFWorkbook();
        //创建页表 可以添加名字，没有名字默认名字是 Sheet0 且只有1个页表
         Sheet sheet = workbook.createSheet();
        //创建行数；
        for (int i = 0; i < 60000; i++) {
             Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                 row.createCell(j).setCellValue(i+j);
            }
        }
        System.out.println("数据填充：over");

        // 设置响应体相关编码信息
        response.setCharacterEncoding("utf-8");
        //设置返回值类型
        response.setContentType("application/vnd.ms-excel");
        //文件名 编码utf-8
        String fileName = URLEncoder.encode("用户信息文档","utf-8");
        //告诉浏览器这个文件的名字和类型，attachment：作为附件下载；inline：直接打开     xls/03版,xlsx/07版,
        response.setHeader("Content-disposition","attachment;filename="+ fileName + ".xlsx");
        //输出
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            outputStream.flush();
        }finally {
            workbook.dispose();
        }
    }
    @InJect
    @RequestMapping("upload")//MultipartFile file
    public void upLoadPoiExcel(){
        //可以有名字判断使用那种类型的对象来解析Excel ，
        //final String name = file.getName();
        //模拟路径 D:\chromelog
        String dirFileName="D:\\chromelog\\用户信息文档.xlsx";
        try (FileInputStream fileInputStream = new FileInputStream(dirFileName)) {
            //可以有名字判断使用那种类型的对象来解析Excel xls/03版HSSFWorkbook,xlsx/07版/XSSFWorkbook,
            //Workbook workbook=new HSSFWorkbook(fileInputStream);//xls/03版
           Workbook workbook=new XSSFWorkbook(fileInputStream);//xlsx/07版
            //获取sheet的数量
            int numberOfSheets = workbook.getNumberOfSheets();
            workbook.getSheetAt(0);//得到对应的sheet页 从0开始
            System.out.println("sheet的数量: "+numberOfSheets);

            //由Excel的Sheet 的默认名字得到Sheet
            Sheet sheet = workbook.getSheet("Sheet0");
            //获取到sheet的Row行数;获取到最后一行的行数 (Row的index是从0开始的)
            //获取的最后一个rowNum是下标，得到的数字应该加1
            //physicalNumberOfRows 这个方法更号一些
            int lastRowNum = sheet.getLastRowNum();
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            System.out.println("获取到row的行数"+lastRowNum+" physicalNumberOfRows"+physicalNumberOfRows);

            for (int i = 0; i < physicalNumberOfRows; i++) {

                Row row = sheet.getRow(i);
                //如果有一行为空就跳过,否则会报空指针异常
                if (row==null)
                    continue;
                //获取到最后一个cell数的下标
                short lastCellNum = row.getLastCellNum();
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();

                System.out.println("获取到call的数"+lastCellNum+"  physicalNumberOfCells: "+physicalNumberOfCells);
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j);
                    //非空处理
                    if (cell!=null)
                        System.out.println("第"+i+"row的"+"第"+j+"个: "+cell.getNumericCellValue());
                }
                //在这里就可以持久化或处理数据了
            }

        } catch (IOException e) {
        }


    }
}
