package ee.rik.application.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

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

}
