package com.xtu.qinlake.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xtu.qinlake.mapper.UserMapper;
import com.xtu.qinlake.modal.User;
import com.xtu.qinlake.service.UserService;

import static com.xtu.qinlake.dto.UserAndAuthParam.preRole;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", s);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("该用户名未找到对应用户，请先注册");

        }
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getLevel());
        //String roles = user.getLevel().split(",");

        authorities.add(new SimpleGrantedAuthority(user.getLevel()));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getUserName(), passwordEncoder.encode(user.getPassword()), authorities);
        return userDetails;

    }

    @Override
    public List<User> findByParam(Map<String, Object> param) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "user_name", "level", "telephone", "token");
        if(param.get("id") != null) {
            queryWrapper.eq("id", param.get("id"));
        }
        if(param.get("userName") != null) {
            queryWrapper.eq("user_name", param.get("userName"));
        }
        if(param.get("level") != null) {
            queryWrapper.eq("level", param.get("level"));
        }
        if(param.get("telephone") != null) {
            queryWrapper.eq("telephone", param.get("telephone"));
        }
        if(param.get("token") != null) {
            queryWrapper.eq("token", param.get("token"));
        }
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public int addUser(User user){
        List<User> users = findByParam(user.toMap());
        if(users != null) {
            throw new RuntimeException("该用户已存在，请检查待新增的用户信息");
        }
        user.setToken(UUID.randomUUID().toString());
        return 0;

    }
}




