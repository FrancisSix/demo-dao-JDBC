package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
                    "INSERT INTO Department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, obj.getName());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! no rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);

        }

    }

    @Override
    public void update(Department obj) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    UPDATE Department
                    SET Name = ?
                    WHERE Id = ?""");

            statement.setString(1, obj.getName());

            statement.setInt(2, obj.getId());

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("""
                    DELETE FROM Department
                    WHERE Id = ?""");
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Rows deleted = " + rowsAffected);
            } else {
                throw new DbException("Department not found!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Department WHERE Id = ?");

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return instantiateDepartment(resultSet);
            }else {

                throw new DbException("Department not found");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM department");

            resultSet = statement.executeQuery();

            List<Department> departmentList = new ArrayList<>();

            while (resultSet.next()) {

                Department department = instantiateDepartment(resultSet);
                departmentList.add(department);
            }
            return departmentList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
