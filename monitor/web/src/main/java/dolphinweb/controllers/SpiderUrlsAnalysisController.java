package dolphinweb.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.SpiderUrlsAnalysisService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ApiResult;
import model.SpiderUrlsAnalysis;

@RestController
@RequestMapping("dolphin/spider-monitor/api/v1/urlsAnalysis")
@Api(value = "maintype-inapi", description = "获取URL统计信息")
public class SpiderUrlsAnalysisController {

    @Autowired
    private SpiderUrlsAnalysisService spiderUrlsAnalysisService;

    @GetMapping(path = "list")
    @CrossOrigin
    @ApiOperation(value = "", notes = "")
    public ApiResult getAnalysisList() throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startTime", LocalDateTime.now().minusMonths(3));
        paramMap.put("endTime", LocalDateTime.now());
        List<SpiderUrlsAnalysis> analysisResult = spiderUrlsAnalysisService.findList(paramMap);
        ApiResult result =new ApiResult(analysisResult);
        return result;
    }
}
