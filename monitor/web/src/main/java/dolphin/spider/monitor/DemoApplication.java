package dolphin.spider.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = SpiderAnalysisTask.class)
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.addListeners(new ApplicationPidFileWriter("app.pid"));
        app.run(args);
	}

}
