package team20.issuetracker.controller.page;

import org.springframework.data.domain.PageImpl;

import lombok.Getter;
import team20.issuetracker.service.dto.response.ResponseIssueDto;

@Getter
public class CustomPage {

    private boolean first;
    private boolean last;
    private boolean hasNext;
    private int totalPages;
    private long totalElements;
    private int page;
    private int size;

    private CustomPage(PageImpl<ResponseIssueDto> page) {
        this.first = page.isFirst();
        this.last = page.isLast();
        this.hasNext = page.hasNext();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
    }

    public static CustomPage of(PageImpl<ResponseIssueDto> page) {
        return new CustomPage(page);
    }
}
