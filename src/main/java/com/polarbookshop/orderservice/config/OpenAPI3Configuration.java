package com.polarbookshop.orderservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = {@Server(url = "http://localhost:9002")}, info = @Info(title = "Order Service APIs",
        description = "This lists all the Order Service API Calls. The Calls are OAuth2 secured, "
        + "so please use your client ID and Secret to test them out.",
        version = "v1.0"))
@SecurityScheme(name = "oAuth2ClientCredentials", type = SecuritySchemeType.OAUTH2, description="Oauth 2 Client Credential",
        flows = @OAuthFlows(clientCredentials  = @OAuthFlow(tokenUrl = "${openapi.oAuthFlow.tokenUrl}",
                scopes = {@OAuthScope(name = "application.automotive-credit-requests.bcvc.create-credit-requests.write", description = "Create Credit Request"),
                        @OAuthScope(name = "application.automotive-credit-requests.bcvc.get-credit-requests.read", description = "Retrieve Credit Request")}
                )))
public class OpenAPI3Configuration {
}
