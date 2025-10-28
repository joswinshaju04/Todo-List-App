package dao;

import model.TodoModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "");
    }

    public List<TodoModel> getTasks(String username) {
        List<TodoModel> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE created_by = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new TodoModel(
                    rs.getInt("id"),
                    rs.getString("created_by"),
                    rs.getString("description"),
                    rs.getString("created_date"),
                    rs.getString("created_time"),
                    rs.getString("due_date"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void addTask(String username, String desc, String dueDate) {
        String sql = "INSERT INTO tasks (created_by, description, created_date, created_time, due_date, status) VALUES (?, ?, CURDATE(), CURTIME(), ?, 'Pending')";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, desc);
            stmt.setString(3, dueDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskById(int id, String newDesc, String newDueDate) {
        String sql = "UPDATE tasks SET description = ?, due_date = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newDesc);
            stmt.setString(2, newDueDate);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskDoneById(int id) {
        String sql = "UPDATE tasks SET status = 'Done' WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskPendingById(int id) {
        String sql = "UPDATE tasks SET status = 'Pending' WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
