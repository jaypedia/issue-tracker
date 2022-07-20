package team20.issuetracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import team20.issuetracker.domain.label.RequestLabelDto;
import team20.issuetracker.domain.label.ResponseLabelsDto;
import team20.issuetracker.service.LabelService;

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
    public ResponseEntity<Long> save(@RequestBody @Valid RequestLabelDto requestLabelDto) {
        return ResponseEntity.ok(labelService.save(requestLabelDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody @Valid RequestLabelDto requestLabelDto) {
        return ResponseEntity.ok(labelService.update(id, requestLabelDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        labelService.delete(id);
    }
}
