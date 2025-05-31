package org.bebra.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bebra.enums.MatchEventStatus;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEventRequest {
    private Integer id;
    private MatchEventStatus status;
}
