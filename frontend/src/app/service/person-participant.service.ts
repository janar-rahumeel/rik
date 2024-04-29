import {Injectable} from "@angular/core";
import {PersonParticipantRepository} from "../repository/person-participant.repository";
import {ModifyPersonParticipantRequest, PersonParticipant} from "../generated/rik-backend";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class PersonParticipantService {

  public constructor(private readonly repository: PersonParticipantRepository) {
  }

  public getPersonParticipant(id: number): Observable<PersonParticipant> {
    return this.repository.get(id);
  }

  public modifyPersonParticipant(id: number, personParticipant: PersonParticipant): Observable<PersonParticipant> {
    let request: ModifyPersonParticipantRequest = {personParticipant};
    return this.repository.modify(id, request);
  }

  public removePersonParticipant(id: number): Observable<void> {
    return this.repository.remove(id);
  }

}
