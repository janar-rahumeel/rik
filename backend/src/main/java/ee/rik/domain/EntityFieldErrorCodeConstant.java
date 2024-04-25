package ee.rik.domain;

public class EntityFieldErrorCodeConstant {

    public static class Event {

        public static final String START_DATE_TIME_MUST_BE_IN_THE_FUTURE = "Event_StartDateTimeMustBeInTheFuture";
        public static final String NOT_ALLOWED_TO_CHANGE = "Event_NotAllowedToChange";
        public static final String NOT_ALLOWED_TO_DELETE = "Event_NotAllowedToDelete";

    }

    public static class EventParticipant {

        public static final String PERSON_ALREADY_ADDED = "EventParticipant_PersonAlreadyAdded";
        public static final String LEGAL_ENTITY_ALREADY_ADDED = "EventParticipant_LegalEntityAlreadyAdded";

    }

}
