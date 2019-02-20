package dolphin.spider.monitor;

import model.SpiderAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import service.SpiderAnalysisService;

import java.time.LocalDateTime;


@Component
@EnableScheduling
public class SpiderAnalysisTask {

    @Autowired
    private SpiderAnalysisService spiderAnalysisService;

    @Scheduled(cron = "0 */1 * * * *")
    public void analysisTriggerhourly()
    {
        System.out.println("running...");
        SpiderAnalysis spiderAnalysis = new SpiderAnalysis();
        int totalCount = spiderAnalysisService.getAllCount();
        spiderAnalysis.setAnalysisTimestamp(LocalDateTime.now());
        spiderAnalysis.setBookTotalElements(totalCount);
        spiderAnalysisService.insert(spiderAnalysis);
    }


}
