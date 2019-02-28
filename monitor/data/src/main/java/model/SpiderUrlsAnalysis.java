package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpiderUrlsAnalysis {

    private String spiderName;

    private Integer spiderUrlsCount;

    private Integer successCount;

    private Integer failedCount;

    private Integer unfinishedCount;

    private Integer totalUrlsCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime analysisTimestamp;

}
