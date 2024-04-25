INSERT INTO EVENT (ID, NAME, START_DATETIME, LOCATION, DESCRIPTION)
VALUES (2, 'Event Name 2', CURRENT_TIMESTAMP, 'Tallinn', 'Description 2');

INSERT INTO PAYMENT_TYPE (ID, TYPE_VALUE)
VALUES (1, 'Pangaülekanne');

INSERT INTO PAYMENT_TYPE (ID, TYPE_VALUE)
VALUES (2, 'Sularaha');

INSERT INTO PERSON_PARTICIPANT (ID, FIRST_NAME, LAST_NAME, NATIONAL_IDENTIFICATION_CODE, PAYMENT_TYPE_ID)
VALUES (1, 'Janar', 'Rahumeel', '38008180024', 1);

INSERT INTO PERSON_PARTICIPANT (ID, FIRST_NAME, LAST_NAME, NATIONAL_IDENTIFICATION_CODE, PAYMENT_TYPE_ID)
VALUES (2, 'Janar', 'Tasane', '38008180026', 1);

INSERT INTO LEGAL_ENTITY_PARTICIPANT (ID, NAME, REGISTRATION_CODE, PARTICIPANT_COUNT, PAYMENT_TYPE_ID)
VALUES (1, 'Janar Solutions OÜ', '88888888', 1, 2);

INSERT INTO EVENT_PERSON_PARTICIPANT_X (EVENT_ID, PERSON_PARTICIPANT_ID)
VALUES (2, 1);

INSERT INTO EVENT_PERSON_PARTICIPANT_X (EVENT_ID, PERSON_PARTICIPANT_ID)
VALUES (2, 2);

INSERT INTO EVENT_LEGAL_ENTITY_PARTICIPANT_X (EVENT_ID, LEGAL_ENTITY_PARTICIPANT_ID)
VALUES (2, 1);