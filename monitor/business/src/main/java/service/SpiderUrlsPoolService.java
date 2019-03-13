package service;

import mapper.SpiderUrlsPoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderUrlsPoolService {

    @Autowired
    private SpiderUrlsPoolMapper spiderUrlsPoolMapper;

    public Integer getReadyScrapyCount(){
        return spiderUrlsPoolMapper.getReadyScrapyCount();
    }
}
