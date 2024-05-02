package ee.rik.domain.service;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class NationalIdentificationCodeValidationServiceTest {

    private final NationalIdentificationCodeValidationService nationalIdentificationCodeValidationService = new NationalIdentificationCodeValidationService();

    @Test
    void testThatIsValidIsNotSuccessful() {
        // given
        String nationalIdentificationCode = "38307162222";

        // when
        boolean valid = nationalIdentificationCodeValidationService.isValid(nationalIdentificationCode);

        // then
        assertThat(valid, is(false));
    }

    @Test
    void testThatIsValidIsSuccessful() {
        // given
        String nationalIdentificationCode = "39305250276";

        // when
        boolean valid = nationalIdentificationCodeValidationService.isValid(nationalIdentificationCode);

        // then
        assertThat(valid, is(true));
    }

}
