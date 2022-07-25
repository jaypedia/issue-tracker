package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.login.oauth.dto.request.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.service.OauthService;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login/oauth/github")
    public ResponseEntity<ResponseLoginDto> login(@RequestParam String code) {
        ResponseLoginDto responseLoginDto = oauthService.signup(code);
        return ResponseEntity.ok().body(responseLoginDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> requestRefresh(@RequestBody RequestRefreshDto requestRefreshDto) {
        String newAccessToken = oauthService.checkRefreshToken(requestRefreshDto);
        return ResponseEntity.ok().body(newAccessToken);
    }
}
