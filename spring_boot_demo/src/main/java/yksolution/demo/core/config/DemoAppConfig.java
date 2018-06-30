package yksolution.demo.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("yksolution.demo.dao")
public class DemoAppConfig implements WebMvcConfigurer {
}