package com.daniil.application;

import com.daniil.databases.DatabaseInitializer;
import com.daniil.dao.EmployeeDao;
import com.daniil.models.employee.Employee;
import com.daniil.services.employee.EmployeeGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Необходимо передать номер команды. Например: myApp 1");
            return;
        }

        String command = args[0];

        switch (command) {
            case "1":
                DatabaseInitializer databaseInitializer = new DatabaseInitializer();
                databaseInitializer.initializeDatabase();
                System.out.println("База данных успешно инициализирована.");
                break;

            case "2":
                EmployeeDao saveEmployeeDao = new EmployeeDao();
                saveEmployeeDao.saveEmployeesFromArgs(args);
                System.out.println("Все работники были успешно добавлены!");
                break;

            case "3":
                EmployeeDao displayUniqueEmployeeDao = new EmployeeDao();
                displayUniqueEmployeeDao.displayUniqueEmployees();
                break;

            case "4":
                List<Employee> employees = EmployeeGenerator.generateEmployees(1000000);
                EmployeeDao saveAllEmployeesEmployeeDao = new EmployeeDao();
                saveAllEmployeesEmployeeDao.saveAllToDatabase(employees);
                break;

            case "5":
                EmployeeDao getExecutionTimeEmployeeDao = new EmployeeDao();
                getExecutionTimeEmployeeDao.displayExecutionTime();
        }
    }
}
