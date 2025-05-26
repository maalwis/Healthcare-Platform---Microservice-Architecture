package com.healthcareplatform.AnalyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {
    private UUID stockItemId;
    private UUID productId;
    private String productName;
    private Integer currentQuantity;
    private Integer threshold;
    private LocalDateTime reportedAt;

}
