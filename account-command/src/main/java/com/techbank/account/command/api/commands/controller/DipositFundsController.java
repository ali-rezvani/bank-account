package com.techbank.account.command.api.commands.controller;

import com.techbank.account.command.api.commands.DepositFundsCommand;
import com.techbank.account.command.api.commands.dto.OpenAcountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/depositFunds")
@RequiredArgsConstructor
@Slf4j
public class DipositFundsController {
    private final CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id")UUID id, @RequestBody DepositFundsCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully"), HttpStatus.OK);
        }catch (IllegalStateException e){
            log.warn("Client made a bad request.",e);
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse("Client made a bad request."));
        }catch (Exception e){
            log.error("Error wile processing request.",e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse("Error wile processing request."));
        }
    }
}
