-- Check if the table exists before creating it
DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'erp' AND table_name = 'job_log') THEN
        -- Create schema if not exists
        CREATE SCHEMA IF NOT EXISTS erp;
        
        -- Switch to the "erp" schema
        SET search_path TO erp;
        
        -- Create the table
        CREATE TABLE erp.job_log (
            flowno SERIAL PRIMARY KEY,
            startdt TIMESTAMP,
            enddt TIMESTAMP,
            status VARCHAR(10),
            jobname VARCHAR(50),
            description VARCHAR(100),
            update_time TIMESTAMP,
        );
        
        -- Insert data into the table
        INSERT INTO erp.job_log (startdt, enddt, status, jobname, description,update_time) VALUES
            ('2024-02-23 12:00:00', '2024-02-23 12:30:00', 'Success', 'JobA', 'aaa', CURRENT_TIMESTAMP),
            ('2024-02-23 13:00:00', '2024-02-23 13:45:00', 'Failure', 'JobB', 'bbb', CURRENT_TIMESTAMP),
            ('2024-02-23 14:30:00', '2024-02-23 15:15:00', 'Success', 'JobC', 'ccc', CURRENT_TIMESTAMP);

        CREATE TABLE IF NOT EXISTS erp.user (
            id SERIAL PRIMARY KEY,
            firstname VARCHAR(20),
            lastname VARCHAR(20),
            email VARCHAR(100) UNIQUE,
            password VARCHAR(200),
            role VARCHAR(10),
            tokens varchar[]
        );

    END IF;
END $$;