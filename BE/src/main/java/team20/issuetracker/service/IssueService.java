package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.issue.IssueRepository;
import team20.issuetracker.domain.issue.SaveIssueDto;

@RequiredArgsConstructor
@Service
public class IssueService {

    private final IssueRepository issueRepository;

    @Transactional
    public Long save(SaveIssueDto saveIssueDto) {
        return issueRepository.save(saveIssueDto.toEntity()).getId();
    }
}
