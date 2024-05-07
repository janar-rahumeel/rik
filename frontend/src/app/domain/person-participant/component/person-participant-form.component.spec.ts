import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { PaymentTypeService } from '../../shared/service/payment-type.service';
import { of } from 'rxjs';
import { PaymentTypeListItem, PersonParticipant } from '../../../generated/rik-backend';
import { PersonParticipantEntity, PersonParticipantService } from '../service/person-participant.service';
import { PersonParticipantFormComponent } from './person-participant-form.component';
import { PersonParticipantRepository } from '../../../infrastructure/repository/person-participant.repository';

describe('PersonParticipantFormComponent', (): void => {
  let fixture: ComponentFixture<PersonParticipantFormComponent>;
  let component: PersonParticipantFormComponent;
  let rootElement: HTMLElement;
  let personParticipantService: PersonParticipantService;
  let paymentTypeService: PaymentTypeService;

  const personParticipant: PersonParticipant = {
    firstName: 'Janar',
    lastName: 'Rahumeel',
    nationalIdentificationCode: '39485867737',
    paymentTypeId: 1,
    additionalInformation: 'Lisainfo',
  };

  beforeEach(async () => {
    const paymentTypeServiceStub: Partial<PaymentTypeService> = { listPaymentTypes: () => of() };
    const personParticipantRepositoryStub: Partial<PersonParticipantRepository> = {};
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, ReactiveFormsModule],
      declarations: [PersonParticipantFormComponent],
      providers: [
        PersonParticipantService,
        { provide: PaymentTypeService, useValue: paymentTypeServiceStub },
        { provide: PersonParticipantRepository, useValue: personParticipantRepositoryStub },
      ],
    }).compileComponents();
  });

  beforeEach((): void => {
    fixture = TestBed.createComponent(PersonParticipantFormComponent);
    component = fixture.componentInstance;
    rootElement = fixture.nativeElement as HTMLElement;
    personParticipantService = TestBed.inject(PersonParticipantService);
    paymentTypeService = TestBed.inject(PaymentTypeService);

    const paymentTypes: PaymentTypeListItem[] = [{ id: 1, name: 'Sularaha' }];
    jest.spyOn(paymentTypeService, 'listPaymentTypes').mockReturnValueOnce(of(paymentTypes));
  });

  test('should create', (): void => {
    expect(component).toBeTruthy();
  });

  test('should fill the form', (): void => {
    // given
    personParticipantService.propagate(personParticipant);

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
    personParticipantService.propagate(personParticipantEntity);
    fixture.detectChanges();

    const modifiedPersonParticipantEntity: PersonParticipantEntity = {
      ...personParticipantEntity,
      additionalInformation: 'Lisainfo 2',
    };
    jest.spyOn(personParticipantService, 'modifyPersonParticipant').mockReturnValueOnce(of(modifiedPersonParticipantEntity));

    const additionalInformationElement: HTMLTextAreaElement = rootElement.querySelector<HTMLTextAreaElement>('#additionalInformation')!;
    additionalInformationElement.value = 'Lisainfo 2';
    additionalInformationElement.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    // when
    rootElement.querySelector<HTMLButtonElement>('button[type=submit]')!.click();

    // then
    fixture.detectChanges();

    expect(personParticipantService.modifyPersonParticipant).toHaveBeenCalledTimes(1);
    expect(personParticipantService.modifyPersonParticipant).toHaveBeenCalledWith(1, modifiedPersonParticipantEntity);
    expect(rootElement.querySelector<HTMLSpanElement>('span.success')!.textContent).toContain('Muudetud');
  });
});
