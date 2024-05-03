import { NgModule } from '@angular/core';
import { BackButtonDirective } from './directive/back-button.directive';
import { ViewComponent } from './component/view.component';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ViewService } from './service/view.service';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, RouterOutlet],
  declarations: [BackButtonDirective, ViewComponent],
  exports: [CommonModule, BackButtonDirective, ReactiveFormsModule, ViewComponent],
  providers: [ViewService],
})
export class AppSharedModule {}
