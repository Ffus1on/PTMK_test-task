package com.daniil.models.employee;

import com.daniil.databases.connections.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;

public class Employee {
    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String sex;

    public Employee(String fullName, LocalDate birthDate, String sex) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.sex = sex;
    }

    public int calculateAge() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public void saveToDatabase() {
        String sql = "SELECT insert_employee(?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, fullName);
            preparedStatement.setDate(2, Date.valueOf(birthDate));
            preparedStatement.setString(3, sex);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                '}';
    }
}
