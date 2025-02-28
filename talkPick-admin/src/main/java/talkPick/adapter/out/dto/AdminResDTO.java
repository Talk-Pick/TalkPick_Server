package talkPick.adapter.out.dto;

public class AdminResDTO {

    public record Signup (
            long adminId,
            String email,
            String password
    ){}

    public record Login (
            String email,
            String password
    ){}
}
