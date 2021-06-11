package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagValidatorTest {

    @Mock
    private Errors errors;
    private TagValidator tagValidator;

    @BeforeAll
    public void init(){
        tagValidator = new TagValidator();
        errors = Mockito.mock(Errors.class);
    }

    @Test
    public void validateTagTest(){
        TagDto tagDto = new TagDto("birthday");
        String errorEmptyName = "name_empty";
        String errorTooLongName = "name_too_long";
        tagValidator.validate(tagDto, errors);
        Mockito.verify(errors, Mockito.times(0)).reject(errorEmptyName);
        Mockito.verify(errors, Mockito.times(0)).reject(errorTooLongName);
    }

    @Test
    public void validateTagNameEmptyTest(){
        TagDto tagDto = new TagDto("");
        String errorName = "name_empty";
        tagValidator.validate(tagDto, errors);
        Mockito.verify(errors, Mockito.times(1)).reject(errorName);
    }

    @Test
    public void validateTagNameTooLongTest(){
        TagDto tagDto = new TagDto("birthdayjsdkljjjjjjjjjjjjjjjjjjjj" +
                "jjjjjjjjjjjjjjjjjjjjjjjjjzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzz");
        String errorName = "name_too_long";
        tagValidator.validate(tagDto, errors);
        Mockito.verify(errors, Mockito.times(1)).reject(errorName);
    }
}
