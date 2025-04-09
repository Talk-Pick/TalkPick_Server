package talkPick.adapter.out.dto;

import talkPick.domain.type.Role;

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
}
