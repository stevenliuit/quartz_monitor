
DROP TABLE ST_JOB_CONTROL;


CREATE TABLE ST_JOB_CONTROL(
  JOB_NAME VARCHAR2(50) UNIQUE ,
  JOB_STATUS VARCHAR2(20) DEFAULT 'WAIT',
  JOB_LOCK INT DEFAULT 1 ,
  JOB_GROUP VARCHAR2(50) DEFAULT NULL ,
  JOB_CLASS_NAME VARCHAR2(100) DEFAULT NULL,
  SCHEDULE_NAME VARCHAR2(200) DEFAULT NULL,
  LAST_START_TIME TIMESTAMP,
  LAST_END_TIME TIMESTAMP,
  RUN_FLAG INT DEFAULT 0,
  TASKS_NUM INT DEFAULT 0,
  JOB_TYPE VARCHAR2(50) DEFAULT 'NORMAL'
);