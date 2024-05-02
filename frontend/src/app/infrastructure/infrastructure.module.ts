import { NgModule } from '@angular/core';
import { EventRepository } from './repository/event.repository';
import { LegalEntityParticipantRepository } from './repository/legal-entity-participant.repository';
import { PaymentTypeRepository } from './repository/payment-type.repository';
import { PersonParticipantRepository } from './repository/person-participant.repository';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ErrorHandlerHttpInterceptor } from './interceptor/error-handler.interceptor';

@NgModule({
  imports: [HttpClientModule],
  providers: [
    EventRepository,
    LegalEntityParticipantRepository,
    PaymentTypeRepository,
    PersonParticipantRepository,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerHttpInterceptor,
      multi: true,
    },
  ],
})
export class InfrastructureModule {}
