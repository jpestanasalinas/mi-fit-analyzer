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

DROP TABLE IF EXISTS activitystage;

CREATE TABLE activitystage (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  date DATE NOT NULL,
  start TIMESTAMP ,
  stop TIMESTAMP ,
  distance DECIMAL,
  calories DECIMAL,
  steps INT
);

DROP TABLE IF EXISTS body;

CREATE TABLE body (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  timestamp TIMESTAMP,
  weight DECIMAL,
  height DECIMAL,
  bmi INT,
  fatRate DECIMAL,
  bodyWaterRate DECIMAL,
  boneMass DECIMAL,
  metabolism DECIMAL,
  muscleRate DECIMAL,
  visceralFat DECIMAL,
  impedance DECIMAL
);

DROP TABLE IF EXISTS heartrate;

CREATE TABLE heartrate (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  date DATE,
  lastSyncTime TIMESTAMP,
  heartRate INT,
  timestamp TIMESTAMP
);