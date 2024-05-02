package ee.rik.application.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ee.rik.application.model.EventData;
import ee.rik.domain.Event;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class EventMapper {

    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Mapping(target = "startDateTime", ignore = true)
    public abstract EventData map(Event event);

    @AfterMapping
    protected void afterMapping(Event event, @MappingTarget EventData.EventDataBuilder eventDataBuilder) {
        String startDateTime = DATE_TIME_FORMATTER.format(event.getStartDateTime());
        eventDataBuilder.startDateTime(startDateTime);
    }

    @Mapping(target = "startDateTime", ignore = true)
    public abstract Event map(EventData eventData);

    @AfterMapping
    protected void afterMapping(EventData eventData, @MappingTarget Event.EventBuilder eventBuilder) {
        LocalDateTime startDateTime = LocalDateTime.parse(eventData.getStartDateTime(), DATE_TIME_FORMATTER);
        eventBuilder.startDateTime(startDateTime);
    }

}
