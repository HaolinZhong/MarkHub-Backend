package hz.blog.markhub.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pager<T> {
    private Integer currentPage;
    private Integer pageSize;
    private List<T> rows;
    private Integer total;
}
