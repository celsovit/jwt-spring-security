package com.crv.usersgroup.core.security.auth;

import com.crv.usersgroup.core.security.model.AccountCredentialModel;
import com.crv.usersgroup.core.security.model.TokenResponseModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("security")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public TokenResponseModel login(@RequestBody AccountCredentialModel data) {
        return authService.signin(data);
    }

    @PutMapping(value = "/refresh")
    public TokenResponseModel refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }

}
