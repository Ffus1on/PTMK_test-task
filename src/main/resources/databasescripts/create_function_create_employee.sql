CREATE OR REPLACE FUNCTION insert_employee(full_name VARCHAR, birth_date DATE, sex VARCHAR)
    RETURNS VOID AS $$
BEGIN
    INSERT INTO employees (FullName, BirthDate, sex)
    VALUES (full_name, birth_date, sex);
END;
$$ LANGUAGE plpgsql;
