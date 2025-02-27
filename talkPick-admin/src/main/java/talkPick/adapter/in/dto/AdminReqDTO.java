package talkPick.adapter.in.dto;

import talkPick.domain.type.Role;

public class AdminReqDTO {

    public record Admin(
        Long adminId,
        Role role
    ){}

    public record Signup (
        String email,
        String password
    ){}

    public record Login (
        String email,
        String password
    ){}
}
