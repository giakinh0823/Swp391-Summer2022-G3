/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.question.Question;
import com.se1617.learning.model.question.QuestionUser;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.subject.Lesson;
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
public class QuestionDBContext extends DBContext<Question> {

    public ResultPageable<Question> searchAndSortAndFilterMulti(String search, Pageable pageable) {
        ResultPageable<Question> resultPageable = new ResultPageable<>();
        MediaDBContext mediaDB = new MediaDBContext();
        ArrayList<Question> questions = new ArrayList<>();
        String sql_query_data = "SELECT * FROM (SELECT [question].[id]\n"
                + "      ,[question].[content]\n"
                + "      ,[question].[status]\n"
                + "      ,[question].[is_multi]\n"
                + "      ,[question].[dimensionid]\n"
                + "      ,[question].[userid]\n"
                + "      ,[question].[levelid]\n"
                + "      ,[question].[lessonid]\n"
                + "      ,[question].[subjectid]\n"
                + "      ,[question].[content_html]\n"
                + "      ,[question].[explain]\n"
                + "	  ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_categoryid'\n"
                + "      ,[subject].[userid] as 'subject_userid'\n"
                + "      ,[subject].[created_at] as 'subject_created_at'\n"
                + "      ,[subject].[updated_at] as 'subject_updated_at'\n"
                + "	  ,[dimension].[name] as 'dimension_name'\n"
                + "      ,[dimension].[description] as 'dimension_description'\n"
                + "      ,[dimension].[typeid] as 'dimension_typeid'\n"
                + "	  ,[lesson].[name] as 'lesson_name'\n"
                + "      ,[lesson].[order] as 'lesson_order'\n"
                + "      ,[lesson].[status] as 'lesson_status'\n"
                + "      ,[lesson].[video] as 'lesson_video'\n"
                + "      ,[lesson].[content] as 'lesson_content'\n"
                + "      ,[lesson].[typeid] as 'lesson_typeid'\n"
                + "      ,[lesson].[topicid] as 'lesson_topicid'\n"
                + "      ,[lesson].[quizzesid] as 'lesson_quizzesid'\n"
                + "	  ,[setting].[type] as 'setting_type'\n"
                + "	  ,[setting].[status] as 'setting_status'\n"
                + "	  ,[setting].[value] as 'setting_value'\n"
                + "	  ,[setting].[description] as 'setting_description'\n";

        sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY ";

        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("level")) {
                    sql_query_data += " [setting].[value] " + val + ",";
                } else if (key.equalsIgnoreCase("subject")) {
                    sql_query_data += " [subject].[name] " + val + ",";
                } else if (key.equalsIgnoreCase("dimension")) {
                    sql_query_data += " [dimension].[name] " + val + ",";
                } else if (key.equalsIgnoreCase("lesson")) {
                    sql_query_data += " [lesson].[name] " + val + ",";
                } else if (key.equalsIgnoreCase("multi")) {
                    sql_query_data += " [question].[is_multi] " + val + ",";
                } else {
                    sql_query_data += " [question].[" + key + "] " + val + ",";
                }
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [question].[id] DESC";
        }

        sql_query_data += " ) as row_index\n";

        sql_query_data += "  FROM [question]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [question].[subjectid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [question].[dimensionid]\n"
                + "  INNER JOIN [lesson] ON [lesson].[id] = [question].[lessonid]\n"
                + "  INNER JOIN [setting] ON [setting].[id] = [question].[levelid]"
                + "  WHERE [question].[content] LIKE ? ";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            if (key.equalsIgnoreCase("level")) {
                key = " [question].[levelid] ";
            } else if (key.equalsIgnoreCase("subject")) {
                key = " [question].[subjectid] ";
            } else if (key.equalsIgnoreCase("lesson")) {
                key = " [question].[lessonid] ";
            } else if (key.equalsIgnoreCase("dimension")) {
                key = " [question].[dimensionid] ";
            } else if (key.equalsIgnoreCase("multi")) {
                key = " [question].[is_multi] ";
            } else {
                key = " [question].[" + key + "] ";
            }
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }

        sql_query_data += ") [question]\n"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        String sql_count_all_data = "SELECT COUNT(*) as size FROM [question]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [question].[subjectid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [question].[dimensionid]\n"
                + "  INNER JOIN [lesson] ON [lesson].[id] = [question].[lessonid]\n"
                + "  INNER JOIN [setting] ON [setting].[id] = [question].[levelid]"
                + "  WHERE [question].[content] LIKE ?";

        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_all_data += " AND ( ";
            if (key.equalsIgnoreCase("level")) {
                key = " [question].[levelid] ";
            } else if (key.equalsIgnoreCase("subject")) {
                key = " [question].[subjectid] ";
            } else if (key.equalsIgnoreCase("lesson")) {
                key = " [question].[lessonid] ";
            } else if (key.equalsIgnoreCase("dimension")) {
                key = " [question].[dimensionid] ";
            } else if (key.equalsIgnoreCase("multi")) {
                key = " [question].[is_multi] ";
            } else {
                key = " [question].[" + key + "] ";
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
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 2, isStatus);
                        } else if (key.equalsIgnoreCase("multi")) {
                            String multiParams = val.get(i);
                            boolean isMulti = multiParams != null && !multiParams.isEmpty() && multiParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 2, isMulti);
                        } else {
                            stm.setString(count + 2, val.get(i));
                        }
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
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getBoolean("status"));
                question.set_multi(rs.getBoolean("is_multi"));
                question.setDimensionId(rs.getInt("dimensionid"));
                question.setUserId(rs.getInt("userid"));
                question.setSubjectId(rs.getInt("subjectid"));
                question.setLevelId(rs.getInt("levelid"));
                question.setLessonId(rs.getInt("lessonid"));
                question.setContentHtml(rs.getString("content_html"));
                question.setExplain(rs.getString("explain"));

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_categoryid"));
                    subject.setUserId(rs.getInt("subject_userid"));
                    question.setSubject(subject);
                }

                if (rs.getString("setting_type") != null) {
                    Setting level = new Setting();
                    level.setId(rs.getInt("levelid"));
                    level.setType(rs.getString("setting_type"));
                    level.setValue(rs.getString("setting_value"));
                    level.setStatus(rs.getBoolean("setting_status"));
                    level.setDescription(rs.getString("setting_description"));
                    question.setLevel(level);
                }

                if (rs.getString("dimension_name") != null) {
                    Dimension dimension = new Dimension();
                    dimension.setId(rs.getInt("dimensionid"));
                    dimension.setName(rs.getString("dimension_name"));
                    dimension.setDescription(rs.getString("dimension_description"));
                    dimension.setTypeId(rs.getInt("dimension_typeid"));
                    question.setDimension(dimension);
                }

                if (rs.getString("lesson_name") != null) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("lessonid"));
                    lesson.setName(rs.getString("lesson_name"));
                    lesson.setOrder(rs.getInt("lesson_order"));
                    lesson.setStatus(rs.getBoolean("lesson_status"));
                    lesson.setVideo(rs.getString("lesson_video"));
                    lesson.setContent(rs.getString("lesson_content"));
                    lesson.setTypeid(rs.getInt("lesson_typeid"));
                    lesson.setTopicId(rs.getInt("lesson_topicid"));
                    lesson.setQuizzesid(rs.getInt("lesson_quizzesid"));
                    question.setLesson(lesson);
                }
                question.setMedia(mediaDB.getByQuestionId(question.getId()));
                questions.add(question);
            }
            resultPageable.setList(questions);
            stm = connection.prepareCall(sql_count_all_data);
            stm.setString(1, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 2, isStatus);
                        } else if (key.equalsIgnoreCase("multi")) {
                            String multiParams = val.get(i);
                            boolean isMulti = multiParams != null && !multiParams.isEmpty() && multiParams.equalsIgnoreCase("true");
                            stm.setBoolean(count + 2, isMulti);
                        } else {
                            stm.setString(count + 2, val.get(i));
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
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int countByDimension(int dimensionId) {
        String sql = "SELECT COUNT(*) as 'number'\n"
                + "  FROM [question]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [question].[subjectid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [question].[dimensionid]\n"
                + "  INNER JOIN [lesson] ON [lesson].[id] = [question].[lessonid]\n"
                + "  INNER JOIN [setting] ON [setting].[id] = [question].[levelid]"
                + "  WHERE [dimension].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, dimensionId);
            ResultSet rs = stm.executeQuery();
            int number = 0;
            if (rs.next()) {
                number = rs.getInt(1);
            }
            return number;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<Question> list() {
        MediaDBContext mediaDB = new MediaDBContext();
        ArrayList<Question> questions = new ArrayList<>();
        String sql = "SELECT [question].[id]\n"
                + "      ,[question].[content]\n"
                + "      ,[question].[status]\n"
                + "      ,[question].[is_multi]\n"
                + "      ,[question].[dimensionid]\n"
                + "      ,[question].[userid]\n"
                + "      ,[question].[levelid]\n"
                + "      ,[question].[lessonid]\n"
                + "      ,[question].[subjectid]\n"
                + "      ,[question].[content_html]\n"
                + "      ,[question].[explain]\n"
                + "	  ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_categoryid'\n"
                + "      ,[subject].[userid] as 'subject_userid'\n"
                + "      ,[subject].[created_at] as 'subject_created_at'\n"
                + "      ,[subject].[updated_at] as 'subject_updated_at'\n"
                + "	  ,[dimension].[name] as 'dimension_name'\n"
                + "      ,[dimension].[description] as 'dimension_description'\n"
                + "      ,[dimension].[typeid] as 'dimension_typeid'\n"
                + "	  ,[lesson].[name] as 'lesson_name'\n"
                + "      ,[lesson].[order] as 'lesson_order'\n"
                + "      ,[lesson].[status] as 'lesson_status'\n"
                + "      ,[lesson].[video] as 'lesson_video'\n"
                + "      ,[lesson].[content] as 'lesson_content'\n"
                + "      ,[lesson].[typeid] as 'lesson_typeid'\n"
                + "      ,[lesson].[topicid] as 'lesson_topicid'\n"
                + "      ,[lesson].[quizzesid] as 'lesson_quizzesid'\n"
                + "	  ,[setting].[type] as 'setting_type'\n"
                + "	  ,[setting].[status] as 'setting_status'\n"
                + "	  ,[setting].[value] as 'setting_value'\n"
                + "	  ,[setting].[description] as 'setting_description'\n"
                + "  FROM [question]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [question].[subjectid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [question].[dimensionid]\n"
                + "  INNER JOIN [lesson] ON [lesson].[id] = [question].[lessonid]\n"
                + "  INNER JOIN [setting] ON [setting].[id] = [question].[levelid]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getBoolean("status"));
                question.set_multi(rs.getBoolean("is_multi"));
                question.setDimensionId(rs.getInt("dimensionid"));
                question.setUserId(rs.getInt("userid"));
                question.setSubjectId(rs.getInt("subjectid"));
                question.setLevelId(rs.getInt("levelid"));
                question.setLessonId(rs.getInt("lessonid"));
                question.setContentHtml(rs.getString("content_html"));
                question.setExplain(rs.getString("explain"));

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_categoryid"));
                    subject.setUserId(rs.getInt("subject_userid"));
                    question.setSubject(subject);
                }

                if (rs.getString("setting_type") != null) {
                    Setting level = new Setting();
                    level.setId(rs.getInt("levelid"));
                    level.setType(rs.getString("setting_type"));
                    level.setValue(rs.getString("setting_value"));
                    level.setStatus(rs.getBoolean("setting_status"));
                    level.setDescription(rs.getString("setting_description"));
                    question.setLevel(level);
                }

                if (rs.getString("dimension_name") != null) {
                    Dimension dimension = new Dimension();
                    dimension.setId(rs.getInt("dimensionid"));
                    dimension.setName(rs.getString("dimension_name"));
                    dimension.setDescription(rs.getString("dimension_description"));
                    dimension.setTypeId(rs.getInt("dimension_typeid"));
                    question.setDimension(dimension);
                }

                if (rs.getString("lesson_name") != null) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("lessonid"));
                    lesson.setName(rs.getString("lesson_name"));
                    lesson.setOrder(rs.getInt("lesson_order"));
                    lesson.setStatus(rs.getBoolean("lesson_status"));
                    lesson.setVideo(rs.getString("lesson_video"));
                    lesson.setContent(rs.getString("lesson_content"));
                    lesson.setTypeid(rs.getInt("lesson_typeid"));
                    lesson.setTopicId(rs.getInt("lesson_topicid"));
                    lesson.setQuizzesid(rs.getInt("lesson_quizzesid"));
                    question.setLesson(lesson);
                }
                question.setMedia(mediaDB.getByQuestionId(question.getId()));
                questions.add(question);
            }
            return questions;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Question> findBySubjectAndDimension(int subjectId, int dimensionId, int top) {
        MediaDBContext mediaDB = new MediaDBContext();
        AnswerQuestionDBContext answerQuestionDB = new AnswerQuestionDBContext();
        ArrayList<Question> questions = new ArrayList<>();
        String sql = "SELECT TOP " + top + " [question].[id]\n"
                + "      ,[question].[content]\n"
                + "      ,[question].[status]\n"
                + "      ,[question].[is_multi]\n"
                + "      ,[question].[dimensionid]\n"
                + "      ,[question].[userid]\n"
                + "      ,[question].[levelid]\n"
                + "      ,[question].[lessonid]\n"
                + "      ,[question].[subjectid]\n"
                + "      ,[question].[content_html]\n"
                + "      ,[question].[explain]\n"
                + "	  ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_categoryid'\n"
                + "      ,[subject].[userid] as 'subject_userid'\n"
                + "      ,[subject].[created_at] as 'subject_created_at'\n"
                + "      ,[subject].[updated_at] as 'subject_updated_at'\n"
                + "	  ,[dimension].[name] as 'dimension_name'\n"
                + "      ,[dimension].[description] as 'dimension_description'\n"
                + "      ,[dimension].[typeid] as 'dimension_typeid'\n"
                + "	  ,[lesson].[name] as 'lesson_name'\n"
                + "      ,[lesson].[order] as 'lesson_order'\n"
                + "      ,[lesson].[status] as 'lesson_status'\n"
                + "      ,[lesson].[video] as 'lesson_video'\n"
                + "      ,[lesson].[content] as 'lesson_content'\n"
                + "      ,[lesson].[typeid] as 'lesson_typeid'\n"
                + "      ,[lesson].[topicid] as 'lesson_topicid'\n"
                + "      ,[lesson].[quizzesid] as 'lesson_quizzesid'\n"
                + "	  ,[setting].[type] as 'setting_type'\n"
                + "	  ,[setting].[status] as 'setting_status'\n"
                + "	  ,[setting].[value] as 'setting_value'\n"
                + "	  ,[setting].[description] as 'setting_description'\n"
                + "  FROM [question]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [question].[subjectid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [question].[dimensionid]\n"
                + "  INNER JOIN [lesson] ON [lesson].[id] = [question].[lessonid]\n"
                + "  INNER JOIN [setting] ON [setting].[id] = [question].[levelid]"
                + "  WHERE [question].[subjectid]= ? AND [question].[dimensionid] = ?"
                + "  ORDER BY NEWID()";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            stm.setInt(2, dimensionId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getBoolean("status"));
                question.set_multi(rs.getBoolean("is_multi"));
                question.setDimensionId(rs.getInt("dimensionid"));
                question.setUserId(rs.getInt("userid"));
                question.setSubjectId(rs.getInt("subjectid"));
                question.setLevelId(rs.getInt("levelid"));
                question.setLessonId(rs.getInt("lessonid"));
                question.setContentHtml(rs.getString("content_html"));
                question.setExplain(rs.getString("explain"));

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_categoryid"));
                    subject.setUserId(rs.getInt("subject_userid"));
                    question.setSubject(subject);
                }

                if (rs.getString("setting_type") != null) {
                    Setting level = new Setting();
                    level.setId(rs.getInt("levelid"));
                    level.setType(rs.getString("setting_type"));
                    level.setValue(rs.getString("setting_value"));
                    level.setStatus(rs.getBoolean("setting_status"));
                    level.setDescription(rs.getString("setting_description"));
                    question.setLevel(level);
                }

                if (rs.getString("dimension_name") != null) {
                    Dimension dimension = new Dimension();
                    dimension.setId(rs.getInt("dimensionid"));
                    dimension.setName(rs.getString("dimension_name"));
                    dimension.setDescription(rs.getString("dimension_description"));
                    dimension.setTypeId(rs.getInt("dimension_typeid"));
                    question.setDimension(dimension);
                }

                if (rs.getString("lesson_name") != null) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("lessonid"));
                    lesson.setName(rs.getString("lesson_name"));
                    lesson.setOrder(rs.getInt("lesson_order"));
                    lesson.setStatus(rs.getBoolean("lesson_status"));
                    lesson.setVideo(rs.getString("lesson_video"));
                    lesson.setContent(rs.getString("lesson_content"));
                    lesson.setTypeid(rs.getInt("lesson_typeid"));
                    lesson.setTopicId(rs.getInt("lesson_topicid"));
                    lesson.setQuizzesid(rs.getInt("lesson_quizzesid"));
                    question.setLesson(lesson);
                }
                question.setMedia(mediaDB.getByQuestionId(question.getId()));
                question.setAnswers(answerQuestionDB.findByQuestionid(question.getId()));
                questions.add(question);
            }
            return questions;
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Question get(int id) {
        MediaDBContext mediaDB = new MediaDBContext();
        AnswerQuestionDBContext answerQuestionDB = new AnswerQuestionDBContext();
        String sql = "SELECT [question].[id]\n"
                + "      ,[question].[content]\n"
                + "      ,[question].[status]\n"
                + "      ,[question].[is_multi]\n"
                + "      ,[question].[dimensionid]\n"
                + "      ,[question].[userid]\n"
                + "      ,[question].[levelid]\n"
                + "      ,[question].[lessonid]\n"
                + "      ,[question].[subjectid]\n"
                + "      ,[question].[content_html]\n"
                + "      ,[question].[explain]\n"
                + "	  ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_categoryid'\n"
                + "      ,[subject].[userid] as 'subject_userid'\n"
                + "      ,[subject].[created_at] as 'subject_created_at'\n"
                + "      ,[subject].[updated_at] as 'subject_updated_at'\n"
                + "	  ,[dimension].[name] as 'dimension_name'\n"
                + "      ,[dimension].[description] as 'dimension_description'\n"
                + "      ,[dimension].[typeid] as 'dimension_typeid'\n"
                + "	  ,[lesson].[name] as 'lesson_name'\n"
                + "      ,[lesson].[order] as 'lesson_order'\n"
                + "      ,[lesson].[status] as 'lesson_status'\n"
                + "      ,[lesson].[video] as 'lesson_video'\n"
                + "      ,[lesson].[content] as 'lesson_content'\n"
                + "      ,[lesson].[typeid] as 'lesson_typeid'\n"
                + "      ,[lesson].[topicid] as 'lesson_topicid'\n"
                + "      ,[lesson].[quizzesid] as 'lesson_quizzesid'\n"
                + "	  ,[setting].[type] as 'setting_type'\n"
                + "	  ,[setting].[status] as 'setting_status'\n"
                + "	  ,[setting].[value] as 'setting_value'\n"
                + "	  ,[setting].[description] as 'setting_description'\n"
                + "  FROM [question]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [question].[subjectid]\n"
                + "  INNER JOIN [dimension] ON [dimension].[id] = [question].[dimensionid]\n"
                + "  INNER JOIN [lesson] ON [lesson].[id] = [question].[lessonid]\n"
                + "  INNER JOIN [setting] ON [setting].[id] = [question].[levelid]"
                + "  WHERE [question].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setStatus(rs.getBoolean("status"));
                question.set_multi(rs.getBoolean("is_multi"));
                question.setDimensionId(rs.getInt("dimensionid"));
                question.setUserId(rs.getInt("userid"));
                question.setSubjectId(rs.getInt("subjectid"));
                question.setLevelId(rs.getInt("levelid"));
                question.setLessonId(rs.getInt("lessonid"));
                question.setContentHtml(rs.getString("content_html"));
                question.setExplain(rs.getString("explain"));

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_categoryid"));
                    subject.setUserId(rs.getInt("subject_userid"));
                    question.setSubject(subject);
                }

                if (rs.getString("setting_type") != null) {
                    Setting level = new Setting();
                    level.setId(rs.getInt("levelid"));
                    level.setType(rs.getString("setting_type"));
                    level.setValue(rs.getString("setting_value"));
                    level.setStatus(rs.getBoolean("setting_status"));
                    level.setDescription(rs.getString("setting_description"));
                    question.setLevel(level);
                }

                if (rs.getString("dimension_name") != null) {
                    Dimension dimension = new Dimension();
                    dimension.setId(rs.getInt("dimensionid"));
                    dimension.setName(rs.getString("dimension_name"));
                    dimension.setDescription(rs.getString("dimension_description"));
                    dimension.setTypeId(rs.getInt("dimension_typeid"));
                    question.setDimension(dimension);
                }

                if (rs.getString("lesson_name") != null) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("lessonid"));
                    lesson.setName(rs.getString("lesson_name"));
                    lesson.setOrder(rs.getInt("lesson_order"));
                    lesson.setStatus(rs.getBoolean("lesson_status"));
                    lesson.setVideo(rs.getString("lesson_video"));
                    lesson.setContent(rs.getString("lesson_content"));
                    lesson.setTypeid(rs.getInt("lesson_typeid"));
                    lesson.setTopicId(rs.getInt("lesson_topicid"));
                    lesson.setQuizzesid(rs.getInt("lesson_quizzesid"));
                    question.setLesson(lesson);
                }
                question.setAnswers(answerQuestionDB.findByQuestionid(question.getId()));
                question.setMedia(mediaDB.getByQuestionId(question.getId()));
                return question;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Question insert(Question model) {
        String sql = "INSERT INTO [question]\n"
                + "           ([content]\n"
                + "           ,[status]\n"
                + "           ,[is_multi]\n"
                + "           ,[dimensionid]\n"
                + "           ,[userid]\n"
                + "           ,[levelid]\n"
                + "           ,[lessonid]\n"
                + "           ,[subjectid]\n"
                + "           ,[content_html]\n"
                + "           ,[explain])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getContent());
            stm.setBoolean(2, model.isStatus());
            stm.setBoolean(3, model.is_multi());
            stm.setInt(4, model.getDimensionId());
            stm.setInt(5, model.getUserId());
            stm.setInt(6, model.getLevelId());
            stm.setInt(7, model.getLessonId());
            stm.setInt(8, model.getSubjectId());
            stm.setString(9, model.getContentHtml());
            stm.setString(10, model.getExplain());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
                return model;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Question model) {
        String sql = "UPDATE [question]\n"
                + "         SET [content] = ?\n"
                + "           ,[status] = ?\n"
                + "           ,[is_multi] = ?\n"
                + "           ,[dimensionid] = ?\n"
                + "           ,[userid] = ?\n"
                + "           ,[levelid] = ?\n"
                + "           ,[lessonid] = ?\n"
                + "           ,[subjectid] = ?\n"
                + "           ,[content_html] = ?\n"
                + "           ,[explain] = ?\n"
                + "     WHERE [id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getContent());
            stm.setBoolean(2, model.isStatus());
            stm.setBoolean(3, model.is_multi());
            stm.setInt(4, model.getDimensionId());
            stm.setInt(5, model.getUserId());
            stm.setInt(6, model.getLevelId());
            stm.setInt(7, model.getLessonId());
            stm.setInt(8, model.getSubjectId());
            stm.setString(9, model.getContentHtml());
            stm.setString(10, model.getExplain());
            stm.setInt(11, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            connection.setAutoCommit(false);
            String sql = "DELETE FROM [question_answer]\n"
                    + "      WHERE [question_answer].[questionid] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();

            sql = "DELETE FROM [question]\n"
                    + "      WHERE [question].[id] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
