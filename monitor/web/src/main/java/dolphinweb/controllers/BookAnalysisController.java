package dolphinweb.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.ApiResult;
import model.SpiderAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.SpiderAnalysisService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dolphin/spider-monitor/api/v1/analysis")
@Api(value = "maintype-inapi", description = "获取统计信息")
public class BookAnalysisController {

    @Autowired
    private SpiderAnalysisService spiderAnalysisService;

    @GetMapping(path = "list")
    @CrossOrigin
    @ApiOperation(value = "", notes = "")
    public ApiResult getAnalysisList(){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startTime", LocalDateTime.now().minusDays(3));
        paramMap.put("endTime", LocalDateTime.now());
        List<SpiderAnalysis> analysisResult = spiderAnalysisService.findList(paramMap);
        ApiResult result =new ApiResult(analysisResult);
        return result;
    }
}
