 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.blog;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.model.blog.Post;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.user.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public class PostDBContext extends DBContext<Post> {

    public ResultPageable<Post> searchByTitleAndFilterByCategoryAndAuthorAndStatus(String search,
            String category, String author, String statusParams, Pageable pageable) {

        boolean status = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("show");
        int groupId = -1;
        try {
            groupId = Integer.parseInt(author);
        } catch (Exception e) {
            groupId = -1;
        }
        int categoryid = -1;
        try {
            categoryid = Integer.parseInt(category);
        } catch (Exception e) {
            categoryid = -1;
        }

        statusParams = (statusParams != null && !statusParams.trim().isEmpty()) ? statusParams : null;
        author = (author != null && !author.trim().isEmpty()) ? author : null;
        category = (category != null && !category.trim().isEmpty()) ? category : null;

        ResultPageable<Post> resultPageable = new ResultPageable<>();
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql_query_data = "SELECT * FROM (SELECT  [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
            if (pageable.getFieldSort().equalsIgnoreCase("author")) {
                sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY [user].[username] " + pageable.getOrder() + ") as row_index\n";
            } else if (pageable.getFieldSort().equalsIgnoreCase("category")) {
                sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY [setting].[value] " + pageable.getOrder() + ") as row_index\n";
            } else {
                sql_query_data += " ,ROW_NUMBER() OVER (ORDER BY [post].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") as row_index\n";
            }
            sql_query_data += "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]\n"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]\n";
            if (author != null && !author.isEmpty()) {
                sql_query_data += " INNER JOIN [group_user] ON [group_user].[userid] = [user].[id] ";
            }
            sql_query_data += "  WHERE [post].[title] LIKE ? ";
            if (statusParams != null && !statusParams.isEmpty()) {
                sql_query_data += " AND [post].[status] = ? ";
            }
            if (category != null && !category.isEmpty()) {
                sql_query_data += " AND [setting].[id] = ? ";
            }
            if (author != null && !author.isEmpty()) {
                sql_query_data += " AND [group_user].[groupid] = ? ";
            }
            sql_query_data += " ) [post]"
                    + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

            String sql_count_data = "SELECT COUNT([post].[id]) as size FROM [post]"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]\n"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]\n";
            if (author != null && !author.isEmpty()) {
                sql_count_data += " INNER JOIN [group_user] ON [group_user].[userid] = [user].[id] ";
            }
            sql_count_data += " WHERE [post].[title] LIKE ? ";

            if (statusParams != null && !statusParams.isEmpty()) {
                sql_count_data += " AND [post].[status] = ? ";
            }
            if (category != null && !category.isEmpty()) {
                sql_count_data += " AND [setting].[id] = ? ";
            }
            if (author != null && !author.isEmpty()) {
                sql_count_data += " AND [group_user].[groupid] = ? ";
            }

            PreparedStatement stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            if (statusParams != null && category != null && author != null) {
                stm.setBoolean(2, status);
                stm.setInt(3, categoryid);
                stm.setInt(4, groupId);

                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
                stm.setInt(7, pageable.getPageIndex());
                stm.setInt(8, pageable.getPageSize());
            } else if (statusParams != null && category != null) {
                stm.setBoolean(2, status);
                stm.setInt(3, categoryid);

                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
                stm.setInt(6, pageable.getPageIndex());
                stm.setInt(7, pageable.getPageSize());
            } else if (statusParams != null && author != null) {
                stm.setBoolean(2, status);
                stm.setInt(3, groupId);

                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
                stm.setInt(6, pageable.getPageIndex());
                stm.setInt(7, pageable.getPageSize());
            } else if (category != null && author != null) {
                stm.setInt(2, categoryid);
                stm.setInt(3, groupId);

                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
                stm.setInt(6, pageable.getPageIndex());
                stm.setInt(7, pageable.getPageSize());
            } else if (statusParams != null) {
                stm.setBoolean(2, status);

                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
            } else if (category != null) {
                stm.setInt(2, categoryid);

                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());

            } else if (author != null) {
                stm.setInt(2, groupId);

                stm.setInt(3, pageable.getPageIndex());
                stm.setInt(4, pageable.getPageSize());
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());

            } else {
                stm.setInt(2, pageable.getPageIndex());
                stm.setInt(3, pageable.getPageSize());
                stm.setInt(4, pageable.getPageIndex());
                stm.setInt(5, pageable.getPageSize());
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
            stm = connection.prepareCall(sql_count_data);
            stm.setString(1, "%" + search + "%");
            if (statusParams != null && category != null && author != null) {
                stm.setBoolean(2, status);
                stm.setInt(3, categoryid);
                stm.setInt(4, groupId);
            } else if (statusParams != null && category != null) {
                stm.setBoolean(2, status);
                stm.setInt(3, categoryid);
            } else if (statusParams != null && author != null) {
                stm.setBoolean(2, status);
                stm.setInt(3, groupId);
            } else if (category != null && author != null) {
                stm.setInt(2, categoryid);
                stm.setInt(3, groupId);
            } else if (statusParams != null) {
                stm.setBoolean(2, status);
            } else if (category != null) {
                stm.setInt(2, categoryid);
            } else if (author != null) {
                stm.setInt(2, groupId);
            }

            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setList(posts);
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Post> list() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT  [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    @Override
    public Post get(int id) {
        try {
            String sql = "SELECT  [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]"
                    + " WHERE [post].[id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                return post;
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Post insert(Post model) {
        String sql = "INSERT INTO [dbo].[post]\n"
                + "           ([title]\n"
                + "           ,[thumbnail]\n"
                + "           ,[content]\n"
                + "           ,[created_at]\n"
                + "           ,[updated_at]\n"
                + "           ,[userid]\n"
                + "           ,[categoryId]\n"
                + "           ,[flag]\n"
                + "           ,[status]\n"
                + "           ,[featured]\n"
                + "           ,[description])\n"
                + "     VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, model.getTitle());
            stm.setString(2, model.getThumbnail());
            stm.setString(3, model.getContent());
            stm.setTimestamp(4, model.getCreated_at());
            stm.setTimestamp(5, model.getUpdated_at());
            stm.setInt(6, model.getUser().getId());
            stm.setInt(7, model.getCategoryId());
            stm.setBoolean(8, model.isFlag());
            stm.setBoolean(9, model.isStatus());
            stm.setBoolean(10, model.isFeatured());
            stm.setString(11, model.getDescription());
            stm.executeUpdate();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                model.setId(rs.getInt(1));
            }
            return model;
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Post model) {
        String sql = "UPDATE [post]\n"
                + "        SET [title] = ?\n"
                + "           ,[thumbnail] = ?\n"
                + "           ,[content] = ?\n"
                + "           ,[created_at] = ?\n"
                + "           ,[updated_at] = ?\n"
                + "           ,[userid] = ?\n"
                + "           ,[categoryId] = ?\n"
                + "           ,[flag] = ?\n"
                + "           ,[status] = ?\n"
                + "           ,[featured] = ?\n"
                + "           ,[description] = ?\n"
                + " WHERE [post].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getTitle());
            stm.setString(2, model.getThumbnail());
            stm.setString(3, model.getContent());
            stm.setTimestamp(4, model.getCreated_at());
            stm.setTimestamp(5, model.getUpdated_at());
            stm.setInt(6, model.getUser().getId());
            stm.setInt(7, model.getCategoryId());
            stm.setBoolean(8, model.isFlag());
            stm.setBoolean(9, model.isStatus());
            stm.setBoolean(10, model.isFeatured());
            stm.setString(11, model.getDescription());
            stm.setInt(12, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM [post]\n"
                    + "      WHERE [post].[id]=?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultPageable<Post> searchByTitleAndFilterMultiFieldAndMultiSort(String search, Pageable pageable) {

        if (pageable.getFilters() == null) {
            pageable.setFilters(new HashMap<String, ArrayList<String>>());
        }
        ResultPageable<Post> resultPageable = new ResultPageable<>();
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<String> authors = pageable.getFilters().get("author");

        try {
            String sql_query_data = "SELECT * FROM (SELECT  [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
                    + "	  ,[setting].[parent]\n"
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
                        sql_query_data += " [user].[username] " + val + ",";
                    } else if (key.equalsIgnoreCase("category")) {
                        sql_query_data += " [setting].[value] " + val + ",";
                    } else {
                        if (key.equalsIgnoreCase("created") || key.equalsIgnoreCase("updated")) {
                            key = key + "_at";
                        }
                        sql_query_data += " [post].[" + key + "] " + val + ",";
                    }
                }
                sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
            } else {
                sql_query_data += " [post].[id] DESC";
            }
            sql_query_data += " ) as row_index\n";

            sql_query_data += "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]\n"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]\n";
            if (authors != null
                    && !authors.isEmpty()) {
                sql_query_data += " INNER JOIN [group_user] ON [group_user].[userid] = [user].[id] ";
            }
            sql_query_data += "  WHERE [post].[title] LIKE ? ";

            for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                String key = entry.getKey();
                ArrayList<String> val = entry.getValue();
                sql_query_data += " AND ( ";
                if (key.equalsIgnoreCase("author")) {
                    key = " [group_user].[groupid] ";
                } else if (key.equalsIgnoreCase("category")) {
                    key = " [setting].[id] ";
                } else {
                    key = " [post].[" + key + "] ";
                }
                sql_query_data += key + " in (?";
                for (int i = 1; i < val.size(); i++) {
                    sql_query_data += ",?";
                }
                sql_query_data += ") ) ";
            }

            sql_query_data += " ) [post]"
                    + "  WHERE row_index >= (? - 1) * ? + 1 AND row_index <= ? * ?";

            String sql_count_data = "SELECT COUNT([post].[id]) as size FROM [post]"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]\n"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]\n";
            if (authors != null && !authors.isEmpty()) {
                sql_count_data += " INNER JOIN [group_user] ON [group_user].[userid] = [user].[id] ";
            }
            sql_count_data += " WHERE [post].[title] LIKE ? ";

            for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                String key = entry.getKey();
                ArrayList<String> val = entry.getValue();
                sql_count_data += " AND ( ";
                if (key.equalsIgnoreCase("author")) {
                    key = " [group_user].[groupid] ";
                } else if (key.equalsIgnoreCase("category")) {
                    key = " [setting].[id] ";
                } else {
                    key = " [post].[" + key + "] ";
                }
                sql_count_data += key + " in (?";
                for (int i = 1; i < val.size(); i++) {
                    sql_count_data += ",?";
                }
                sql_count_data += ") ) ";
            }

            System.out.println(sql_query_data);

            PreparedStatement stm = connection.prepareStatement(sql_query_data);

            stm.setString(1, "%" + search + "%");

            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("show");
                            stm.setBoolean(count + 2, isStatus);
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
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                setting.setParent(rs.getInt("parent"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
            stm = connection.prepareCall(sql_count_data);
            stm.setString(1, "%" + search + "%");
            int count = 0;
            for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                String key = entry.getKey();
                ArrayList<String> val = entry.getValue();
                for (int i = 0; i < val.size(); i++) {
                    if (key.equalsIgnoreCase("status")) {
                        String statusParams = val.get(i);
                        boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("show");
                        stm.setBoolean(count + 2, isStatus);
                    } else {
                        stm.setString(count + 2, val.get(i));
                    }
                    count++;
                }
            }

            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setList(posts);
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Post> listPostLatest(int top) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT TOP  "+top+" [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                     + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]\n"
                    + "  ORDER BY [post].[updated_at] DESC";
            PreparedStatement stm = connection.prepareStatement(sql);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    public ArrayList<Post> getFeatureBlogs(int quantity) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT TOP " + quantity + " [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]"
                    + " WHERE [post].featured = 1 AND [post].[status] = 1 \n"
                    + "ORDER BY [post].updated_at desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
            return posts;
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Post> getFeatureBlogsRandom(int quantity) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT TOP " + quantity + " [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]"
                    + " WHERE [post].featured = 1 AND [post].[status] = 1\n"
                    + "ORDER BY NEWID()";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
            return posts;
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Post> getFeatureBlogsByCategory(int categoryid, int quantity) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            String sql = "SELECT TOP " + quantity + " [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]"
                    + " WHERE [post].featured = 1 AND [post].[categoryId] = ? AND [post].[status] = 1\n"
                    + "ORDER BY NEWID()";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, categoryid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                posts.add(post);
            }
            return posts;
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Post> searchPosts(String search) {
        ArrayList<Post> postList = new ArrayList<>();
        try {
            String sql = "SELECT  [post].[id]\n"
                    + "      ,[post].[title]\n"
                    + "      ,[post].[thumbnail]\n"
                    + "      ,[post].[content]\n"
                    + "      ,[post].[created_at]\n"
                    + "      ,[post].[updated_at]\n"
                    + "      ,[post].[userid]\n"
                    + "      ,[post].[categoryId]\n"
                    + "      ,[post].[flag]\n"
                    + "      ,[post].[status]\n"
                    + "      ,[post].[featured]\n"
                    + "      ,[post].[description]\n"
                    + "	  ,[setting].[type]\n"
                    + "	  ,[setting].[value]\n"
                    + "	  ,[setting].[status] as 'setting_status'\n"
                    + "	  ,[setting].[order]\n"
                    + "	  ,[setting].[description] as 'setting_description'\n"
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
                    + "  FROM [post]\n"
                    + "  INNER JOIN [setting] ON [setting].[id] = [post].[categoryId]"
                    + "  INNER JOIN [user] ON [user].[id] = [post].[userid]"
                    + "  WHERE [post].[title] LIKE ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setThumbnail(rs.getString("thumbnail"));
                post.setContent(rs.getString("content"));
                post.setCategoryId(rs.getInt("categoryId"));
                post.setCreated_at(rs.getTimestamp("created_at"));
                post.setUpdated_at(rs.getTimestamp("updated_at"));
                post.setStatus(rs.getBoolean("status"));
                post.setFeatured(rs.getBoolean("featured"));
                post.setFlag(rs.getBoolean("flag"));
                post.setDescription(rs.getString("description"));

                Setting setting = new Setting();
                setting.setId(rs.getInt("categoryId"));
                setting.setType(rs.getString("type"));
                setting.setValue(rs.getString("value"));
                setting.setOrder(rs.getInt("order"));
                setting.setStatus(rs.getBoolean("setting_status"));
                setting.setDescription(rs.getString("setting_description"));
                post.setCategory(setting);

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

                post.setUser(user);
                postList.add(post);
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return postList;
    }

}
