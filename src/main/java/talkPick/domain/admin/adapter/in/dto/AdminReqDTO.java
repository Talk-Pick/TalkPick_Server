package talkPick.domain.admin.adapter.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import talkPick.domain.admin.application.validator.annotation.ValidPassword;
import talkPick.domain.auth.Role;

public class AdminReqDTO {

    public record Admin (
        Long id,
        Role role
    ) {}

    public record Signup (
        @NotNull @Email String email,
        @NotNull String name,
        @NotNull @ValidPassword String password
    ) {}

    public record Login (
        @NotNull @Email String email,
        @NotNull String password
    ) {}
}
