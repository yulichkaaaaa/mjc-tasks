package com.epam.esm.interceptor;

import com.epam.esm.exception.pagination.IncorrectPageNumberException;
import com.epam.esm.exception.pagination.IncorrectPageSizeException;
import com.epam.esm.exception.pagination.TooMuchPageElementsException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Check pagination parameters.
 *
 * @author Shuleiko Yulia
 */
public class PaginationInterceptor implements HandlerInterceptor {

    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final int MAX_SIZE_VALUE = 100;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey(PAGE_PARAM) && NumberUtils.isCreatable(parameters.get(PAGE_PARAM)[0])) {
            int page = Integer.parseInt(parameters.get(PAGE_PARAM)[0]);
            if (page < 0) {
                throw new IncorrectPageNumberException(page);
            }
        }
        if (parameters.containsKey(SIZE_PARAM) && NumberUtils.isCreatable(parameters.get(SIZE_PARAM)[0])) {
            int size = Integer.parseInt(parameters.get(SIZE_PARAM)[0]);
            if (size <= 0) {
                throw new IncorrectPageSizeException(size);
            } else if (size > MAX_SIZE_VALUE) {
                throw new TooMuchPageElementsException(size);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
