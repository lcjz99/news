package com.pcaifu;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.pcaifu.commonutils.common.utils.SnowflakeIdWorker;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.pcaifu.service") // 指定jpa资源库
@ServletComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EntityScan(basePackages = { "com.paicaifu.news.entity" })
@PropertySource(value = "file:/app/riches/config/riches_newser_config.properties")
public class Application {
	@Value("${redis.cache.expireSeconds}")
	private int expireSeconds;
	@Value("${redis.cache.clusterNodes}")
	private String clusterNodes;
	@Value("${redis.cache.commandTimeout}")
	private int commandTimeout;

	@Bean
	public SnowflakeIdWorker snowflakeIdWorker() {
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		return idWorker;
	}

	/**
	 * 注意： 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
	 * 
	 * @return
	 */
	@Bean
	public JedisCluster getJedisCluster() {
		System.out.println("-----配置节点=" + clusterNodes);
		String[] serverArray = clusterNodes.split(",");//
		// 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}

		return new JedisCluster(nodes, commandTimeout);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
