package com.waminet.common.tools.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: hutool 雪花算法全局ID
 * @author Lance
 * @version 1.0.0
 * @data: 2022-02-28
 */
@Component
public class SnowFlakeUtils {
    /** 工作机器id */
    private long workerId=0L;
    /** 数据中心id */
    private long datacenterId=2L;
    /** 分布式id生成器 */
    private Snowflake snowflake= IdUtil.createSnowflake(workerId, datacenterId);
    @PostConstruct
    public void init(){
        try{
            //根据ip地址计算出long型的数据
            workerId= NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        }catch(Exception e){
            //获取本机ip地址字符串
            workerId=NetUtil.getLocalhostStr().hashCode();
        }
    }

    /**
     * 生成自定义id
     * @param workerId
     * @param datacenterId
     * @return
     */
    public synchronized long snowflakeId(long workerId,long datacenterId){
        Snowflake snowflake= IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

    /**
     * 生成id
     * @return
     */
    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        SnowFlakeUtils snowFlakeUtils = new SnowFlakeUtils();
        for (int i = 0; i < 50; i++) {
            System.out.println(snowFlakeUtils.snowflakeId());
        }
    }
}