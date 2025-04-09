package talkPick.adapter.in.dto;

import talkPick.application.validator.annotation.ValidPassword;
import talkPick.domain.type.Role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

public class AdminReqDTO {

    public record Admin (
        Long id,
        Role role
    ) {}

    public record Signup (
        @NotNull @Email String email,
        @NotNull @ValidPassword String password
    ) {}

    public record Login (
        @NotNull @Email String email,
        @NotNull String password
    ) {}
}
