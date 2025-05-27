package com.healthcareplatform.AnalyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    private Long stockItemId;
    private Long productId;
    private String productName;
    private Integer currentQuantity;
    private Integer threshold;
    private LocalDateTime reportedAt;

}
