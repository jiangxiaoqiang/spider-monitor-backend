package service;

import mapper.SpiderUrlsPoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SpiderUrlsPoolService {

    @Autowired
    private SpiderUrlsPoolMapper spiderUrlsPoolMapper;

    @Cacheable(value = "analysisinfo")
    public Integer getReadyScrapyCount(){
        return spiderUrlsPoolMapper.getReadyScrapyCount();
    }
}
