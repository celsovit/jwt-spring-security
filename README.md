# Spring Security com JWT: Autenticação e Autorização

## Login
http://localhost:8080/security/login

- `JwtTokenFilter` libera o acesso do endpoint baseado nas definições de `SecurityConfig`
- `AuthController` intercepta a solicitação e passa o controle para `AuthService`
- `JwtTokenProvider` inicia processo de criação de um novo token
- `CustomUserDetailService` pesquisa usuário no banco de dados e instancia `UserEntity`, `UsersGroup` e `Permission`
- `TokenResponseModel` é instanciado com token de acesso e token de atualização que é devolvido como resposta ao cliente

### Request:
```json
{ 
  "username": "lovelace",
  "password": "admin123"
}
```

### Response:
```json
{
	"username": "lovelace",
	"authenticated": true,
	"created": "2024-02-28T20:03:15.010Z",
	"expiration": "2024-02-28T20:04:15.000Z",
	"accessToken": "...",
	"refreshToken": "..."
}
```

## Renovação de Token de Acesso
http://localhost:8080/security/refresh

- `JwtTokenFilter` libera o acesso do endpoint baseado nas definições de `SecurityConfig`
- `AuthController` intercepta a solicitação e passa o controle para `AuthService`
- `JwtTokenProvider` inicia processo de criação de um novo token
- `CustomUserDetailService` pesquisa usuário no banco de dados e instancia `UserEntity`, `UsersGroup` e `Permission`
- `TokenResponseModel` é instanciado com token de acesso e token de atualização que é devolvido como resposta ao cliente

### Request:
```header
  Authorization: Bearer <refreshToken>
```

### Response:
```json
{
	"username": "lovelace",
	"authenticated": true,
	"created": "2024-02-28T20:06:12.010Z",
	"expiration": "2024-02-28T20:07:12.000Z",
	"accessToken": "...",
	"refreshToken": "..."
}
```

## Acesso a Recursos
http://localhost:8080/api/v1/consume

- `JwtTokenFilter` chama `JwtTokenProvider` que valida o token e recupera Roles e Authorities a partir dele
- `JwtTokenFilter` libera o acesso do endpoint baseado nas definições de `SecurityConfig`
- `AuthController` intercepta a solicitação e se autorizado (hasAutorithies), assume o controle

### Request:
```header
  Authorization: Bearer <refreshToken>
```

---

## Saiba mais:

- [JWT: Usuários e Grupos](https://www.javadevjournal.com/spring-security/spring-security-roles-and-permissions/)
- [Managing JWT With Auth0 java-jwt](https://www.baeldung.com/java-auth0-jwt)
- [Começando com Spring Security](https://medium.com/cwi-software/come%C3%A7ando-com-spring-security-86a3caec8c40)
- [Como configurar a autenticação ea autorização no JWT para o Spring Boot em Java](https://www.freecodecamp.org/portuguese/news/como-configurar-a-autenticacao-e-a-autorizacao-no-jwt-para-o-spring-boot-em-java/)
- [Autorização baseada em função do Spring Boot Security](https://howtodoinjava.com/spring-security/spring-boot-role-based-authorization/)
- [JWT - JSON Web Token](https://glysns.gitbook.io/spring-framework/spring-security/spring-security-e-jwt)
- [Spring Framework](https://glysns.gitbook.io/spring-framework/)
- [Spring Boot como invalidar o token JWT, como fazer logout ou redefinir todos os tokens ativos?](https://sopheamak.medium.com/springboot-how-to-invalidate-jwt-token-such-as-logout-or-reset-all-active-tokens-73f55289d47b)
- [Como gerenciar exceções lançadas em filtros no Spring?](https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring)
