package talkPick.adapter.in.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import talkPick.application.admin.validator.annotation.ValidPassword;
import talkPick.domain.admin.type.Role;

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
