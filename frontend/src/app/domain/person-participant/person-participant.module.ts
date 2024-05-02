import { NgModule } from '@angular/core';
import { PersonParticipantFormComponent } from './component/person-participant-form.component';
import { PersonParticipantComponent } from './component/person-participant.component';
import { PersonParticipantService } from './service/person-participant.service';
import { AppSharedModule } from '../../application/shared/app-shared.module';

@NgModule({
  imports: [AppSharedModule],
  declarations: [PersonParticipantComponent, PersonParticipantFormComponent],
  providers: [PersonParticipantService],
  exports: [PersonParticipantFormComponent],
})
export class PersonParticipantModule {}
