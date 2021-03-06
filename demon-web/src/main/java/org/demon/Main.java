package org.demon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
// 开启缓存，暂不使用
//@EnableCaching
// 异步调用
//@EnableAsync
// 开启事务管理
@EnableTransactionManagement
public class Main extends SpringBootServletInitializer {

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(100);
    }

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext ctx = SpringApplication.run(Main.class, args);
        System.out.println("项目启动!");
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        closeLatch.await();
    }
}
