CREATE OR REPLACE FUNCTION set_timestamp() RETURNS TRIGGER AS $$
BEGIN
  IF TG_OP = 'INSERT' THEN
    NEW.created_at := COALESCE(NEW.created_at, CURRENT_TIMESTAMP);
    NEW.updated_at := COALESCE(NEW.updated_at, CURRENT_TIMESTAMP);
  ELSE
    NEW.updated_at := CURRENT_TIMESTAMP;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_learning_data_timestamp
BEFORE INSERT OR UPDATE ON learning_data
FOR EACH ROW EXECUTE FUNCTION set_timestamp();
