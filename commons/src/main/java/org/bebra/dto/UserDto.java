package org.bebra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String login;
    private String name;
    private Integer balanceAmount;
    private Set<RoleDto> roles;
}
