package service;


import mapper.SpiderAnalysisMapper;
import model.Page;
import model.Pageable;
import model.SpiderAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpiderAnalysisService {

    @Autowired
    private SpiderAnalysisMapper spiderAnalysisMapper;

    public void insert(SpiderAnalysis spiderAnalysis){
        spiderAnalysisMapper.insert(spiderAnalysis);
    }

    public void getAllCount(){
        spiderAnalysisMapper.getAllCount();
    }

    public Page<List<SpiderAnalysis>> findPage(Pageable pageable){
        return spiderAnalysisMapper.findPage(pageable);
    }

    public List<SpiderAnalysis> findList(Map<String,Object> paramMap){
        return spiderAnalysisMapper.findList(paramMap);
    }
}
