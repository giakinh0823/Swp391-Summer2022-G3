/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.se1617.learning.filter;

import com.se1617.learning.config.security.AuthorizationConfig;
import com.se1617.learning.config.url.client.UrlAuthClient;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.user.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giaki
 */
public class PermissionFilter implements Filter {

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public PermissionFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("PermissionFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("PermissionFilter:DoAfterProcessing");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("PermissionFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            HttpSession session = req.getSession(false);
            String loginURI = req.getContextPath() + "/auth/login";

            boolean loggedIn = session != null && session.getAttribute("user") != null;

            String contextPath = req.getContextPath();
            String URI = req.getRequestURI().trim().replaceFirst(contextPath, "");

            if (loggedIn) {
                User user = (User) session.getAttribute("user");
                UserDBContext userDB = new UserDBContext();
                user = userDB.get(user.getId());

                if (!user.isEnable() && !URI.matches(UrlAuthClient.AUTH_ENABLE)
                        && !URI.matches(UrlAuthClient.AUTH_ENABLE_CONFIRM)
                        && !URI.matches(UrlAuthClient.AUTH_ENABLE_SENDMAIL_DONE)
                        && !URI.matches(UrlAuthClient.AUTH_LOGOUT)) {
                    res.sendRedirect(req.getContextPath() + "/auth/enable");
                } else {
                    try {
                        AuthorizationConfig.checkAccess(req, res);
                        chain.doFilter(request, response);
                    } catch (IOException | ServletException e) {
                        if (URI.matches("^/admin/.*") && (user.is_super() || user.is_staff())) {
                            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("/views/error/client/500.jsp").forward(request, response);
                        }
                    } catch (Exception ex) {
                        request.setAttribute("code", ex.getMessage());
                        if (URI.matches("^/admin/.*") && (user.is_super() || user.is_staff())) {
                            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
                        }
                    }
                }

            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable t) {
            problem = t;
            t.printStackTrace();
        }
        doAfterProcessing(request, response);
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("PermissionFilter:Initializing filter");
            }
        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("PermissionFilter()");
        }
        StringBuffer sb = new StringBuffer("PermissionFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
