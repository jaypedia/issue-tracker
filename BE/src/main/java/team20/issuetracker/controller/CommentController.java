package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.service.CommentService;
import team20.issuetracker.service.dto.request.RequestCommentDto;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity<Long> save(@RequestBody RequestCommentDto requestCommentDto, HttpServletRequest request) {
        String oauthId = request.getAttribute("oauthId").toString();
        return ResponseEntity.ok(commentService.save(requestCommentDto, oauthId));
    }
}
