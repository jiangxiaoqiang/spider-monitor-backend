package mapper;

import model.Page;
import model.Pageable;
import model.SpiderAnalysis;
import java.util.List;
import java.util.Map;

public interface SpiderAnalysisMapper {
    int insert(SpiderAnalysis spiderAnalysis);
    int getAllCount();
    Page<List<SpiderAnalysis>> findPage(Pageable pageable);
    List<SpiderAnalysis> findList(Map<String,Object> paramMap);

}
