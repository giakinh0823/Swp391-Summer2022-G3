/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.model.blog;

import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.user.User;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giaki
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private String title;
    private String thumbnail;
    private String content;
    private String description;
    private int categoryId;
    private Timestamp updated_at;
    private Timestamp created_at;
    private boolean flag;
    private boolean status;
    private boolean featured;
    private int userId;
    private User user;
    private Setting category;
}
