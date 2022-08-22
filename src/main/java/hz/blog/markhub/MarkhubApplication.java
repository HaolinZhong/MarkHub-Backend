package hz.blog.markhub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("hz.blog.markhub.mapper")
public class MarkhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarkhubApplication.class, args);
    }

}
