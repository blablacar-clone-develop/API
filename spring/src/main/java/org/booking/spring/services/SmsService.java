package org.booking.spring.services;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final VonageClient client;

    public SmsService(
            @Value("${vonage.api.key}") String apiKey,
            @Value("${vonage.api.secret}") String apiSecret
    ) {
        this.client = VonageClient.builder().apiKey(apiKey).apiSecret(apiSecret).build();
    }


    public boolean sendVerificationCode(String phoneNumber, String code) {
        TextMessage message = new TextMessage("GroundGlide",
                phoneNumber,
                "Your verification code is: " + code);

        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
        return response.getMessages().get(0).getStatus() == MessageStatus.OK;
    }
}
