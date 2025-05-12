package org.example.appliancestore.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable getPageable(Integer page, Integer size, String sort) {
        if (page == null || size == null) {
            return Pageable.unpaged();
        }
        if (sort == null) {
            return PageRequest.of(page, size);
        }
        return PageRequest.of(page, size, Sort.by(sort).ascending());
    }

}
