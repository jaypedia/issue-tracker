package team20.issuetracker.controller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.label.Label;
import team20.issuetracker.domain.label.LabelRepository;
import team20.issuetracker.domain.label.ResponseLabelDto;
import team20.issuetracker.domain.label.ResponseLabelsDto;
import team20.issuetracker.domain.label.SaveLabelDto;
import team20.issuetracker.domain.label.UpdateLabelDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabelService {

    private final LabelRepository labelRepository;

    public ResponseLabelsDto findAll() {
        List<ResponseLabelDto> labels = labelRepository.findAll().stream()
                .map(ResponseLabelDto::form)
                .collect(Collectors.toList());
        return ResponseLabelsDto.from(labels);
    }

    @Transactional
    public Long save(SaveLabelDto saveLabelDto) {
        String title = saveLabelDto.getTitle();
        String textColor = saveLabelDto.getTextColor();
        String backgroundColor = saveLabelDto.getBackgroundColor();
        String description = saveLabelDto.getDescription();

        Label label = Label.of(title, textColor, backgroundColor, description);

        return labelRepository.save(label).getId();
    }

    @Transactional
    public Long update(Long id, UpdateLabelDto updateLabelDto) {
        Label label = labelRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 Label 입니다.");
        });
        label.update(updateLabelDto);
        return label.getId();
    }

    @Transactional
    public void delete(Long id) {
        Label label = labelRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 Label 입니다.");
        });
        labelRepository.delete(label);
    }
}
