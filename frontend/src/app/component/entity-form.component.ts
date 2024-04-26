import {ErrorService} from "../service/error.service";
import {FormGroup, ValidationErrors} from "@angular/forms";
import {Observable, ReplaySubject, Subject, take} from "rxjs";
import {DestroyRef, Directive, inject, OnInit} from "@angular/core";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {ErrorResponse} from "../generated/rik-backend";
import {Router} from "@angular/router";

@Directive()
export abstract class AbstractEntityFormComponent<E extends {}> implements OnInit {

  protected entityForm: FormGroup;
  protected entity$: Observable<E>;
  protected generalError: string;
  protected readonly entitySubject: Subject<E>;
  private readonly destroyReference: DestroyRef = inject(DestroyRef);

  protected constructor(protected readonly router: Router,
                        protected readonly errorService: ErrorService) {
    this.entitySubject = new ReplaySubject<E>();
  }

  protected abstract onInit(): void;

  protected abstract getForm(): FormGroup;

  protected abstract onSubmit(entity: E): void;

  public ngOnInit(): void {
    this.entityForm = this.getForm();
    this.errorService.resetFieldValidationErrors();
    this.entity$ = this.entitySubject.asObservable();
    this.subscribe(this.errorService.globalErrors$, (errorResponse: ErrorResponse): void => {
      if (errorResponse.message) {
        this.generalError = errorResponse.message;
      }
    });
    this.subscribe(this.errorService.entityFieldValidationErrors$, (validationErrors: ValidationErrors): void => {
      Object.entries(validationErrors)
        .forEach(([fieldName, errorMessage]): void => {
          let transformedFieldName: string = fieldName.split(".")[1];
          if (this.entityForm.contains(transformedFieldName)) {
            let formFieldControl = this.entityForm.get(transformedFieldName);
            if (formFieldControl) {
              formFieldControl.markAsDirty({onlySelf: true});
              formFieldControl.setErrors({server: errorMessage});
            }
          } else {
            this.generalError = errorMessage;
          }
        })
    });
    this.subscribe(this.entity$, (entity: E): void => {
      this.entityForm.patchValue(entity);
    });
    this.onInit();
  }

  protected onSubmitButtonClicked(): void {
    this.errorService.resetFieldValidationErrors();
    let entity: E = this.entityForm.getRawValue() as E;
    this.onSubmit(entity);
  }

  protected hasGeneralError(): boolean {
    return !!this.generalError;
  }

  protected subscribe<T>(observable: Observable<T>, callback: (entity: T) => void): void {
    observable.pipe(takeUntilDestroyed(this.destroyReference)).subscribe(callback);
  }

  protected subscribeOnce<T>(observable: Observable<T>, callback: (entity: T) => void): void {
    observable.pipe(take(1)).subscribe(callback);
  }

  protected reloadPage() {
    let currentUrl: string = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then((): void => {
      this.router.navigate([currentUrl]);
    });
  }

}
