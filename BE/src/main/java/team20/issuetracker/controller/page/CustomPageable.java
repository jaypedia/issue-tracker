package team20.issuetracker.controller.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

public class CustomPageable {

    private int page;
    private int size = 25;
    private Direction direction = Direction.DESC;
    private String sortType = "id";

    private CustomPageable(int page) {
        this.page = page;
    }

    public static PageRequest defaultPage(String page) {
        int pageNumber = 1;
        if (page.matches("^\\d*$")) {
            pageNumber = Integer.parseInt(page) > 0 ? Integer.parseInt(page) : pageNumber;
        }
        CustomPageable customPageable = new CustomPageable(pageNumber);
        return PageRequest.of(customPageable.page - 1, customPageable.size, customPageable.direction, customPageable.sortType);
    }
}
