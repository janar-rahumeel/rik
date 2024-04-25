package ee.rik.application.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.application.response.EventParticipantsResponse;
import ee.rik.domain.EventParticipant;
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
        assertThat(eventParticipant1.getId(), is("PP-1"));
        assertThat(eventParticipant1.getName(), is("Janar Rahumeel"));
        assertThat(eventParticipant1.getIdentityCode(), is("38008180024"));

        EventParticipant eventParticipant2 = iterator.next();
        assertThat(eventParticipant2.getId(), is("LEP-1"));
        assertThat(eventParticipant2.getName(), is("Janar Solutions OÜ"));
        assertThat(eventParticipant2.getIdentityCode(), is("88888888"));

        EventParticipant eventParticipant3 = iterator.next();
        assertThat(eventParticipant3.getId(), is("PP-2"));
        assertThat(eventParticipant3.getName(), is("Janar Tasane"));
        assertThat(eventParticipant3.getIdentityCode(), is("38008180026"));
    }

}
