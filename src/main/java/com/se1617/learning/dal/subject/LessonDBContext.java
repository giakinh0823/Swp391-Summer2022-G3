/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.subject;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.quizzes.Quizzes;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.subject.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class LessonDBContext extends DBContext<Lesson> {

    @Override
    public ArrayList<Lesson> list() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [les1].[id] DESC) as row_index\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] ";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findBySubjectId(int subjectId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [les1].[id] DESC) as row_index\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + " WHERE [les1].[subjectid] = ? AND [les1].[topicid] is null and [les1].[quizzesid] is null"
                + " ORDER BY [les1].[id] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findByActiveSubjectId(int subjectId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "      ,[user_lesson].[created_at] as 'user_lesson_created_at'\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  LEFT JOIN [user_lesson] ON [user_lesson].[lessonid] = [les1].[id]"
                + " WHERE [les1].[subjectid] = ? AND [les1].[topicid] is null and [les1].[status] = 1"
                + " ORDER BY [les1].[order] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }

                if (rs.getDate("user_lesson_created_at") != null) {
                    lesson.setLearning(true);
                } else {
                    lesson.setLearning(false);
                }
                ArrayList<Lesson> childs = findChildsActiveByTopic(lesson.getId());
                lesson.setLessons(childs);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findByActiveSubjectIdAndUserId(int subjectId, int userId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + " WHERE [les1].[subjectid] = ? AND [les1].[topicid] is null and [les1].[status] = 1"
                + " ORDER BY [les1].[order] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }
                sql = "SELECT * FROM [user_lesson]"
                        + " WHERE lessonid = ? and userid = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, lesson.getId());
                stm.setInt(2, userId);
                ResultSet resultSet = stm.executeQuery();
                if (resultSet.next()) {
                    lesson.setLearning(true);
                } else {
                    lesson.setLearning(false);
                }
                ArrayList<Lesson> childs = findChildsActiveByTopicAndUserId(lesson.getId(), userId);
                lesson.setLessons(childs);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findChildsByTopic(int topicId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "       ,[quizzes].[name] as 'quizzes_name'\n"
                + "      ,[quizzes].[duration] as 'quizzes_duration'\n"
                + "      ,[quizzes].[pass_rate] as 'quizzes_pass_rate'\n"
                + "      ,[quizzes].[description] as 'quizzes_description'\n"
                + "      ,[quizzes].[subjectid] as 'quizzes_subjectid'\n"
                + "      ,[quizzes].[typeid] as 'quizzes_typeid'\n"
                + "      ,[quizzes].[levelid]  as 'quizzes_levelid'"
                + "      ,ROW_NUMBER() OVER (ORDER BY [les1].[id] DESC) as row_index\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  LEFT JOIN [quizzes] on [quizzes].[id] = [les1].[quizzesid] "
                + "  WHERE [les1].[topicid] = ? "
                + "  ORDER BY [les1].[order] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, topicId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }

                if (rs.getString("quizzes_name") != null) {
                    Quizzes quizzes = new Quizzes();
                    quizzes.setId(rs.getInt("quizzesid"));
                    quizzes.setName(rs.getString("quizzes_name"));
                    quizzes.setDescription(rs.getString("quizzes_description"));
                    quizzes.setDuration(rs.getInt("quizzes_duration"));
                    quizzes.setPass_rate(rs.getInt("quizzes_pass_rate"));
                    quizzes.setLevelId(rs.getInt("quizzes_levelid"));
                    quizzes.setTypeId(rs.getInt("quizzes_typeid"));
                    quizzes.setSubjectId(rs.getInt("quizzes_subjectid"));
                    lesson.setQuizzes(quizzes);
                }
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findChildsActiveByTopic(int topicId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "       ,[quizzes].[name] as 'quizzes_name'\n"
                + "      ,[quizzes].[duration] as 'quizzes_duration'\n"
                + "      ,[quizzes].[pass_rate] as 'quizzes_pass_rate'\n"
                + "      ,[quizzes].[description] as 'quizzes_description'\n"
                + "      ,[quizzes].[subjectid] as 'quizzes_subjectid'\n"
                + "      ,[quizzes].[typeid] as 'quizzes_typeid'\n"
                + "      ,[quizzes].[levelid]  as 'quizzes_levelid'"
                + "      ,[user_lesson].[created_at] as 'user_lesson_created_at'\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  LEFT JOIN [quizzes] on [quizzes].[id] = [les1].[quizzesid] "
                + "  LEFT JOIN [user_lesson] ON [user_lesson].[lessonid] = [les1].[id]"
                + "  WHERE [les1].[topicid] = ? and [les1].[status] = 1"
                + "  ORDER BY [les1].[id] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, topicId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }

                if (rs.getString("quizzes_name") != null) {
                    Quizzes quizzes = new Quizzes();
                    quizzes.setId(rs.getInt("quizzesid"));
                    quizzes.setName(rs.getString("quizzes_name"));
                    quizzes.setDescription(rs.getString("quizzes_description"));
                    quizzes.setDuration(rs.getInt("quizzes_duration"));
                    quizzes.setPass_rate(rs.getInt("quizzes_pass_rate"));
                    quizzes.setLevelId(rs.getInt("quizzes_levelid"));
                    quizzes.setTypeId(rs.getInt("quizzes_typeid"));
                    quizzes.setSubjectId(rs.getInt("quizzes_subjectid"));
                    lesson.setQuizzes(quizzes);
                }

                if (rs.getDate("user_lesson_created_at") != null) {
                    lesson.setLearning(true);
                } else {
                    lesson.setLearning(false);
                }

                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findChildsActiveByTopicAndUserId(int topicId, int userId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "       ,[quizzes].[name] as 'quizzes_name'\n"
                + "      ,[quizzes].[duration] as 'quizzes_duration'\n"
                + "      ,[quizzes].[pass_rate] as 'quizzes_pass_rate'\n"
                + "      ,[quizzes].[description] as 'quizzes_description'\n"
                + "      ,[quizzes].[subjectid] as 'quizzes_subjectid'\n"
                + "      ,[quizzes].[typeid] as 'quizzes_typeid'\n"
                + "      ,[quizzes].[levelid]  as 'quizzes_levelid'"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  LEFT JOIN [quizzes] on [quizzes].[id] = [les1].[quizzesid] "
                + "  WHERE [les1].[topicid] = ? and [les1].[status] = 1"
                + "  ORDER BY [les1].[id] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, topicId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }

                if (rs.getString("quizzes_name") != null) {
                    Quizzes quizzes = new Quizzes();
                    quizzes.setId(rs.getInt("quizzesid"));
                    quizzes.setName(rs.getString("quizzes_name"));
                    quizzes.setDescription(rs.getString("quizzes_description"));
                    quizzes.setDuration(rs.getInt("quizzes_duration"));
                    quizzes.setPass_rate(rs.getInt("quizzes_pass_rate"));
                    quizzes.setLevelId(rs.getInt("quizzes_levelid"));
                    quizzes.setTypeId(rs.getInt("quizzes_typeid"));
                    quizzes.setSubjectId(rs.getInt("quizzes_subjectid"));
                    lesson.setQuizzes(quizzes);
                }

                sql = "SELECT * FROM [user_lesson]"
                        + " WHERE lessonid = ? and userid = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, lesson.getId());
                stm.setInt(2, userId);
                ResultSet resultSet = stm.executeQuery();
                if (resultSet.next()) {
                    lesson.setLearning(true);
                } else {
                    lesson.setLearning(false);
                }

                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Lesson> findTopicBySubjectId(int subjectId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  LEFT JOIN [quizzes] on [quizzes].[id] = [les1].[quizzesid] "
                + " WHERE [les1].[subjectid] = ? AND [les1].[topicid] is null"
                + " ORDER BY [les1].[order] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }
                ArrayList<Lesson> childs = findChildsByTopic(lesson.getId());
                lesson.setLessons(childs);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Lesson get(int id) {
        PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "       ,[quizzes].[name] as 'quizzes_name'\n"
                + "      ,[quizzes].[duration] as 'quizzes_duration'\n"
                + "      ,[quizzes].[pass_rate] as 'quizzes_pass_rate'\n"
                + "      ,[quizzes].[description] as 'quizzes_description'\n"
                + "      ,[quizzes].[subjectid] as 'quizzes_subjectid'\n"
                + "      ,[quizzes].[typeid] as 'quizzes_typeid'\n"
                + "      ,[quizzes].[levelid]  as 'quizzes_levelid'"
                + "      ,[user_lesson].[created_at] as 'user_lesson_created_at'\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  LEFT JOIN [quizzes] on [quizzes].[id] = [les1].[quizzesid] "
                + "  LEFT JOIN [user_lesson] ON [user_lesson].[lessonid] = [les1].[id]"
                + "  WHERE [les1].[id] = ? ";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }

                if (rs.getString("quizzes_name") != null) {
                    Quizzes quizzes = new Quizzes();
                    quizzes.setId(rs.getInt("quizzesid"));
                    quizzes.setName(rs.getString("quizzes_name"));
                    quizzes.setDescription(rs.getString("quizzes_description"));
                    quizzes.setDuration(rs.getInt("quizzes_duration"));
                    quizzes.setPass_rate(rs.getInt("quizzes_pass_rate"));
                    quizzes.setLevelId(rs.getInt("quizzes_levelid"));
                    quizzes.setTypeId(rs.getInt("quizzes_typeid"));
                    quizzes.setSubjectId(rs.getInt("quizzes_subjectid"));
                    lesson.setQuizzes(quizzes);
                }

                if (rs.getDate("user_lesson_created_at") != null) {
                    lesson.setLearning(true);
                } else {
                    lesson.setLearning(false);
                }

                ArrayList<PackagePrice> packagePrices = packagePriceDB.findByLessonId(lesson.getId());
                lesson.setPackagePrices(packagePrices);
                return lesson;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Lesson insert(Lesson model) {
        String sql = "INSERT INTO [lesson]\n"
                + "           ([name]\n"
                + "           ,[order]\n"
                + "           ,[status]\n"
                + "           ,[video]\n"
                + "           ,[content]\n"
                + "           ,[typeid]\n"
                + "           ,[topicid]\n"
                + "           ,[quizzesid]\n"
                + "           ,[subjectid])\n"
                + "     VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getName());
            stm.setInt(2, model.getOrder());
            stm.setBoolean(3, model.isStatus());
            stm.setString(4, model.getVideo());
            stm.setString(5, model.getContent());
            stm.setInt(6, model.getTypeid());
            if (model.getTopic() == null) {
                stm.setNull(7, java.sql.Types.INTEGER);
            } else {
                stm.setInt(7, model.getTopicId());
            }
            if (model.getQuizzes() == null) {
                stm.setNull(8, java.sql.Types.INTEGER);
            } else {
                stm.setInt(8, model.getQuizzesid());
            }
            stm.setInt(9, model.getSubjectId());
            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }

            sql = "INSERT INTO [lesson_price_package]\n"
                    + "           ([lessonid]\n"
                    + "           ,[price_packageid])\n"
                    + "     VALUES\n"
                    + "           (?,?)";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql);
            for (PackagePrice packagePrice : model.getPackagePrices()) {
                stm.setInt(1, model.getId());
                stm.setInt(2, packagePrice.getId());
                stm.executeUpdate();
            }
            connection.commit();
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void insertDone(int lessonId, int userId) {
        deleteDone(lessonId, userId);
        String sql = "INSERT INTO [user_lesson]\n"
                + "  (created_at \n"
                + "  ,userid"
                + "  ,lessonid)"
                + " VALUES(?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stm.setInt(2, userId);
            stm.setInt(3, lessonId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteDone(int lessonId, int userId) {
        String sql = "DELETE FROM [user_lesson]\n"
                + " WHERE lessonid = ? AND userid = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            stm.setInt(2, userId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Lesson model) {
        String sql = "UPDATE [lesson]\n"
                + "         SET [name] = ?\n"
                + "           ,[order] = ?\n"
                + "           ,[status] = ?\n"
                + "           ,[video] = ?\n"
                + "           ,[content] = ?\n"
                + "           ,[typeid] = ?\n"
                + "           ,[topicid] = ?\n"
                + "           ,[quizzesid] = ?\n"
                + "           ,[subjectid] = ?\n"
                + "    WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setInt(2, model.getOrder());
            stm.setBoolean(3, model.isStatus());
            stm.setString(4, model.getVideo());
            stm.setString(5, model.getContent());
            stm.setInt(6, model.getTypeid());
            if (model.getTopic() == null) {
                stm.setNull(7, java.sql.Types.INTEGER);
            } else {
                stm.setInt(7, model.getTopicId());
            }
            if (model.getQuizzes() == null) {
                stm.setNull(8, java.sql.Types.INTEGER);
            } else {
                stm.setInt(8, model.getQuizzesid());
            }
            stm.setInt(9, model.getSubjectId());
            stm.setInt(10, model.getId());
            stm.executeUpdate();

            deleteLessonPrice(model.getId());
            sql = "INSERT INTO [lesson_price_package]\n"
                    + "           ([lessonid]\n"
                    + "           ,[price_packageid])\n"
                    + "     VALUES\n"
                    + "           (?,?)";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql);
            for (PackagePrice packagePrice : model.getPackagePrices()) {
                stm.setInt(1, model.getId());
                stm.setInt(2, packagePrice.getId());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void deleteLessonPrice(int id) {
        String sql = "DELETE FROM [lesson_price_package]\n"
                + " WHERE lessonid = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM [lesson]\n"
                + " WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Lesson> searchSelected(int subjectId, String search, int pageIndex, int pageSize) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM(SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [les1].[id] DESC) as row_index\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + "  WHERE [les1].[subjectid] = ? AND [les1].[name] LIKE ?) [lesson]"
                + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            stm.setInt(5, pageIndex);
            stm.setInt(6, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<Lesson> listPageable(String search, Pageable pageable) {
        ArrayList<Lesson> lessons = new ArrayList();
        ResultPageable<Lesson> resultPageable = new ResultPageable<>();

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Lesson> findLessonBySubjectId(int subjectId) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT [les1].[id]\n"
                + "      ,[les1].[name]\n"
                + "      ,[les1].[order]\n"
                + "      ,[les1].[status]\n"
                + "      ,[les1].[video]\n"
                + "      ,[les1].[content]\n"
                + "      ,[les1].[subjectid]\n"
                + "      ,[les1].[typeid]\n"
                + "      ,[les1].[topicid]\n"
                + "      ,[les1].[quizzesid]\n"
                + "	 ,[les2].[name] as 'topic_name'\n"
                + "      ,[les2].[order] as 'topic_order'\n"
                + "      ,[les2].[status] as 'topic_status'\n"
                + "      ,[les2].[video] as 'topic_video'\n"
                + "      ,[les2].[content] as 'topic_content'\n"
                + "      ,[les2].[typeid] as 'topic_type'\n"
                + "      ,[setting].[type] as 'type_type'\n"
                + "      ,[setting].[value] as 'type_value'\n"
                + "      ,[setting].[order] as 'type_order'\n"
                + "      ,[setting].[status] as 'type_status'\n"
                + "      ,[setting].[description] as 'type_description'\n"
                + "      ,[setting].[parent] as 'type_parent'"
                + "	 ,[subject].[name] as 'subject_name'\n"
                + "      ,[subject].[description] as 'subject_description'\n"
                + "      ,[subject].[image] as 'subject_image'\n"
                + "      ,[subject].[status] as 'subject_status'\n"
                + "      ,[subject].[featured] as 'subject_featured'\n"
                + "      ,[subject].[categoryid] as 'subject_category'\n"
                + "      ,[subject].[userid] as 'subject_user'\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [les1].[id] DESC) as row_index\n"
                + "  FROM [lesson] les1\n"
                + "  LEFT JOIN [lesson] AS les2 ON [les2].[id] = [les1].[topicid]\n"
                + "  LEFT JOIN [setting] ON [setting].[id] = [les1].[typeid]"
                + "  LEFT JOIN [subject] ON [subject].[id] = [les1].[subjectid] "
                + " WHERE [les1].[subjectid] = ? "
                + " ORDER BY [les1].[id] ASC";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {

                Lesson lesson = new Lesson();
                lesson.setId(rs.getInt("id"));
                lesson.setName(rs.getString("name"));
                lesson.setOrder(rs.getInt("order"));
                lesson.setStatus(rs.getBoolean("status"));
                lesson.setVideo(rs.getString("video"));
                lesson.setContent(rs.getString("content"));
                lesson.setSubjectId(rs.getInt("subjectid"));
                lesson.setTypeid(rs.getInt("typeid"));
                lesson.setTopicId(rs.getInt("topicid"));

                if (rs.getString("topic_name") != null) {
                    Lesson topic = new Lesson();
                    topic.setId(rs.getInt("topicid"));
                    topic.setName(rs.getString("topic_name"));
                    topic.setOrder(rs.getInt("topic_order"));
                    topic.setStatus(rs.getBoolean("topic_status"));
                    topic.setVideo(rs.getString("topic_video"));
                    topic.setContent(rs.getString("topic_content"));
                    topic.setTypeid(rs.getInt("topic_type"));
                    lesson.setTopic(topic);
                }

                if (rs.getString("type_value") != null) {
                    Setting type = new Setting();
                    type.setId(rs.getInt("typeid"));
                    type.setType(rs.getString("type_type"));
                    type.setValue(rs.getString("type_value"));
                    type.setOrder(rs.getInt("type_order"));
                    type.setStatus(rs.getBoolean("type_status"));
                    type.setDescription(rs.getString("type_description"));
                    type.setParent(rs.getInt("type_parent"));
                    lesson.setType(type);
                }

                if (rs.getString("subject_name") != null) {
                    Subject subject = new Subject();
                    subject.setId(rs.getInt("subjectid"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setDescription(rs.getString("subject_description"));
                    subject.setStatus(rs.getBoolean("subject_status"));
                    subject.setFeatured(rs.getBoolean("subject_featured"));
                    subject.setImage(rs.getString("subject_image"));
                    subject.setCategoryId(rs.getInt("subject_category"));
                    subject.setUserId(rs.getInt("subject_user"));
                    lesson.setSubject(subject);
                }
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
