/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.subject;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Lesson;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class SubjectDBContext extends DBContext<Subject> {

    public ResultPageable<Subject> listByUseridPageable(int userId, String search, Pageable pageable) {
        ResultPageable<Subject> resultPageable = new ResultPageable<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "      ,[subject].[userid]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n";
        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";

        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("author")) {
                    sql_query_data += " [user].[first_name] " + val + ",";
                    sql_query_data += " [user].[last_name] " + val + ",";
                } else if (key.equalsIgnoreCase("category")) {
                    sql_query_data += " [setting].[value] " + val + ",";
                } else {
                    sql_query_data += " [subject].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [subject].[id] DESC";
        }

        sql_query_data += " ) as row_index\n";
        sql_query_data += "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND [subject].[userid] = ? AND ([subject].[name] LIKE ? OR [subject].[description] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            if (key.equalsIgnoreCase("category")) {
                key = " [subject].[categoryid] ";
            } else {
                key = " [subject].[" + key + "] ";
            }
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }

        sql_query_data += ") [subject]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        System.err.println(sql_query_data);

        String sql_count_all_data = "SELECT COUNT([subject].[id]) as size FROM [subject]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND [subject].[userid] = ? AND ([subject].[name] LIKE ? OR [subject].[description] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_all_data += " AND ( ";
            if (key.equalsIgnoreCase("category")) {
                key = " [subject].[categoryid] ";
            } else {
                key = " [subject].[" + key + "] ";
            }
            sql_count_all_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_count_all_data += ",?";
            }
            sql_count_all_data += ") ) ";
        }

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setInt(1, userId);
            stm.setString(2, "%" + search + "%");
            stm.setString(3, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 4, isStatus);
                        }
                        if (key.equalsIgnoreCase("featured")) {
                            String featureParams = val.get(i);
                            boolean isFeatured = featureParams != null && !featureParams.isEmpty() && featureParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 4, isFeatured);
                        } else {
                            stm.setString(count + 4, val.get(i));
                        }
                        count++;
                    }
                }
                stm.setInt(count - 1 + 5, pageable.getPageIndex());
                stm.setInt(count - 1 + 6, pageable.getPageSize());
                stm.setInt(count - 1 + 7, pageable.getPageIndex());
                stm.setInt(count - 1 + 8, pageable.getPageSize());
            } else {
                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
                stm.setInt(6, pageable.getPageIndex());
                stm.setInt(7, pageable.getPageSize());
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                subjects.add(subject);
            }
            resultPageable.setList(subjects);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setInt(1, userId);
            stm.setString(2, "%" + search + "%");
            stm.setString(3, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 4, isStatus);
                        }
                        if (key.equalsIgnoreCase("featured")) {
                            String featureParams = val.get(i);
                            boolean isFeatured = featureParams != null && !featureParams.isEmpty() && featureParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 4, isFeatured);
                        } else {
                            stm.setString(count + 4, val.get(i));
                        }
                        count++;
                    }
                }

            }
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void create(Subject subject) {
        String sql = "INSERT INTO [subject]\n"
                + "           ([name]\n"
                + "           ,[description]\n"
                + "           ,[status]\n"
                + "           ,[featured]\n"
                + "           ,[categoryid]\n"
                + "           ,[image]\n"
                + "           ,[userid])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, subject.getName());
            stm.setString(2, subject.getDescription());
            stm.setBoolean(3, subject.isStatus());
            stm.setBoolean(4, subject.isFeatured());
            stm.setInt(5, subject.getCategory().getId());
            stm.setString(6, subject.getImage());
            stm.setInt(7, subject.getUserId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultPageable<Subject> listPageable(Pageable pageable) {
        ResultPageable<Subject> resultPageable = new ResultPageable<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [subject].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT') [subject]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT([subject].[id]) as size FROM [subject]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setInt(1, pageable.getPageIndex());
            stm.setInt(2, pageable.getPageSize());
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                subjects.add(subject);
            }
            resultPageable.setList(subjects);

            stm = connection.prepareCall(sql_count_all_data);
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<String, ArrayList<Subject>> dataByDateMonthCreate(Timestamp dateStart, Timestamp dateEnd) {
        Map<String, ArrayList<Subject>> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String sql = "SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND [subject].[created_at] >= ? AND [subject].[created_at] <= ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, dateStart);
            stm.setTimestamp(2, dateEnd);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }
                String key = sdf.format(new java.util.Date(subject.getCreated_at().getTime()));
                if (maps.get(key) != null) {
                    maps.get(key).add(subject);
                } else {
                    ArrayList<Subject> subjects = new ArrayList<>();
                    subjects.add(subject);
                    maps.put(key, subjects);
                }
            }
            return maps;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<String, ArrayList<Subject>> dataByDateMonthUpdate(Timestamp dateStart, Timestamp dateEnd) {
        Map<String, ArrayList<Subject>> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String sql = "SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND [subject].[updated_at] >= ? AND [subject].[updated_at] <= ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, dateStart);
            stm.setTimestamp(2, dateEnd);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }
                String key = sdf.format(new java.util.Date(subject.getUpdated_at().getTime()));
                if (maps.get(key) != null) {
                    maps.get(key).add(subject);
                } else {
                    ArrayList<Subject> subjects = new ArrayList<>();
                    subjects.add(subject);
                    maps.put(key, subjects);
                }
            }
            return maps;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Subject> list() {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT'";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Subject get(int id) {
        LessonDBContext lessonDB = new LessonDBContext();
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        String sql = "SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND [subject].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                ArrayList<Lesson> lessons = lessonDB.findByActiveSubjectId(subject.getId());
                if (lessons != null) {
                    subject.setLessons(lessons);
                } else {
                    subject.setLessons(new ArrayList<Lesson>());
                }

                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
     public Subject getWithUser(int subjectId, int userId) {
        LessonDBContext lessonDB = new LessonDBContext();
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        String sql = "SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND [subject].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                ArrayList<Lesson> lessons = lessonDB.findByActiveSubjectIdAndUserId(subject.getId(), userId);
                if (lessons != null) {
                    subject.setLessons(lessons);
                } else {
                    subject.setLessons(new ArrayList<Lesson>());
                }

                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Subject insert(Subject subject
    ) {
        String sql = "INSERT INTO [subject]\n"
                + "           ([name]\n"
                + "           ,[description]\n"
                + "           ,[status]\n"
                + "           ,[featured]\n"
                + "           ,[categoryid]\n"
                + "           ,[image]\n"
                + "           ,[userid]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, subject.getName());
            stm.setString(2, subject.getDescription());
            stm.setBoolean(3, subject.isStatus());
            stm.setBoolean(4, subject.isFeatured());
            stm.setInt(5, subject.getCategory().getId());
            stm.setString(6, subject.getImage());
            stm.setInt(7, subject.getUserId());
            stm.setTimestamp(8, subject.getCreated_at());
            stm.setTimestamp(9, subject.getUpdated_at());
            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                subject.setId(rs.getInt(1));
            }
            return subject;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Subject model
    ) {
        String sql = "UPDATE [subject]\n"
                + "   SET [name] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[featured] = ?\n"
                + "      ,[categoryid] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[created_at] = ?\n"
                + "      ,[updated_at] = ?\n"
                + " WHERE [subject].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setString(2, model.getDescription());
            stm.setBoolean(3, model.isStatus());
            stm.setBoolean(4, model.isFeatured());
            stm.setInt(5, model.getCategory().getId());
            stm.setString(6, model.getImage());
            stm.setTimestamp(7, model.getCreated_at());
            stm.setTimestamp(8, model.getUpdated_at());
            stm.setInt(9, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id
    ) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM [subject]\n"
                    + "      WHERE [subject].[id]=?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultPageable<Subject> listBySearchBySortByFilter(String search, Pageable pageable) {
        ResultPageable<Subject> resultPageable = new ResultPageable<>();
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql_query_data = "SELECT * FROM(SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n";
        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";

        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("author")) {
                    sql_query_data += " [user].[first_name] " + val + ",";
                    sql_query_data += " [user].[last_name] " + val + ",";
                } else if (key.equalsIgnoreCase("category")) {
                    sql_query_data += " [setting].[value] " + val + ",";
                } else {
                    if (key.equalsIgnoreCase("created") || key.equalsIgnoreCase("updated")) {
                        key = key + "_at";
                    }
                    sql_query_data += " [subject].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [subject].[id] DESC";
        }

        sql_query_data += " ) as row_index\n";
        sql_query_data += "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND ([subject].[name] LIKE ? OR [subject].[description] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            if (key.equalsIgnoreCase("category")) {
                key = " [subject].[categoryid] ";
            } else {
                key = " [subject].[" + key + "] ";
            }
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }

        sql_query_data += ") [subject]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        System.err.println(sql_query_data);

        String sql_count_all_data = "SELECT COUNT([subject].[id]) as size FROM [subject]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND ([subject].[name] LIKE ? OR [subject].[description] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_all_data += " AND ( ";
            if (key.equalsIgnoreCase("category")) {
                key = " [subject].[categoryid] ";
            } else {
                key = " [subject].[" + key + "] ";
            }
            sql_count_all_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_count_all_data += ",?";
            }
            sql_count_all_data += ") ) ";
        }

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isStatus);
                        } else if (key.equalsIgnoreCase("featured")) {
                            String featureParams = val.get(i);
                            boolean isFeatured = featureParams != null && !featureParams.isEmpty() && featureParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isFeatured);
                        } else {
                            stm.setString(count + 3, val.get(i));
                        }
                        count++;
                    }
                }
                stm.setInt(count - 1 + 4, pageable.getPageIndex());
                stm.setInt(count - 1 + 5, pageable.getPageSize());
                stm.setInt(count - 1 + 6, pageable.getPageIndex());
                stm.setInt(count - 1 + 7, pageable.getPageSize());
            } else {
                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                subjects.add(subject);
            }
            resultPageable.setList(subjects);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isStatus);
                        }
                        if (key.equalsIgnoreCase("featured")) {
                            String featureParams = val.get(i);
                            boolean isFeatured = featureParams != null && !featureParams.isEmpty() && featureParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isFeatured);
                        } else {
                            stm.setString(count + 3, val.get(i));
                        }
                        count++;
                    }
                }

            }
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Subject> listCourseActiveBySearchBySortByFilter(String search, Pageable pageable) {
        ResultPageable<Subject> resultPageable = new ResultPageable<>();
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        ArrayList<Subject> subjects = new ArrayList<>();
        LessonDBContext lessonDB = new LessonDBContext();
        String sql_query_data = "SELECT * FROM(SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[parent]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n";
        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";

        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("author")) {
                    sql_query_data += " [user].[first_name] " + val + ",";
                    sql_query_data += " [user].[last_name] " + val + ",";
                } else if (key.equalsIgnoreCase("category")) {
                    sql_query_data += " [setting].[value] " + val + ",";
                } else {
                    if (key.equalsIgnoreCase("created") || key.equalsIgnoreCase("updated")) {
                        key = key + "_at";
                    }
                    sql_query_data += " [subject].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [subject].[id] DESC";
        }

        sql_query_data += " ) as row_index\n";
        sql_query_data += "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [subject].[status]=1 AND [setting].[type] = 'CATEGORY_SUBJECT' AND ([subject].[name] LIKE ? OR [subject].[description] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            if (key.equalsIgnoreCase("category")) {
                key = " [subject].[categoryid] ";
            } else if (key.equalsIgnoreCase("author")) {
                key = " [subject].[userid] ";
            } else {
                key = " [subject].[" + key + "] ";
            }
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }

        sql_query_data += ") [subject]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        System.err.println(sql_query_data);

        String sql_count_all_data = "SELECT COUNT([subject].[id]) as size FROM [subject]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' AND ([subject].[name] LIKE ? OR [subject].[description] LIKE ?) ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_all_data += " AND ( ";
            if (key.equalsIgnoreCase("category")) {
                key = " [subject].[categoryid] ";
            } else if (key.equalsIgnoreCase("author")) {
                key = " [subject].[userid] ";
            } else {
                key = " [subject].[" + key + "] ";
            }
            sql_count_all_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_count_all_data += ",?";
            }
            sql_count_all_data += ") ) ";
        }

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isStatus);
                        } else if (key.equalsIgnoreCase("featured")) {
                            String featureParams = val.get(i);
                            boolean isFeatured = featureParams != null && !featureParams.isEmpty() && featureParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isFeatured);
                        } else {
                            stm.setString(count + 3, val.get(i));
                        }
                        count++;
                    }
                }
                stm.setInt(count - 1 + 4, pageable.getPageIndex());
                stm.setInt(count - 1 + 5, pageable.getPageSize());
                stm.setInt(count - 1 + 6, pageable.getPageIndex());
                stm.setInt(count - 1 + 7, pageable.getPageSize());
            } else {
                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                ArrayList<Lesson> lessons = lessonDB.findByActiveSubjectId(subject.getId());
                if (lessons != null) {
                    subject.setLessons(lessons);
                } else {
                    subject.setLessons(new ArrayList<Lesson>());
                }

                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(subject.getId());
                if (prices != null) {
                    subject.setPackegePrices(prices);
                } else {
                    subject.setPackegePrices(new ArrayList<PackagePrice>());
                }

                subjects.add(subject);
            }
            resultPageable.setList(subjects);

            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isStatus);
                        }
                        if (key.equalsIgnoreCase("featured")) {
                            String featureParams = val.get(i);
                            boolean isFeatured = featureParams != null && !featureParams.isEmpty() && featureParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 3, isFeatured);
                        } else {
                            stm.setString(count + 3, val.get(i));
                        }
                        count++;
                    }
                }

            }
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Subject> searchSubject(String search, int pageIndex, int pageSize) {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM(SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [subject].[id] DESC) as row_index\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE  [subject].[name] LIKE ? ) [subject]"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setInt(2, pageIndex);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageIndex);
            stm.setInt(5, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Subject> searchSubject(int id, String search, int pageIndex, int pageSize) {
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM(SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [subject].[id] DESC) as row_index\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [subject].[userid] = ? AND [subject].[name] LIKE ? ) [subject]"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            stm.setInt(5, pageIndex);
            stm.setInt(6, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }

                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<User> getAllAuthorBySubject() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT distinct [user].[id]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[password]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject] INNER JOIN [user] ON [subject].[userid] = [user].[id]"
                + "  ORDER BY [user].[first_name] ASC";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setGender(rs.getBoolean("gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.set_active(rs.getBoolean("is_active"));
                user.setEnable(rs.getBoolean("enable"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public ResultPageable<Subject> listSubjectBySearchByFilterByPagination(String search) {
        ResultPageable<Subject> results = new ResultPageable<>();
        List<Subject> subjects = new ArrayList<>();
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();

        String sql = "SELECT top 10 [subject].[id] as sid,\n"
                + "       [subject].[name],\n"
                + "        [subject].[description],\n"
                + "        [subject].[image],\n"
                + "        [subject].[status],\n"
                + "       [subject].[featured],\n"
                + "       [subject].[categoryid],\n"
                + "       [subject].[created_at],\n"
                + "       [subject].[updated_at],\n"
                + "       [subject].[categoryid],\n"
                + "       [setting].[type],\n"
                + "       [setting].[value],\n"
                + "       [setting].[status] as 'setting_status',\n"
                + "       [setting].[order],\n"
                + "       [user].[id] as uid,\n"
                + "       [user].[username],\n"
                + "       [user].[password],\n"
                + "       [user].[email],\n"
                + "       [user].[phone],\n"
                + "       [user].[first_name],\n"
                + "       [user].[last_name],\n"
                + "       [user].[gender],\n"
                + "       [user].[avatar],\n"
                + "       [user].[is_staff],\n"
                + "       [user].[is_super],\n"
                + "       [user].[enable],\n"
                + "       [user].[is_active]\n"
                + "       FROM [subject]\n"
                + "       LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "       LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "	  WHERE [subject].[status] = 1\n";
        if (search != null || !search.trim().equals("")) {
            sql += "and [subject].[name] LIKE ?";
        }
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            if (search != null || !search.trim().equals("")) {
                stm.setString(1, "%" + search + "%");
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("sid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                subject.setUserId(rs.getInt("uid"));
                User user = new User();
                user.setId(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setGender(rs.getBoolean("gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.set_active(rs.getBoolean("is_active"));
                user.setEnable(rs.getBoolean("enable"));
                subject.setUser(user);

                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(rs.getInt("sid"));
                if (prices != null) {
                    subject.setPackegePrices(prices);
                }
                subjects.add(subject);

            }
            results.setList(subjects);
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    public ArrayList<Subject> getFeatureSubjectsByCategory(int categoryid, int quantity) {
        ArrayList<Subject> subjectList = new ArrayList<>();
        LessonDBContext lessonDB = new LessonDBContext();
        try {
            String sql = "SELECT top " + quantity + " [subject].[id] as sid,\n"
                    + "       [subject].[name],\n"
                    + "        [subject].[description],\n"
                    + "             [subject].[image],\n"
                    + "        [subject].[status],\n"
                    + "       [subject].[featured],\n"
                    + "       [subject].[categoryid],\n"
                    + "       [subject].[created_at],\n"
                    + "       [subject].[updated_at],\n"
                    + "       [subject].[categoryid],\n"
                    + "       [setting].[type],\n"
                    + "       [setting].[value],\n"
                    + "       [setting].[status] as 'setting_status',\n"
                    + "       [setting].[order],\n"
                    + "       [user].[id] as uid,\n"
                    + "       [user].[username],\n"
                    + "       [user].[password],\n"
                    + "       [user].[email],\n"
                    + "       [user].[phone],\n"
                    + "       [user].[first_name],\n"
                    + "       [user].[last_name],\n"
                    + "       [user].[gender],\n"
                    + "       [user].[avatar],\n"
                    + "       [user].[is_staff],\n"
                    + "       [user].[is_super],\n"
                    + "       [user].[enable],\n"
                    + "       [user].[is_active]\n"
                    + "       FROM [subject]\n"
                    + "       INNER JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                    + "       INNER JOIN [user] ON [user].[id] = [subject].[userid]\n"
                    + "	   WHERE [subject].[featured] = 1 AND [subject].[categoryid] = ? AND [subject].[status] = 1\n"
                    + " ORDER BY NEWID()";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, categoryid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("sid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                subject.setUserId(rs.getInt("uid"));
                User user = new User();
                user.setId(rs.getInt("uid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setGender(rs.getBoolean("gender"));
                user.setAvatar(rs.getString("avatar"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.set_active(rs.getBoolean("is_active"));
                user.setEnable(rs.getBoolean("enable"));

                subject.setUser(user);
                subjectList.add(subject);
            }
            return subjectList;

        } catch (Exception ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Subject> getFeatedtSubject() {
        PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
        ArrayList<Subject> subjects = new ArrayList<>();
        String sql = "SELECT [subject].[id]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,[subject].[created_at]\n"
                + "      ,[subject].[updated_at]\n"
                + "	 ,[setting].[type]\n"
                + "	 ,[setting].[value]\n"
                + "	 ,[setting].[status] as 'setting_status'\n"
                + "	 ,[setting].[order]\n"
                + "      ,[user].[username]\n"
                + "      ,[user].[email]\n"
                + "      ,[user].[phone]\n"
                + "      ,[user].[first_name]\n"
                + "      ,[user].[last_name]\n"
                + "      ,[user].[gender]\n"
                + "      ,[user].[avatar]\n"
                + "      ,[user].[is_staff]\n"
                + "      ,[user].[is_super]\n"
                + "      ,[user].[enable]\n"
                + "      ,[user].[is_active]\n"
                + "  FROM [subject]"
                + "  LEFT JOIN [setting] ON [setting].[id] = [subject].[categoryid]\n"
                + "  LEFT JOIN [user] ON [user].[id] = [subject].[userid]\n"
                + "  WHERE [setting].[type] = 'CATEGORY_SUBJECT' and [subject].[featured] = 1";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("id"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCreated_at(rs.getTimestamp("created_at"));
                subject.setUpdated_at(rs.getTimestamp("updated_at"));

                Setting category = new Setting();
                category.setId(rs.getInt("categoryId"));
                category.setType(rs.getString("type"));
                category.setValue(rs.getString("value"));
                category.setStatus(rs.getBoolean("setting_status"));
                category.setOrder(rs.getInt("order"));
                subject.setCategory(category);

                if (rs.getString("userid") != null) {
                    User user = new User();
                    user.setId(rs.getInt("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setGender(rs.getBoolean("gender"));
                    user.set_active(rs.getBoolean("is_active"));
                    user.set_staff(rs.getBoolean("is_staff"));
                    user.set_super(rs.getBoolean("is_super"));
                    user.setEnable(rs.getBoolean("enable"));
                    subject.setUserId(user.getId());
                    subject.setUser(user);
                }
                ArrayList<PackagePrice> prices = packagePriceDBContext.findBySubjectIdAndActive(rs.getInt("id"));
                if (prices != null) {
                    subject.setPackegePrices(prices);
                }

                subjects.add(subject);
            }
            return subjects;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
