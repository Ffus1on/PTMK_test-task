CREATE OR REPLACE FUNCTION get_unique_employees()
    RETURNS TABLE(fullName VARCHAR, birthDate DATE, sex VARCHAR) AS $$
BEGIN
    RETURN QUERY
        SELECT DISTINCT ON (employees.fullName, employees.birthDate)
            employees.fullName,
            employees.birthDate,
            employees.sex
        FROM employees
        ORDER BY employees.fullName;
END;
$$ LANGUAGE plpgsql;
