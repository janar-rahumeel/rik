import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ErrorService } from '../../application/core/error.service';

@Injectable({ providedIn: 'root' })
export class ErrorHandlerHttpInterceptor implements HttpInterceptor {
  constructor(private readonly errorService: ErrorService) {}

  public intercept(httpRequest: HttpRequest<unknown>, nextHttpHandler: HttpHandler): Observable<HttpEvent<unknown>> {
    return nextHttpHandler.handle(httpRequest).pipe(
      tap({
        error: (httpErrorResponse: HttpErrorResponse): void => {
          this.errorService.handleHttpErrorResponse(httpErrorResponse);
        },
      }),
    );
  }
}
