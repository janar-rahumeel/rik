package ee.rik.domain.service;

import java.util.Set;

import ee.rik.domain.PaymentTypeListItem;

public interface PaymentTypeService {

    Set<PaymentTypeListItem> getAllPaymentTypes();

}
