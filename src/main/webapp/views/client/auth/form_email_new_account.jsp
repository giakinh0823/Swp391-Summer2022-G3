<%-- 
    Document   : form_email_reset_password
    Created on : May 27, 2022, 3:24:03 AM
    Author     : lanh0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"><head>
        <title> Welcome to Coded Mails </title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <style type="text/css">
            #outlook a {
                padding: 0;
            }

            body {
                margin: 0;
                padding: 0;
                -webkit-text-size-adjust: 100%;
                -ms-text-size-adjust: 100%;
            }

            table,
            td {
                border-collapse: collapse;
                mso-table-lspace: 0pt;
                mso-table-rspace: 0pt;
            }

            img {
                border: 0;
                height: auto;
                line-height: 100%;
                outline: none;
                text-decoration: none;
                -ms-interpolation-mode: bicubic;
            }

            p {
                display: block;
                margin: 13px 0;
            }
        </style>
        <style type="text/css">
            @media only screen and (min-width:480px) {
                .mj-column-per-100 {
                    width: 100% !important;
                    max-width: 100%;
                }
            }
        </style>
        <style type="text/css">
            @media only screen and (max-width:480px) {
                table.mj-full-width-mobile {
                    width: 100% !important;
                }

                td.mj-full-width-mobile {
                    width: auto !important;
                }
            }
        </style>
        <style type="text/css">
            a,
            span,
            td,
            th {
                -webkit-font-smoothing: antialiased !important;
                -moz-osx-font-smoothing: grayscale !important;
            }
        </style>
    </head>
    <%
        request.setAttribute("password", request.getParameter("password"));
        request.setAttribute("fullname", request.getParameter("fullname"));
    %>
    <body style="background-color:#ffffff;">
        <div style="display:none;font-size:1px;color:#ffffff;line-height:1px;max-height:0px;max-width:0px;opacity:0;overflow:hidden;"> Preview - Welcome to Coded Mails </div>
        <div style="background-color:#ffffff;">
            <div style="margin:0px auto;max-width:600px;">
                <table align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="width:100%;">
                    <tbody>
                        <tr>
                            <td style="direction:ltr;font-size:0px;padding:20px 0;padding-bottom:0px;text-align:center;">
                                <div class="mj-column-per-100 mj-outlook-group-fix" style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                                    <table border="0" cellpadding="0" cellspacing="0" role="presentation" style="vertical-align:top;" width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#434245;">
                                                        <h1 style="margin: 0; font-size: 24px; line-height: normal; font-weight: bold;"> Your password </h1>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody></table>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div style="margin:0px auto;max-width:600px;">
                <table align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="width:100%;">
                    <tbody>
                        <tr>
                            <td style="direction:ltr;font-size:0px;padding:20px 0;text-align:center;">
                                <div class="mj-column-per-100 mj-outlook-group-fix" style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                                    <table border="0" cellpadding="0" cellspacing="0" role="presentation" style="vertical-align:top;" width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#434245;">
                                                        <p style="margin: 5px 0;">Hello, ${fullname}</p>
                                                    </div>
                                                </td>
                                            </tr>
                                            
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#434245;">
                                                        <p style="margin: 5px 0;">Enter your password and started!</p>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#434245;">
                                                        <p style="margin: 5px 0;">password: ${password}</p>
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div style="margin:0px auto;max-width:600px;">
                <table align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="width:100%;">
                    <tbody>
                        <tr>
                            <td style="direction:ltr;font-size:0px;padding:20px 0;padding-top:0;text-align:center;">
                                <div class="mj-column-per-100 mj-outlook-group-fix" style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                                    <table border="0" cellpadding="0" cellspacing="0" role="presentation" style="vertical-align:top;" width="100%">
                                        <tbody><tr>
                                                <td style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <p style="border-top: dashed 1px lightgrey; font-size: 1px; margin: 0px auto; width: 100%;">
                                                    </p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#999999;">Have questions or need help? Email us at <a href="mailto:courseswp391@gmail.com" style="color: #2e58ff; text-decoration: none;">courseswp391@gmail.com</a></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#434245;">SWP391 - SE1617 - GROUP03 - Summer2022<br /> © 2022 swp391 Inc.</div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#999999;">Update your <a href="https://google.com" style="color: #2e58ff; text-decoration: none;">email preferences</a> to choose the types of emails you receive, or you can <a href="https://google.com" style="color: #2e58ff; text-decoration: none;"> unsubscribe </a>from all future emails.</div>
                                                </td>
                                            </tr>
                                        </tbody></table>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div style="margin:0px auto;max-width:600px;">
                <table align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="width:100%;">
                    <tbody>
                        <tr>
                            <td style="direction:ltr;font-size:0px;padding:20px 0;text-align:center;">
                                <div class="mj-column-per-100 mj-outlook-group-fix" style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                                    <table border="0" cellpadding="0" cellspacing="0" role="presentation" style="vertical-align:top;" width="100%">
                                        <tbody><tr>
                                                <td style="font-size:0px;word-break:break-word;">
                                                    <div style="height:1px;">   </div>
                                                </td>
                                            </tr>
                                        </tbody></table>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>