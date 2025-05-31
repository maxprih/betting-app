package org.bebra.dto.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bebra.enums.MatchEventStatus;

/**
 * @author max_pri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class CreateMatchEventRequest {
    private Integer matchId;
    private String name;
    private Double coefficient;
    private MatchEventStatus status;
}