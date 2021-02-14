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

DROP TABLE IF EXISTS activityminute;

CREATE TABLE activityminute (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  date DATE NOT NULL,
  time TIMESTAMP ,
  steps INT
);

DROP TABLE IF EXISTS activity;

CREATE TABLE activity (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  date DATE NOT NULL,
  start TIMESTAMP ,
  stop TIMESTAMP ,
  distance DECIMAL,
  calories DECIMAL,
  steps INT
);