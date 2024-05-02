package ee.rik.application;

import ee.rik.application.response.EntityFieldValidationError;
import ee.rik.application.response.ErrorResponse;
import ee.rik.domain.EntityFieldNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders httpHeaders,
            HttpStatusCode httpStatusCode,
            WebRequest webRequest) {
        List<EntityFieldValidationError> entityFieldValidationErrorDataList = methodArgumentNotValidException.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String validationErrorMessage = objectError.getDefaultMessage();
                    return EntityFieldValidationError.builder()
                            .fieldName(fieldName)
                            .validationErrorMessage(validationErrorMessage)
                            .build();
                })
                .sorted(Comparator.comparing(EntityFieldValidationError::getFieldName))
                .toList();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .entityFieldValidationErrors(entityFieldValidationErrorDataList)
                .build();
        return handleExceptionInternal(
                methodArgumentNotValidException,
                errorResponse,
                httpHeaders,
                HttpStatus.UNPROCESSABLE_ENTITY,
                webRequest);
    }

    @ExceptionHandler(EntityFieldNotValidException.class)
    public final ResponseEntity<Object> handleEntityFieldNotValidException(
            EntityFieldNotValidException entityFieldNotValidException,
            WebRequest webRequest) {
        String fieldName = entityFieldNotValidException.getFieldName();
        String validationErrorMessage = messageSource.getMessage(
                String.format("domain.constraints.%s.message", entityFieldNotValidException.getErrorCode()),
                null,
                LocaleContextHolder.getLocale());
        EntityFieldValidationError entityFieldValidationError = EntityFieldValidationError.builder()
                .fieldName(fieldName)
                .validationErrorMessage(validationErrorMessage)
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .entityFieldValidationErrors(List.of(entityFieldValidationError))
                .build();
        return handleExceptionInternal(
                entityFieldNotValidException,
                errorResponse,
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                webRequest);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<Object> handleResponseStatusException(
            ResponseStatusException responseStatusException,
            WebRequest webRequest) {
        return resolveResponseEntity(responseStatusException.getStatusCode(), responseStatusException, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest webRequest) {
        return resolveResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception, webRequest);
    }

    private ResponseEntity<Object> resolveResponseEntity(
            HttpStatusCode httpStatusCode,
            Exception exception,
            WebRequest webRequest) {
        ErrorResponse errorResponse = resolveErrorResponse(httpStatusCode, exception);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(exception, errorResponse, httpHeaders, httpStatusCode, webRequest);
    }

    private static ErrorResponse resolveErrorResponse(HttpStatusCode httpStatusCode, Exception exception) {
        UUID uuid = UUID.randomUUID();
        log.error(String.format("Unable to handle the request [ERROR_UUID:%s]", uuid), exception);
        String message = switch ((HttpStatus) httpStatusCode) {
        case BAD_REQUEST -> "Bad Request";
        case INTERNAL_SERVER_ERROR -> "Internal Server Error";
        default -> exception.getMessage();
        };
        return ErrorResponse.builder().uuid(uuid).message(message).build();
    }

}
