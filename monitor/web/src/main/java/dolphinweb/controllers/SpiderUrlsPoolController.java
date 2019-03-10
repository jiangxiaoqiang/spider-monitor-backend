package dolphinweb.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.ApiResult;
import model.SpiderUrlsAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.SpiderUrlsAnalysisService;
import service.SpiderUrlsPoolService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dolphin/spider-monitor/api/v1/urlsPool")
@Api(value = "maintype-inapi", description = "获取URL统计信息")
public class SpiderUrlsPoolController {

    @Autowired
    private SpiderUrlsPoolService spiderUrlsPoolService;

    @GetMapping(path = "readyToScrapy")
    @CrossOrigin
    @ApiOperation(value = "", notes = "")
    public ApiResult getAnalysisList(){
        Integer readyToScrapyCount = spiderUrlsPoolService.getReadyScrapyCount();
        ApiResult result =new ApiResult(readyToScrapyCount);
        return result;
    }
}
