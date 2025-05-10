package talkPick.adapter.out.admin.dto;

import talkPick.domain.auth.Role;

public class AdminResDTO {

    public record Admin(
            Long id,
            Role role
    ){}

    public record Signup(
            Long id,
            String name,
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
