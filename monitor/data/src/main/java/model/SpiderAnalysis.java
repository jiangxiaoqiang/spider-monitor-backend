package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpiderAnalysis {
    private Integer bookTotalElements;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime analysisTimestamp;

    private Integer todayElements;

    private Integer todayScrapyUrlsCount;

    private Integer readyUrlsCount;
}
