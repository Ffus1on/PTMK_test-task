CREATE OR REPLACE FUNCTION get_execution_time()
    RETURNS TEXT AS $$
DECLARE
    start_time TIMESTAMP;
    end_time TIMESTAMP;
    elapsed_time INTERVAL;
    result TEXT;
BEGIN
    start_time := clock_timestamp();

    PERFORM * FROM employees WHERE sex = 'Male' AND fullname >= 'F' AND fullname < 'G';

    end_time := clock_timestamp();
    elapsed_time := end_time - start_time;

    result := 'Execution time: ' || EXTRACT(MILLISECOND FROM elapsed_time) || ' ms';
    RETURN result;
END;
$$ LANGUAGE plpgsql;
