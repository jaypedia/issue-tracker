package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.service.AssigneeService;
import team20.issuetracker.service.dto.response.ResponseAssigneesDto;

@RequestMapping("/api/assignees")
@RequiredArgsConstructor
@RestController
public class AssigneeController {

    private final AssigneeService assigneeService;

    @GetMapping
    public ResponseEntity<ResponseAssigneesDto> read() {
        return ResponseEntity.ok(assigneeService.findAll());
    }
}

