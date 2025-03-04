package talkPick.adapter.in.dto;

import talkPick.application.validator.annotation.ValidPassword;
import talkPick.domain.type.Role;


public class AdminReqDTO {

    public record Admin(
        Long id,
        Role role
    ){}

    public record Signup (
        String email,
        @ValidPassword
        String password
    ){}

    public record Login (
        String email,
        String password
    ){}
}
