import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from '../generated/rik-backend';
import { Observable, Subject } from 'rxjs';
import { ValidationErrors } from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class ErrorService {
  public globalErrors$: Observable<ErrorResponse>;
  public entityFieldValidationErrors$: Observable<ValidationErrors>;

  private globalErrorsSubject: Subject<ErrorResponse> = new Subject<ErrorResponse>();
  private entityFieldValidationErrorsSubject: Subject<ValidationErrors> = new Subject();

  public constructor() {
    this.globalErrors$ = this.globalErrorsSubject.asObservable();
    this.entityFieldValidationErrors$ = this.entityFieldValidationErrorsSubject.asObservable();
  }

  public handleHttpErrorResponse(httpErrorResponse: HttpErrorResponse): void {
    if (httpErrorResponse.error instanceof ErrorEvent) {
      console.log(httpErrorResponse.error.message);
      const error: ErrorResponse = this.buildError('CLIENT', 'Unknown error');
      this.push(error);
    } else {
      switch (httpErrorResponse.status) {
        case 0: {
          const error: ErrorResponse = this.buildError('NETWORK', 'Connection error');
          this.push(error);
          break;
        }
        case 422: {
          this.handleFieldErrors(httpErrorResponse);
          break;
        }
        default: {
          try {
            const error: ErrorResponse = httpErrorResponse.error as ErrorResponse;
            this.push(error);
          } catch (e) {
            console.log(e);
            const error: ErrorResponse = this.buildError('EXCEPTION', 'Unknown error');
            this.push(error);
          }
        }
      }
    }
  }

  private buildError(uuid: string, message: string): ErrorResponse {
    return {
      uuid: uuid,
      timestamp: new Date(),
      message: message,
      entityFieldValidationErrors: [],
    };
  }

  private push(error: ErrorResponse): void {
    console.log(error.timestamp + ' [ERROR_UUID:' + error.uuid! + '] ' + error.message ?? 'Unknown error');
    this.globalErrorsSubject.next(error);
  }

  private handleFieldErrors(httpErrorResponse: HttpErrorResponse): void {
    const error: ErrorResponse = httpErrorResponse.error as ErrorResponse;
    const validationErrors: ValidationErrors = {};
    for (const fieldError of error.entityFieldValidationErrors) {
      validationErrors[fieldError.fieldName] = fieldError.validationErrorMessage;
    }
    this.entityFieldValidationErrorsSubject.next(validationErrors);
  }

  public resetFieldValidationErrors(): void {
    this.entityFieldValidationErrorsSubject.next({});
  }
}
