package com.crv.usersgroup.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.crv.usersgroup.core.security.config.SecurityProps;
import com.crv.usersgroup.core.security.model.TokenResponseModel;
import com.crv.usersgroup.domain.exception.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    private static final String HEADER_AUTH = "Authorization";

    private final UserDetailsService detailsService;
    private Algorithm algorithm;

    public JwtTokenProvider(UserDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @PostConstruct
    protected void init() {
        final String secretKey = Base64.getEncoder().encodeToString(SecurityProps.KEY.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        final DecodedJWT decodedJWT = verifyAndDecodeJWT(token);
        if (decodedJWT == null) return null;

        final String username = decodedJWT.getSubject();
        final Collection<? extends GrantedAuthority> authorities = resolveClaim(decodedJWT);

        return new UsernamePasswordAuthenticationToken(username, "", authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HEADER_AUTH);

        if (bearerToken != null && bearerToken.startsWith(SecurityProps.PREFIX))
            return bearerToken.substring(SecurityProps.PREFIX.length());

        return null;
    }

    public TokenResponseModel refreshAccessToken(String refreshToken) {
        if (refreshToken.startsWith(SecurityProps.PREFIX))
            refreshToken = refreshToken.substring(SecurityProps.PREFIX.length());

        final DecodedJWT decodedJWT = verifyAndDecodeJWT(refreshToken);
        final String username = decodedJWT.getSubject();

        return createAccessToken(username, refreshToken);
    }

    public TokenResponseModel createAccessToken(String username) {
        return createAccessToken(username, null);
    }

    public TokenResponseModel createAccessToken(String username, String refreshTokenInUse) {
        final UserDetails user = detailsService.loadUserByUsername(username);

        final String issuer = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        final List<String> str_authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        final Instant expirationAccess = genExpirationDate(SecurityProps.EXPIRATION_MILLIS);
        final String accessToken = generateToken(username, str_authorities, issuer, expirationAccess);

        String refreshToken;

        if (refreshTokenInUse == null) {
            final Instant expirationRefresh = genExpirationDate(SecurityProps.EXPIRATION_MILLIS * 3);
            refreshToken = generateToken(username, str_authorities, issuer, expirationRefresh);
        } else {
            refreshToken = refreshTokenInUse;   // Maintains the current refreshToken
        }

        return new TokenResponseModel(username, true, Instant.now(),
                expirationAccess, accessToken, refreshToken);
    }

    private String generateToken(String username, List<String> authorities, String issuer, Instant expirationDate) {
        return JWT.create()
                .withClaim("roles", authorities)
                .withIssuedAt(Instant.now())
                .withExpiresAt(expirationDate)
                .withSubject(username)
                .withIssuer(issuer)
                .sign(algorithm)
                .strip();
    }

    private DecodedJWT verifyAndDecodeJWT(String token) throws InvalidJwtAuthenticationException {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch(Exception e) {
            System.out.println("\n\n\n\nDeu Erro !!!!\n\n\n\n");
            throw new InvalidJwtAuthenticationException();
        }
    }

    private Instant genExpirationDate(long validityInMilliseconds) {
        return Instant.now().plusMillis(validityInMilliseconds);
    }

    private Collection<? extends GrantedAuthority> resolveClaim(DecodedJWT decodedJWT) {
        final Claim claim = decodedJWT.getClaim("roles");
        if (claim == null || claim.isNull())
            return new ArrayList<>();

        return claim.asList(String.class).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
