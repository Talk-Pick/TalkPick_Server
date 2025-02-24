package talkPick.adapter.out.dto;

public class AdminResDTO {

    public record Signup (
            long adminId,
            String email,
            String password,
            String adminCode
    ){}

    public record Login (
            String email,
            String password,
            String adminCode
    ){}
}
