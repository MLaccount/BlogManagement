package com.kickcity.task.blogmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackageClasses = {BlogManagementApplication.class, Jsr310JpaConverters.class})
@EnableTransactionManagement
@SpringBootApplication
@PropertySource("classpath:security.properties")
public class BlogManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogManagementApplication.class, args);
    }
}
