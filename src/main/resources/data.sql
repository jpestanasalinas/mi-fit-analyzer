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

DROP TABLE IF EXISTS heartrateAuto;

CREATE TABLE heartrateAuto (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  date DATE,
  time TIMESTAMP,
  heartRate INT
  );

  DROP TABLE IF EXISTS sleep;

  CREATE TABLE sleep (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    date DATE,
    lastSyncTime TIMESTAMP,
    deepSleepTime INT,
    shallowSleepTime INT,
    wakeTime INT,
    start TIMESTAMP,
    stop TIMESTAMP
    );

      DROP TABLE IF EXISTS sport;

      CREATE TABLE sport (
        id INT AUTO_INCREMENT  PRIMARY KEY,
        type INT,
        startTime TIMESTAMP,
        sportTime INT,
        distance INT,
        maxPace INT,
        minPace INT,
        avgPace INT,
        calories DECIMAL
        );