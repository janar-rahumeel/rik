/* tslint:disable */
/* eslint-disable */

export interface AddLegalEntityParticipantRequest {
  eventId: number;
  legalEntityParticipant: LegalEntityParticipant;
}

export interface AddPersonParticipantRequest {
  eventId: number;
  personParticipant: PersonParticipant;
}

export interface CreateEventRequest {
  event: Event;
}

export interface ListEventsRequest {
  newEvents: boolean;
}

export interface ModifyEventRequest {
  event: Event;
}

export interface ModifyLegalEntityParticipantRequest {
  legalEntityParticipant: LegalEntityParticipant;
}

export interface ModifyPersonParticipantRequest {
  personParticipant: PersonParticipant;
}

export interface EntityFieldValidationError {
  fieldName: string;
  validationErrorMessage: string;
}

export interface ErrorResponse {
  uuid?: string;
  timestamp: Date;
  message?: string;
  entityFieldValidationErrors: EntityFieldValidationError[];
}

export interface EventParticipantsResponse {
  participants: EventParticipant[];
}

export interface LegalEntityParticipantResponse {
  legalEntityParticipant: LegalEntityParticipant;
}

export interface ListEventsResponse {
  events: EventListItem[];
}

export interface ListPaymentTypesResponse {
  paymentTypes: PaymentTypeListItem[];
}

export interface PersonParticipantResponse {
  personParticipant: PersonParticipant;
}

export interface LegalEntityParticipant {
  name: string;
  registrationCode: string;
  participantCount: number;
  paymentTypeId: number;
  additionalInformation?: string;
}

export interface PersonParticipant {
  firstName: string;
  lastName: string;
  nationalIdentificationCode: string;
  paymentTypeId: number;
  additionalInformation?: string;
}

export interface Event {
  name: string;
  startDateTime: string;
  location: string;
  description: string;
}

export interface EventParticipant {
  id: string;
  name: string;
  identityCode: string;
}

export interface EventListItem {
  id: number;
  name: string;
  startDate: string;
  location: string;
  totalParticipantCount: number;
}

export interface PaymentTypeListItem {
  id: number;
  name: string;
}
