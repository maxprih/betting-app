package org.bebra.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.bebra.dto.MatchDto;

import java.util.List;

/**
 * @author max_pri
 */
@Data
@Builder
public class GetAllMatchesResponse {
    private List<MatchDto> matches;
}
