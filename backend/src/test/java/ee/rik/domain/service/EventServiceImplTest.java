package ee.rik.domain.service;

import ee.rik.domain.EntityFieldNotValidException;
import ee.rik.domain.Event;
import ee.rik.domain.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testThatCreateEventIsNotSuccessfulWhenStartDateTimeIsInThePast() {
        // given
        Event event = Event.builder().startDateTime(LocalDateTime.now().minusSeconds(1)).build();

        // when
        EntityFieldNotValidException entityFieldNotValidException = assertThrows(EntityFieldNotValidException.class, () -> eventService.createEvent(event));

        // then
        assertThat(entityFieldNotValidException.getFieldName(), is("event.startDateTime"));
        assertThat(entityFieldNotValidException.getErrorCode(), is("Event_StartDateTimeMustBeInTheFuture"));
    }

    @Test
    void testThatCreateEventIsSuccessful() {
        // given
        Event event = Event.builder().startDateTime(LocalDateTime.now().plusSeconds(1)).build();

        given(eventRepository.create(event)).willReturn(1L);

        // when
        Long id = eventService.createEvent(event);

        // then
        assertThat(id, is(1L));
    }

    @Test
    void testThatDeleteEventIsNotSuccessfulWhenStartDateTimeIsInThePast() {
        // given
        Event event = Event.builder().startDateTime(LocalDateTime.now().minusSeconds(1)).build();
        given(eventRepository.get(1L)).willReturn(event);

        // when
        EntityFieldNotValidException entityFieldNotValidException = assertThrows(EntityFieldNotValidException.class, () -> eventService.deleteEvent(1L));

        // then
        assertThat(entityFieldNotValidException.getFieldName(), is("event.general"));
        assertThat(entityFieldNotValidException.getErrorCode(), is("Event_NotAllowedToDelete"));
    }

}