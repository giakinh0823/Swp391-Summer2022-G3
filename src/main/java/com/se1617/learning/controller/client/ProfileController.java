/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.client;

import com.se1617.learning.config.url.client.UrlProfileClient;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.BcryptUtility;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author congg
 */
@MultipartConfig
public class ProfileController extends BaseAuthController {

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_active();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlProfileClient.CHANGE_PASSWORD)) {
                changePasswordGet(request, response);
            } else if (super.getURI().matches(UrlProfileClient.PROFILE)) {
                profileGet(request, response);
            } else if (super.getURI().matches(UrlProfileClient.PROFILE_EDIT)) {
                profileEditGet(request, response);
            } else {
                request.getRequestDispatcher("/views/error/client/404.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlProfileClient.CHANGE_PASSWORD)) {
                changePasswordPost(request, response);
            } else if (super.getURI().matches(UrlProfileClient.PROFILE)) {
                profilePost(request, response);
            } else if (super.getURI().matches(UrlProfileClient.PROFILE_EDIT)) {
                profileEditPost(request, response);
            } else {
                request.getRequestDispatcher("/views/error/client/404.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    //------------------ GET METHOD-----------------//
    private void changePasswordGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/client/profile/change_password.jsp").forward(request, response);
    }

    private void profileGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        UserDBContext userDB = new UserDBContext();
        User profile = userDB.get(user.getId());
        request.setAttribute("user", profile);

        String message = request.getParameter("message");
        String code = request.getParameter("code");
        if (message != null) {
            Message msg = new Message(code, message);
            request.setAttribute("message", msg);
        }
        request.getRequestDispatcher("/views/client/profile/profile.jsp").forward(request, response);
    }

    private void profileEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        UserDBContext userDB = new UserDBContext();
        User profile = userDB.get(user.getId());
        request.setAttribute("user", profile);

        String message = request.getParameter("message");
        String code = request.getParameter("code");
        if (message != null) {
            Message msg = new Message(code, message);
            request.setAttribute("message", msg);
        }
        request.getRequestDispatcher("/views/client/profile/profile_edit.jsp").forward(request, response);
    }

    //------------------ POST METHOD-----------------//
    private void profilePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(this.getServletContext().getContextPath() + "/profile");
    }

    private void profileEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String username = validate.getField(request, "username", true);
            String firstname = validate.getField(request, "first_name", true);
            String lastname = validate.getField(request, "last_name", true);
            String phone = validate.getField(request, "phone", true);
            String raw_gender = validate.getField(request, "gender", true);
            boolean gender = (raw_gender.equalsIgnoreCase("male"));
            UserDBContext userDB = new UserDBContext();
            User user = (User) request.getSession().getAttribute("user");
            user = userDB.get(user.getId());

            if (!user.getUsername().equalsIgnoreCase(username) && userDB.findOne("username", username) != null) {
                throw new Exception("Username already exits! Please try again username");
            }
            
            if (!phone.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                throw new Exception("Please enter a phone number in VN!");
            }

            Part part = request.getPart("avatar");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/user");
            String filename = null;

            if (part.getSize() != 0) {
                if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                    fileUtility.delete(user.getAvatar(), folder);
                }
                filename = fileUtility.upLoad(part, folder);
            }

            if (filename != null) {
                user.setAvatar(filename);
            }

            user.setUsername(username);
            user.setFirst_name(firstname);
            user.setLast_name(lastname);
            user.setPhone(phone);
            user.setGender(gender);

            userDB.update(user);
            request.getSession().setAttribute("user", user);

            String message = "Update profile sucess!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/profile?code=" + code + "&message=" + message);
        } catch (Exception ex) {
            User user = (User) request.getSession().getAttribute("user");
            UserDBContext userDB = new UserDBContext();
            User profile = userDB.get(user.getId());
            request.setAttribute("user", profile);
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/client/profile/profile_edit.jsp").forward(request, response);
        }
    }

    private void changePasswordPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String currentPassword = validate.getField(request, "current-password", true);
            String newPassword = validate.getField(request, "new-password", true);
            String renewPassword = validate.getField(request, "renew-password", true);
            
            UserDBContext userDB = new UserDBContext();
            User user = (User) request.getSession().getAttribute("user");
            user = userDB.get(user.getId());
            
            if (!BcryptUtility.verifyHash(currentPassword, user.getPassword())) {
                throw new Exception("Current password is wrong!");
            }
            
            if(currentPassword.equals(newPassword)){
                throw new Exception("New password same current password!");
            }
            
            if (newPassword.length() < 6) {
                throw new Exception("Password must have length or by 6");
            }
            
            if(!newPassword.equals(renewPassword) ){
                throw new Exception("Password don't match.!");
            }
            
            user.setPassword(BcryptUtility.hashpw(newPassword));
            userDB.updatePassword(user);
            request.getSession().setAttribute("user", user);

            String message = "Change password sucess!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/profile?code=" + code + "&message=" + message);
        } catch (Exception ex) {
            User user = (User) request.getSession().getAttribute("user");
            UserDBContext userDB = new UserDBContext();
            User profile = userDB.get(user.getId());
            request.setAttribute("user", profile);
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/client/profile/change_password.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
