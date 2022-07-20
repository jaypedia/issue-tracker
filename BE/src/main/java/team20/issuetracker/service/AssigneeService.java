package team20.issuetracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.assginee.AssigneeRepository;
import team20.issuetracker.service.dto.response.ResponseAssigneeDto;
import team20.issuetracker.service.dto.response.ResponseAssigneesDto;

@RequiredArgsConstructor
@Service
public class AssigneeService {

    private final AssigneeRepository assigneeRepository;

    @Transactional(readOnly = true)
    public ResponseAssigneesDto findAll() {
        List<ResponseAssigneeDto> assignees = assigneeRepository
                .findAll()
                .stream()
                .map(ResponseAssigneeDto::from)
                .collect(Collectors.toList());

        return ResponseAssigneesDto.from(assignees);
    }
}
