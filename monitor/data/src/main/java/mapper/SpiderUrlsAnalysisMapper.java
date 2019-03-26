package mapper;

import model.SpiderUrlsAnalysis;

import java.util.List;
import java.util.Map;

public interface SpiderUrlsAnalysisMapper {
    List<SpiderUrlsAnalysis> findList(Map<String, Object> paramMap);
}
