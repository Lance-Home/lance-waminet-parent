

package com.waminet.common.tools.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonConfigProperties redissonConfigProperties;

    @Bean
    public RedissonClient redisson() {

        // Redis单机模式
        if (StringUtils.isNotEmpty(redissonConfigProperties.getHost()) && StringUtils.isNotEmpty(redissonConfigProperties.getPort())) {
            Config config = new Config();

            // Redis密码存在的场合
            if (StringUtils.isNotEmpty(redissonConfigProperties.getPassword())) {
                config.useSingleServer().setAddress("redis://" + redissonConfigProperties.getHost() + ":" + redissonConfigProperties.getPort()).setPassword(redissonConfigProperties.getPassword());
            } else {
                config.useSingleServer().setAddress("redis://" + redissonConfigProperties.getHost() + ":" + redissonConfigProperties.getPort());
            }

            return Redisson.create(config);
        }

        // Redis集群配置
        RedissonConfigProperties.Cluster cluster = redissonConfigProperties.getCluster();

        // Redis集群模式
        if (cluster != null && cluster.getNodes() != null && cluster.getNodes().isEmpty() == false) {
            Config config = new Config();
            ClusterServersConfig serversConfig = config.useClusterServers();
            int size = cluster.getNodes().size();
            String[] address = new String[size];
            for (int i = 0; i < size; i++) {
                address[i] = "redis://" + cluster.getNodes().get(i);
            }

            // Redis密码存在的场合
            if (StringUtils.isNotEmpty(redissonConfigProperties.getPassword())) {
                serversConfig.addNodeAddress(address).setPassword(redissonConfigProperties.getPassword()).setScanInterval(5000);
            } else {
                serversConfig.addNodeAddress(address).setScanInterval(5000);
            }

            return Redisson.create(config);
        }

        return null;
    }

}
