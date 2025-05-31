package org.bebra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BalanceDto {
    private Integer id;
    private Integer userId;
    private Integer amount;
}
