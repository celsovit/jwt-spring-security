package com.crv.usersgroup.core.security.auth;

import com.crv.usersgroup.core.security.JwtTokenProvider;
import com.crv.usersgroup.core.security.model.AccountCredentialModel;
import com.crv.usersgroup.core.security.model.TokenResponseModel;
import com.crv.usersgroup.domain.exception.InvalidCredentialsException;
import com.crv.usersgroup.domain.exception.InvalidJwtAuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public TokenResponseModel signin(AccountCredentialModel data) {

        try {
            if (isInvalidCredential(data)) throw new Exception();

            String username = data.getUsername();
            String password = data.getPassword();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            return tokenProvider.createAccessToken(username);

        } catch(Exception e) {
            throw new InvalidCredentialsException();
        }

    }

    public TokenResponseModel refreshAccessToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank())
            throw new InvalidJwtAuthenticationException();
        return tokenProvider.refreshAccessToken(refreshToken);
    }

    private boolean isInvalidCredential(AccountCredentialModel data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() ||
                data.getPassword() == null || data.getPassword().isBlank();
    }

}
