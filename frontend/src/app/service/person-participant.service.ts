import {Injectable} from "@angular/core";
import {PersonParticipantRepository} from "../repository/person-participant.repository";
import {PersonParticipant} from "../generated/rik-backend";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class PersonParticipantService {

  public constructor(private readonly repository: PersonParticipantRepository) {
  }

  public getPersonParticipant(hybridId: string): Observable<PersonParticipant> {
    let id: number = Number(hybridId.replace('PP-', ''));
    return this.repository.get(id);
  }

}
