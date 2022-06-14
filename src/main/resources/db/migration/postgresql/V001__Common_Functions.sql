-- in postgresql there is no 'ON UPDATE' statement (example below), so you have to do a trigger.
-- updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

CREATE OR REPLACE FUNCTION upd_timestamp() RETURNS TRIGGER
LANGUAGE plpgsql
AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;
