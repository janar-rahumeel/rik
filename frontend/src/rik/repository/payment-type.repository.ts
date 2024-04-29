import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ListPaymentTypesResponse } from '../generated/rik-backend';

@Injectable({ providedIn: 'root' })
export class PaymentTypeRepository {
  public constructor(private readonly httpClient: HttpClient) {}

  public list(): Observable<ListPaymentTypesResponse> {
    return this.httpClient.get<ListPaymentTypesResponse>(
      `${environment.apiUrl}/payment-types/list`,
    );
  }
}
