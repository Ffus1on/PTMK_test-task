package com.daniil.services.employee;

import com.daniil.models.employee.Employee;

import java.time.LocalDate;
import java.util.*;

public class EmployeeGenerator {
    private static final String[] MALE_FIRST_NAMES = {"Alexander", "Ivan", "Sergey", "Dmitry", "Alexey", "Fedor"};
    private static final String[] FEMALE_FIRST_NAMES = {"Anna", "Elena", "Maria", "Olga", "Tatiana", "Faina"};
    private static final String[] MALE_PATRONYMICS = {
            "Alexandrovich", "Ivanovich", "Sergeyevich", "Dmitrievich", "Alexeyevich", "Fedorovich"
    };
    private static final String[] FEMALE_PATRONYMICS = {
            "Alexandrovna", "Ivanovna", "Sergeyevna", "Dmitrievna", "Alexeyevna", "Fedorovna"
    };
    private static final String[] F_SURNAMES = {
            "Fisher", "Ferguson", "Frost", "Fowler", "Fitzgerald", "Farrell", "Fleming", "Fields"
    };

    private static final HashMap<Character, String[]> SURNAMES_BY_LETTER = new HashMap<>() {{
        put('A', new String[]{"Adams", "Allen", "Armstrong"});
        put('B', new String[]{"Brown", "Baker", "Bennett"});
        put('C', new String[]{"Clark", "Collins", "Carter"});
        put('D', new String[]{"Davis", "Diaz", "Dixon"});
        put('E', new String[]{"Edwards", "Evans", "Ellis"});
        put('F', new String[]{"Fisher", "Fleming", "Ford"});
        put('G', new String[]{"Garcia", "Green", "Gonzalez"});
        put('H', new String[]{"Harris", "Hill", "Howard"});
    }};

    private static final Random random = new Random();

    public static List<Employee> generateEmployees(int count) {
        List<Employee> employees = new ArrayList<>();
        List<Character> letters = new ArrayList<>(SURNAMES_BY_LETTER.keySet());

        for (int i = 0; i < count; i++) {
            String fullName;
            LocalDate birthDate = generateRandomBirthDate();
            String sex;

            if (random.nextBoolean()) {
                sex = "Male";
                fullName = generateFullName(MALE_FIRST_NAMES, MALE_PATRONYMICS, letters);
            } else {
                sex = "Female";
                fullName = generateFullName(FEMALE_FIRST_NAMES, FEMALE_PATRONYMICS, letters);
            }

            employees.add(new Employee(fullName, birthDate, sex));
        }

        employees.addAll(generateUniqueFEmployees());

        return employees;
    }

    private static List<Employee> generateUniqueFEmployees() {
        HashSet<String> uniqueNames = new HashSet<>();
        List<Employee> employees = new ArrayList<>();

        while (uniqueNames.size() < 100) {
            String firstName = MALE_FIRST_NAMES[random.nextInt(MALE_FIRST_NAMES.length)];
            String patronymic = MALE_PATRONYMICS[random.nextInt(MALE_PATRONYMICS.length)];
            String surname = F_SURNAMES[random.nextInt(F_SURNAMES.length)];

            String fullName = surname + " " + firstName + " " + patronymic;
            if (uniqueNames.add(fullName)) {
                LocalDate birthDate = generateRandomBirthDate();
                employees.add(new Employee(fullName, birthDate, "Male"));
            }
        }

        return employees;
    }

    private static LocalDate generateRandomBirthDate() {
        int year = 1950 + random.nextInt(51);
        int dayOfYear = random.nextInt(365) + 1;
        return LocalDate.ofYearDay(year, dayOfYear);
    }

    private static String generateFullName(String[] firstNames, String[] patronymics, List<Character> letters) {

        if (letters.isEmpty()) {
            letters.addAll(SURNAMES_BY_LETTER.keySet());
        }

        String firstName = firstNames[random.nextInt(firstNames.length)];
        String patronymic = patronymics[random.nextInt(patronymics.length)];

        char letter = letters.remove(random.nextInt(letters.size()));
        String[] possibleSurnames = SURNAMES_BY_LETTER.get(letter);
        String surname = possibleSurnames[random.nextInt(possibleSurnames.length)];

        return surname + " " + firstName + " " + patronymic;
    }
}
