package model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Pageable {

    private int page;

    private int size;

    private int offset;

    private Sort sort;

    private List<Sort> sorts;

    private Map<String, Object> params = new HashMap<String, Object>();

    private int totalElements;

    public Pageable(){
        
    }

    public Pageable(int page, int size) {
        this(page, size, null);
    }

    public Pageable(int page, int size, List<Sort> sorts) {
        this.page = page;
        this.size = size;
        this.sorts = sorts;
        this.offset = (page - 1) * size;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public Pageable addParam(String key, Object value) {
        params.put(key, value);

        return this;
    }

    public Pageable addSomeParam(String key, Object value) {
        if (value != null)
            params.put(key, value);

        return this;
    }

    public Pageable addSomeParam(String key, String value) {
        if (StringUtils.isNoneBlank(value))
            params.put(key, value);

        return this;
    }

}