package com.polarbookshop.orderservice.consumer;

import com.polarbookshop.orderservice.model.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Validated
public interface OrderControllerInterface {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Operation(summary = "View all orders",
            security = @SecurityRequirement(name = "oAuth2ClientCredentials", scopes = {"application.automotive-credit-requests.bcvc.create-credit-requests.read"}),
            responses = {@ApiResponse(content = {@Content(schema = @Schema(implementation = OrderResponseModel.class))})}
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful response.", content = {@Content(schema = @Schema(implementation = OrderResponseModel.class))}),
            @ApiResponse(responseCode = "400", description = "Indicates that the server could not understand the request. This is not the same as 422 which indicates a validation error", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized.  This will be returned when no authentication information is provided.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "403", description = "The principal associated with the request does not have sufficient rights to preform this operation on the requested resource.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "404", description = "The requested resource was not found.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "422", description = "The request was syntactically correct but was not semantically correct. Usually indicating a validation problem.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))})
    })
    @RequestMapping(value = "/orders", produces = {"application/json"}, method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('employee', 'customer')")
    default ResponseEntity<OrderResponseModel> getAllOrders(@AuthenticationPrincipal Jwt jwt){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(summary = "Post an Order",
            security = @SecurityRequirement(name = "oAuth2ClientCredentials", scopes = {"application.automotive-credit-requests.bcvc.create-credit-requests.write"}),
            responses = {@ApiResponse(content = {@Content(schema = @Schema(implementation = SubmitOrderResponseModel.class))})}
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful response.", content = {@Content(schema = @Schema(implementation = SubmitOrderResponseModel.class))}),
            @ApiResponse(responseCode = "400", description = "Indicates that the server could not understand the request. This is not the same as 422 which indicates a validation error", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized.  This will be returned when no authentication information is provided.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "403", description = "The principal associated with the request does not have sufficient rights to preform this operation on the requested resource.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "404", description = "The requested resource was not found.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "422", description = "The request was syntactically correct but was not semantically correct. Usually indicating a validation problem.", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(implementation = EmptyDataResponse.class))})
    })
    @RequestMapping(value = "/orders", produces = {"application/json"}, consumes = {"application/json"}, method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('employee', 'customer')")
    default ResponseEntity<SubmitOrderResponseModel> submitOrder(@Valid @RequestBody OrderRequest consumerRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
