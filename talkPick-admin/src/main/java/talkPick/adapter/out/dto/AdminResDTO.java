package talkPick.adapter.out.dto;

public class AdminResDTO {

    public record Signup (
            long adminId,
            String email
    ){}

    public record Login (
            long adminId,
            String email
    ){}
}
