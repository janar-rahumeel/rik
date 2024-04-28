package ee.rik.application.response;

import java.util.Set;

import ee.rik.application.GenerateTypescript;
import ee.rik.domain.EventListItem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@GenerateTypescript
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ListEventsResponse {

    private Set<EventListItem> events;

}
