package team20.issuetracker.login.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import team20.issuetracker.login.oauth.dto.LoginResponse;
import team20.issuetracker.login.oauth.service.OauthService;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login")
    public RedirectView getToken(RedirectAttributes redirectAttributes) {

        return oauthService.getToken(redirectAttributes);
    }

    @GetMapping("/login/oauth/github/callback")
    public ResponseEntity<LoginResponse> login(@RequestParam String code, @RequestParam String state) {
        LoginResponse loginResponse = oauthService.signup(code);
        return ResponseEntity.ok().body(loginResponse);
    }
}
