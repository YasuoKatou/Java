package yks.ticket.lite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("yks.ticket.lite.dao")
public class YksTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(YksTicketApplication.class, args);
	}
}