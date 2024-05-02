package ee.rik.domain.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
class NationalIdentificationCodeValidationService {

    boolean isValid(String nationalIdentificationCode) {
        if (nationalIdentificationCode != null && nationalIdentificationCode.length() == 11) {
            try {
                Long.parseLong(nationalIdentificationCode);
            } catch (NumberFormatException e) {
                return false;
            }
            Integer[] codes = Arrays.stream(nationalIdentificationCode.split(""))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
            int checksum = calculateFirstChecksum(codes);
            if (checksum == 10) {
                checksum = calculateSecondChecksum(codes);
            }
            return codes[10] == checksum;
        }
        return false;
    }

    private static int calculateFirstChecksum(Integer[] codes) {
        Integer[] weights = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1 };
        int sum = sumOf(codes, weights);
        return sum % 11;
    }

    private static int calculateSecondChecksum(Integer[] codes) {
        Integer[] weights = new Integer[] { 3, 4, 5, 6, 7, 8, 9, 1, 2, 3 };
        int sum = sumOf(codes, weights);
        int remainder = sum % 11;
        return (remainder == 10) ? 0 : remainder;
    }

    private static int sumOf(Integer[] codes, Integer[] weights) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += codes[i] * weights[i];
        }
        return sum;
    }

}
