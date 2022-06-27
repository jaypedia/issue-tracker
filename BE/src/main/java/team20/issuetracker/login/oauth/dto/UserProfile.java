package team20.issuetracker.login.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team20.issuetracker.login.domain.member.Member;
import team20.issuetracker.login.oauth.Role;

@NoArgsConstructor
@Getter
public class UserProfile {
    @JsonProperty("id")
    private String oauthId;
    private String email;
    private String name;
    @JsonProperty("avatar_url")
    private String profileImageUrl;

    @Builder
    public UserProfile(String oauthId, String email, String name, String profileImageUrl) {
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public Member toMember() {
        return Member.builder()
                .oauthId(oauthId)
                .email(email)
                .name(name)
                .profileImageUrl(profileImageUrl)
                .role(Role.GUEST)
                .build();
    }
}
