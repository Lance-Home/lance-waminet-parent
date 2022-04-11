

package com.waminet.common.tools.redisson;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis配置参数
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfigProperties {

    private String host;
    private String port;
    private String password;

    private Cluster cluster;

    public static class Cluster {

        private List<String> nodes;

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
