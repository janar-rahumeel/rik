import { Directive, HostListener } from '@angular/core';
import { Location } from '@angular/common';

@Directive({
  selector: '[rikBackButton]',
})
export class BackButtonDirective {
  public constructor(private readonly location: Location) {}

  @HostListener('click')
  public onClick(): void {
    this.location.back();
  }
}
