/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.se1617.learning.config.url.admin.UrlUserAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.user.GroupDBContext;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.user.Group;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.BcryptUtility;
import com.se1617.learning.utils.EmailUtility;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.JWTUtility;
import com.se1617.learning.utils.Validate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author Computer
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserController extends BaseAuthController {

    private static final int pageSize = 20;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlUserAdmin.USER_DELETE_SELECTED)) {
                userDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_EDIT)) {
                userEditGet(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_CREATE)) {
                userCreateGet(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_DELETE)) {
                userDelete(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_DETAIL)) {
                userDetail(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_LIST)) {
                userList(request, response);
            } else {
                request.getRequestDispatcher("/views/error/admin/404.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlUserAdmin.USER_DELETE_SELECTED)) {
                userDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_EDIT)) {
                userEditPost(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_CREATE)) {
                userCreatePost(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_DELETE)) {
                userDelete(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_DETAIL)) {
                userDetail(request, response);
            } else if (super.getURI().matches(UrlUserAdmin.USER_LIST)) {
                userList(request, response);
            } else {
                request.getRequestDispatcher("/views/error/admin/404.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }

    //------------- METHOD GET --------------------//
    private void userDeleteSelected(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void userEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            UserDBContext userDB = new UserDBContext();
            User user = userDB.get(id);
            if (user == null) {
                throw new Exception("User not found!");
            }
            request.setAttribute("user", user);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("roles", settingDB.getByType("USER_ROLE"));
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/user/user_edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("User not found!");
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }

    }

    private void userCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDBContext settingDB = new SettingDBContext();
        request.setAttribute("roles", settingDB.getByType("USER_ROLE"));
        request.getRequestDispatcher("/views/admin/user/user_create.jsp").forward(request, response);
    }

    private void userDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(paramId);
            UserDBContext userDB = new UserDBContext();
            User auth = (User) request.getSession().getAttribute("user");
            User user = userDB.get(id);
            if (user == null) {
                throw new NumberFormatException();
            }
            if (user.is_super()) {
                ArrayList<User> users = userDB.findUserSuper();
                if (users == null || users.size() <= 1) {
                    throw new Exception("Cant delete user admin! because only one user is super admin!");
                }
            }
            
            if(auth.getId()==user.getId()){
                throw new Exception("You can't delete your self!");
            }
            userDB.delete(id);
            if (request.getHeader("referer").toLowerCase().contains("/admin/user/" + id) && !request.getHeader("referer").toLowerCase().contains("/edit/")) {
                response.sendRedirect(request.getContextPath() + "/admin/user");
            } else {
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (NumberFormatException e) {
            String message = "Cant delete user!";
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/user?code=" + code + "&message=" + message);
        } catch (Exception ex) {
            String message = ex.getMessage();
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/user?code=" + code + "&message=" + message);
        }
    }

    private void userDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            UserDBContext userDB = new UserDBContext();
            User user = userDB.get(id);
            if (user == null) {
                throw new Exception("User not found!");
            }
            request.setAttribute("user", user);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("roles", settingDB.getByType("USER_ROLE"));
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/user/user_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("User not found!");
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }

    private void userList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String page = validate.getField(request, "page", false);
            if (page == null || page.trim().length() == 0) {
                page = "1";
            }
            int pageIndex = 1;
            try {
                pageIndex = validate.fieldInt(page, "Page size need a number!");
                if (pageIndex <= 0) {
                    pageIndex = 1;
                }
            } catch (Exception e) {
                pageIndex = 1;
            }

            String message = request.getParameter("message");
            String code = request.getParameter("code");
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);

            Map<String, String> orderMap = new HashMap<>();
            String[] sorts = validate.getFieldsAjax(request, "sort", false);
            if (sorts != null && sorts.length > 0) {
                int count = 0;
                for (String ordering : sorts) {
                    if (ordering != null && ordering.matches("^[a-zA-z]{1,}_(DESC|ASC)$")) {
                        String[] sort = ordering.split("_", 2);
                        if (sort[0].equalsIgnoreCase("id")
                                || sort[0].equalsIgnoreCase("email")
                                || sort[0].equalsIgnoreCase("gender")
                                || sort[0].equalsIgnoreCase("phone")
                                || sort[0].equalsIgnoreCase("active")
                                || sort[0].equalsIgnoreCase("super")
                                || sort[0].equalsIgnoreCase("status")
                                || sort[0].equalsIgnoreCase("firstName")
                                || sort[0].equalsIgnoreCase("lastName")
                                || sort[0].equalsIgnoreCase("enable")
                                || sort[0].equalsIgnoreCase("staff")
                                || sort[0].equalsIgnoreCase("username")) {

                            orderMap.put(++count + "." + sort[0], sort[1]);
                        }
                    }
                }
            }
            pageable.setOrderings(orderMap);
            request.setAttribute("pageable", pageable);

            UserDBContext userDB = new UserDBContext();

            String[] status = validate.getFieldsAjax(request, "status", false);
            String[] genders = validate.getFieldsAjax(request, "gender", false);
            String[] roles = validate.getFieldsAjax(request, "role", false);
            Map<String, ArrayList<String>> filters = new HashMap<>();

            if (status != null && status.length > 0) {
                ArrayList<String> listStatus = new ArrayList<>();
                Collections.addAll(listStatus, status);
                for (String statu : status) {
                    if (statu.equalsIgnoreCase("active") || statu.equalsIgnoreCase("inactive")) {
                        listStatus.add(statu);
                    }
                }
                if (!listStatus.isEmpty()) {
                    filters.put("status", listStatus);
                }
            }
            if (genders != null && genders.length > 0) {
                ArrayList<String> listGender = new ArrayList<>();
                for (String gender : genders) {
                    if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")) {
                        listGender.add(gender);
                    }
                }
                if (!listGender.isEmpty()) {
                    filters.put("gender", listGender);
                }
            }
            if (roles != null && roles.length > 0) {
                ArrayList<String> listRole = new ArrayList<>();
                for (String role : roles) {
                    if (role.matches("[0-9]+")) {
                        listRole.add(role);
                    }
                }
                if (!listRole.isEmpty()) {
                    filters.put("role", listRole);
                }
            }

            pageable.setFilters(filters);

            ResultPageable<User> resultPageable = userDB
                    .searchByNameOrEmailOrPhoneAndFilterByGenderAndRoleAndStatusAndSortMultiField(search, pageable);

            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("users", resultPageable.getList());
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("roles", settingDB.getByType("USER_ROLE"));
            request.getRequestDispatcher("/views/admin/user/user.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //--------------------METHOD POST---------------------//
    private void userCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("Create user post!");
            Validate validate = new Validate();
            UserDBContext userDB = new UserDBContext();
            User auth = (User) request.getSession().getAttribute("user");

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("roles", settingDB.getByType("USER_ROLE"));

            String username = validate.getField(request, "username", true);
            String email = validate.getField(request, "email", true);
            String first_name = validate.getField(request, "first_name", true);
            String last_name = validate.getField(request, "last_name", true);
            String gender = validate.getField(request, "gender", true);
            String phone = validate.getField(request, "phone", true);
            String superParam = validate.getField(request, "super", false);
            String staffParam = validate.getField(request, "staff", false);
            String status = validate.getField(request, "status", false);
            String[] roles = validate.getFieldsAjax(request, "roles", false);

            if (userDB.findOne("username", username) != null) {
                throw new Exception("Username already exits! please try again username");
            }

            if (!phone.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                throw new Exception("Please enter a phone number in VN!");
            }

            if (userDB.findOne("email", email) != null) {
                throw new Exception("Email already exits! please try again email");
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setGender(gender.equalsIgnoreCase("male"));
            user.setPhone(phone);
            if (auth.checkFeature("USER_CHANGE_STATUS")) {
                user.set_active(status != null && status.equalsIgnoreCase("active"));
            } else {
                user.set_active(false);
            }

            ArrayList<Group> groups = new ArrayList<>();
            if (auth.checkFeature("USER_EDIT_ROLE")) {
                user.set_super(superParam != null && superParam.equalsIgnoreCase("super"));
                user.set_staff(staffParam != null && staffParam.equalsIgnoreCase("staff"));
                for (String role : roles) {
                    int roleId = Integer.parseInt(role);
                    Group group = new Group();
                    group.setId(roleId);
                    groups.add(group);
                }
            } else {
                user.set_super(false);
                user.set_staff(false);
                GroupDBContext groupDB = new GroupDBContext();
                Group group = groupDB.findOne("user");
                groups.add(group);
            }
            user.setGroups(groups);

            Part part = request.getPart("avatar");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/user");
            String filename = null;

            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
            }

            if (filename != null) {
                user.setAvatar(filename);
            }
            String password = String.valueOf(generatePassword(8));
            System.out.println(password);
            user.setPassword(BcryptUtility.hashpw(password));
            user = userDB.adminInsert(user);
            request.setAttribute("user", user);

            sendTokenToEmail(request, user, password, "Coursera - Your password!", "/views/client/auth/form_email_new_account.jsp");

            String message = "Create user sucess!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/user/edit/" + user.getId() + "?code=" + code + "&message=" + message);
        } catch (Exception ex) {
            if (request.getParameter("first_name") != null) {
                request.setAttribute("first_name", new String(request.getParameter("first_name").getBytes("iso-8859-1"), "utf-8"));
            }
            if (request.getParameter("last_name") != null) {
                request.setAttribute("last_name", new String(request.getParameter("last_name").getBytes("iso-8859-1"), "utf-8"));
            }
            request.setAttribute("gender", request.getParameter("gender"));
            if (request.getParameter("phone") != null) {
                request.setAttribute("phone", new String(request.getParameter("phone").getBytes("iso-8859-1"), "utf-8"));
            }
            if (request.getParameter("username") != null) {
                request.setAttribute("username", new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8"));
            }
            if (request.getParameter("email") != null) {
                request.setAttribute("email", new String(request.getParameter("email").getBytes("iso-8859-1"), "utf-8"));
            }
            request.setAttribute("staff", request.getParameter("staff"));
            request.setAttribute("super", request.getParameter("super"));
            request.setAttribute("status", request.getParameter("status"));
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/user/user_create.jsp").forward(request, response);
        }
    }

    private void userEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        try {
            Validate validate = new Validate();
            int id = Integer.parseInt(paramId);
            UserDBContext userDB = new UserDBContext();
            User user = userDB.get(id);
            if (user == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("user", user);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("roles", settingDB.getByType("USER_ROLE"));

            String username = validate.getField(request, "username", true);
            String email = validate.getField(request, "email", true);
            String first_name = validate.getField(request, "first_name", true);
            String last_name = validate.getField(request, "last_name", true);
            String gender = validate.getField(request, "gender", true);
            String phone = validate.getField(request, "phone", true);
            String superParam = validate.getField(request, "super", false);
            String staffParam = validate.getField(request, "staff", false);
            String status = validate.getField(request, "status", false);
            String[] roles = validate.getFieldsAjax(request, "roles", false);

            if (!user.getUsername().equalsIgnoreCase(username) && userDB.findOne("username", username) != null) {
                throw new Exception("Username already exits! please try again username");
            }

            if (!phone.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                throw new Exception("Please enter a phone number in VN!");
            }

            if (!user.getEmail().equalsIgnoreCase(email) && userDB.findOne("email", email) != null) {
                throw new Exception("Email already exits! please try again email");
            }

            User auth = (User) request.getSession().getAttribute("user");

            user.setUsername(username);
            user.setEmail(email);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setGender(gender.equalsIgnoreCase("male"));
            user.setPhone(phone);
            user.set_super(superParam != null && superParam.equalsIgnoreCase("super"));
            user.set_staff(staffParam != null && staffParam.equalsIgnoreCase("staff"));
            if (auth.checkFeature("USER_CHANGE_STATUS")) {
                user.set_active(status != null && status.equalsIgnoreCase("active"));
            }

            if (id == auth.getId() && user.is_super() != auth.is_super()) {
                throw new Exception("Can't remove or add super for yourself!");
            }
            if (id == auth.getId() && user.is_staff() != auth.is_staff()) {
                throw new Exception("Can't remove or add staff for yourself!");
            }
            if (id == auth.getId() && user.is_active() != auth.is_active()) {
                throw new Exception("Can't remove or add active for yourself!");
            }

            if (auth.checkFeature("USER_EDIT_ROLE")) {
                ArrayList<Group> groups = new ArrayList<>();
                for (String role : roles) {
                    int roleId = Integer.parseInt(role);
                    Group group = new Group();
                    group.setId(roleId);
                    groups.add(group);
                }
                user.setGroups(groups);
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

            userDB.adminUpdate(user);

            String message = "Update user sucess!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/user/edit/" + user.getId() + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found user!");
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/user/user_edit.jsp").forward(request, response);
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

    public void sendTokenToEmail(HttpServletRequest request, User user, String password, String subject, String urlContenthtml)
            throws URISyntaxException, MalformedURLException, IOException, MessagingException {
        int serverPort = request.getServerPort();
        String uri = "https"
                + "://"
                + request.getServerName()
                + request.getContextPath();
        if (serverPort != 80 && serverPort != 443) {
            uri = request.getScheme()
                    + "://"
                    + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + request.getContextPath();
        }

        String token = JWTUtility.createJWT(user, 5);
        String url_form_mail = uri + urlContenthtml;
        URIBuilder urlBuilder = new URIBuilder(url_form_mail);
        urlBuilder.addParameter("password", password);
        urlBuilder.addParameter("fullname", user.getFirst_name() + " " + user.getLast_name());
        URLConnection urlConnection = urlBuilder.build().toURL().openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream()));
        String inputLine, data = "";
        while ((inputLine = in.readLine()) != null) {
            data += inputLine;
        }
        in.close();
        EmailUtility emailUtility = new EmailUtility();
        ServletContext context = getServletContext();
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String email_context = context.getInitParameter("email");
        String pass = context.getInitParameter("pass");
        emailUtility.sendEmail(host, port, email_context, pass, user.getEmail(), subject, data);
    }

    private static char[] generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

}
