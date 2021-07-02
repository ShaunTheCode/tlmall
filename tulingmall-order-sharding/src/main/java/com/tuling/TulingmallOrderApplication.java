package com.tuling;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

//不使用ShardingSphere。SpringBootConfiguration会往Spring中注入ShardingDataSource
//@SpringBootApplication(exclude = {SpringBootConfiguration.class}) /*(exclude = {GlobalTransactionAutoConfiguration.class})*/
@SpringBootApplication
@EnableFeignClients
public class TulingmallOrderApplication {

	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(TulingmallOrderApplication.class, args);
	}

	public static <T> T getBean(String beanName) {
		return applicationContext != null ? (T) applicationContext.getBean(beanName) : null;
	}


}
