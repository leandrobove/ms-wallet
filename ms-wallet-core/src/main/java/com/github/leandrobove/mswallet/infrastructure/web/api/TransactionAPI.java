package com.github.leandrobove.mswallet.infrastructure.web.api;

import com.github.leandrobove.mswallet.infrastructure.web.api.exception.ApiError;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateTransactionRequest;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateTransactionResponse;
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

@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)

@Tag(name = "Transaction", description = "Endpoint to manage account's transactions")
public interface TransactionAPI {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)

    @Operation(summary = "Create new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "A validation error was thrown",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "account_id parameter was not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiError.class)))
    })
    CreateTransactionResponse create(@RequestBody @Valid CreateTransactionRequest request);
}
