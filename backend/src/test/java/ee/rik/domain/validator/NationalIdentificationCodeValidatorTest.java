package ee.rik.domain.validator;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class NationalIdentificationCodeValidatorTest {

    private final NationalIdentificationCodeValidator nationalIdentificationCodeValidator = new NationalIdentificationCodeValidator();

    @Test
    void testThatIsValidIsNotSuccessful() {
        // given
        String nationalIdentificationCode = "38307162222";

        // when
        boolean valid = nationalIdentificationCodeValidator.isValid(nationalIdentificationCode, null);

        // then
        assertThat(valid, is(false));
    }

    @Test
    void testThatIsValidIsSuccessful() {
        // given
        String nationalIdentificationCode = "39305250276";

        // when
        boolean valid = nationalIdentificationCodeValidator.isValid(nationalIdentificationCode, null);

        // then
        assertThat(valid, is(true));
    }

}