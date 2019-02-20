package model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpiderAnalysis {
    private Integer bookTotalElements;
    private LocalDateTime analysisTimestamp;
}
