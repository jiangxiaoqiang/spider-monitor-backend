package service;

import mapper.SpiderAnalysisMapper;
import model.SpiderAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderAnalysisService {

    @Autowired
    private SpiderAnalysisMapper spiderAnalysisMapper;

    public void insert(SpiderAnalysis spiderAnalysis){
        spiderAnalysisMapper.insert(spiderAnalysis);
    }

    public int getAllCount(){
        spiderAnalysisMapper.getAllCount();
    }
}
