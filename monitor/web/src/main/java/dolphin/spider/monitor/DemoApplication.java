package dolphin.spider.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"controllers","service","mapper","config"})
@MapperScan("mapper")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.addListeners(new ApplicationPidFileWriter("app.pid"));
        app.run(args);
	}

}
