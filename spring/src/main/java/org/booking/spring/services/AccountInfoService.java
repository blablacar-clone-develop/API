package org.booking.spring.services;

import org.booking.spring.models.user.AccountInfo;
import org.booking.spring.repositories.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountInfoService {
    @Autowired
    private AccountInfoRepository accountInfoRepository;
    public Optional<AccountInfo> getByUserId(Long userId) {
        return accountInfoRepository.findById(userId);
    }
    public void updateAccountInfo(AccountInfo accountInfo) {
        accountInfoRepository.save(accountInfo);
    }
    public void saveAccountInfo(AccountInfo newAccountInfo) {
        accountInfoRepository.save(newAccountInfo);
    }
}
