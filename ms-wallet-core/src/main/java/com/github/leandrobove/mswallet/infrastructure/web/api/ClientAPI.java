package com.github.leandrobove.mswallet.infrastructure.web.api;

import com.github.leandrobove.mswallet.infrastructure.web.api.exception.ApiError;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateClientRequest;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateClientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)

@Tag(name = "Client", description = "Endpoints to manage clients")
public interface ClientAPI {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)

    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "A validation error was thrown",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class)))
    })
    CreateClientResponse create(@RequestBody @Valid CreateClientRequest request);

}
