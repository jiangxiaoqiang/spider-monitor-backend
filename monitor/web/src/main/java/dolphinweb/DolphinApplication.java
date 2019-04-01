package dolphinweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

        List<String> string = new ArrayList();
        SpringApplication app = new SpringApplication(DolphinApplication.class);
        app.addListeners(new ApplicationPidFileWriter("app.pid"));
        app.run(args);

    }
}
