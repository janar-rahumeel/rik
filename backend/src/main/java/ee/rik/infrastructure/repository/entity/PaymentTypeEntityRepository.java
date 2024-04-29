package ee.rik.infrastructure.repository.entity;

import ee.rik.infrastructure.entity.PaymentTypeEntity;

import org.springframework.data.repository.CrudRepository;

public interface PaymentTypeEntityRepository extends CrudRepository<PaymentTypeEntity, Integer> {
}
