package com.techbank.account.command.api.commands.dto;

import com.techbank.account.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAcountResponse extends BaseResponse {
    private UUID accountId;
    public OpenAcountResponse(String message,UUID accountId) {
        super(message);
        this.accountId = accountId;
    }
}
