package com.techbank.account.command.api.commands.controller;

import com.techbank.account.command.api.commands.OpenAccountCommand;
import com.techbank.account.command.api.commands.dto.OpenAcountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/openAccount")
@RequiredArgsConstructor
@Slf4j
public class OpenAccountController {
    private final CommandDispatcher dispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id= UUID.randomUUID();
        command.setId(id);
        try {
            dispatcher.send(command);
            return new ResponseEntity<>(new OpenAcountResponse("Bank account creation completed succesfully",id), HttpStatus.CREATED);
        }catch (IllegalStateException e) {
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
