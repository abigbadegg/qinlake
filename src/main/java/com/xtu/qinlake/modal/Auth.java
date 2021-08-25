package com.xtu.qinlake.modal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单-权限表
 * @TableName auth
 */
@TableName(value ="auth")
@Data
public class Auth implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单
     */
    @TableField(value = "menu")
    private String menu;

    /**
     * 级别-权限
     */
    @TableField(value = "level")
    private String level;

    /**
     * 备注
     */
    @TableField(value = "note")
    private String note;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}