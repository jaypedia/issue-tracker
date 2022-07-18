package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.label.ResponseLabelsDto;
import team20.issuetracker.domain.label.SaveLabelDto;
import team20.issuetracker.domain.label.UpdateLabelDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/labels")
public class LabelController {
    private final LabelService labelService;

    @GetMapping
    public ResponseEntity<ResponseLabelsDto> findAllLabels() {
        ResponseLabelsDto labels = labelService.findAll();
        return ResponseEntity.ok(labels);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody SaveLabelDto saveLabelDto) {
        return ResponseEntity.ok(labelService.save(saveLabelDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody UpdateLabelDto updateLabelDto) {
        return ResponseEntity.ok(labelService.update(id, updateLabelDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        labelService.delete(id);
    }
}
