package com.daniil.dao;

import com.daniil.databases.connections.DatabaseConnection;
import com.daniil.models.employee.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    public void saveEmployeesFromArgs(String[] args) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i < args.length; i += 3) {
            if (i + 2 < args.length) {
                String fullName = args[i];
                LocalDate birthDate = LocalDate.parse(args[i + 1]);
                String sex = args[i + 2];

                Employee employee = new Employee(fullName, birthDate, sex);
                employees.add(employee);
            } else {
                System.out.println("Недостаточно аргументов для создания сотрудника.");
                break;
            }
        }

        for (Employee employee : employees) {
            employee.saveToDatabase();
        }
    }

    private List<String> getUniqueEmployees() {
        String sql = "SELECT * FROM get_unique_employees()";
        List<String> employees = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String fullName = resultSet.getString("fullName");
                LocalDate birthDate = resultSet.getDate("birthDate").toLocalDate();
                String sex = resultSet.getString("sex");

                Employee employee = new Employee(fullName, birthDate, sex);
                int age = employee.calculateAge();

                employees.add(String.format("ФИО: %s, Дата рождения: %s, Пол: %s, Возраст: %d",
                        fullName, birthDate, sex, age));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving employees: " + e.getMessage());
        }

        return employees;
    }

    public void displayUniqueEmployees() {
        getUniqueEmployees().forEach(System.out::println);
    }

    public void saveAllToDatabase(List<Employee> employees) {
        String sql = "SELECT insert_employee(?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Employee employee : employees) {
                preparedStatement.setString(1, employee.getFullName());
                preparedStatement.setDate(2, Date.valueOf(employee.getBirthDate()));
                preparedStatement.setString(3, employee.getSex());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    private String getExecutionTime() {
        String executionTime = null;
        String sql = "SELECT get_execution_time();";

        try(Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                executionTime = resultSet.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return executionTime;
    }

    public void displayExecutionTime() {
        System.out.println(getExecutionTime());
    }

}
