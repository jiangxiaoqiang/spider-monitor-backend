package controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.SpiderAnalysisService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inapi/analysis")
@Api(value = "maintype-inapi", description = "获取统计信息")
public class BookAnalysisController {

    @Autowired
    private SpiderAnalysisService spiderAnalysisService;

    @GetMapping(path = "page")
    @ApiOperation(value = "", notes = "")
    public ApiResult getAnalysisList() throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startTime", LocalDateTime.now().minusMonths(3));
        paramMap.put("endTime", LocalDateTime.now());
        List<SpiderAnalysis> analysisResult = spiderAnalysisService.findList(paramMap);
        ApiResult result =new ApiResult(analysisResult);
        return result;
    }
}
