package com.crv.usersgroup.api.v1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableMethodSecurity
@RestController
@RequestMapping("api/v1")
public class TestController {

    @PreAuthorize("hasAuthority('CAN_VIEW_BALANCE')")
    @GetMapping("/balance")
    public String viewBalance() {
        return "Balanço visível";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restrict")
    public String restrict() {
        return "Área restrita visível";
    }

//    @PreAuthorize("hasRole('ROLE_MATHEMATICIAN')")
    @GetMapping("/mathematical")
    public String mathematical() {
        return "Área matemática";
    }

//    @PreAuthorize("hasAuthority('CAN_READ')")
    @GetMapping("/common")
    public String common() {
        return "Área comum";
    }

}
