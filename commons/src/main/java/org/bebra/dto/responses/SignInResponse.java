package org.bebra.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bebra.dto.RoleDto;

import java.util.Set;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class SignInResponse {
    private String accessToken;
    private Integer id;
    private String login;
    private String name;
    private Integer balanceAmount;
    private Set<RoleDto> roles;
}
