package com.generic.common.util.redis;

import com.generic.common.util.DeployInfoUtil;
import com.generic.common.util.LogUtil;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyuzhu
 * @description: ${todo}(这里用一句话描述这个类的作用)
 * @date 2018/1/21 0:07
 */
public class RedisDataSource {

    private static LogUtil log = LogUtil.getLogger(RedisUtil.class);

    private static RedisDataSource redisDataSource = null;

    /** 切片池 */
    private ShardedJedisPool shardedJedisPool;
    /** 非切片池 */
    private JedisPool jedisPool;
    /** 池配置 */
    private JedisPoolConfig jedisPoolConfig;

    private RedisDataSource() {
        if(jedisPoolConfig == null){
            setJedisPoolConfig();
        }
        if(jedisPool == null){
            setJedisPool();
        }
        if(shardedJedisPool == null){
            setShardedJedisPool();
        }
    }

    public synchronized static RedisDataSource getInstance(){
        if(redisDataSource==null){
            redisDataSource = new RedisDataSource();
        }
        return redisDataSource;
    }

    /**
     * <b>概要：</b>取得redis的客户端，可以执行命令了。
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年3月31日 </br>
     * @return
     */
    public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("getRedisClient error", e);
        }
        return null;
    }

    /**
     * <b>概要：</b>将资源返还给pool
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年3月31日 </br>
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    /**
     * <b>概要：</b>出现异常后，将资源返还给pool （其实不需要第二个方法）
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年3月31日 </br>
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    /**
     * <b>概要：</b>取得redis的客户端，可以执行命令了。
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年3月31日 </br>
     * @return
     */
    public Jedis getJedisClient() {
        try {
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            log.error("getJedisClient error", e);
        }
        return null;
    }

    /**
     * <b>概要：</b>将资源返还给pool
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年3月31日 </br>
     * @param jedis
     */
    public void returnJedisResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    /**
     * <b>概要：</b>出现异常后，将资源返还给pool （其实不需要第二个方法）
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年3月31日 </br>
     * @param jedis
     * @param broken
     */
    public void returnJedisResource(Jedis jedis, boolean broken) {
        if (broken) {
            jedisPool.returnBrokenResource(jedis);
        } else {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * <b>概要：</b>创建切片连接池
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年4月6日 </br>
     */
    private void setShardedJedisPool() {
        List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);

        JedisShardInfo infoA = new JedisShardInfo(DeployInfoUtil.getRedisHost(), Integer.valueOf(DeployInfoUtil.getRedisPort()));
        infoA.setPassword(DeployInfoUtil.getRedisPassword());
        jdsInfoList.add(infoA);

        // String hostB = "127.0.0.1";
        // int portB = 7000;
        // infoB.setPassword("admin");
        // JedisShardInfo infoB = new JedisShardInfo(hostB, portB);
        // jdsInfoList.add(infoB);
        shardedJedisPool = new ShardedJedisPool(this.jedisPoolConfig, jdsInfoList);
        log.info("###setShardedJedisPool 创建切片连接池");
    }

    /**
     * <b>概要：</b>创建非切片连接池
     * <b>作者：</b>zhouxw </br>
     * <b>日期：</b>2016年4月6日 </br>
     */
    private void setJedisPool() {
        jedisPool = new JedisPool(this.jedisPoolConfig, DeployInfoUtil.getRedisHost(), Integer.valueOf(DeployInfoUtil.getRedisPort()));
        log.info("###setJedisPool 创建非切片连接池");
    }

    private void setJedisPoolConfig() {
        this.jedisPoolConfig = new JedisPoolConfig();
        this.jedisPoolConfig.setMaxTotal(Integer.valueOf(DeployInfoUtil.getRedisMaxActive()));// 最大活动的对象个数
        this.jedisPoolConfig.setMaxIdle(Integer.valueOf(DeployInfoUtil.getRedisMaxIdle()));// 对象最大空闲时间
        this.jedisPoolConfig.setMaxWaitMillis(Long.valueOf(DeployInfoUtil.getRedisMaxWait()));// 获取对象时最大等待时间
        this.jedisPoolConfig.setTestOnBorrow(DeployInfoUtil.getRedisTestOnBorrow());
        log.info("###setJedisPoolConfig 创建jedis连接池配置");
    }

}
