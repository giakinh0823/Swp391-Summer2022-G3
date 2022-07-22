/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.security;

import com.se1617.learning.model.user.Feature;
import com.se1617.learning.model.user.Group;
import com.se1617.learning.model.user.User;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author giaki
 */
public class SecurityConfig {

    private static SecurityConfig instance = new SecurityConfig();

    private SecurityConfig() {
    }

    public static SecurityConfig getInstance() {
        return instance;
    }

    public static boolean isAccess(HttpServletRequest request, String... featureds) {
        User user = (User) request.getSession().getAttribute("user");
        if (user.is_super()) {
            return true;
        }
        int count = 0;
        for (Group group : user.getGroups()) {
            if (group.isStatus()) {
                for (Feature feature : group.getFeatures()) {
                    for (String featured : featureds) {
                        if (feature.getFeature().equalsIgnoreCase(featured)) {
                            count++;
                            break;
                        }
                    }
                }
            }
        }
        return count == featureds.length;
    }
}
