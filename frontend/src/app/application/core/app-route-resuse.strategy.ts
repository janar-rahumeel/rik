import { ActivatedRouteSnapshot, BaseRouteReuseStrategy } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AppRouteReuseStrategy extends BaseRouteReuseStrategy {
  /* eslint-disable @typescript-eslint/no-unused-vars */
  public override shouldReuseRoute(future: ActivatedRouteSnapshot, current: ActivatedRouteSnapshot): boolean {
    return false;
  }
}
