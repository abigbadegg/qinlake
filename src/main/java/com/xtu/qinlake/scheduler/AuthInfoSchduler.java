package com.xtu.qinlake.scheduler;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xtu.qinlake.modal.Auth;
import com.xtu.qinlake.service.AuthService;

@Component
public class AuthInfoSchduler {
    public static List<Auth> authInfo;

    @Autowired
    AuthService authService;

    @PostConstruct
    public void init() {
        authInfo = authService.list();
    }

    @Scheduled(fixedDelay = 1000 * 30)
    public void authInfoRefresh() {
        authInfo = authService.list();
    }

}
