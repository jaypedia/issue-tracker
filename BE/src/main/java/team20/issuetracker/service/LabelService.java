package team20.issuetracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.service.dto.request.RequestLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelDto;
import team20.issuetracker.service.dto.response.ResponseLabelsDto;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public ResponseLabelsDto findAll() {
        List<ResponseLabelDto> labels = labelRepository.findAll().stream()
                .map(ResponseLabelDto::form)
                .collect(Collectors.toList());
        return ResponseLabelsDto.from(labels);
    }

    @Transactional
    public Long save(RequestLabelDto requestLabelDto) {
        String title = requestLabelDto.getTitle();
        String textColor = requestLabelDto.getTextColor();
        String backgroundColor = requestLabelDto.getBackgroundColor();
        String description = requestLabelDto.getDescription();

        Label label = Label.of(title, textColor, backgroundColor, description);

        return labelRepository.save(label).getId();
    }

    @Transactional
    public Long update(Long id, RequestLabelDto requestLabelDto) {
        Label label = labelRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 Label 입니다.");
        });
        label.update(requestLabelDto);
        return label.getId();
    }

    @Transactional
    public void delete(Long id) {
        labelRepository.deleteById(id);
    }
}

