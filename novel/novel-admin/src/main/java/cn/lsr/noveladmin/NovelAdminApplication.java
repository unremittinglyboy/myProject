package cn.lsr.noveladmin;

import cn.lsr.noveladmin.initialize.ApplicationStartup;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@EnableCaching
@SpringBootApplication
@MapperScan(value = "cn.lsr.noveladmin.mapping")
public class NovelAdminApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(NovelAdminApplication.class);
		springApplication.addListeners(new ApplicationStartup());
		springApplication.run(args);
	}

	@Bean
	public RestHighLevelClient client(){
		return new RestHighLevelClient(RestClient.builder(HttpHost.create("http://192.168.98.128:9200")));
	}
}
