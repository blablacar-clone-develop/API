package org.booking.spring.services;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class VerificationCodeService {

    private static final String NUMERIC = "0123456789"; // Тільки цифри
    private static final int CODE_LENGTH = 6; // Довжина коду
    private final SecureRandom random = new SecureRandom();

    /**
     * Генерує випадковий 6-символьний код, що складається тільки з цифр.
     * @return 6-символьний код
     */
    public String generateVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(NUMERIC.length());
            code.append(NUMERIC.charAt(index));
        }
        return code.toString();
    }
}
