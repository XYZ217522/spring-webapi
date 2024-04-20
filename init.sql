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
            description VARCHAR(100)
        );
        
        -- Insert data into the table
        INSERT INTO erp.job_log (startdt, enddt, status, jobname, description) VALUES 
            ('2024-02-23 12:00:00', '2024-02-23 12:30:00', 'Success', 'JobA', 'aaa'),
            ('2024-02-23 13:00:00', '2024-02-23 13:45:00', 'Failure', 'JobB', 'bbb'),
            ('2024-02-23 14:30:00', '2024-02-23 15:15:00', 'Success', 'JobC', 'ccc');
    END IF;
END $$;