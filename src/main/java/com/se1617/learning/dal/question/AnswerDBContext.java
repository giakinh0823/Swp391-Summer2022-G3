/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.question;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.question.Answer;
import com.se1617.learning.model.subject.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class AnswerDBContext extends DBContext<Answer> {
    
    public Answer findAnswerHaveInQuestion(int id) {
        String sql = "SELECT [answer].[id]\n"
                + "      ,[answer].[text]\n"
                + "      ,[answer].[media]\n"
                + "      ,[answer].[subjectid]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "  FROM [answer]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [answer].[subjectid]"
                + "  INNER JOIN [question_answer] ON [question_answer].[answerid] = [answer].[id]"
                + "  WHERE [answer].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setText(rs.getString("text"));
                answer.setMedia(rs.getString("media"));
                answer.setSubjectId(rs.getInt("subjectid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCategoryId(rs.getInt("categoryid"));
                subject.setUserId(rs.getInt("userid"));
                return answer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<Answer> findAnswerHaveInAnotherQuestion(int id) {
        ArrayList<Answer> answers = new ArrayList<>();
        String sql = "SELECT [answer].[id]\n"
                + "      ,[answer].[text]\n"
                + "      ,[answer].[media]\n"
                + "      ,[answer].[subjectid]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "  FROM [answer]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [answer].[subjectid]"
                + "  INNER JOIN [question_answer] ON [question_answer].[answerid] = [answer].[id]"
                + "  WHERE [answer].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setText(rs.getString("text"));
                answer.setMedia(rs.getString("media"));
                answer.setSubjectId(rs.getInt("subjectid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCategoryId(rs.getInt("categoryid"));
                subject.setUserId(rs.getInt("userid"));
                answers.add(answer);
            }
            return answers;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Answer> findBySubect(int subjectid, String search, int pageIndex, int pageSize) {
        ArrayList<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT [answer].[id]\n"
                + "      ,[answer].[text]\n"
                + "      ,[answer].[media]\n"
                + "      ,[answer].[subjectid]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "      ,ROW_NUMBER() OVER (ORDER BY [answer].[id] DESC) as row_index\n"
                + "  FROM [answer] "
                + "  INNER JOIN [subject] ON [subject].[id] = [answer].[subjectid]"
                + " WHERE [answer].[subjectid] = ? AND [answer].[text] LIKE ?)[answer]\n"
                + " WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, subjectid);
            stm.setString(2, "%" + search + "%");
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            stm.setInt(5, pageIndex);
            stm.setInt(6, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setText(rs.getString("text"));
                answer.setMedia(rs.getString("media"));
                answer.setSubjectId(rs.getInt("subjectid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCategoryId(rs.getInt("categoryid"));
                subject.setUserId(rs.getInt("userid"));
                answers.add(answer);
            }
            return answers;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Answer> list() {
        ArrayList<Answer> answers = new ArrayList<>();
        String sql = "SELECT [answer].[id]\n"
                + "      ,[answer].[text]\n"
                + "      ,[answer].[media]\n"
                + "      ,[answer].[subjectid]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "  FROM [answer]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [answer].[subjectid]";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setText(rs.getString("text"));
                answer.setMedia(rs.getString("media"));
                answer.setSubjectId(rs.getInt("subjectid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCategoryId(rs.getInt("categoryid"));
                subject.setUserId(rs.getInt("userid"));
                answers.add(answer);
            }
            return answers;
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Answer get(int id) {
        String sql = "SELECT [answer].[id]\n"
                + "      ,[answer].[text]\n"
                + "      ,[answer].[media]\n"
                + "      ,[answer].[subjectid]\n"
                + "      ,[subject].[name]\n"
                + "      ,[subject].[description]\n"
                + "      ,[subject].[image]\n"
                + "      ,[subject].[status]\n"
                + "      ,[subject].[featured]\n"
                + "      ,[subject].[categoryid]\n"
                + "      ,[subject].[userid]\n"
                + "  FROM [answer]\n"
                + "  INNER JOIN [subject] ON [subject].[id] = [answer].[subjectid]"
                + "  WHERE [answer].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setText(rs.getString("text"));
                answer.setMedia(rs.getString("media"));
                answer.setSubjectId(rs.getInt("subjectid"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subjectid"));
                subject.setName(rs.getString("name"));
                subject.setDescription(rs.getString("description"));
                subject.setStatus(rs.getBoolean("status"));
                subject.setFeatured(rs.getBoolean("featured"));
                subject.setImage(rs.getString("image"));
                subject.setCategoryId(rs.getInt("categoryid"));
                subject.setUserId(rs.getInt("userid"));
                return answer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Answer insert(Answer model) {
        String sql = "INSERT INTO [answer]\n"
                + "           ([text]\n"
                + "           ,[media]\n"
                + "           ,[subjectid])\n"
                + "     VALUES(?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getText());
            stm.setString(2, model.getMedia());
            stm.setInt(3, model.getSubjectId());
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                model.setId(id);
                return model;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Answer model) {
         String sql = "UPDATE [answer]\n"
                + "        SET [text] = ?\n"
                + "           ,[media] = ?\n"
                + "           ,[subjectid] = ?\n"
                + "    WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getText());
            stm.setString(2, model.getMedia());
            stm.setInt(3, model.getSubjectId());
            stm.setInt(4, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM [answer]\n"
                    + "      WHERE [answer].[id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

}
