/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.client;

import com.google.gson.Gson;
import com.se1617.learning.config.url.client.UrlAuthClient;
import com.se1617.learning.controller.base.BaseController;
import com.se1617.learning.dal.user.GroupDBContext;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.user.Group;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.BcryptUtility;
import com.se1617.learning.utils.EmailUtility;
import com.se1617.learning.utils.JWTUtility;
import com.se1617.learning.utils.Validate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author giaki
 */
public class AuthController extends BaseController {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlAuthClient.AUTH_LOGIN)) {
                loginGet(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_SIGNUP)) {
                singupGet(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_LOGOUT)) {
                logout(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_ENABLE)) {
                enableGet(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_ENABLE_SENDMAIL_DONE)) {
                enableSendmailDone(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_ENABLE_CONFIRM)) {
                enableConfirm(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_RESETPASSWORD)) {
                resetPasswordGet(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_RESETPASSWORD_CONFIRM)) {
                confirmResetPasswordGet(request, response);
            } else {
                request.getRequestDispatcher("/views/error/client/404.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/client/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlAuthClient.API_AUTH_LOGIN)) {
                apiLoginPost(request, response);
            } else if (super.getURI().matches(UrlAuthClient.API_AUTH_SIGNUP)) {
                apiSignupPost(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_LOGIN)) {
                loginPost(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_SIGNUP)) {
                singupPost(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_LOGOUT)) {
                logout(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_ENABLE)) {
                enablePost(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_RESETPASSWORD)) {
                resetPasswordPost(request, response);
            } else if (super.getURI().matches(UrlAuthClient.AUTH_RESETPASSWORD_CONFIRM)) {
                confirmResetPasswordPost(request, response);
            } else {
                request.getRequestDispatcher("/views/error/client/404.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/client/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    private void enableGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = session.getAttribute("user") != null;
        if (loggedIn) {
            request.getRequestDispatcher("/views/client/auth/confirm_register.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth/login");
        }
    }

    //--------------- GET METHOD---------------------//
    private void loginGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = session.getAttribute("user") != null;
        if (loggedIn) {
            response.sendRedirect(request.getContextPath());
        } else {
            request.getRequestDispatcher("/views/client/auth/login.jsp").forward(request, response);
        }
    }

    private void singupGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = session.getAttribute("user") != null;
        if (loggedIn) {
            response.sendRedirect(request.getContextPath());
        } else {
            request.getRequestDispatcher("/views/client/auth/register.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/auth/login");
    }

    private void resetPasswordGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/client/auth/reset_password.jsp").forward(request, response);

    }

    private void confirmResetPasswordGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String token = super.getPaths().get(1);
            User user = JWTUtility.verifyToken(token);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/client/auth/new_password.jsp").forward(request, response);
            } else {
                request.setAttribute("code", "Some thing wrong!");
                request.getRequestDispatcher("/views/error/client/error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/client/error.jsp").forward(request, response);
        }
    }

    private void enableConfirm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String token = super.getPaths().get(1);
            User user = JWTUtility.verifyToken(token);
            if (user != null) {
                user.setEnable(true);
                UserDBContext userDB = new UserDBContext();
                userDB.updateEnable(user);
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath());
            } else {
                request.setAttribute("code", "Some thing wrong!");
                request.getRequestDispatcher("/views/error/client/error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/client/error.jsp").forward(request, response);
        }
    }

    private void enableSendmailDone(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/client/auth/confirm_enable.jsp").forward(request, response);
    }

    //--------------- POST METHOD---------------------//
    private void loginPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDBContext userDB = new UserDBContext();
        try {
            Validate validate = new Validate();
            String username = validate.getField(request, "username", true);
            String password = validate.getField(request, "password", true);
            User user = userDB.findOne("email", username);

            if (user == null) {
                user = userDB.findOne("username", username);
            }
            if (user == null) {
                request.setAttribute("error", "Can't find username or email!");
                request.getRequestDispatcher("/views/client/auth/login.jsp").forward(request, response);
                return;
            } else if (!BcryptUtility.verifyHash(password, user.getPassword())) {
                request.setAttribute("username", username);
                request.setAttribute("error", "Password not match!");
                request.getRequestDispatcher("/views/client/auth/login.jsp").forward(request, response);
                return;
            }

            if ((!user.is_super()) && (!user.is_active() || !user.hasGroupActive())) {
                request.setAttribute("error", "User is not active!");
                request.getRequestDispatcher("/views/client/auth/login.jsp").forward(request, response);
                return;
            }

            request.getSession().setAttribute("user", user);
            HttpSession session = request.getSession();
            String uri = (String) session.getAttribute("request-uri");
            if (uri != null) {
                response.sendRedirect(uri);
                session.removeAttribute("request-uri");
            } else {
                if (user.is_super() || user.checkGroup("admin") || user.checkGroup("marketing")) {
                    if(user.checkGroup("admin") && !user.is_super()){
                        response.sendRedirect(request.getContextPath() + "/admin/setting");
                    }else{
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    }
                } else if (user.checkGroup("expert")) {
                    response.sendRedirect(request.getContextPath() + "/admin/subject");
                } else if (user.checkGroup("sale")) {
                    response.sendRedirect(request.getContextPath() + "/admin/registrations");
                } else {
                    response.sendRedirect(request.getContextPath());
                }
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/client/auth/login.jsp").forward(request, response);
        }
    }

    private void apiLoginPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDBContext userDB = new UserDBContext();
        try {
            Validate validate = new Validate();
            String username = validate.getField(request, "username", true);
            String password = validate.getField(request, "password", true);
            User user = userDB.findOne("email", username);

            if (user == null) {
                user = userDB.findOne("username", username);
            }

            if (user == null) {
                throw new Exception("Can't find username or email!");
            } else if (!BcryptUtility.verifyHash(password, user.getPassword())) {
                throw new Exception("Password not match!");
            }

            if ((!user.is_super()) && (!user.is_active() || !user.hasGroupActive())) {
                throw new Exception("User is not active!");
            }

            request.getSession().setAttribute("user", user);
            HttpSession session = request.getSession();
            String uri = (String) session.getAttribute("request-uri");
            if (uri != null) {
                Message message = new Message();
                message.setCode("sucess");
                message.setMessage(uri);
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } else {
                String riderect = request.getContextPath();
                if (user.is_super() || user.checkGroup("admin") || user.checkGroup("marketing")) {
                    riderect = request.getContextPath() + "/admin/dashboard";
                } else if (user.checkGroup("expert")) {
                    riderect = request.getContextPath() + "/admin/subject";
                } else if (user.checkGroup("sale")) {
                    riderect = request.getContextPath() + "/admin/registrations";
                }
                Message message = new Message();
                message.setCode("sucess");
                message.setMessage(riderect);
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void singupPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();

            String first_name = validate.getField(request, "first_name", true);
            String last_name = validate.getField(request, "last_name", true);
            String raw_gender = validate.getField(request, "gender", true);
            String phone = validate.getField(request, "phone", true);
            String username = validate.getField(request, "username", true);
            String email = validate.getField(request, "email", true);
            String password = validate.getField(request, "password", true);
            String re_password = validate.getField(request, "re-password", true);
            boolean gender = raw_gender.equals("male");

            if (password.length() < 6) {
                throw new Exception("Password must have length or by 6");
            }
            if (!phone.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                throw new Exception("Please enter a phone number in VN!");
            }
            if (!email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
                throw new Exception("Please enter a email!");
            }
            if (!password.equals(re_password)) {
                throw new Exception("Password not match!");
            }
            UserDBContext userDB = new UserDBContext();

            if (userDB.findOne("email", email) != null) {
                throw new Exception("Email already exists!");
            }

            if (userDB.findOne("username", username) != null) {
                throw new Exception("Username already exists!");
            }

            // Construct Object
            User user = new User();
            user.setUsername(username);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPassword(BcryptUtility.hashpw(password));
            user.set_staff(false);
            user.set_super(false);
            user.setEnable(false);
            user.set_active(true);

            user = userDB.insert(user);
            GroupDBContext groupDB = new GroupDBContext();
            Group group = groupDB.findOne("user");
            ArrayList<Group> groups = new ArrayList<>();
            groups.add(group);
            groupDB.updateUserGroup(user.getId(), groups);
            sendTokenToEmail(request, user, "Coursera - Enable your account", "/auth/enable/", "/views/client/auth/form_email_enable_account.jsp");
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath());
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
            request.setAttribute("gender", request.getParameter("gender"));
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/client/auth/register.jsp").forward(request, response);
        }

    }

    private void apiSignupPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();

            String first_name = validate.getFieldAjax(request, "first_name", true);
            String last_name = validate.getFieldAjax(request, "last_name", true);
            String raw_gender = validate.getFieldAjax(request, "gender", true);
            String phone = validate.getField(request, "phone", true);
            String username = validate.getFieldAjax(request, "username", true);
            String email = validate.getFieldAjax(request, "email", true);
            String password = validate.getFieldAjax(request, "password", true);
            String re_password = validate.getFieldAjax(request, "re-password", true);
            boolean gender = raw_gender.equals("male");

            if (password.length() < 6) {
                throw new Exception("Password must have length or by 6");
            }
            if (!email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
                throw new Exception("Please enter a email!");
            }
            if (!phone.matches("^(((\\+|)84)|0)+(3|5|7|8|9|1[2|6|8|9])+([0-9]{8})$")) {
                throw new Exception("Please enter a phone number in VN!");
            }
            if (!password.equals(re_password)) {
                throw new Exception("Password not match!");
            }
            UserDBContext userDB = new UserDBContext();

            if (userDB.findOne("email", email) != null) {
                throw new Exception("Email already exists!");
            }

            if (userDB.findOne("username", username) != null) {
                throw new Exception("Username already exists!");
            }

            // Construct Object
            User user = new User();
            user.setUsername(username);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPassword(BcryptUtility.hashpw(password));
            user.set_staff(false);
            user.set_super(false);
            user.setEnable(false);
            user.set_active(true);

            user = userDB.insert(user);
            GroupDBContext groupDB = new GroupDBContext();
            Group group = groupDB.findOne("user");
            ArrayList<Group> groups = new ArrayList<>();
            groups.add(group);
            groupDB.updateUserGroup(user.getId(), groups);
            sendTokenToEmail(request, user, "Coursera - Enable your account", "/auth/enable/", "/views/client/auth/form_email_enable_account.jsp");
            request.getSession().setAttribute("user", user);

            Message message = new Message();
            message.setCode("sucess");
            message.setMessage(request.getContextPath() + "/");
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void resetPasswordPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String email = validate.getField(request, "email", true);
            UserDBContext userDBContext = new UserDBContext();
            User user = userDBContext.findOne("email", email);
            if (user == null) {
                request.setAttribute("error", "Can't find your email!");
                request.getRequestDispatcher("/views/client/auth/reset_password.jsp").forward(request, response);
                return;
            } else {
                try {
                    sendTokenToEmail(request, user, "Coursera - Reset your password", "/auth/very/", "/views/client/auth/form_email_reset_password.jsp");
                    request.getRequestDispatcher("/views/client/auth/confirm_reset.jsp").forward(request, response);
                } catch (IOException e) {
                    request.getRequestDispatcher("/views/client/auth/confirm_reset_error.jsp").forward(request, response);
                }
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/client/auth/reset_password.jsp").forward(request, response);
        }

    }

    private void confirmResetPasswordPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String token = super.getPaths().get(1);
            User user = JWTUtility.verifyToken(token);
            if (user != null) {
                Validate validate = new Validate();
                String password = validate.getField(request, "password", true);
                String confirm_password = validate.getField(request, "confirm-password", true);
                if (password.length() < 6) {
                    throw new Exception("Password must have length or by 6");
                }
                if (!password.equals(confirm_password)) {
                    request.setAttribute("error", "Confirm password not match!");
                    request.getRequestDispatcher("/views/client/auth/new_password.jsp").forward(request, response);
                }
                user.setPassword(BcryptUtility.hashpw(password));
                UserDBContext userDBContext = new UserDBContext();
                userDBContext.updatePassword(user);
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else {
                request.setAttribute("code", "Some thing wrong!");
                request.getRequestDispatcher("/views/error/client/error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/client/error.jsp").forward(request, response);
        }
    }

    private void enablePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else if (!user.isEnable()) {
                try {
                    sendTokenToEmail(request, user, "Coursera - Enable your account", "/auth/enable/", "/views/client/auth/form_email_enable_account.jsp");
                    response.sendRedirect(request.getContextPath() + "/auth/enable/confirm");
                } catch (IOException e) {
                    request.setAttribute("error", e.getMessage());
                    request.getRequestDispatcher("/views/client/auth/confirm_register.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/auth/login");
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/client/auth/confirm_register.jsp").forward(request, response);
        }
    }

    public void sendTokenToEmail(HttpServletRequest request, User user, String subject, String url, String urlContenthtml)
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

        System.out.println(uri);

        String token = JWTUtility.createJWT(user, 5);
        String url_form_mail = uri + urlContenthtml;
        URIBuilder urlBuilder = new URIBuilder(url_form_mail);
        urlBuilder.addParameter("url_confirm", uri + url + token);
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
        String password = context.getInitParameter("pass");
        emailUtility.sendEmail(host, port, email_context, password, user.getEmail(), subject, data);
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
