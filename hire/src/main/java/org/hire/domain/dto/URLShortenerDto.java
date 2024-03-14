package org.hire.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URLShortenerDto {
    private String alias;
    private String url;
    private StatisticsDto statisticsDto;
}
