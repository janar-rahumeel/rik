package ee.rik.application.rest;

import java.util.Set;

import ee.rik.application.response.ListPaymentTypesResponse;
import ee.rik.domain.PaymentTypeListItem;
import ee.rik.domain.service.PaymentTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-types")
@RequiredArgsConstructor
public class PaymentTypeRestController {

    private final PaymentTypeService paymentTypeService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListPaymentTypesResponse> listAll() {
        Set<PaymentTypeListItem> paymentTypes = paymentTypeService.getAllPaymentTypes();
        return ResponseEntity.ok(ListPaymentTypesResponse.builder().paymentTypes(paymentTypes).build());
    }

}
