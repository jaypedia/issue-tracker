package team20.issuetracker.login.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team20.issuetracker.login.oauth.dto.LoginResponse;
import team20.issuetracker.login.oauth.service.OauthService;

@RequiredArgsConstructor
@Controller
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @ResponseBody
    @GetMapping("/login/oauth/{provider}")
    public LoginResponse signup(@PathVariable String provider, @RequestParam String code) {

        return oauthService.signup(provider, code);
    }

}
