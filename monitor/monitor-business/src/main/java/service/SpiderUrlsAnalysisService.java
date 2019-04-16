package service;

import mapper.SpiderUrlsAnalysisMapper;
import model.SpiderUrlsAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpiderUrlsAnalysisService {

    @Autowired
    private SpiderUrlsAnalysisMapper spiderUrlsAnalysisMapper;

    public List<SpiderUrlsAnalysis> findList(Map<String,Object> paramMap){
        return spiderUrlsAnalysisMapper.findList(paramMap);
    }
}
