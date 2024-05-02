package ee.rik.domain;

public class EntityFieldErrorCodeConstant {

    public static class Event {

        public static final String START_DATE_TIME_INVALID_FORMAT = "Event_StartDateTimeInvalidFormat";
        public static final String START_DATE_TIME_MUST_BE_IN_THE_FUTURE = "Event_StartDateTimeMustBeInTheFuture";
        public static final String NOT_ALLOWED_TO_CHANGE = "Event_NotAllowedToChange";
        public static final String NOT_ALLOWED_TO_DELETE = "Event_NotAllowedToDelete";

    }

    public static class EventParticipant {

        public static final String PERSON_ALREADY_ADDED = "EventParticipant_PersonAlreadyAdded";
        public static final String LEGAL_ENTITY_ALREADY_ADDED = "EventParticipant_LegalEntityAlreadyAdded";

    }

    public static class PersonParticipant {

        public static final String NATIONAL_IDENTIFICATION_CODE_INVALID = "PersonParticipant_NationalIdentificationCodeInvalid";
        public static final String NATIONAL_IDENTIFICATION_CODE_MISMATCH = "PersonParticipant_NationalIdentificationCodeMismatch";

    }

    public static class LegalEntityParticipant {

        public static final String REGISTRATION_CODE_MISMATCH = "LegalEntityParticipant_RegistrationCodeMismatch";

    }

}
