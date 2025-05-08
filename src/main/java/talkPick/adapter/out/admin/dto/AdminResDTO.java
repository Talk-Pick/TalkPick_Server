package talkPick.adapter.out.admin.dto;

import talkPick.domain.admin.type.Role;

public class AdminResDTO {

    public record Admin(
            Long id,
            Role role
    ){}

    public record Signup(
            Long id,
            Role role
    ) {}

    public record Login (
            long id,
            String email
    ){}

    public record Test (
            String message
    ) {}
}
