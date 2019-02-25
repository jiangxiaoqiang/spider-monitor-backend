package mapper;

import java.util.List;
import java.util.Map;
import model.SpiderUrlsAnalysis;

public interface SpiderUrlsAnalysisMapper {
    List<SpiderUrlsAnalysis> findList(Map<String, Object> paramMap);
}
