package com.smxr;

import com.smxr.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
//        stringRedisTemplate.opsForValue().set("10:31:string","练习redis");
//        stringRedisTemplate.opsForValue().set("data5","");
//        stringRedisTemplate.opsForValue().set("10:31:string","练习redis");
        System.out.println(stringRedisTemplate.opsForValue().get("10:31:string"));
        System.out.println(stringRedisTemplate.getExpire("data5")+"");   //数据不存在返回值是 -2

    }
    @Test
    void contextLoadsRedis(){
        stringRedisTemplate.opsForValue().set("data5","springdata",60, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set("data6","springdata1",60,TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.getExpire("data5"));  //对已经存在的数据 并没有定时的数据返回 -1
        System.out.println(stringRedisTemplate.getExpire("data6"));  //对不存在的数据使用返回：-2
        System.out.println(stringRedisTemplate.persist("data5"));  //true  对已经是永存的使用返回：false
        // 输出结果： springdata-redis  原数据：springdata
        stringRedisTemplate.opsForValue().set("data5","-redis",10);//可以添加内容，参数3在第几位添加
        //是否存在 不存在就创建并设置 如同setnx  返回值：已经存在 false，不存在 true
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("data4", "nihaoyawoshilailianside");
        Boolean abBoolean = stringRedisTemplate.opsForValue().setIfPresent("data3", "heheheheehehehe");
        System.out.println(aBoolean);
        Long data5 = stringRedisTemplate.opsForValue().size("data3"); //获取长度
        System.out.println(stringRedisTemplate.opsForValue().get("data3"));
    }
    @Test
    void contextLoadsRedis_1(){
        Map<String,String> maps = new HashMap<String, String>();
        maps.put("multi1","multi1");
        maps.put("multi2","multi2");
        maps.put("multi3","multi3");
//        为多个键分别设置它们的值
        stringRedisTemplate.opsForValue().multiSet(maps);
        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
//        获取多个值
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
        stringRedisTemplate.opsForHash().putIfAbsent("redisHash","adree","jerry Home"); //添加一个哈希对象(hash)的属性值，存在false，不存在 true
        stringRedisTemplate.opsForHash().get("redisHash","adree"); //从键中的哈希获取给定hashKey的属性值
        RedisOperations<String, ?> operations = stringRedisTemplate.opsForHash().getOperations();  //不知道什么鬼
        stringRedisTemplate.opsForHash().delete("redisHash","sex");//删除指定hash对象的 属性值
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
        Cursor<Map.Entry<Object, Object>> redisHash = stringRedisTemplate.opsForHash().scan("redisHash", ScanOptions.scanOptions().build());
        //相当于得到迭代器
        while (redisHash.hasNext()){
            Map.Entry<Object, Object> next = redisHash.next();
            System.out.println(next.getKey());
            System.out.println(next.getValue());
        }

        stringRedisTemplate.opsForHash().lengthOfValue("redisHash","name");//得到属性值的长度
        stringRedisTemplate.opsForHash().size("redisHash");  //获取属性的个数
    }

}
