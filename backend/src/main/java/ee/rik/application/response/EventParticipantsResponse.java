package ee.rik.application.response;

import java.util.HashSet;
import java.util.Set;

import ee.rik.application.GenerateTypescript;
import ee.rik.domain.EventParticipant;

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
public class EventParticipantsResponse {

    @Builder.Default
    private final Set<EventParticipant> participants = new HashSet<>();

}
