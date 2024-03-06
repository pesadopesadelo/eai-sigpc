/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 *
 * @author elton
 */
//@Configuration
public class OpenApi30Config {

    @Value("${keycloak.auth-server-url}")
    private String AUTH_SERVER;
    @Value("${keycloak.realm}")
    private String AUTH_REALM;

    private String moduleName;
    private String apiVersion;

    public OpenApi30Config() {
        this.moduleName = "module";
        this.apiVersion = "api30";
    }

    @Bean
    public OpenAPI customOpenAPI() {
        
        final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));

        String authorizeUrl = AUTH_SERVER + "/realms/"+AUTH_REALM+"/protocol/openid-connect/auth";
        String tokenUrl = AUTH_SERVER + "/realms/"+AUTH_REALM+"/protocol/openid-connect/token";
        Scopes scopes = new Scopes();
        scopes.addString("read", "Read API");
        scopes.addString("write", "Write API");
        
        OAuthFlow oAuthFlow = new OAuthFlow();
        oAuthFlow.tokenUrl(tokenUrl);
        oAuthFlow.authorizationUrl(authorizeUrl);
        
        oAuthFlow.setScopes(scopes);

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("oauth"))
                .components(
                        new Components()
                                .addSecuritySchemes("oauth", new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .scheme("bearer")
                                        .in(SecurityScheme.In.HEADER)
                                        .bearerFormat("JWT")
                                        .flows(
                                                new OAuthFlows().authorizationCode(oAuthFlow))
                                )
                ).info(new Info().title(apiTitle).version(apiVersion));
    }
}
