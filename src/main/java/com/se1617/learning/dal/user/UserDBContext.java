/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.dal.user;

import com.se1617.learning.context.DBContext;
import com.se1617.learning.dal.subject.PackagePriceDBContext;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Pagination;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.registration.Registration;
import com.se1617.learning.model.user.Group;
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
public class UserDBContext extends DBContext<User> {

    public User login(String username, String password) {
        GroupDBContext groupDB = new GroupDBContext();
        String sql = "SELECT [user].[id]\n"
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
                + "  FROM [user]\n"
                + "  WHERE ([user].[username] = ? OR [user].[email] = ?) AND [user].[password] =  ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, username);
            stm.setString(3, password);
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User findOne(String field, String value) {
        GroupDBContext groupDB = new GroupDBContext();
        String sql = "SELECT [user].[id]\n"
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
                + "  FROM [user]\n"
                + "  WHERE UPPER([user].[" + field + "]) = UPPER(?)";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, value);
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<User> listPageable(Pageable pageable) {
        GroupDBContext groupDB = new GroupDBContext();
        ResultPageable<User> resultPageable = new ResultPageable<User>();
        ArrayList<User> users = new ArrayList<>();
        String sql_query_data = "SELECT *\n"
                + "FROM\n"
                + "  (SELECT * ,\n"
                + "          ROW_NUMBER() OVER (\n"
                + "                             ORDER BY [user].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") AS row_index\n"
                + "   FROM\n"
                + "     (SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "      FROM [user]) [user]) [user]\n"
                + "WHERE row_index >= (? - 1) * ? + 1\n"
                + "  AND row_index <= ? * ?";

        String sql_count_data = "SELECT COUNT([user].[id]) as size FROM [user]\n";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setInt(1, pageable.getPageIndex());
            stm.setInt(2, pageable.getPageSize());
            stm.setInt(3, pageable.getPageIndex());
            stm.setInt(4, pageable.getPageSize());
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                users.add(user);
            }
            resultPageable.setList(users);

            stm = connection.prepareCall(sql_count_data);
            rs = stm.executeQuery();
            int size = 0;
            if (rs.next()) {
                size = rs.getInt("size");
            }
            resultPageable.setPagination(new Pagination(pageable.getPageIndex(), pageable.getPageSize(), size));
            return resultPageable;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<User> findUserSuper() {
        ArrayList<User> users = new ArrayList<>();
        GroupDBContext groupDB = new GroupDBContext();
        String sql = "SELECT [user].[id]\n"
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
                + "  FROM [user]\n"
                + "  WHERE [user].[is_super] =  1";
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<User> list() {
        ArrayList<User> users = new ArrayList<>();
        GroupDBContext groupDB = new GroupDBContext();
        String sql = "SELECT [user].[id]\n"
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
                + "  FROM [user]\n";
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User get(int id) {
        GroupDBContext groupDB = new GroupDBContext();
        String sql = "SELECT [user].[id]\n"
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
                + "  FROM [user]\n"
                + "  WHERE [user].[id] = ?";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User insert(User user) {
        String sql = "INSERT INTO [user]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[email]\n"
                + "           ,[phone]\n"
                + "           ,[first_name]\n"
                + "           ,[last_name]\n"
                + "           ,[gender]\n"
                + "           ,[avatar]\n"
                + "           ,[is_staff]\n"
                + "           ,[is_super]\n"
                + "           ,[enable]\n"
                + "           ,[is_active]\n"
                + "           ,[create_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?,?,GETDATE())";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPhone());
            stm.setString(5, user.getFirst_name());
            stm.setString(6, user.getLast_name());
            stm.setBoolean(7, user.isGender());
            stm.setString(8, user.getAvatar());
            stm.setBoolean(9, user.is_staff());
            stm.setBoolean(10, user.is_super());
            stm.setBoolean(11, user.isEnable());
            stm.setBoolean(12, user.is_active());

            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);
            }
            return get(user.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;

    }

    public User adminInsert(User user) {
        String sql = "INSERT INTO [user]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[email]\n"
                + "           ,[phone]\n"
                + "           ,[first_name]\n"
                + "           ,[last_name]\n"
                + "           ,[gender]\n"
                + "           ,[avatar]\n"
                + "           ,[is_staff]\n"
                + "           ,[is_super]\n"
                + "           ,[enable]\n"
                + "           ,[is_active]\n"
                + "           ,[create_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?,?,GETDATE())";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql, stm.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPhone());
            stm.setString(5, user.getFirst_name());
            stm.setString(6, user.getLast_name());
            stm.setBoolean(7, user.isGender());
            stm.setString(8, user.getAvatar());
            stm.setBoolean(9, user.is_staff());
            stm.setBoolean(10, user.is_super());
            stm.setBoolean(11, user.isEnable());
            stm.setBoolean(12, user.is_active());

            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);
            }

            GroupDBContext groupDB = new GroupDBContext();
            groupDB.updateUserGroup(user.getId(), user.getGroups());
            return get(user.getId());
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void updateEnable(User user) {
        String sql = "UPDATE [user]\n"
                + "   SET [enable] = ?\n"
                + " WHERE [user].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, user.isEnable());
            stm.setInt(2, user.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE [user]\n"
                + "   SET [username] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[first_name] = ?\n"
                + "      ,[last_name] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[avatar] = ?\n"
                + " WHERE [user].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPhone());
            stm.setString(3, user.getFirst_name());
            stm.setString(4, user.getLast_name());
            stm.setBoolean(5, user.isGender());
            stm.setString(6, user.getAvatar());
            stm.setInt(7, user.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void adminUpdate(User user) {
        String sql = "UPDATE [user]\n"
                + "   SET [username] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[first_name] = ?\n"
                + "      ,[last_name] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[avatar] = ?\n"
                + "      ,[is_super] = ?\n"
                + "      ,[is_staff] = ?\n"
                + "      ,[is_active] = ?\n"
                + "      ,[email] = ?\n"
                + " WHERE [user].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPhone());
            stm.setString(3, user.getFirst_name());
            stm.setString(4, user.getLast_name());
            stm.setBoolean(5, user.isGender());
            stm.setString(6, user.getAvatar());
            stm.setBoolean(7, user.is_super());
            stm.setBoolean(8, user.is_staff());
            stm.setBoolean(9, user.is_active());
            stm.setString(10, user.getEmail());
            stm.setInt(11, user.getId());
            stm.executeUpdate();

            GroupDBContext groupDB = new GroupDBContext();
            groupDB.updateUserGroup(user.getId(), user.getGroups());

        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM [group_user] "
                    + " WHERE [group_user].[userid] = ?";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            sql = "DELETE FROM [user]\n"
                    + "      WHERE [user].[id]=?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePassword(User user) {
        String sql = "UPDATE [user]\n"
                + "   SET [password] = ?\n"
                + " WHERE [user].[id] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getPassword());
            stm.setInt(2, user.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ResultPageable<User> searchByNameOrEmailOrPhoneAndFilterByGenderAndRoleAndStatus(
            String search, String genderParam, String statusParam, String role, Pageable pageable) {

        boolean gender = genderParam != null && !genderParam.isEmpty() && genderParam.equalsIgnoreCase("male");
        boolean status = statusParam != null && !statusParam.isEmpty() && statusParam.equalsIgnoreCase("active");

        GroupDBContext groupDB = new GroupDBContext();
        ResultPageable<User> resultPageable = new ResultPageable<User>();
        ArrayList<User> users = new ArrayList<>();
        String sql_query_data = "SELECT *\n"
                + "FROM\n"
                + "  (SELECT * ,\n"
                + "          ROW_NUMBER() OVER (\n"
                + "                             ORDER BY [user].[" + pageable.getFieldSort() + "] " + pageable.getOrder() + ") AS row_index\n"
                + "   FROM\n"
                + "     (SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "      FROM [user]\n";
        if (role != null && !role.isEmpty()) {
            sql_query_data += "      INNER JOIN [group_user] ON [group_user].[userid] = [user].[id]\n"
                    + "      INNER JOIN [setting] ON [setting].[id] = [group_user].[groupid]\n"
                    + "      WHERE [setting].[value] = ?\n";
        }
        sql_query_data += " ) [user]\n"
                + "   WHERE ([user].[fullname] LIKE ?\n"
                + "     OR [user].[email] LIKE ?\n"
                + "     OR [user].[phone] LIKE ?)";
        if (genderParam != null && statusParam != null) {
            sql_query_data += " AND ([user].[gender] = ? AND [user].[is_active] = ? )) [user]\n";
        } else if (genderParam != null) {
            sql_query_data += " AND ([user].[gender] = ?)) [user]\n";
        } else if (statusParam != null) {
            sql_query_data += " AND ([user].[is_active] = ?)) [user]\n";
        } else {
            sql_query_data += " ) [user]\n";
        }
        sql_query_data += "WHERE row_index >= (? - 1) * ? + 1\n"
                + "  AND row_index <= ? * ?";

        String sql_count_data = "SELECT COUNT([user].[id]) as size FROM(SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "     FROM [user]\n";
        if (role != null) {
            sql_count_data += "  INNER JOIN [group_user] ON [group_user].[userid] = [user].[id]\n"
                    + "      INNER JOIN [setting] ON [setting].[id] = [group_user].[groupid]\n"
                    + "      WHERE [setting].[value] = ?\n";
        }
        sql_count_data += " ) [user]\n"
                + "   WHERE ([user].[fullname] LIKE ?\n"
                + "     OR [user].[email] LIKE ?\n"
                + "     OR [user].[phone] LIKE ?) ";
        if (genderParam != null && statusParam != null) {
            sql_count_data += " AND ([user].[gender] = ? AND [user].[is_active] = ? )\n";
        } else if (genderParam != null) {
            sql_count_data += " AND ([user].[gender] = ?)\n";
        } else if (statusParam != null) {
            sql_count_data += " AND ([user].[is_active] = ?)\n";
        }
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            if (role != null) {
                stm.setString(1, role);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(5, gender);
                    stm.setBoolean(6, status);

                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                    stm.setInt(9, pageable.getPageIndex());
                    stm.setInt(10, pageable.getPageSize());
                } else if (genderParam != null) {
                    stm.setBoolean(5, gender);
                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                } else if (statusParam != null) {
                    stm.setBoolean(5, status);
                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                } else {
                    stm.setInt(5, pageable.getPageIndex());
                    stm.setInt(6, pageable.getPageSize());
                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                }
            } else {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(4, gender);
                    stm.setBoolean(5, status);

                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                } else if (genderParam != null) {
                    stm.setBoolean(4, gender);

                    stm.setInt(5, pageable.getPageIndex());
                    stm.setInt(6, pageable.getPageSize());
                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                } else if (statusParam != null) {
                    stm.setBoolean(4, status);

                    stm.setInt(5, pageable.getPageIndex());
                    stm.setInt(6, pageable.getPageSize());
                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                } else {
                    stm.setInt(4, pageable.getPageIndex());
                    stm.setInt(5, pageable.getPageSize());
                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                }
            }

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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                users.add(user);
            }
            resultPageable.setList(users);

            stm = connection.prepareCall(sql_count_data);
            if (role != null) {
                stm.setString(1, role);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(5, gender);
                    stm.setBoolean(6, status);
                } else if (genderParam != null) {
                    stm.setBoolean(5, gender);
                } else if (statusParam != null) {
                    stm.setBoolean(5, status);
                }
            } else {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(4, gender);
                    stm.setBoolean(5, status);
                } else if (genderParam != null) {
                    stm.setBoolean(4, gender);
                } else if (statusParam != null) {
                    stm.setBoolean(4, status);
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
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<User> searchByNameOrEmailOrPhoneAndFilterByGenderAndRoleAndStatusAndSortMultiField(String search, String genderParam, String statusParam, String role, Pageable pageable) {
        boolean gender = genderParam != null && !genderParam.isEmpty() && genderParam.equalsIgnoreCase("male");
        boolean status = statusParam != null && !statusParam.isEmpty() && statusParam.equalsIgnoreCase("active");

        GroupDBContext groupDB = new GroupDBContext();
        ResultPageable<User> resultPageable = new ResultPageable<User>();
        ArrayList<User> users = new ArrayList<>();
        String sql_query_data = "SELECT *\n"
                + "FROM\n"
                + "  (SELECT * ,\n"
                + "          ROW_NUMBER() OVER (ORDER BY ";
        if (pageable.getOrderings() != null && pageable.getOrderings().size() > 0) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("status")) {
                    key = "is_active";
                } else if (key.equalsIgnoreCase("super") || key.equalsIgnoreCase("staff")) {
                    key = "is_" + key;
                } else if (key.equalsIgnoreCase("firstName")) {
                    key = "first_name";
                } else if (key.equalsIgnoreCase("lastName")) {
                    key = "last_name";
                }
                sql_query_data += " [user].[" + key + "] " + val + ",";
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [user].[id] DESC ";
        }
        sql_query_data += " ) AS row_index\n";
        sql_query_data += "   FROM\n"
                + "     (SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "      FROM [user]\n";
        if (role != null && !role.isEmpty()) {
            sql_query_data += "      INNER JOIN [group_user] ON [group_user].[userid] = [user].[id]\n"
                    + "      INNER JOIN [setting] ON [setting].[id] = [group_user].[groupid]\n"
                    + "      WHERE [setting].[value] = ?\n";
        }
        sql_query_data += " ) [user]\n"
                + "   WHERE ([user].[fullname] LIKE ?\n"
                + "     OR [user].[email] LIKE ?\n"
                + "     OR [user].[phone] LIKE ?\n"
                + "     OR [user].[username] LIKE ?)";
        if (genderParam != null && statusParam != null) {
            sql_query_data += " AND ([user].[gender] = ? AND [user].[is_active] = ? )) [user]\n";
        } else if (genderParam != null) {
            sql_query_data += " AND ([user].[gender] = ?)) [user]\n";
        } else if (statusParam != null) {
            sql_query_data += " AND ([user].[is_active] = ?)) [user]\n";
        } else {
            sql_query_data += " ) [user]\n";
        }
        sql_query_data += "WHERE row_index >= (? - 1) * ? + 1\n"
                + "  AND row_index <= ? * ?";

        String sql_count_data = "SELECT COUNT([user].[id]) as size FROM(SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "     FROM [user]\n";
        if (role != null) {
            sql_count_data += "  INNER JOIN [group_user] ON [group_user].[userid] = [user].[id]\n"
                    + "      INNER JOIN [setting] ON [setting].[id] = [group_user].[groupid]\n"
                    + "      WHERE [setting].[value] = ?\n";
        }
        sql_count_data += " ) [user]\n"
                + "   WHERE ([user].[fullname] LIKE ?\n"
                + "     OR [user].[email] LIKE ?\n"
                + "     OR [user].[phone] LIKE ?\n"
                + "     OR [user].[username] LIKE ?)";
        if (genderParam != null && statusParam != null) {
            sql_count_data += " AND ([user].[gender] = ? AND [user].[is_active] = ? )\n";
        } else if (genderParam != null) {
            sql_count_data += " AND ([user].[gender] = ?)\n";
        } else if (statusParam != null) {
            sql_count_data += " AND ([user].[is_active] = ?)\n";
        }

        System.out.println(sql_query_data);

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            if (role != null) {
                stm.setString(1, role);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
                stm.setString(5, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(6, gender);
                    stm.setBoolean(7, status);

                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                    stm.setInt(10, pageable.getPageIndex());
                    stm.setInt(11, pageable.getPageSize());
                } else if (genderParam != null) {
                    stm.setBoolean(6, gender);
                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                    stm.setInt(9, pageable.getPageIndex());
                    stm.setInt(10, pageable.getPageSize());
                } else if (statusParam != null) {
                    stm.setBoolean(6, status);
                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                    stm.setInt(9, pageable.getPageIndex());
                    stm.setInt(10, pageable.getPageSize());
                } else {
                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                }
            } else {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(5, gender);
                    stm.setBoolean(6, status);

                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                    stm.setInt(9, pageable.getPageIndex());
                    stm.setInt(10, pageable.getPageSize());
                } else if (genderParam != null) {
                    stm.setBoolean(5, gender);

                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                } else if (statusParam != null) {
                    stm.setBoolean(5, status);

                    stm.setInt(6, pageable.getPageIndex());
                    stm.setInt(7, pageable.getPageSize());
                    stm.setInt(8, pageable.getPageIndex());
                    stm.setInt(9, pageable.getPageSize());
                } else {
                    stm.setInt(5, pageable.getPageIndex());
                    stm.setInt(6, pageable.getPageSize());
                    stm.setInt(7, pageable.getPageIndex());
                    stm.setInt(8, pageable.getPageSize());
                }
            }

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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                users.add(user);
            }
            resultPageable.setList(users);

            stm = connection.prepareCall(sql_count_data);
            if (role != null) {
                stm.setString(1, role);
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");
                stm.setString(5, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(6, gender);
                    stm.setBoolean(7, status);
                } else if (genderParam != null) {
                    stm.setBoolean(6, gender);
                } else if (statusParam != null) {
                    stm.setBoolean(6, status);
                }
            } else {
                stm.setString(1, "%" + search + "%");
                stm.setString(2, "%" + search + "%");
                stm.setString(3, "%" + search + "%");
                stm.setString(4, "%" + search + "%");

                if (genderParam != null && statusParam != null) {
                    stm.setBoolean(5, gender);
                    stm.setBoolean(6, status);
                } else if (genderParam != null) {
                    stm.setBoolean(5, gender);
                } else if (statusParam != null) {
                    stm.setBoolean(5, status);
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
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultPageable<User> searchByNameOrEmailOrPhoneAndFilterByGenderAndRoleAndStatusAndSortMultiField(String search, Pageable pageable) {

        ArrayList<String> roles = pageable.getFilters().get("role");

        GroupDBContext groupDB = new GroupDBContext();
        ResultPageable<User> resultPageable = new ResultPageable<User>();
        ArrayList<User> users = new ArrayList<>();
        String sql_query_data = "SELECT *\n"
                + "FROM\n"
                + "  (SELECT * ,\n"
                + "          ROW_NUMBER() OVER (ORDER BY ";
        if (pageable.getOrderings() != null && !pageable.getOrderings().isEmpty()) {
            for (Map.Entry<String, String> en : pageable.getOrderings().entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                key = key.split("[.]")[1];
                if (key.equalsIgnoreCase("status")) {
                    key = "is_active";
                } else if (key.equalsIgnoreCase("super") || key.equalsIgnoreCase("staff")) {
                    key = "is_" + key;
                } else if (key.equalsIgnoreCase("firstName")) {
                    key = "first_name";
                } else if (key.equalsIgnoreCase("lastName")) {
                    key = "last_name";
                }
                sql_query_data += " [user].[" + key + "] " + val + ",";
            }
            sql_query_data = sql_query_data.substring(0, sql_query_data.length() - 1);
        } else {
            sql_query_data += " [user].[id] DESC ";
        }
        sql_query_data += " ) AS row_index\n";
        sql_query_data += "   FROM\n"
                + "     (SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "      FROM [user] ) [user]\n";
        if (roles != null && !roles.isEmpty()) {
            sql_query_data += "      INNER JOIN [group_user] ON [group_user].[userid] = [user].[id]\n";
        }
        sql_query_data += " WHERE ([user].[fullname] LIKE ?\n"
                + "     OR [user].[email] LIKE ?\n"
                + "     OR [user].[phone] LIKE ?\n"
                + "     OR [user].[username] LIKE ?)";
        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_query_data += " AND ( ";
            if (key.equalsIgnoreCase("role")) {
                key = " [group_user].[groupid] ";
            } else if (key.equalsIgnoreCase("status")) {
                key = " [user].[is_active] ";
            } else {
                key = " [user].[" + key + "] ";
            }
            sql_query_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_query_data += ",?";
            }
            sql_query_data += ") ) ";
        }

        sql_query_data += " ) [user] WHERE row_index >= (? - 1) * ? + 1\n"
                + "  AND row_index <= ? * ?";

        String sql_count_data = "SELECT COUNT([user].[id]) as size FROM(SELECT [user].[id] ,\n"
                + "             [user].[username] ,\n"
                + "             [user].[password] ,\n"
                + "             [user].[email],\n"
                + "             [user].[phone],\n"
                + "             [user].[first_name] ,\n"
                + "             [user].[last_name],\n"
                + "             [user].[gender],\n"
                + "             [user].[avatar],\n"
                + "             [user].[is_staff],\n"
                + "             [user].[is_super],\n"
                + "             [user].[enable],\n"
                + "             [user].[is_active] ,\n"
                + "             [user].[first_name] + ' ' + [user].[last_name] AS 'fullname'\n"
                + "     FROM [user] ) [user]\n";
        if (roles != null && !roles.isEmpty()) {
            sql_count_data += "  INNER JOIN [group_user] ON [group_user].[userid] = [user].[id]\n";
        }
        sql_count_data += " WHERE ([user].[fullname] LIKE ?\n"
                + "     OR [user].[email] LIKE ?\n"
                + "     OR [user].[phone] LIKE ?\n"
                + "     OR [user].[username] LIKE ?)";
        for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> val = entry.getValue();
            sql_count_data += " AND ( ";
            if (key.equalsIgnoreCase("role")) {
                key = " [group_user].[groupid] ";
            } else if (key.equalsIgnoreCase("status")) {
                key = " [user].[is_active] ";
            } else {
                key = " [user].[" + key + "] ";
            }
            sql_count_data += key + " in (?";
            for (int i = 1; i < val.size(); i++) {
                sql_count_data += ",?";
            }
            sql_count_data += ") ) ";
        }

        System.out.println(sql_query_data);

        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql_query_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            stm.setString(3, "%" + search + "%");
            stm.setString(4, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("active");
                            stm.setBoolean(count + 5, isStatus);
                        } else if (key.equalsIgnoreCase("gender")) {
                            String genderParam = val.get(i);
                            boolean isGender = genderParam != null && !genderParam.isEmpty() && genderParam.equalsIgnoreCase("male");
                            stm.setBoolean(count + 5, isGender);
                        } else {
                            stm.setString(count + 5, val.get(i));
                        }
                        count++;
                    }
                }
                stm.setInt(count - 1 + 6, pageable.getPageIndex());
                stm.setInt(count - 1 + 7, pageable.getPageSize());
                stm.setInt(count - 1 + 8, pageable.getPageIndex());
                stm.setInt(count - 1 + 9, pageable.getPageSize());
            } else {
                stm.setInt(5, pageable.getPageIndex());
                stm.setInt(6, pageable.getPageSize());
                stm.setInt(7, pageable.getPageIndex());
                stm.setInt(8, pageable.getPageSize());
            }

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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                users.add(user);
            }
            resultPageable.setList(users);

            stm = connection.prepareCall(sql_count_data);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            stm.setString(3, "%" + search + "%");
            stm.setString(4, "%" + search + "%");
            if (pageable.getFilters() != null && !pageable.getFilters().isEmpty()) {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : pageable.getFilters().entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> val = entry.getValue();
                    for (int i = 0; i < val.size(); i++) {
                        if (key.equalsIgnoreCase("status")) {
                            String statusParams = val.get(i);
                            boolean isStatus = statusParams != null && !statusParams.isEmpty() && statusParams.equalsIgnoreCase("active");
                            stm.setBoolean(count + 5, isStatus);
                        } else if (key.equalsIgnoreCase("gender")) {
                            String genderBoolean = val.get(i);
                            boolean isGender = genderBoolean != null && !genderBoolean.isEmpty() && genderBoolean.equalsIgnoreCase("male");
                            stm.setBoolean(count + 5, isGender);
                        } else {
                            stm.setString(count + 5, val.get(i));
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
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Map<String, ArrayList<User>> dataByDateMonth(Timestamp dateStart, Timestamp dateEnd) {
        Map<String, ArrayList<User>> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        GroupDBContext groupDB = new GroupDBContext();
        String sql = "SELECT [user].[id]\n"
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
                + "     ,[user].[create_at]\n"
                + "  FROM [user]\n"
                + " WHERE [user].[create_at] >= ? AND [user].[create_at] <= ? ";
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, dateStart);
            stm.setTimestamp(2, dateEnd);
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
                user.setAvatar(rs.getString("avatar"));
                user.setGender(rs.getBoolean("gender"));
                user.set_active(rs.getBoolean("is_active"));
                user.set_staff(rs.getBoolean("is_staff"));
                user.set_super(rs.getBoolean("is_super"));
                user.setEnable(rs.getBoolean("enable"));
                user.setCreated_at(rs.getTimestamp("create_at"));
                ArrayList<Group> groups = groupDB.getByUserId(user.getId());
                user.setGroups(groups);
                String key = sdf.format(new java.util.Date(user.getCreated_at().getTime()));
                if (maps.get(key) != null) {
                    maps.get(key).add(user);
                } else {
                    ArrayList<User> users = new ArrayList<>();
                    users.add(user);
                    maps.put(key, users);
                }
            }
            return maps;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
