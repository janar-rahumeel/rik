import { NgModule } from '@angular/core';
import { BackButtonDirective } from './directive/back-button.directive';
import { ViewComponent } from './component/view.component';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, RouterOutlet],
  declarations: [ViewComponent, BackButtonDirective],
  exports: [CommonModule, BackButtonDirective, ReactiveFormsModule],
})
export class AppSharedModule {}
