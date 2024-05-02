package ee.rik.application.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import ee.rik.application.model.LegalEntityParticipantData;
import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.application.response.LegalEntityParticipantResponse;
import ee.rik.test.AbstractRestControllerIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

class LegalEntityParticipantRestControllerTest extends AbstractRestControllerIntegrationTest {

    @Sql("/sql/LegalEntityParticipantRestControllerTest/testThatAddLegalEntityParticipantIsNotSuccessfulWhenAlreadyAdded.sql")
    @Test
    void testThatAddLegalEntityParticipantIsNotSuccessfulWhenAlreadyAdded() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"eventId\": 5, \"legalEntityParticipant\": {\"name\": \"IT Solutions OÜ\", \"registrationCode\": \"77777777\", \"participantCount\": 1, \"paymentTypeId\": 5}}",
                httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/legal-entity-participants/", HttpMethod.POST, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody()
                .getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(1));

        EntityFieldValidationError entityFieldValidationError = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError.getFieldName(), is("legalEntityParticipant.general"));
        assertThat(entityFieldValidationError.getValidationErrorMessage(), is("Ettevõte on sündmusele juba lisatud"));
    }

    @Sql("/sql/LegalEntityParticipantRestControllerTest/testThatAddLegalEntityParticipantIsSuccessful.sql")
    @Test
    void testThatAddLegalEntityParticipantIsSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"eventId\": 6, \"legalEntityParticipant\": {\"name\": \"IT2 Solutions OÜ\", \"registrationCode\": \"66666666\", \"participantCount\": 1, \"paymentTypeId\": 6}}",
                httpHeaders);

        // when
        ResponseEntity<LegalEntityParticipantResponse> responseEntity = testRestTemplate
                .exchange("/legal-entity-participants/", HttpMethod.POST, httpEntity, LegalEntityParticipantResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        LegalEntityParticipantData legalEntityParticipant = responseEntity.getBody().getLegalEntityParticipant();
        assertThat(legalEntityParticipant, is(notNullValue()));
        assertThat(legalEntityParticipant.getName(), is("IT2 Solutions OÜ"));
        assertThat(legalEntityParticipant.getRegistrationCode(), is("66666666"));
        assertThat(legalEntityParticipant.getParticipantCount(), is(1));
        assertThat(legalEntityParticipant.getPaymentTypeId(), is(6));
        assertThat(legalEntityParticipant.getAdditionalInformation(), is(nullValue()));
    }

}
