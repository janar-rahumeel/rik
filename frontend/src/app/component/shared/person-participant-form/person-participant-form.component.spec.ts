import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PersonParticipantEntity, PersonParticipantFormComponent } from './person-participant-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { PaymentTypeService } from '../../../service/payment-type.service';
import { of } from 'rxjs';
import { PaymentTypeListItem, PersonParticipant } from '../../../generated/rik-backend';
import { PersonParticipantService } from '../../../service/person-participant.service';

describe('PersonParticipantFormComponent', (): void => {
  let fixture: ComponentFixture<PersonParticipantFormComponent>;
  let component: PersonParticipantFormComponent;
  let rootElement: HTMLElement;
  const paymentTypeServiceStub: Partial<PaymentTypeService> = {
    listPaymentTypes: () => of(),
  };
  const personParticipantServiceStub: Partial<PersonParticipantService> = {
    modifyPersonParticipant: () => of(),
  };

  const personParticipant: PersonParticipant = {
    firstName: 'Janar',
    lastName: 'Rahumeel',
    nationalIdentificationCode: '39485867737',
    paymentTypeId: 1,
    additionalInformation: 'Lisainfo',
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, ReactiveFormsModule],
      declarations: [PersonParticipantFormComponent],
      providers: [
        { provide: PaymentTypeService, useValue: paymentTypeServiceStub },
        {
          provide: PersonParticipantService,
          useValue: personParticipantServiceStub,
        },
      ],
    }).compileComponents();
  });

  beforeEach((): void => {
    fixture = TestBed.createComponent(PersonParticipantFormComponent);
    component = fixture.componentInstance;
    rootElement = fixture.nativeElement as HTMLElement;

    const paymentTypes: PaymentTypeListItem[] = [{ id: 1, name: 'Sularaha' }];
    jest.spyOn(paymentTypeServiceStub, 'listPaymentTypes').mockReturnValueOnce(of(paymentTypes));
  });

  it('should create', (): void => {
    expect(component).toBeTruthy();
  });

  test('should fill the form', (): void => {
    // given
    component.getEntitySubject().next(personParticipant);

    // when
    fixture.detectChanges();

    // then
    expect(rootElement.querySelector<HTMLInputElement>('#firstName')!.value).toContain('Janar');
    expect(rootElement.querySelector<HTMLInputElement>('#lastName')!.value).toContain('Rahumeel');
    expect(rootElement.querySelector<HTMLInputElement>('#nationalIdentificationCode')!.value).toContain('39485867737');
    expect(rootElement.querySelector<HTMLSelectElement>('#paymentTypeId')!.options[0].text).toContain('Sularaha');
    expect(rootElement.querySelector<HTMLTextAreaElement>('#additionalInformation')!.value).toContain('Lisainfo');
  });

  test('should submit the form with changed additional data', (): void => {
    // given
    const personParticipantEntity: PersonParticipantEntity = {
      id: 1,
      ...personParticipant,
    };
    component.getEntitySubject().next(personParticipantEntity);
    fixture.detectChanges();

    const modifiedPersonParticipantEntity: PersonParticipantEntity = {
      ...personParticipantEntity,
      additionalInformation: 'Lisainfo 2',
    };
    jest.spyOn(personParticipantServiceStub, 'modifyPersonParticipant').mockReturnValueOnce(of(modifiedPersonParticipantEntity));

    const additionalInformationElement: HTMLTextAreaElement = rootElement.querySelector<HTMLTextAreaElement>('#additionalInformation')!;
    additionalInformationElement.value = 'Lisainfo 2';
    additionalInformationElement.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    // when
    rootElement.querySelector<HTMLButtonElement>('button[type=submit]')!.click();

    // then
    fixture.detectChanges();

    expect(personParticipantServiceStub.modifyPersonParticipant).toHaveBeenCalledTimes(1);
    expect(personParticipantServiceStub.modifyPersonParticipant).toHaveBeenCalledWith(1, modifiedPersonParticipantEntity);
    expect(rootElement.querySelector<HTMLSpanElement>('span.success')!.textContent).toContain('Muudetud');
  });
});
