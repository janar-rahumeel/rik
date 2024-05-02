import { DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Observable, take } from 'rxjs';
import { Router } from '@angular/router';

export abstract class AbstractComponent {
  private readonly destroyReference: DestroyRef = inject(DestroyRef);

  protected constructor(protected readonly router: Router) {}

  protected subscribeOnce<T>(observable: Observable<T>, callback: (context: T) => void): void {
    observable.pipe(take(1)).subscribe(callback);
  }

  protected subscribe<T>(observable: Observable<T>, callback: (context: T) => void): void {
    observable.pipe(takeUntilDestroyed(this.destroyReference)).subscribe(callback);
  }

  protected reloadPage(): void {
    const currentUrl: string = this.router.url;
    this.router.navigate([currentUrl]);
  }
}
