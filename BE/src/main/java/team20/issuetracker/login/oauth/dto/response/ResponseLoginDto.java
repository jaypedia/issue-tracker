package team20.issuetracker.login.oauth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.login.oauth.Role;

@Getter
@NoArgsConstructor
public class ResponseLoginDto {
    private Long id;
    private String name;
    private String email;
    private String profileImageUrl;
    private Role role;
    private String tokenType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public ResponseLoginDto(Long id, String name, String email, String profileImageUrl, Role role, String tokenType, String accessToken, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
