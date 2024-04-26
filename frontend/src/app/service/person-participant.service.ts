import {Injectable} from "@angular/core";
import {PersonParticipantRepository} from "../repository/person-participant.repository";
import {PersonParticipant} from "../generated/rik-backend";
import {Observable} from "rxjs";

@Injectable({providedIn: "root"})
export class PersonParticipantService {

  public constructor(private readonly repository: PersonParticipantRepository) {
  }

  public get(hybridId: string): Observable<PersonParticipant> {
    let id: number = Number(hybridId.split("-")[1]);
    return this.repository.get(id);
  }


}
