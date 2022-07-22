/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.quizzes;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.quizzes.Quizzes;
import com.se1617.learning.model.quizzes.QuizzesDimension;
import com.se1617.learning.model.quizzes.SettingQuizzes;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class QuizzesDBContext extends DBContext<Quizzes> {

    @Override
    public ArrayList<Quizzes> list() {
        ArrayList<Quizzes> quizzes = new ArrayList<>();
        String sql = "SELECT [quizzes].[id]\n"
                + "      ,[quizzes].[name]\n"
                + "      ,[quizzes].[duration]\n"
                + "      ,[quizzes].[pass_rate]\n"
                + "      ,[quizzes].[subjectid]\n"
                + "      ,[quizzes].[typeid]\n"
                + "      ,[quizzes].[levelid]\n"
                + "      ,[quizzes].[description]\n"
                + "	  ,[subject].[id] as 'subject_id'\n"
                + "      ,[subject].[name] 'subject_name'\n"
                + "      ,[subject].[description] 'subject_description'\n"
                + "      ,[subject].[image] 'subject_image'\n"
                + "      ,[subject].[status] 'subject_status'\n"
                + "      ,[subject].[featured] 'subject_featured'\n"
                + "      ,[subject].[categoryid] 'subject_categoryid'\n"
                + "      ,[subject].[userid] 'subject_userid'\n"
                + "      ,[subject].[created_at] 'subject_create'\n"
                + "	 ,[subject].[updated_at] 'subject_update'\n"
                + "	 ,[type].[id] as 'type_id'\n"
                + "      ,[type].[type] as 'type_type'\n"
                + "      ,[type].[value] as 'type_value'\n"
                + "      ,[type].[order] as 'type_order'\n"
                + "      ,[type].[status] as 'type_status'\n"
                + "      ,[type].[description] as 'type_description'\n"
                + "      ,[type].[parent] as 'type_parent'\n"
                + "      ,[level].[id] as 'level_id'\n"
                + "      ,[level].[type] as 'level_type'\n"
                + "      ,[level].[value] as 'level_value'\n"
                + "      ,[level].[order] as 'level_order'\n"
                + "      ,[level].[status] as 'level_status'\n"
                + "      ,[level].[description] as 'level_description'\n"
                + "      ,[level].[parent] as 'level_parent'  \n"
                + "  FROM [quizzes]\n"
                + "  INNER JOIN [subject] on [subject].[id] = [quizzes].[subjectid]\n"
                + "  INNER JOIN [setting] as [type] on [type].[id] = [quizzes].[typeid]\n"
                + "  INNER JOIN [setting] as [level] on [level].[id] = [quizzes].[levelid]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quizzes quizze = new Quizzes();
                quizze.setId(rs.getInt("id"));
                quizze.setName(rs.getString("name"));
                quizze.setDescription(rs.getString("description"));
                quizze.setDuration(rs.getInt("duration"));
                quizze.setPass_rate(rs.getInt("pass_rate"));
                quizze.setSubjectId(rs.getInt("subjectid"));
                quizze.setTypeId(rs.getInt("typeid"));
                quizze.setLevelId(rs.getInt("levelid"));
                quizze.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setUserId(rs.getInt("subject_userid"));
                subject.setCategoryId(rs.getInt("subject_categoryid"));
                quizze.setSubject(subject);

                Setting type = new Setting();
                type.setId(rs.getInt("type_id"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setOrder(rs.getInt("type_order"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setDescription(rs.getString("type_description"));
                type.setParent(rs.getInt("type_parent"));
                quizze.setType(type);

                Setting level = new Setting();
                level.setId(rs.getInt("level_id"));
                level.setType(rs.getString("level_type"));
                level.setValue(rs.getString("level_value"));
                level.setStatus(rs.getBoolean("level_status"));
                level.setDescription(rs.getString("level_description"));
                level.setParent(rs.getInt("level_parent"));
                quizze.setLevel(level);

                quizzes.add(quizze);

            }
            return quizzes;
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Quizzes get(int id) {
        String sql = "SELECT [quizzes].[id]\n"
                + "      ,[quizzes].[name]\n"
                + "      ,[quizzes].[duration]\n"
                + "      ,[quizzes].[pass_rate]\n"
                + "      ,[quizzes].[subjectid]\n"
                + "      ,[quizzes].[typeid]\n"
                + "      ,[quizzes].[levelid]\n"
                + "      ,[quizzes].[description]\n"
                + "	  ,[subject].[id] as 'subject_id'\n"
                + "      ,[subject].[name] 'subject_name'\n"
                + "      ,[subject].[description] 'subject_description'\n"
                + "      ,[subject].[image] 'subject_image'\n"
                + "      ,[subject].[status] 'subject_status'\n"
                + "      ,[subject].[featured] 'subject_featured'\n"
                + "      ,[subject].[categoryid] 'subject_categoryid'\n"
                + "      ,[subject].[userid] 'subject_userid'\n"
                + "      ,[subject].[created_at] 'subject_create'\n"
                + "	 ,[subject].[updated_at] 'subject_update'\n"
                + "	 ,[type].[id] as 'type_id'\n"
                + "      ,[type].[type] as 'type_type'\n"
                + "      ,[type].[value] as 'type_value'\n"
                + "      ,[type].[order] as 'type_order'\n"
                + "      ,[type].[status] as 'type_status'\n"
                + "      ,[type].[description] as 'type_description'\n"
                + "      ,[type].[parent] as 'type_parent'\n"
                + "      ,[level].[id] as 'level_id'\n"
                + "      ,[level].[type] as 'level_type'\n"
                + "      ,[level].[value] as 'level_value'\n"
                + "      ,[level].[order] as 'level_order'\n"
                + "      ,[level].[status] as 'level_status'\n"
                + "      ,[level].[description] as 'level_description'\n"
                + "      ,[level].[parent] as 'level_parent'  \n"
                + "      ,[setting_quizzes].[id] as 'setting_quizzesid' \n"
                + "      ,[setting_quizzes].[total_question]\n"
                + "  FROM [quizzes]\n"
                + "  INNER JOIN [subject] on [subject].[id] = [quizzes].[subjectid]\n"
                + "  INNER JOIN [setting] as [type] on [type].[id] = [quizzes].[typeid]\n"
                + "  INNER JOIN [setting] as [level] on [level].[id] = [quizzes].[levelid]"
                + "  INNER JOIN [setting_quizzes] ON [setting_quizzes].quizzesid = [quizzes].[id]"
                + " WHERE [quizzes].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quizzes quizze = new Quizzes();
                quizze.setId(rs.getInt("id"));
                quizze.setName(rs.getString("name"));
                quizze.setDescription(rs.getString("description"));
                quizze.setDuration(rs.getInt("duration"));
                quizze.setPass_rate(rs.getInt("pass_rate"));
                quizze.setSubjectId(rs.getInt("subjectid"));
                quizze.setTypeId(rs.getInt("typeid"));
                quizze.setLevelId(rs.getInt("levelid"));
                quizze.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setUserId(rs.getInt("subject_userid"));
                subject.setCategoryId(rs.getInt("subject_categoryid"));
                quizze.setSubject(subject);

                Setting type = new Setting();
                type.setId(rs.getInt("type_id"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setOrder(rs.getInt("type_order"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setDescription(rs.getString("type_description"));
                type.setParent(rs.getInt("type_parent"));
                quizze.setType(type);

                Setting level = new Setting();
                level.setId(rs.getInt("level_id"));
                level.setType(rs.getString("level_type"));
                level.setValue(rs.getString("level_value"));
                level.setStatus(rs.getBoolean("level_status"));
                level.setDescription(rs.getString("level_description"));
                level.setParent(rs.getInt("level_parent"));
                quizze.setLevel(level);

                SettingQuizzes settingQuizzes = new SettingQuizzes();
                settingQuizzes.setQuizzesId(quizze.getId());
                settingQuizzes.setId(rs.getInt("setting_quizzesid"));
                settingQuizzes.setTotalQuestion(rs.getInt("total_question"));

                QuizzesDimensionDBContext quizzesDimensionDB = new QuizzesDimensionDBContext();
                ArrayList<QuizzesDimension> quizzesDimensions = quizzesDimensionDB.findByQuizzesId(settingQuizzes.getId());
                settingQuizzes.setQuizzesDimensions(quizzesDimensions);

                quizze.setSettingQuizzes(settingQuizzes);

                return quizze;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Quizzes insert(Quizzes model) {
        String sql = "INSERT INTO [quizzes]\n"
                + "           ([name]\n"
                + "           ,[duration]\n"
                + "           ,[pass_rate]\n"
                + "           ,[subjectid]\n"
                + "           ,[typeid]\n"
                + "           ,[levelid]\n"
                + "           ,[description])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getName());
            stm.setInt(2, model.getDuration());
            stm.setInt(3, model.getPass_rate());
            stm.setInt(4, model.getSubjectId());
            stm.setInt(5, model.getTypeId());
            stm.setInt(6, model.getLevelId());
            stm.setString(7, model.getDescription());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
            }
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;

    }

    public SettingQuizzes insertSetting(SettingQuizzes model) {
        String sql = "INSERT INTO [setting_quizzes]\n"
                + "           ([total_question]\n"
                + "           ,[quizzesid])\n"
                + "     VALUES\n"
                + "           (?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setInt(1, model.getTotalQuestion());
            stm.setInt(2, model.getQuizzesId());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
            }
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;

    }
    
    
     public void updateSetting(SettingQuizzes model) {
        String sql = "UPDATE [setting_quizzes]\n"
                + "         SET [total_question] = ?\n"
                + "     WHERE [setting_quizzes].quizzesid = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getTotalQuestion());
            stm.setInt(2, model.getQuizzesId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void update(Quizzes model) {
        String sql = "UPDATE [quizzes]\n"
                + "         SET [name] = ?\n"
                + "           ,[duration] = ?\n"
                + "           ,[pass_rate] = ?\n"
                + "           ,[subjectid] = ?\n"
                + "           ,[typeid] = ?\n"
                + "           ,[levelid] = ?\n"
                + "           ,[description] = ?"
                + "   WHERE [quizzes].id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setInt(2, model.getDuration());
            stm.setInt(3, model.getPass_rate());
            stm.setInt(4, model.getSubjectId());
            stm.setInt(5, model.getTypeId());
            stm.setInt(6, model.getLevelId());
            stm.setString(7, model.getDescription());
            stm.setInt(8, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [quizzes]\n"
                    + "      WHERE [quizzes].[id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultPageable searchAndSortAndFilterMulti(String search, Pageable pageable) {
        ArrayList<Quizzes> quizzes = new ArrayList<>();
        ResultPageable<Quizzes> resultPageable = new ResultPageable<>();
        String sql_query_data = "SELECT * FROM (SELECT [quizzes].[id]\n"
                + "      ,[quizzes].[name]\n"
                + "      ,[quizzes].[duration]\n"
                + "      ,[quizzes].[pass_rate]\n"
                + "      ,[quizzes].[subjectid]\n"
                + "      ,[quizzes].[typeid]\n"
                + "      ,[quizzes].[levelid]\n"
                + "      ,[quizzes].[description]\n"
                + "	 ,[subject].[id] as 'subject_id'\n"
                + "      ,[subject].[name] 'subject_name'\n"
                + "      ,[subject].[description] 'subject_description'\n"
                + "      ,[subject].[image] 'subject_image'\n"
                + "      ,[subject].[status] 'subject_status'\n"
                + "      ,[subject].[featured] 'subject_featured'\n"
                + "      ,[subject].[categoryid] 'subject_categoryid'\n"
                + "      ,[subject].[userid] 'subject_userid'\n"
                + "      ,[subject].[created_at] 'subject_create'\n"
                + "	 ,[subject].[updated_at] 'subject_update'\n"
                + "	 ,[type].[id] as 'type_id'\n"
                + "      ,[type].[type] as 'type_type'\n"
                + "      ,[type].[value] as 'type_value'\n"
                + "      ,[type].[order] as 'type_order'\n"
                + "      ,[type].[status] as 'type_status'\n"
                + "      ,[type].[description] as 'type_description'\n"
                + "      ,[type].[parent] as 'type_parent'\n"
                + "      ,[level].[id] as 'level_id'\n"
                + "      ,[level].[type] as 'level_type'\n"
                + "      ,[level].[value] as 'level_value'\n"
                + "      ,[level].[order] as 'level_order'\n"
                + "      ,[level].[status] as 'level_status'\n"
                + "      ,[level].[description] as 'level_description'\n"
                + "      ,[level].[parent] as 'level_parent'  \n"
                + "      ,[setting_quizzes].[id] as 'setting_quizzesid' \n"
                + "      ,[setting_quizzes].[total_question]\n";

        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";

        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("level")) {
                    sql_query_data += " [level].[value] " + val + ",";
                } else if (key.equalsIgnoreCase("subject")) {
                    sql_query_data += " [subject].[name] " + val + ",";
                } else if (key.equalsIgnoreCase("type")) {
                    sql_query_data += " [type].[value]  " + val + ",";
                } else if (key.equalsIgnoreCase("pass")) {
                    sql_query_data += " [quizzes].[pass_rate]  " + val + ",";
                } else if (key.equalsIgnoreCase("total")) {
                    sql_query_data += " [setting_quizzes].[total_question] " + val + ",";
                } else {
                    sql_query_data += " [quizzes].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [quizzes].[id] DESC ";
        }

        sql_query_data += " ) as row_index\n";

        sql_query_data += "  FROM [quizzes]\n"
                + "  INNER JOIN [subject] on [subject].[id] = [quizzes].[subjectid]\n"
                + "  INNER JOIN [setting] as [type] on [type].[id] = [quizzes].[typeid]\n"
                + "  INNER JOIN [setting] as [level] on [level].[id] = [quizzes].[levelid]"
                + "  INNER JOIN [setting_quizzes] ON [setting_quizzes].quizzesid = [quizzes].[id]"
                + "  WHERE [quizzes].[name] LIKE ? ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            if (key.equalsIgnoreCase("level")) {
                key = " [level].[id] ";
            } else if (key.equalsIgnoreCase("subject")) {
                key = " [subject].[id] ";
            } else if (key.equalsIgnoreCase("type")) {
                key = " [type].[id] ";
            }
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }

        sql_query_data += " ) [quizzes]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT(*) as size FROM [quizzes]\n"
                + "  INNER JOIN [subject] on [subject].[id] = [quizzes].[subjectid]\n"
                + "  INNER JOIN [setting] as [type] on [type].[id] = [quizzes].[typeid]\n"
                + "  INNER JOIN [setting] as [level] on [level].[id] = [quizzes].[levelid]"
                + "  WHERE [quizzes].[name] LIKE ? ";
        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_all_data += " AND ( ";
            if (key.equalsIgnoreCase("level")) {
                key = " [level].[id] ";
            } else if (key.equalsIgnoreCase("subject")) {
                key = " [subject].[id] ";
            } else if (key.equalsIgnoreCase("type")) {
                key = " [type].[id] ";
            }
            sql_count_all_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_count_all_data += ",?";
            }
            sql_count_all_data += ") ) ";
        }
        System.out.println(sql_query_data);
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        stm.setString(count + 2, val.get(i));
                        count++;
                    }
                }
                stm.setInt(count - 1 + 3, pageable.getPageIndex());
                stm.setInt(count - 1 + 4, pageable.getPageSize());
                stm.setInt(count - 1 + 5, pageable.getPageIndex());
                stm.setInt(count - 1 + 6, pageable.getPageSize());
            } else {
                stm.setInt(2, pageable.getPageIndex());
                stm.setInt(3, pageable.getPageSize());
                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Quizzes quizze = new Quizzes();
                quizze.setId(rs.getInt("id"));
                quizze.setName(rs.getString("name"));
                quizze.setDescription(rs.getString("description"));
                quizze.setDuration(rs.getInt("duration"));
                quizze.setPass_rate(rs.getInt("pass_rate"));
                quizze.setSubjectId(rs.getInt("subjectid"));
                quizze.setTypeId(rs.getInt("typeid"));
                quizze.setLevelId(rs.getInt("levelid"));
                quizze.setDescription(rs.getString("description"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setName(rs.getString("subject_name"));
                subject.setDescription(rs.getString("subject_description"));
                subject.setStatus(rs.getBoolean("subject_image"));
                subject.setFeatured(rs.getBoolean("subject_status"));
                subject.setImage(rs.getString("subject_featured"));
                subject.setCreated_at(rs.getTimestamp("subject_create"));
                subject.setUpdated_at(rs.getTimestamp("subject_update"));
                subject.setUserId(rs.getInt("subject_userid"));
                subject.setCategoryId(rs.getInt("subject_categoryid"));
                quizze.setSubject(subject);

                Setting type = new Setting();
                type.setId(rs.getInt("type_id"));
                type.setType(rs.getString("type_type"));
                type.setValue(rs.getString("type_value"));
                type.setOrder(rs.getInt("type_order"));
                type.setStatus(rs.getBoolean("type_status"));
                type.setDescription(rs.getString("type_description"));
                type.setParent(rs.getInt("type_parent"));
                quizze.setType(type);

                Setting level = new Setting();
                level.setId(rs.getInt("level_id"));
                level.setType(rs.getString("level_type"));
                level.setValue(rs.getString("level_value"));
                level.setStatus(rs.getBoolean("level_status"));
                level.setDescription(rs.getString("level_description"));
                level.setParent(rs.getInt("level_parent"));
                quizze.setLevel(level);

                SettingQuizzes settingQuizzes = new SettingQuizzes();
                settingQuizzes.setId(rs.getInt("setting_quizzesid"));
                settingQuizzes.setTotalQuestion(rs.getInt("total_question"));
                quizze.setSettingQuizzes(settingQuizzes);

                quizzes.add(quizze);
            }
            resultPageable.setList(quizzes);
            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        stm.setString(count + 2, val.get(i));
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
            Logger.getLogger(QuizzesDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
