package y2017.m02.d04;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import y2016.m08.day20160825.Closer;

import java.util.List;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2017-02-04 PM02:52
 */
public class TestRedis {
    private JedisPool pool;
    private String host;
    private int port;

    @Before
    public void before() {
        host = "127.0.0.1";
        port = 6379;
        pool = getPool();
    }
    @Test
    public void testSet1() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            System.out.println(jedis.set("name", "kevin"));     // 设置
            System.out.println(jedis.setnx("name", "kevin"));   // 如果不存在就set
            System.out.println(jedis.setex("name", 10, "kevin"));   // 设置过期时间，单位为s
//            System.out.println(jedis.set("name", "kevin", "NX"));   // NX如果不存在就插入，XX如果存在就覆盖
//            System.out.println(jedis.set("name", "kevin", "XX"));   // NX如果不存在就插入，XX如果存在就覆盖
            System.out.println(jedis.getSet("name", "kevin1"));
            System.out.println(jedis.setrange("name", 2, "22"));        // 从offset开始覆盖，保持length不变，等效于replace
            System.out.println(jedis.setbit("name", 6, "0"));           // 这个setbit是对value的二进制进行操作
            System.out.println(jedis.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void testMset() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            System.out.println(jedis.mset("name", "kevin", "age", "28"));
            System.out.println(jedis.msetnx("name", "kevin", "age", "28")); // 如果不存在
            System.out.println(jedis.mget("name", "age"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void testIncrAndDecr() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            System.out.println(jedis.incr("age"));
            System.out.println(jedis.incrBy("age", 1));
            System.out.println(jedis.decr("age"));
            System.out.println(jedis.decrBy("age", 1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void test2() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.setex("name", 10, "kevin"); // 存活10S
            Long name = jedis.ttl("name");
            while (name > 0) {
                System.out.println("ttl : " + name);
                name = jedis.ttl("name");
            }
            System.out.println(jedis.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void test3() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            List<String> mget = jedis.mget("name", "age");
            String name = jedis.type("name"); // 查看value对应的类型,string,list,set,zset,hash
            System.out.println(name);
            Set<String> keys = jedis.keys("*");
            System.out.println(keys);
            System.out.println(mget);
            Long append = jedis.append("name", "333");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void testHash() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.hset("student", "name", "test1");
            jedis.hset("student", "age", "22");
            System.out.println(jedis.hget("student", "name"));
            System.out.println(jedis.hgetAll("student"));
            System.out.println(jedis.hincrBy("student", "age", 1));
            System.out.println(jedis.hkeys("student"));     // 所有的key
            System.out.println(jedis.hvals("student"));     // 所有的value
            System.out.println(jedis.hlen("student"));      // key的个数
            System.out.println(jedis.hexists("student", "age"));
            System.out.println(jedis.hdel("student", "age1"));
            System.out.println(jedis.hdel("student", "age"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void testList() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.rpush("list", "name");
            jedis.lpush("list", "name");
//            System.out.println(jedis.lpop("list"));
//            System.out.println(jedis.rpop("list"));
            System.out.println(jedis.lrange("list", 0, 2)); // 查看
            System.out.println(jedis.ltrim("list", 0, 1));          // 截取
            System.out.println(jedis.lindex("list", 1));
            //System.out.println(jedis.lrem("list", 1, "name"));    // 返回操作成功的数目
            System.out.println(jedis.lset("list", 1, "after"));     // 设置index位置的值
            System.out.println(jedis.lrange("list", 0, -1));
            System.out.println(jedis.lindex("list", 1));            // 获取index位置的值
            System.out.println(jedis.rpoplpush("list", "test2"));
            System.out.println(jedis.llen("list"));
            System.out.println(jedis.type("list"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }


    @Test
    public void testSet() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.srem("set", "test1", "test2" , "test3");
            System.out.println(jedis.sadd("set", "test1", "test4", "test8")); // 果然不重复
            System.out.println(jedis.smembers("set"));
//            System.out.println(jedis.srem("set", "test1"));
            System.out.println(jedis.smembers("set"));
            System.out.println(jedis.spop("set"));   // 随机返回一个值
//            System.out.println(jedis.spop("set", 1));
            System.out.println(jedis.scard("set")); // set的个数
            System.out.println(jedis.sismember("set", "test"));
            System.out.println(jedis.sismember("set", "test1"));

            jedis.sadd("set1", "test1", "test2", "test3");
            System.out.println(jedis.sdiff("set", "set1")); // set中差集

            System.out.println(jedis.sinter("set", "set1"));    // 交集
            System.out.println(jedis.sunion("set", "set1"));    // 并集
//            System.out.println(jedis.smembers("set"));
//            System.out.println(jedis.sinterstore("set", "set1"));   // 将交集结果存储在set中 TODO
            System.out.println(jedis.smembers("set"));
            System.out.println(jedis.sunionstore("set", "set1"));      // TODO
            System.out.println(jedis.smembers("set"));

            System.out.println(jedis.srandmember("set")); // pop会remove掉
            System.out.println(jedis.smembers("set"));
//            System.out.println(jedis.srandmember("set", 1));
            System.out.println(jedis.type("set"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void testZset() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            System.out.println(jedis.zadd("zset", 1, "test1"));    // score用于排序的权重
            System.out.println(jedis.zadd("zset", 2, "test2"));
            System.out.println(jedis.zadd("zset", 2, "test2"));
            System.out.println(jedis.zadd("zset", 2, "test3"));
            System.out.println(jedis.zadd("zset", 7, "test4"));
            System.out.println(jedis.zcard("zset"));            // 统计数据
            System.out.println(jedis.zcount("zset", 1, 2));     // 按score范围统计
            System.out.println(jedis.zrange("zset", 0, 2));     // 按下标index统计，由低到高
            System.out.println(jedis.zrevrange("zset", 0, 2));  // 按下标index统计，由高到低
//            System.out.println(jedis.zincrby("zset", 6, "test1"));  // 增加某个元素的权重
            System.out.println(jedis.zrank("zset", "test4"));   // 计算排名，由低到高  log(n)
            System.out.println(jedis.zrevrank("zset", "test4"));   // 计算排名，由高到低  log(n)
//            System.out.println(jedis.zrem("zset", "test3"));    // 删除元素
            System.out.println(jedis.zscore("zset", "test3"));
            System.out.println(jedis.zscan("zset", "0"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }

    @Test
    public void testTransaction() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
//            jedis.watch("age");
            Transaction transaction = jedis.multi();
            transaction.set("age", "7");
            transaction.watch("age");
            // 同一个jedis实例不能开启事务后，不能set
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Jedis finalJedis = null;
                    try {
                        finalJedis = pool.getResource();
                        finalJedis.set("age", "17");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Closer.closeQuietly(finalJedis);
                    }
                }
            }).start();
            Thread.sleep(2000);
            System.out.println(transaction.exec());
            System.out.println(jedis.get("age"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Closer.closeQuietly(jedis);
        }
    }


    public JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, host, port);
        }
        return pool;
    }
}
