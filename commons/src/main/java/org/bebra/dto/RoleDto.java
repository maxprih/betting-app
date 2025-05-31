package org.bebra.dto;

import lombok.Value;

import javax.management.relation.Role;
import java.io.Serializable;

@Value
public class RoleDto {
    Integer id;
    String roleName;
}