package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TagValidator implements Validator {

    private static final int NAME_MAX_LENGTH = 40;
    private static final String EMPTY_NAME_ERROR = "name_empty";
    private static final String TOO_LONG_NAME_ERROR = "name_too_long";

    @Override
    public boolean supports(Class<?> clazz) {
        return TagDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TagDto tag = (TagDto) target;
        validateName(tag.getName(), errors);
    }

    private void validateName(String name, Errors errors){
        if(name.isEmpty()) {
            errors.reject(EMPTY_NAME_ERROR);
        } else if (name.length() > NAME_MAX_LENGTH) {
            errors.reject(TOO_LONG_NAME_ERROR);
        }
    }
}