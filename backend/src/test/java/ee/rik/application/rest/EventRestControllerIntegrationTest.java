package ee.rik.application.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.application.response.EventParticipantsResponse;
import ee.rik.application.response.PersonParticipantResponse;
import ee.rik.domain.EventParticipant;
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

        EntityFieldValidationError entityFieldValidationError1 = entityFieldValidationErrors.get(0);
        assertThat(entityFieldValidationError1.getFieldName(), is("event.description"));
        assertThat(entityFieldValidationError1.getValidationErrorMessage(), is("Nõutud"));

        EntityFieldValidationError entityFieldValidationError2 = entityFieldValidationErrors.get(1);
        assertThat(entityFieldValidationError2.getFieldName(), is("event.location"));
        assertThat(entityFieldValidationError2.getValidationErrorMessage(), is("Nõutud"));

        EntityFieldValidationError entityFieldValidationError3 = entityFieldValidationErrors.get(2);
        assertThat(entityFieldValidationError3.getFieldName(), is("event.startDateTime"));
        assertThat(entityFieldValidationError3.getValidationErrorMessage(), is("Nõutud"));
    }

    @Sql("/sql/EventRestControllerIntegrationTest/testThatModifyEventIsNotSuccessful.sql")
    @Test
    void testThatModifyEventIsNotSuccessful() {
        // given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(
                "{\"event\": {\"name\": \"Event Name 1\", \"startDateTime\": \"01.01.2024 12:59\", \"location\": \"Tallinn\", \"description\": \"Description 1\"}}",
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
                .exchange("/events/9/participants", HttpMethod.GET, httpEntity, EventParticipantsResponse.class);

        // then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        List<EventParticipant> eventParticipants = responseEntity.getBody().getParticipants();
        assertThat(eventParticipants.size(), is(3));

        EventParticipant eventParticipant1 = eventParticipants.get(0);
        assertThat(eventParticipant1.getId(), is("PP-9"));
        assertThat(eventParticipant1.getName(), is("Janar Rahumeel"));
        assertThat(eventParticipant1.getIdentityCode(), is("38008180024"));

        EventParticipant eventParticipant2 = eventParticipants.get(1);
        assertThat(eventParticipant2.getId(), is("LEP-9"));
        assertThat(eventParticipant2.getName(), is("Janar Solutions OÜ"));
        assertThat(eventParticipant2.getIdentityCode(), is("88888888"));

        EventParticipant eventParticipant3 = eventParticipants.get(2);
        assertThat(eventParticipant3.getId(), is("PP-10"));
        assertThat(eventParticipant3.getName(), is("Janar Tasane"));
        assertThat(eventParticipant3.getIdentityCode(), is("38008180026"));
    }

}
