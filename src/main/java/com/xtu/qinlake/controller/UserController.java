package com.xtu.qinlake.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xtu.qinlake.modal.User;
import com.xtu.qinlake.service.UserService;

/**
 * 用户信息表(User)表控制层
 *
 * @author makejava
 * @since 2021-08-18 16:40:20
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/findOne")
    public User findOne(long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        List<User> users = userService.findByParam(param);
        if (users == null || users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    @GetMapping("/findByParam")
    public List<User> findByParam(Map<String, Object> param) {
        return userService.findByParam(param);
    }

    @GetMapping("/findCurrentUser")
    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Map<String, Object> param = new HashMap<>();
        param.put("userName", userDetails.getUsername());
        return userService.findByParam(param).get(0);
    }

    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody Map<String, Object> param) {
        if (param == null || param.isEmpty()) {
            return false;
        }

        User user;
        List<User> users = findByParam(param);
        if (users != null && users.size() > 0) {
            user = users.get(0);
        } else {
            user = User.genNewUser();
        }
        user.updateUser(param);
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestBody Map<String, Object> param) {
        List<User> users = userService.findByParam(param);
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        return userService.removeByIds(userIds);
    }
}