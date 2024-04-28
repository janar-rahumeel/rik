package ee.rik.domain.service;

import java.util.Set;

import ee.rik.domain.PaymentTypeListItem;
import ee.rik.domain.repository.PaymentTypeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public Set<PaymentTypeListItem> getAllPaymentTypes() {
        return paymentTypeRepository.getAll();
    }

}
