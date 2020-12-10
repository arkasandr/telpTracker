package ru.arkaleks.telptracker.controllers.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRoleDto {

    public EmployeeRoleDto(String rolename) {
        this.rolename = rolename;
    }

    private int id;

    private String rolename;


}
