package progressor.userservice.profile.dto.request;

public record ProfileRequest(
   String nickname,
   String avatar,
   String statusMessage
)
{}
