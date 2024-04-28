package ee.rik.infrastructure.repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import ee.rik.domain.PaymentTypeListItem;
import ee.rik.domain.repository.PaymentTypeRepository;
import ee.rik.infrastructure.entity.PaymentTypeEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentTypeRepositoryImpl implements PaymentTypeRepository {

    private final PaymentTypeEntityRepository paymentTypeEntityRepository;

    @Override
    public Set<PaymentTypeListItem> getAll() {
        return StreamSupport.stream(paymentTypeEntityRepository.findAll().spliterator(), false)
                .map(PaymentTypeRepositoryImpl::toListItem)
                .collect(Collectors.toSet());
    }

    private static PaymentTypeListItem toListItem(PaymentTypeEntity paymentTypeEntity) {
        return PaymentTypeListItem.builder().id(paymentTypeEntity.getId()).name(paymentTypeEntity.getValue()).build();
    }

}
