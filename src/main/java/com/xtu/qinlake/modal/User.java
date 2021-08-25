package com.xtu.qinlake.modal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * 用户信息表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 级别-权限
     */
    private String level;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 主键
     */
    private String token;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Map<String, Object> toMap() {
        Map<String, Object> user = new HashMap<>();
        if(this.id != null) {
            user.put("id", this.id);
        }
        if(this.userName != null) {
            user.put("userName", this.userName);
        }
        if(this.password != null) {
            user.put("password", this.password);
        }
        if(this.level != null) {
            user.put("level", this.level);
        }
        if(this.telephone != null) {
            user.put("telephone", this.telephone);
        }
        if(this.token != null) {
            user.put("token", this.token);
        }
        return user;
    }

    public void updateUser(Map<String, Object> param) {
        if(param.get("id") != null) {
            this.id = Long.parseLong(param.get("id").toString());
        }
        if(param.get("userName") != null) {
            this.userName = param.get("userName").toString();
        }
        if(param.get("password") != null) {
            this.password = param.get("password").toString();
        }
        if(param.get("level") != null) {
            this.level = param.get("level").toString();
        }
        if(param.get("telephone") != null) {
            this.telephone = param.get("telephone").toString();
        }
        if(param.get("token") != null) {
            this.token = param.get("token").toString();
        }
    }

    public static User genNewUser() {
        User user = new User();
        user.setToken(UUID.randomUUID().toString());
        return user;
    }
}