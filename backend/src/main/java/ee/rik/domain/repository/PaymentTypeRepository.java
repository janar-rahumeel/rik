package ee.rik.domain.repository;

import java.util.Set;

import ee.rik.domain.PaymentTypeListItem;

public interface PaymentTypeRepository {

    Set<PaymentTypeListItem> getAll();

}
