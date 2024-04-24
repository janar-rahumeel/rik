package ee.rik.application.rest;

import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.test.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class LegalEntityParticipantRestControllerTest extends AbstractRestControllerIntegrationTest {

    @Test
    void testThatCreateLegalEntityParticipantIsNotSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>("{\"legalEntityParticipant\": {\"name\": \"Co\", \"participantCount\": 0, \"registrationCode\": \"7777\"}}", httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/legal-entity-participants", HttpMethod.POST, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody().getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(4));

        EntityFieldValidationError entityFieldValidationError1 = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError1.getFieldName(), is("legalEntityParticipant.name"));
        assertThat(entityFieldValidationError1.getValidationErrorMessage(), is("Ettevõtte nimi peab olema vähemalt 3 tähemärki"));

        EntityFieldValidationError entityFieldValidationError2 = entityFieldValidationErrors.get(1);
        assertThat(entityFieldValidationError2.getFieldName(), is("legalEntityParticipant.participantCount"));
        assertThat(entityFieldValidationError2.getValidationErrorMessage(), is("Peab olema vähemalt üks osaline"));

        EntityFieldValidationError entityFieldValidationError3 = entityFieldValidationErrors.get(2);
        assertThat(entityFieldValidationError3.getFieldName(), is("legalEntityParticipant.paymentTypeId"));
        assertThat(entityFieldValidationError3.getValidationErrorMessage(), is("must not be null")); // TODO !?!?

        EntityFieldValidationError entityFieldValidationError4 = entityFieldValidationErrors.get(3);
        assertThat(entityFieldValidationError4.getFieldName(), is("legalEntityParticipant.registrationCode"));
        assertThat(entityFieldValidationError4.getValidationErrorMessage(), is("Registrikoodi pikkus peab olema 11"));
    }

}