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
    public void init() {
        tagValidator = new TagValidator();
        errors = Mockito.mock(Errors.class);
    }

    @Test
    public void validateTagTest() {
        TagDto tagDto = new TagDto("birthday");
        tagValidator.validate(tagDto, errors);
        Mockito.verify(errors, Mockito.times(0)).reject(Mockito.anyString());
    }

    @Test
    public void validateNameEmptyTest() {
        TagDto tagDto = new TagDto("");
        tagValidator.validate(tagDto, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("name", "name_empty");
    }

    @Test
    public void validateNameTooLongTest() {
        TagDto tagDto = new TagDto("birthdayjsdkljjjjjjjjjjjjjjjjjjjj" +
                "jjjjjjjjjjjjjjjjjjjjjjjjjzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz" +
                "zzzzzzzzzzzzzzzzzzzzzzzzzz");
        tagValidator.validate(tagDto, errors);
        Mockito.verify(errors, Mockito.times(1))
                .rejectValue("name", "name_too_long");
    }
}
