package team20.issuetracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team20.issuetracker.domain.milestone.MilestoneRepository;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    public final MilestoneRepository milestoneRepository;

    @Transactional
    public Long save() {
        return 0L;
    }
}
