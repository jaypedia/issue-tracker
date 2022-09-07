package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team20.issuetracker.login.oauth.dto.request.RequestMaintainDto;
import team20.issuetracker.login.oauth.dto.request.RequestRefreshDto;
import team20.issuetracker.login.oauth.dto.response.ResponseLoginDto;
import team20.issuetracker.service.OauthService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OauthController {

    private final OauthService oauthService;

    @GetMapping("/login/oauth/github")
    public ResponseEntity<ResponseLoginDto> login(@RequestParam String code) {
        ResponseLoginDto responseLoginDto = oauthService.signup(code);
        log.info("code = {}", code);
        return ResponseEntity.ok().body(responseLoginDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> requestRefresh(@RequestBody RequestRefreshDto requestRefreshDto) {
        String newAccessToken = oauthService.checkRefreshToken(requestRefreshDto);
        return ResponseEntity.ok().body(newAccessToken);
    }

    @GetMapping("/maintain")
    public ResponseEntity<ResponseLoginDto> maintain(@RequestBody RequestMaintainDto requestMaintainDto) {
        String refreshToken = requestMaintainDto.getRefreshToken();
        ResponseLoginDto maintainUserInfo = oauthService.getMaintainUserInfo(refreshToken);

        return ResponseEntity.ok(maintainUserInfo);
    }
}
