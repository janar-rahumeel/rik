import {DestroyRef, Directive, inject} from "@angular/core";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {Observable, take} from "rxjs";

@Directive()
export abstract class AbstractComponent {

  private readonly destroyReference: DestroyRef = inject(DestroyRef);

  protected subscribeOnce<T>(observable: Observable<T>, callback: (context: T) => void): void {
    observable.pipe(take(1)).subscribe(callback);
  }

  protected subscribe<T>(observable: Observable<T>, callback: (context: T) => void): void {
    observable.pipe(takeUntilDestroyed(this.destroyReference)).subscribe(callback);
  }

}
