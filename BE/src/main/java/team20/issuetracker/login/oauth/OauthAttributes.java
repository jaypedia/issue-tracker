package team20.issuetracker.login.oauth;

import java.util.Map;

import team20.issuetracker.login.oauth.dto.UserProfile;

// 얻어온 유저 정보를 UserProfile 클래스로 만들어주기 위한 클래스.

public class OauthAttributes {
    public UserProfile of(Map<String, Object> attributes) {
        return UserProfile.builder()
                .oauthId(String.valueOf(attributes.get("id")))
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .imageUrl((String) attributes.get("avatar_url"))
                .build();
    }
}



//public enum OauthAttributes {
//    GITHUB("github") {
//        @Override
//        public UserProfile of(Map<String, Object> attributes) {
//            return UserProfile.builder()
//                    .oauthId(String.valueOf(attributes.get("id")))
//                    .email((String) attributes.get("email"))
//                    .name((String) attributes.get("name"))
//                    .imageUrl((String) attributes.get("avatar_url"))
//                    .build();
//        }
//    };
//
//    private final String providerName;
//
//    OauthAttributes(String name) {
//        this.providerName = name;
//    }
//
//    public static UserProfile extract( Map<String, Object> attributes) {
//        return Arrays.stream(values())
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new)
//                .of(attributes);
//    }
//
//    // GitHub Oauth 만 사용하기 떄문에 없어도 될 듯 하다.
//    public abstract UserProfile of(Map<String, Object> attributes);
//}
