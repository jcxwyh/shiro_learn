package pro.onlyou.apache.shiro.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"pro.onlyou.apache.shiro.boot.mapper"})
public class BootStarter {

    public static void main(String[] args){
        SpringApplication.run(BootStarter.class);
    }

}
