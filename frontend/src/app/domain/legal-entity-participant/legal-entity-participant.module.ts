import { NgModule } from '@angular/core';
import { LegalEntityParticipantComponent } from './component/legal-entity-participant.component';
import { LegalEntityParticipantFormComponent } from './component/legal-entity-participant-form.component';
import { LegalEntityParticipantService } from './service/legal-entity-participant.service';
import { AppSharedModule } from '../../application/shared/app-shared.module';

@NgModule({
  imports: [AppSharedModule],
  declarations: [LegalEntityParticipantComponent, LegalEntityParticipantFormComponent],
  providers: [LegalEntityParticipantService],
  exports: [LegalEntityParticipantFormComponent],
})
export class LegalEntityParticipantModule {}
