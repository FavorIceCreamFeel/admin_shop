package com.smxr;

import com.google.common.primitives.Ints;
import com.smxr.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AdminShopApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        System.out.println(redisTemplate.execute(RedisConnectionCommands::ping));
        String data1 = stringRedisTemplate.opsForValue().get("data1");
        System.out.println(data1);
        stringRedisTemplate.opsForValue().set("10:31:string","练习redis");
        stringRedisTemplate.opsForValue().set("data5","");
        stringRedisTemplate.opsForValue().set("10:31:string","练习redis");
        System.out.println(stringRedisTemplate.opsForValue().get("10:31:string"));
        System.out.println(stringRedisTemplate.getExpire("data5")+"");   //获取过期时间 数据不存在返回值是 -2

    }
    @Test
    void contextLoadsRedis(){
        stringRedisTemplate.opsForValue().set("data5","springdata",60, TimeUnit.SECONDS); //设置过期时间
        stringRedisTemplate.opsForValue().set("data6","springdata1",60,TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.getExpire("data5"));  //对已经存在的数据 并没有定时的数据返回 -1
        System.out.println(stringRedisTemplate.getExpire("data6"));  //对不存在的数据使用返回：-2
        //对有过期时间的数据 -修改-> 没有过期时间      true  对已经是永存的使用返回：false
        System.out.println(stringRedisTemplate.persist("data5"));
        // 输出结果： springdata-redis  原数据：springdata
        stringRedisTemplate.opsForValue().set("data5","-redis",10);// 覆盖/重启  可以添加内容，参数3在第几位添加
        stringRedisTemplate.opsForValue().append("data5","-redis"); //追加 内容  在末尾
        //是否存在 不存在就创建并设置 如同setnx  返回值：已经存在 false，不存在 true
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("data4", "nihaoyawoshilailianside");
        Boolean abBoolean = stringRedisTemplate.opsForValue().setIfPresent("data3", "heheheheehehehe");
        System.out.println(aBoolean);
        Long data5 = stringRedisTemplate.opsForValue().size("data3"); //获取长度
        System.out.println(stringRedisTemplate.opsForValue().get("data3"));
    }
    @Test
    void contextLoadsRedis_String(){
        Map<String,String> maps = new HashMap<String, String>();
        maps.put("multi1","multi1");
        maps.put("multi2","multi2");
        maps.put("multi3","multi3");
//        为多个键分别设置它们的值     一次设置多个 string类型的 K-V
        stringRedisTemplate.opsForValue().multiSet(maps);
        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
//        获取多个值                一次获取多个 string类型的 K-V
        System.out.println(stringRedisTemplate.opsForValue().multiGet(keys));
//        设置新值，返回旧值
        System.out.println(stringRedisTemplate.opsForValue().getAndSet("data4","shezhide jlogd"));
//        incr +1 返回加过后的值
        System.out.println(stringRedisTemplate.opsForValue().increment("data"));
        System.out.println(stringRedisTemplate.opsForValue().append("data3","data3tianjia shiyong data  app"));
    }
    @Test
    void contextLoadsRedis_hash(){
        stringRedisTemplate.opsForHash().put("redisHash","name","tom"); //添加一个哈希对象(hash) 一个属性
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name","Tom");
        stringStringHashMap.put("age","23");
        stringStringHashMap.put("sex","mao");
        stringRedisTemplate.opsForHash().putAll("redisHash",stringStringHashMap);//添加一个哈希对象(hash) 属性值为Map键值对
        //添加一个哈希对象(hash)的属性值，存在false，不存在 true
        stringRedisTemplate.opsForHash().putIfAbsent("redisHash","adree","jerry Home");
        stringRedisTemplate.opsForHash().get("redisHash","adree"); //从键中的哈希获取给定hashKey的属性值
        stringRedisTemplate.opsForHash().entries("redisHash");   //获取指定hash对象所有的 属性:值
        stringRedisTemplate.opsForHash().keys("redisHash");     //获取指定hash对象所有的 属性
        stringRedisTemplate.opsForHash().values("redisHash");  //获取指定hash对象所有的 属性值
        stringRedisTemplate.opsForHash().hasKey("redisHash","name");//判断指定hash对象的 属性 是否存在 存在：true 不存在：false
        stringRedisTemplate.opsForHash().increment("redisHash","age",1);   //通过给定的delta增加散列hashKey的值（整型）;double类型类似
        ArrayList arrayList = new ArrayList();
        arrayList.add("name");
        arrayList.add("age");
        arrayList.add("adree");
        stringRedisTemplate.opsForHash().multiGet("redisHash",arrayList);  //得到多个属性值，参数是集合
        stringRedisTemplate.opsForHash().delete("redisHash","sex");//删除指定hash对象的 属性值
        //匹配获取键值对，ScanOptions.NONE为获取全部键对，ScanOptions.scanOptions().match("hash-key2").build()匹配获取键位map1的键值对,不能模糊匹配
        Cursor<Map.Entry<Object, Object>> redisHash = stringRedisTemplate.opsForHash().scan("redisHash", ScanOptions.scanOptions().build());
        //相当于得到迭代器
        while (redisHash.hasNext()){
            Map.Entry<Object, Object> next = redisHash.next();
            System.out.println(next.getKey());
            System.out.println(next.getValue());
        }

        stringRedisTemplate.opsForHash().lengthOfValue("redisHash","name");//得到属性值的长度
        stringRedisTemplate.opsForHash().size("redisHash");  //获取属性的个数
        RedisOperations<String, ?> operations = stringRedisTemplate.opsForHash().getOperations();  //不知道什么鬼
    }
    @Test
    void contextLoadsRedis_list(){
//        类似于list集合，元素可重复
        Long aLong = stringRedisTemplate.opsForList().leftPush("redisList", "javaScript"); //在链表首(即下标为0)添加一个元素  返回元素数量
        //在链表首 添加多个元素 返回元素数量
        Long aLong1 = stringRedisTemplate.opsForList().leftPushAll("redisList", "c", "c#", "golong");
        //三个参数 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
        stringRedisTemplate.opsForList().leftPop("redisList");  //移除获取第一个元素,下标为0的元素
//        stringRedisTemplate.opsForList().leftPushIfPresent("redisList","")
        stringRedisTemplate.opsForList().rightPush("redisList","vue");  //在链表末尾(即下标为最后一个)添加一个元素  返回元素数量
        //三个参数 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
        stringRedisTemplate.opsForList().rightPop("redisList");//移除获取最后一个元素,下标为0的元素
        stringRedisTemplate.opsForList().rightPushAll("redisList", "c", "c#", "golong");//在链表末尾 添加多个元素 返回元素数量
//        stringRedisTemplate.opsForList().rightPushIfPresent();
        //用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回。 4个参数：设置超时时间
        stringRedisTemplate.opsForList().rightPopAndLeftPush("redisList","redisList2");
        stringRedisTemplate.opsForList().index("redisList",3);   //通过索引获取列表中的元素
        stringRedisTemplate.opsForList().range("redisList",0,-1);   //遍历list集合链表 0是开始位置，-1是结束位置(-1为直到结束)
//        count> 0：删除等于从头到尾移动的值的元素。count <0：删除等于从尾到头移动的值的元素。count = 0：删除等于value的所有元素。
        stringRedisTemplate.opsForList().remove("redisList",0,"java"); //移除 redisList 中的所有 java 元素
        stringRedisTemplate.opsForList().set("redisList",2,"vue"); //在列表中index的位置设置value值
        stringRedisTemplate.opsForList().size("redisList");  //返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0
        //修剪现有列表，使其返回 只包含指定的指定范围的元素，起始和停止都是基于0的索引   案例：从1开始 -1是到结束；如果是从0开始是否相当于遍历
        stringRedisTemplate.opsForList().trim("redisList",1,-1);
        System.out.println(aLong);
    }
    @Test
    void contextLoadsRedis_set(){
//        无序集合中添加元素 一个或多个成员，返回添加个数
         stringRedisTemplate.opsForSet().add("redisSet", "1", "2", "3", "4", "5");
//        移除集合中一个或多个成员 返回移除个数  当集合为空时 集合不存在
        Long redisSet = stringRedisTemplate.opsForSet().remove("redisSet","1", "2", "3", "4", "5");
//        System.out.println(redisSet);
        stringRedisTemplate.opsForSet().pop("redisSet");  //移除并返回集合中的一个随机元素
        stringRedisTemplate.opsForSet().move("3","redisSet","redisSet2");     //将 3 元素从 redisSet 集合移动到 redisSet2 集合
        //返回集合中的所有成员
        stringRedisTemplate.opsForSet().members("redisSet");
        stringRedisTemplate.opsForSet().isMember("redisSet","3");   //判断 member 元素是否是集合 key 的成员
        //key无序集合与多个otherKey无序集合的差集
        //  重载: redisSet对应的无序集合与 多个redisSet2,redisSet3... 对应的无序集合(Arraylist<string>()) 的差集
        stringRedisTemplate.opsForSet().difference("redisSet","redisSet2");
//       redisSet无序集合与redisSet2无序集合的差集存储到redisSet3无序集合中
//      重载: key无序集合与多个otherkey无序集合的差集存储到destKey无序集合中
        stringRedisTemplate.opsForSet().differenceAndStore("redisSet","redisSet2","redisSet3");
        stringRedisTemplate.opsForSet().distinctRandomMembers("redisSet",5); //获取多个redisSet无序集合中的元素（去重），count表示个数
        //  redisSet对应的无序集合与redisSet2对应的无序集合 求交集;
        //  重载: redisSet对应的无序集合与 多个redisSet2,redisSet3... 对应的无序集合(Arraylist<string>()) 求交集
        stringRedisTemplate.opsForSet().intersect("redisSet","redisSet2");
//        stringRedisTemplate.opsForSet().intersectAndStore(); //key集合与otherKey集合的交集存储到destKey集合中(其中otherKey可以为单个值或者集合)
        stringRedisTemplate.opsForSet().scan("redisSet", ScanOptions.scanOptions().build()); //遍历set  //相当于得到迭代器
        stringRedisTemplate.opsForSet().size("redisSet");   //无序集合的大小长度
//        stringRedisTemplate.opsForSet().union(); //获取两个或者多个集合的并集(otherKeys可以为单个值或者是集合)
//        stringRedisTemplate.opsForSet().unionAndStore(); //key集合与otherKey集合的并集存储到destKey中(otherKeys可以为单个值或者是集合)
//        stringRedisTemplate.opsForSet().randomMembers(); //随机获取集合中count个元素
        stringRedisTemplate.opsForSet().randomMember("redisSet");  //随机获取redisSet无序集合中的一个元素
    }
    @Test
    void contextLoadsRedis_zset(){
        stringRedisTemplate.opsForZSet().add("redisZSet","hello!",1); //新增一个有序集合，存在的话为false，不存在的话为true
//        通过分数返回有序集合指定区间内的成员个数
        stringRedisTemplate.opsForZSet().count("redisZSet",0,5);
        stringRedisTemplate.opsForZSet().getOperations();
        stringRedisTemplate.opsForZSet().incrementScore("redisZSet","hello!",3);  //增加元素的score值，并返回增加后的值 +值
//        stringRedisTemplate.opsForZSet().intersectAndStore();
//        通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        stringRedisTemplate.opsForZSet().range("redisZSet",0,-1);// 查看集合所有元素
//        stringRedisTemplate.opsForZSet().rangeByLex();
//        通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        stringRedisTemplate.opsForZSet().rangeByScore("redisZSet",0,5);
//        通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
        stringRedisTemplate.opsForZSet().rangeByScoreWithScores("redisZSet",0,5);
//        通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
        stringRedisTemplate.opsForZSet().rangeWithScores("redisZSet",0,-1);
//        返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列  首位为0
        stringRedisTemplate.opsForZSet().rank("redisZSet","hello!");
//        返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
        stringRedisTemplate.opsForZSet().reverseRank("redisZSet","hello!");
        stringRedisTemplate.opsForZSet().remove("redisZSet","hello!");//从有序集合中移除一个或者多个元素
//        遍历zset 获取迭代器
        stringRedisTemplate.opsForZSet().scan("redisZSet", ScanOptions.scanOptions().build());
        stringRedisTemplate.opsForZSet().score("redisZSet","hello!");  //获取指定成员的score值
        stringRedisTemplate.opsForZSet().size("redisZSet");//获取成员数量 内部调用zCard()
//        计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
//        stringRedisTemplate.opsForZSet().unionAndStore();
        stringRedisTemplate.opsForZSet().zCard("redisZSet");//获取有序集合的成员数
    }


    @Test
    void test0310(){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        int size = stringStringHashMap.size();
//        System.out.println(  "  长度：  "+size);

        String string="01 06 02 42 03 00 02 F1 41 02 06 02 42 03 00 01 D6 26 04 06 02 42 83 00 00 87 58 ";
        StringBuffer stringBuffer = new StringBuffer(string);
        int i1 =stringBuffer.length()/27;
        for (int i = 0; i <i1; i++) {
            String substring;
            if (i==i1-1){
                substring=stringBuffer.toString();
            }else {
                substring = stringBuffer.substring(0, 27);
            }
            System.out.println("第" +
                    i +
                    "次截取为 "+substring);
            //01地址位 06功能码 02寄存器 42状态字节 03分度值/正负号 00 02 F1 分度数 41校验
            // 重量=分度数*分度值
            switch (substring.charAt(1)){
                case '1':
                    double kg = get_KG(substring);
                    System.out.println(" lzy --> 1 组"+kg);
                    break;
                case '2':
//                    get_KG(substring);
                    System.out.println(" lzy --> 2 组"+ get_KG(substring));
                    break;
                case '3':
//                    get_KG(substring);
                    System.out.println(" lzy --> 3 组"+ get_KG(substring));
                    break;
                case '4':
//                    get_KG(substring);
                    System.out.println(" lzy --> 4 组"+ get_KG(substring));
                    break;
                case '5':
//                    get_KG(substring);
                    System.out.println(" lzy --> 5 组"+ get_KG(substring));
            }
            if (i!=i1-1){
                stringBuffer = stringBuffer.delete(0, 27);
            }
            System.out.println("第" +
                    i +
                    "次截取之后是："+stringBuffer.toString());

        }


    }
    public static double get_KG(String str){
        //02 06 02
        // C2  状态
        // 03  正负 0000 0011    1000 0000 -> 128 dec/80 hex
        // 00 02 E0  = 10进制 => 00*02*224*0.0001  ==> 2E0hex*0.0001->736dec*0.001==
        // B1
        //02 06 02 C2 03 00 02 E0 B1
//        if (C2) 设备状态暂时不判断
        double v;
        if (Integer.parseInt(str.substring(12,14),16)<128){
            //正
            System.out.println("输出 正");
            v= crossWalks(str.charAt(13));

        }else {
            System.out.println("输出 负");
            //负
            v = -crossWalks(str.charAt(13));
        }
        double dd= Integer.parseInt(str.substring(15,23).replace(" ",""),16)*v;
        return dd;
    }

    public static double crossWalks(char a){
        switch (a){
            case '0':
                return 0.0001;
            case '1':
                return 0.0002;
            case '2':
                return 0.0005;
            case '3':
                return 0.001;
            case '4':
                return 0.002;
            case '5':
                return 0.005;
            case '6':
                return 0.01;
            case '7':
                return 0.02;
            case '8':
                return 0.05;
            case '9':
                return 0.1;
            case 'A':
                return 0.2;
            case 'B':
                return 0.5;
            case 'C':
                return 1;
            case 'D':
                return 2;
            case 'E':
                return 5;
        }
        return 0;
    }
    @Test
    void test316(){
        int[] ints ={1,2,3,4,4};
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            hashMap.put("layerId",i+1);//加1为货道层数
            hashMap.put("weight",ints[i]);//货道重量
        }
        
    }
}
