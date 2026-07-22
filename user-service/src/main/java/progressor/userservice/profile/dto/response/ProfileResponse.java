package progressor.userservice.profile.dto.response;

public record ProfileResponse(
    String nickname,
    String avatar,
    String statusMessage,
    Integer level,
    Integer bossesDefeated,
    Integer questsCompleted
)
{}
