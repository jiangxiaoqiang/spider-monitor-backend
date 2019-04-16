package dolphinweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;


/**
 * Created by dolphin on 23/6/2017.
 */
@ComponentScan(basePackages = {
        "dolphinweb.controllers",
        "service",
        "utils",
        "mapper",
        "dolphinweb.config",
        "combine",
         "model"})
@EnableCaching
@MapperScan("mapper")
@EnableConfigurationProperties(DataSourceConfig.class)
@SpringBootApplication
public class DolphinApplication {
    public static void main(String[] args) throws Exception {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        System.out.print(list1.getClass());

        System.out.print(list1.getClass() == list2.getClass());
        //SpringApplication app = new SpringApplication(DolphinApplication.class);
        //app.addListeners(new ApplicationPidFileWriter("app.pid"));
        //app.run(args);
    }
}
