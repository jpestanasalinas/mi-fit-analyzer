DROP TABLE IF EXISTS activity;

CREATE TABLE activity (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  date DATE NOT NULL,
  last_sync_date TIMESTAMP ,
  steps INT,
  distance DECIMAL,
  run_distance DECIMAL,
  calories DECIMAL
);
