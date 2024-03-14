package org.hire.domain.dto;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatisticsDto {
    private String time_taken;
}
