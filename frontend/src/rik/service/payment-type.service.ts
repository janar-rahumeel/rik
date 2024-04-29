import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import {
  ListPaymentTypesResponse,
  PaymentTypeListItem,
} from '../generated/rik-backend';
import { PaymentTypeRepository } from '../repository/payment-type.repository';

@Injectable({ providedIn: 'root' })
export class PaymentTypeService {
  public constructor(private readonly repository: PaymentTypeRepository) {}

  public listPaymentTypes(): Observable<PaymentTypeListItem[]> {
    return this.repository
      .list()
      .pipe(map((response: ListPaymentTypesResponse) => response.paymentTypes));
  }
}
