package ee.rik.application.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import ee.rik.application.model.PersonParticipantData;
import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.application.response.PersonParticipantResponse;
import ee.rik.test.AbstractRestControllerIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

class PersonParticipantRestControllerTest extends AbstractRestControllerIntegrationTest {

    @Sql("/sql/PersonParticipantRestControllerTest/testThatAddPersonParticipantIsNotSuccessfulWhenAlreadyAdded.sql")
    @Test
    void testThatAddPersonParticipantIsNotSuccessfulWhenAlreadyAdded() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"eventId\": 3, \"personParticipant\": {\"firstName\": \"Janar\", \"lastName\": \"Rahumeel\", \"nationalIdentificationCode\": \"61905270055\", \"paymentTypeId\": 3}}",
                httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/person-participants/", HttpMethod.POST, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody()
                .getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(1));

        EntityFieldValidationError entityFieldValidationError = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError.getFieldName(), is("personParticipant.general"));
        assertThat(entityFieldValidationError.getValidationErrorMessage(), is("Isik on sündmusele juba lisatud"));
    }

    @Sql("/sql/PersonParticipantRestControllerTest/testThatAddPersonParticipantIsSuccessful.sql")
    @Test
    void testThatAddPersonParticipantIsSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"eventId\": 4, \"personParticipant\": {\"firstName\": \"Janar\", \"lastName\": \"Rahumeel\", \"nationalIdentificationCode\": \"60208256593\", \"paymentTypeId\": 4}}",
                httpHeaders);

        // when
        ResponseEntity<PersonParticipantResponse> responseEntity = testRestTemplate
                .exchange("/person-participants/", HttpMethod.POST, httpEntity, PersonParticipantResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        PersonParticipantData personParticipant = responseEntity.getBody().getPersonParticipant();
        assertThat(personParticipant, is(notNullValue()));
        assertThat(personParticipant.getFirstName(), is("Janar"));
        assertThat(personParticipant.getLastName(), is("Rahumeel"));
        assertThat(personParticipant.getNationalIdentificationCode(), is("60208256593"));
        assertThat(personParticipant.getPaymentTypeId(), is(4));
        assertThat(personParticipant.getAdditionalInformation(), is(nullValue()));
    }

}
