package team20.issuetracker.home;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/test")
    public ResponseEntity<String> error() {
        return ResponseEntity.ok("test");
    }
}
