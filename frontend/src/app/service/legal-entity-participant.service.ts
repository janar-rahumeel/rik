import {Injectable} from "@angular/core";
import {LegalEntityParticipant, ModifyLegalEntityParticipantRequest} from "../generated/rik-backend";
import {Observable} from "rxjs";
import {LegalEntityParticipantRepository} from "../repository/legal-entity-participant.repository";

@Injectable({providedIn: "root"})
export class LegalEntityParticipantService {

  public constructor(private readonly repository: LegalEntityParticipantRepository) {
  }

  public getLegalEntityParticipant(id: number): Observable<LegalEntityParticipant> {
    return this.repository.get(id);
  }

  public modifyLegalEntityParticipant(id: number, legalEntityParticipant: LegalEntityParticipant): Observable<LegalEntityParticipant> {
    let request: ModifyLegalEntityParticipantRequest = {legalEntityParticipant};
    return this.repository.modify(id, request);
  }

  public removeLegalEntityParticipant(id: number): Observable<void> {
    return this.repository.remove(id);
  }

}
