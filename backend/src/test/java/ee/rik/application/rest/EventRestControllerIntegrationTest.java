package ee.rik.application.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.application.response.EventParticipantsResponse;
import ee.rik.application.response.LegalEntityParticipantResponse;
import ee.rik.application.response.PersonParticipantResponse;
import ee.rik.domain.EventParticipant;
import ee.rik.domain.LegalEntityParticipant;
import ee.rik.domain.PersonParticipant;
import ee.rik.test.AbstractRestControllerIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

class EventRestControllerIntegrationTest extends AbstractRestControllerIntegrationTest {

    @Test
    void testThatCreateEventIsNotSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>("{\"event\": {\"name\": \"Cool Event\"}}", httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/events", HttpMethod.POST, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody()
                .getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(3));

        EntityFieldValidationError entityFieldValidationError = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError.getFieldName(), is("event.description"));
        assertThat(entityFieldValidationError.getValidationErrorMessage(), is("Nõutud"));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatModifyEventIsNotSuccessful.sql")
    @Test
    void testThatModifyEventIsNotSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"event\": {\"name\": \"Event Name 1\", \"startDateTime\": \"2024-01-01T12:59:59\", \"location\": \"Tallinn\", \"description\": \"Description 1\"}}",
                httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/events/1", HttpMethod.PUT, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody()
                .getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(1));

        EntityFieldValidationError entityFieldValidationError = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError.getFieldName(), is("event.general"));
        assertThat(entityFieldValidationError.getValidationErrorMessage(), is("Sündmust ei ole lubatud muuta"));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatGetParticipantsIsSuccessful.sql")
    @Test
    void testThatGetParticipantsIsSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        // when
        ResponseEntity<EventParticipantsResponse> responseEntity = testRestTemplate
                .exchange("/events/2/participants", HttpMethod.GET, httpEntity, EventParticipantsResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        Set<EventParticipant> eventParticipants = responseEntity.getBody().getParticipants();
        assertThat(eventParticipants.size(), is(3));

        Iterator<EventParticipant> iterator = eventParticipants.iterator();

        EventParticipant eventParticipant1 = iterator.next();
        //assertThat(eventParticipant1.getId(), is("PP-1"));
        assertThat(eventParticipant1.getName(), is("Janar Rahumeel"));
        //assertThat(eventParticipant1.getIdentityCode(), is("38008180024"));

        EventParticipant eventParticipant2 = iterator.next();
        //assertThat(eventParticipant2.getId(), is("LEP-1"));
        assertThat(eventParticipant2.getName(), is("Janar Solutions OÜ"));
        //assertThat(eventParticipant2.getIdentityCode(), is("88888888"));

        EventParticipant eventParticipant3 = iterator.next();
        //assertThat(eventParticipant3.getId(), is("PP-2"));
        assertThat(eventParticipant3.getName(), is("Janar Tasane"));
        //assertThat(eventParticipant3.getIdentityCode(), is("38008180026"));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatAddPersonParticipantIsNotSuccessfulWhenAlreadyAdded.sql")
    @Test
    void testThatAddPersonParticipantIsNotSuccessfulWhenAlreadyAdded() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"personParticipant\": {\"firstName\": \"Janar\", \"lastName\": \"Rahumeel\", \"nationalIdentificationCode\": \"61905270055\", \"paymentTypeId\": 3}}",
                httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/events/3/participants/person", HttpMethod.POST, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody()
                .getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(1));

        EntityFieldValidationError entityFieldValidationError = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError.getFieldName(), is("personParticipant.general"));
        assertThat(entityFieldValidationError.getValidationErrorMessage(), is("Isik on sündmusele juba lisatud"));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatAddPersonParticipantIsSuccessful.sql")
    @Test
    void testThatAddPersonParticipantIsSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"personParticipant\": {\"firstName\": \"Janar\", \"lastName\": \"Rahumeel\", \"nationalIdentificationCode\": \"60208256593\", \"paymentTypeId\": 4}}",
                httpHeaders);

        // when
        ResponseEntity<PersonParticipantResponse> responseEntity = testRestTemplate
                .exchange("/events/4/participants/person", HttpMethod.POST, httpEntity, PersonParticipantResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        PersonParticipant personParticipant = responseEntity.getBody().getPersonParticipant();
        assertThat(personParticipant, is(notNullValue()));
        assertThat(personParticipant.getFirstName(), is("Janar"));
        assertThat(personParticipant.getLastName(), is("Rahumeel"));
        assertThat(personParticipant.getNationalIdentificationCode(), is("60208256593"));
        assertThat(personParticipant.getPaymentTypeId(), is(4));
        assertThat(personParticipant.getAdditionalInformation(), is(nullValue()));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatAddLegalEntityParticipantIsNotSuccessfulWhenAlreadyAdded.sql")
    @Test
    void testThatAddLegalEntityParticipantIsNotSuccessfulWhenAlreadyAdded() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"legalEntityParticipant\": {\"name\": \"IT Solutions OÜ\", \"registrationCode\": \"77777777\", \"participantCount\": 1, \"paymentTypeId\": 5}}",
                httpHeaders);

        // when
        ResponseEntity<ErrorResponse> responseEntity = testRestTemplate
                .exchange("/events/5/participants/legal-entity", HttpMethod.POST, httpEntity, ErrorResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));

        List<EntityFieldValidationError> entityFieldValidationErrors = responseEntity.getBody()
                .getEntityFieldValidationErrors();
        assertThat(entityFieldValidationErrors.size(), is(1));

        EntityFieldValidationError entityFieldValidationError = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError.getFieldName(), is("legalEntityParticipant.general"));
        assertThat(entityFieldValidationError.getValidationErrorMessage(), is("Ettevõte on sündmusele juba lisatud"));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatAddLegalEntityParticipantIsSuccessful.sql")
    @Test
    void testThatAddLegalEntityParticipantIsSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"legalEntityParticipant\": {\"name\": \"IT2 Solutions OÜ\", \"registrationCode\": \"66666666\", \"participantCount\": 1, \"paymentTypeId\": 6}}",
                httpHeaders);

        // when
        ResponseEntity<LegalEntityParticipantResponse> responseEntity = testRestTemplate
                .exchange("/events/6/participants/legal-entity", HttpMethod.POST, httpEntity, LegalEntityParticipantResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        LegalEntityParticipant legalEntityParticipant = responseEntity.getBody().getLegalEntityParticipant();
        assertThat(legalEntityParticipant, is(notNullValue()));
        assertThat(legalEntityParticipant.getName(), is("IT2 Solutions OÜ"));
        assertThat(legalEntityParticipant.getRegistrationCode(), is("66666666"));
        assertThat(legalEntityParticipant.getParticipantCount(), is(1));
        assertThat(legalEntityParticipant.getPaymentTypeId(), is(6));
        assertThat(legalEntityParticipant.getAdditionalInformation(), is(nullValue()));
    }

}
