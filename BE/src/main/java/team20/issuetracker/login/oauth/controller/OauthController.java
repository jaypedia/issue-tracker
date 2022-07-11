package team20.issuetracker.login.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.login.oauth.dto.LoginResponse;
import team20.issuetracker.login.oauth.service.OauthService;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login/oauth/github")
    public ResponseEntity<LoginResponse> login(@RequestParam String code) {
        LoginResponse loginResponse = oauthService.signup(code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
