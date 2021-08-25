package com.xtu.qinlake.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xtu.qinlake.modal.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User>, UserDetailsService {

    List<User> findByParam(Map<String, Object> param) throws UsernameNotFoundException;

    int addUser(User user);
}
