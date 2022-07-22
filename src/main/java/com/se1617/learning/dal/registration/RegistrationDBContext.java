/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.registration;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.dal.subject.PackagePriceDBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.registration.Customer;
import com.se1617.learning.model.registration.Registration;
import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.model.user.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class RegistrationDBContext extends DBContext<Registration> {

    public ArrayList<Registration> findByUserAndSubject(String email, int subjectId) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name]\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image]\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured]\n"
                    + "      ,[subject].[created_at]\n"
                    + "      ,[subject].[updated_at]\n"
                    + "      ,[user].[username]\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar]\n"
                    + "      ,[user].[is_staff]\n"
                    + "      ,[user].[is_super]\n"
                    + "      ,[user].[enable]\n"
                    + "      ,[user].[is_active]\n"
                    + "      ,[customer].[fullname]\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name]\n"
                    + "      ,[price_package].[duration]\n"
                    + "      ,[price_package].[list_price]\n"
                    + "      ,[price_package].[sale_price]\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE ([user].[email] = ? OR [customer].[email] = ?) AND [registration].[subjectid] = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, email);
            stm.setInt(3, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("image"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));
                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.set_active(rs.getBoolean("is_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                registrations.add(registration);
            }
            return registrations;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Registration> findByUser(int userId) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name]\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image]\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured]\n"
                    + "      ,[subject].[created_at]\n"
                    + "      ,[subject].[updated_at]\n"
                    + "      ,[user].[username]\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar]\n"
                    + "      ,[user].[is_staff]\n"
                    + "      ,[user].[is_super]\n"
                    + "      ,[user].[enable]\n"
                    + "      ,[user].[is_active]\n"
                    + "      ,[customer].[fullname]\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name]\n"
                    + "      ,[price_package].[duration]\n"
                    + "      ,[price_package].[list_price]\n"
                    + "      ,[price_package].[sale_price]\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [registration].[userid] = ?) [registration]";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("image"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));
                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.set_active(rs.getBoolean("is_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                registrations.add(registration);
            }
            return registrations;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Registration> findByUser(int userId, String search, Pageable pageable) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT * FROM (SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name] as 'subject_name'\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image] as 'subject_image'\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured] as 'subject_featured'\n"
                    + "      ,[subject].[created_at] as 'subject_create'\n"
                    + "      ,[subject].[updated_at] as 'subject_update'\n"
                    + "      ,[user].[username] as 'user_username'\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar] as 'user_avatar'\n"
                    + "      ,[user].[is_staff] as 'user_staff'\n"
                    + "      ,[user].[is_super] as 'user_super'\n"
                    + "      ,[user].[enable] as 'user_enable'\n"
                    + "      ,[user].[is_active] as 'user_active'\n"
                    + "      ,[customer].[fullname] as 'customer_fullnamel'\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name] as 'price_name'\n"
                    + "      ,[price_package].[duration] 'price_duration'\n"
                    + "      ,[price_package].[list_price] 'price_list'\n"
                    + "      ,[price_package].[sale_price] 'price_sale'\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "       ,ROW_NUMBER() OVER (ORDER BY [registration].[created_at] DESC ) as row_index"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [registration].[userid] = ? AND [subject].[name] like ? ) [registration]"
                    + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

            String size = "SELECT COUNT(*) as 'size' "
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [registration].[userid] = ? AND [subject].[name] like ? ";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            stm.setInt(5, pageable.getPageIndex());
            stm.setInt(6, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setId(rs.getInt("subjectid"));
                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("user_username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("user_avatar"));
                user.set_staff(rs.getBoolean("user_staff"));
                user.set_super(rs.getBoolean("user_super"));
                user.setEnable(rs.getBoolean("user_enable"));
                user.set_active(rs.getBoolean("user_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("customer_fullnamel"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("price_name"));
                package_price.setDuration(rs.getInt("price_duration"));
                package_price.setList_price(rs.getDouble("price_list"));
                package_price.setSale_price(rs.getDouble("price_sale"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                registrations.add(registration);
            }

            stm = connection.prepareStatement(size);
            stm.setInt(1, userId);
            stm.setString(2, "%" + search + "%");
            rs = stm.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("size");
            }

            ResultPageable<Registration> resultPageable = new ResultPageable<>();
            resultPageable.setList(registrations);
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), count));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Registration> listWithPagination(String search, Pageable pageable) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT * FROM (SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name] as 'subject_name'\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image] as 'subject_image'\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured] as 'subject_featured'\n"
                    + "      ,[subject].[created_at] as 'subject_create'\n"
                    + "      ,[subject].[updated_at] as 'subject_update'\n"
                    + "      ,[user].[username] as 'user_username'\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar] as 'user_avatar'\n"
                    + "      ,[user].[is_staff] as 'user_staff'\n"
                    + "      ,[user].[is_super] as 'user_super'\n"
                    + "      ,[user].[enable] as 'user_enable'\n"
                    + "      ,[user].[is_active] as 'user_active'\n"
                    + "      ,[customer].[fullname] as 'customer_fullnamel'\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name] as 'price_name'\n"
                    + "      ,[price_package].[duration] 'price_duration'\n"
                    + "      ,[price_package].[list_price] 'price_list'\n"
                    + "      ,[price_package].[sale_price] 'price_sale'\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "       ,ROW_NUMBER() OVER (ORDER BY [registration].[created_at] DESC ) as row_index"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [subject].[name] like ? or [user].[email] like ? ) [registration]"
                    + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

            String size = "SELECT COUNT(*) as 'size' "
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [subject].[name] like ? or [user].[email] like ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            stm.setInt(5, pageable.getPageIndex());
            stm.setInt(6, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setId(rs.getInt("subjectid"));
                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("user_username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("user_avatar"));
                user.set_staff(rs.getBoolean("user_staff"));
                user.set_super(rs.getBoolean("user_super"));
                user.setEnable(rs.getBoolean("user_enable"));
                user.set_active(rs.getBoolean("user_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("customer_fullnamel"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("price_name"));
                package_price.setDuration(rs.getInt("price_duration"));
                package_price.setList_price(rs.getDouble("price_list"));
                package_price.setSale_price(rs.getDouble("price_sale"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                registrations.add(registration);
            }

            stm = connection.prepareStatement(size);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            rs = stm.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("size");
            }

            ResultPageable<Registration> resultPageable = new ResultPageable<>();
            resultPageable.setList(registrations);
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), count));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Registration> findLearningByUser(int userId, String search, Pageable pageable) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT * FROM (SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name] as 'subject_name'\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image] as 'subject_image'\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured] as 'subject_featured'\n"
                    + "      ,[subject].[created_at] as 'subject_create'\n"
                    + "      ,[subject].[updated_at] as 'subject_update'\n"
                    + "      ,[user].[username] as 'user_username'\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar] as 'user_avatar'\n"
                    + "      ,[user].[is_staff] as 'user_staff'\n"
                    + "      ,[user].[is_super] as 'user_super'\n"
                    + "      ,[user].[enable] as 'user_enable'\n"
                    + "      ,[user].[is_active] as 'user_active'\n"
                    + "      ,[customer].[fullname] as 'customer_fullnamel'\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name] as 'price_name'\n"
                    + "      ,[price_package].[duration] 'price_duration'\n"
                    + "      ,[price_package].[list_price] 'price_list'\n"
                    + "      ,[price_package].[sale_price] 'price_sale'\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "       ,ROW_NUMBER() OVER (ORDER BY [registration].[created_at] DESC ) as row_index"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [registration].[userid] = ? AND [subject].[name] like ? AND [registration].[status] = 1 AND ([registration].[valid_to] > GETDATE() or [registration].[valid_to] is null)) [registration]"
                    + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

            String size = "SELECT COUNT(*) as 'size' "
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [registration].[userid] = ? AND [subject].[name] like ? AND [registration].[status] = 1 AND [registration].[valid_to] > GETDATE()";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            stm.setInt(5, pageable.getPageIndex());
            stm.setInt(6, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setId(rs.getInt("subjectid"));
                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("user_username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("user_avatar"));
                user.set_staff(rs.getBoolean("user_staff"));
                user.set_super(rs.getBoolean("user_super"));
                user.setEnable(rs.getBoolean("user_enable"));
                user.set_active(rs.getBoolean("user_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("customer_fullnamel"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("price_name"));
                package_price.setDuration(rs.getInt("price_duration"));
                package_price.setList_price(rs.getDouble("price_list"));
                package_price.setSale_price(rs.getDouble("price_sale"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                registrations.add(registration);
            }

            stm = connection.prepareStatement(size);
            stm.setInt(1, userId);
            stm.setString(2, "%" + search + "%");
            rs = stm.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("size");
            }

            ResultPageable<Registration> resultPageable = new ResultPageable<>();
            resultPageable.setList(registrations);
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), count));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Registration> findLearningByUser(int userId) {
        ArrayList<Registration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name] as 'subject_name'\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image] as 'subject_image'\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured] as 'subject_featured'\n"
                    + "      ,[subject].[created_at] as 'subject_create'\n"
                    + "      ,[subject].[updated_at] as 'subject_update'\n"
                    + "      ,[user].[username] as 'user_username'\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar] as 'user_avatar'\n"
                    + "      ,[user].[is_staff] as 'user_staff'\n"
                    + "      ,[user].[is_super] as 'user_super'\n"
                    + "      ,[user].[enable] as 'user_enable'\n"
                    + "      ,[user].[is_active] as 'user_active'\n"
                    + "      ,[customer].[fullname] as 'customer_fullnamel'\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name] as 'price_name'\n"
                    + "      ,[price_package].[duration] 'price_duration'\n"
                    + "      ,[price_package].[list_price] 'price_list'\n"
                    + "      ,[price_package].[sale_price] 'price_sale'\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + "  WHERE [registration].[userid] = ? AND [registration].[valid_to] > GETDATE()";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setId(rs.getInt("subjectid"));
                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("user_username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("user_avatar"));
                user.set_staff(rs.getBoolean("user_staff"));
                user.set_super(rs.getBoolean("user_super"));
                user.setEnable(rs.getBoolean("user_enable"));
                user.set_active(rs.getBoolean("user_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("customer_fullnamel"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("price_name"));
                package_price.setDuration(rs.getInt("price_duration"));
                package_price.setList_price(rs.getDouble("price_list"));
                package_price.setSale_price(rs.getDouble("price_sale"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                registrations.add(registration);
            }
            return registrations;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Registration> list() {
        ArrayList<Registration> registrations = new ArrayList<>();
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        try {
            String sql = "SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name]\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image]\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured]\n"
                    + "      ,[subject].[created_at]\n"
                    + "      ,[subject].[updated_at]\n"
                    + "      ,[user].[username]\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar]\n"
                    + "      ,[user].[is_staff]\n"
                    + "      ,[user].[is_super]\n"
                    + "      ,[user].[enable]\n"
                    + "      ,[user].[is_active]\n"
                    + "      ,[customer].[fullname]\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name]\n"
                    + "      ,[price_package].[duration]\n"
                    + "      ,[price_package].[list_price]\n"
                    + "      ,[price_package].[sale_price]\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n";

            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("image"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));
                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.set_active(rs.getBoolean("is_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());
                registrations.add(registration);
            }
            return registrations;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<String, ArrayList<Registration>> dataByDateMonthSucesss(Timestamp dateStart, Timestamp dateEnd) {
        Map<String, ArrayList<Registration>> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        try {
            String sql = "SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name]\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image]\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured]\n"
                    + "      ,[subject].[created_at]\n"
                    + "      ,[subject].[updated_at]\n"
                    + "      ,[user].[username]\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar]\n"
                    + "      ,[user].[is_staff]\n"
                    + "      ,[user].[is_super]\n"
                    + "      ,[user].[enable]\n"
                    + "      ,[user].[is_active]\n"
                    + "      ,[customer].[fullname]\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name]\n"
                    + "      ,[price_package].[duration]\n"
                    + "      ,[price_package].[list_price]\n"
                    + "      ,[price_package].[sale_price]\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + " WHERE [registration].[status]=1 AND [registration].[created_at] >= ? AND [registration].[created_at] <= ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, dateStart);
            stm.setTimestamp(2, dateEnd);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("image"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));
                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.set_active(rs.getBoolean("is_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());
                String key = sdf.format(new java.util.Date(registration.getCreated_at().getTime()));
                if (maps.get(key) != null) {
                    maps.get(key).add(registration);
                } else {
                    ArrayList<Registration> registrations = new ArrayList<>();
                    registrations.add(registration);
                    maps.put(key, registrations);
                }
            }

            return maps;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<String, ArrayList<Registration>> dataByDateMonthPending(Timestamp dateStart, Timestamp dateEnd) {
        Map<String, ArrayList<Registration>> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        try {
            String sql = "SELECT [registration].[id]\n"
                    + "      ,[registration].[status]\n"
                    + "      ,[registration].[total_cost]\n"
                    + "      ,[registration].[valid_from]\n"
                    + "      ,[registration].[valid_to]\n"
                    + "      ,[registration].[created_at]\n"
                    + "      ,[registration].[subjectid]\n"
                    + "      ,[registration].[userid]\n"
                    + "      ,[registration].[customerid]\n"
                    + "      ,[registration].[update_by]\n"
                    + "      ,[registration].[price_packageid]\n"
                    + "      ,[subject].[name]\n "
                    + "      ,[subject].[description] as 'subject_description'\n"
                    + "      ,[subject].[image]\n"
                    + "      ,[subject].[status] as 'subject_status'\n"
                    + "      ,[subject].[featured]\n"
                    + "      ,[subject].[created_at]\n"
                    + "      ,[subject].[updated_at]\n"
                    + "      ,[user].[username]\n"
                    + "      ,[user].[email] as 'user_email'\n"
                    + "      ,[user].[phone] as 'user_phone'\n"
                    + "      ,[user].[first_name] as 'user_first_name'\n"
                    + "      ,[user].[last_name] as 'user_last_name'\n"
                    + "      ,[user].[gender] as 'user_gender'\n"
                    + "      ,[user].[avatar]\n"
                    + "      ,[user].[is_staff]\n"
                    + "      ,[user].[is_super]\n"
                    + "      ,[user].[enable]\n"
                    + "      ,[user].[is_active]\n"
                    + "      ,[customer].[fullname]\n"
                    + "      ,[customer].[email] as 'customer_email'\n"
                    + "      ,[customer].[gender] as 'customer_gender'\n"
                    + "      ,[customer].[phone] as 'customer_phone'\n"
                    + "      ,[price_package].[name]\n"
                    + "      ,[price_package].[duration]\n"
                    + "      ,[price_package].[list_price]\n"
                    + "      ,[price_package].[sale_price]\n"
                    + "      ,[price_package].[status] as 'price_package_status'\n"
                    + "      ,[price_package].[description]as 'price_package_description'\n"
                    + "  FROM [registration]"
                    + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                    + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                    + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                    + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                    + " WHERE [registration].[status]=0 AND [registration].[created_at] >= ? AND [registration].[created_at] <= ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, dateStart);
            stm.setTimestamp(2, dateEnd);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("image"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));
                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.set_active(rs.getBoolean("is_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());
                String key = sdf.format(new java.util.Date(registration.getCreated_at().getTime()));
                if (maps.get(key) != null) {
                    maps.get(key).add(registration);
                } else {
                    ArrayList<Registration> registrations = new ArrayList<>();
                    registrations.add(registration);
                    maps.put(key, registrations);
                }
            }

            return maps;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Registration get(int id) {
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        String sql = "SELECT [registration].[id]\n"
                + "      ,[registration].[status]\n"
                + "      ,[registration].[total_cost]\n"
                + "      ,[registration].[valid_from]\n"
                + "      ,[registration].[valid_to]\n"
                + "      ,[registration].[created_at]\n"
                + "      ,[registration].[subjectid]\n"
                + "      ,[registration].[userid]\n"
                + "      ,[registration].[customerid]\n"
                + "      ,[registration].[update_by]\n"
                + "      ,[registration].[price_packageid]\n"
                + "      ,[subject].[name]\n "
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email] as 'user_email'\n"
                + "      ,[user].[phone] as 'user_phone'\n"
                + "      ,[user].[first_name] as 'user_first_name'\n"
                + "      ,[user].[last_name] as 'user_last_name'\n"
                + "      ,[user].[gender] as 'user_gender'\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "      ,[customer].[fullname]\n"
                + "      ,[customer].[email] as 'customer_email'\n"
                + "      ,[customer].[gender] as 'customer_gender'\n"
                + "      ,[customer].[phone] as 'customer_phone'\n"
                + "      ,[price_package].[name]\n"
                + "      ,[price_package].[duration]\n"
                + "      ,[price_package].[list_price]\n"
                + "      ,[price_package].[sale_price]\n"
                + "      ,[price_package].[status] as 'price_package_status'\n"
                + "      ,[price_package].[description]as 'price_package_description'\n"
                + "  FROM [registration]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [registration].[subjectid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [registration].[userid]\n"
                + "  LEFT JOIN [customer] ON [customer].[id] = [registration].[customerid]\n"
                + "  LEFT JOIN [price_package] ON [price_package].[id] = [registration].[price_packageid]\n"
                + "  WHERE[registration].[id] = ?";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration();

                registration.setId(rs.getInt("id"));
                registration.setStatus(rs.getBoolean("status"));
                registration.setTotal_cost(rs.getDouble("total_cost"));
                registration.setValid_from(rs.getTimestamp("valid_from"));
                registration.setValid_to(rs.getTimestamp("valid_to"));
                registration.setCreated_at(rs.getTimestamp("created_at"));
                registration.setSubjectId(rs.getInt("subjectid"));
                registration.setUserId(rs.getInt("userid"));
                registration.setCustomerId(rs.getInt("customerid"));
                registration.setUpdate_by(rs.getInt("update_by"));
                registration.setPriceId(rs.getInt("price_packageid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("image"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));
                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                registration.setSubject(subject);
                registration.setSubjectId(subject.getId());

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("user_email"));
                user.setPhone(rs.getString("user_phone"));
                user.setFirst_name(rs.getString("user_first_name"));
                user.setLast_name(rs.getString("user_last_name"));
                user.setGender(rs.getBoolean("user_gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.set_active(rs.getBoolean("is_active"));
                registration.setUser(user);
                registration.setUserId(user.getId());

                Customer customer = new Customer();
                customer.setFullname(rs.getString("fullname"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setGender(rs.getBoolean("customer_gender"));
                customer.setPhone(rs.getString("customer_phone"));
                registration.setCustomer(customer);
                registration.setCustomerId(customer.getId());

                PackagePrice package_price = new PackagePrice();
                package_price.setName(rs.getString("name"));
                package_price.setDuration(rs.getInt("duration"));
                package_price.setList_price(rs.getDouble("list_price"));
                package_price.setSale_price(rs.getDouble("sale_price"));
                package_price.setStatus(rs.getBoolean("price_package_status"));
                package_price.setDescription(rs.getString("price_package_description"));
                registration.setPrice(package_price);
                registration.setPriceId(package_price.getId());

                return registration;
            }

        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Registration insert(Registration registration) {
        String sql = "INSERT INTO [registration]\n"
                + "           ([status]\n"
                + "           ,[total_cost]\n"
                + "           ,[valid_from]\n"
                + "           ,[valid_to]\n"
                + "           ,[created_at]\n"
                + "           ,[subjectid]\n"
                + "           ,[userid]\n"
                + "           ,[customerid]\n"
                + "           ,[update_by]\n"
                + "           ,[price_packageid])\n  "
                + "   VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setBoolean(1, registration.isStatus());
            stm.setDouble(2, registration.getTotal_cost());
            stm.setTimestamp(3, registration.getValid_from());
            stm.setTimestamp(4, registration.getValid_to());
            stm.setTimestamp(5, registration.getCreated_at());
            stm.setInt(6, registration.getSubjectId());
            if (registration.getUser() != null) {
                stm.setInt(7, registration.getUserId());
            } else {
                stm.setNull(7, java.sql.Types.INTEGER);
            }

            if (registration.getCustomer()!= null) {
                stm.setInt(8, registration.getCustomerId());
            } else {
                stm.setNull(8, java.sql.Types.INTEGER);
            }

            if (registration.getUpdateBy() != null) {
                stm.setInt(9, registration.getUpdate_by());
            } else {
                stm.setNull(9, java.sql.Types.INTEGER);
            }

            stm.setInt(10, registration.getPriceId());

            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                registration.setId(id);
            }
            return registration;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void updatePrice(Registration registration) {
        String sql = "UPDATE [registration]\n"
                + "   SET [total_cost] = ?\n"
                + "      ,[price_packageid] = ?\n"
                + " WHERE [registration].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setDouble(1, registration.getPrice().getSale_price());
            stm.setInt(2, registration.getPriceId());
            stm.setInt(3, registration.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Registration registration) {
        String sql = "UPDATE [registration]\n"
                + "   SET [status] = ?\n"
                + "      ,[total_cost] = ?\n"
                + "      ,[valid_from] = ?\n"
                + "      ,[valid_to] = ?\n"
                + "      ,[created_at] = ?\n"
                + "      ,[subjectid] = ?\n"
                + "      ,[userid] = ?\n"
                + "      ,[customerid] = ?\n"
                + "      ,[update_by] = ?\n"
                + "      ,[price_packageid] = ?\n"
                + " WHERE [registration].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, registration.isStatus());
            stm.setDouble(2, registration.getTotal_cost());
            stm.setTimestamp(3, registration.getValid_from());
            stm.setTimestamp(4, registration.getValid_to());
            stm.setTimestamp(5, registration.getCreated_at());
            stm.setInt(6, registration.getSubjectId());
            stm.setInt(7, registration.getUserId());
            stm.setInt(8, registration.getCustomerId());
            stm.setInt(9, registration.getUpdate_by());
            stm.setInt(10, registration.getPriceId());
            stm.setInt(11, registration.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void updateStatus(Registration registration) {
        String sql = "UPDATE [registration]\n"
                + "   SET [status] = ?\n"
                + " WHERE [registration].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, registration.isStatus());
            stm.setInt(2, registration.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM [registration]\n"
                    + " WHERE [registration].[id]=?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
