DROP TABLE IF EXISTS ST_JOB_CONTROL;


CREATE TABLE ST_JOB_CONTROL(
  JOB_NAME VARCHAR(50) UNIQUE COMMENT 'JOB BEAN ID',
  JOB_STATUS VARCHAR(20) DEFAULT 'WAIT' COMMENT 'WAIT, RUNNING,COMPLETED,FAILED',
  JOB_LOCK INT DEFAULT 1 COMMENT '1 WAIT, 2 PROCESSING',
  JOB_GROUP VARCHAR(50) DEFAULT NULL COMMENT 'JOB THREAD GROUP',
  JOB_CLASS_NAME VARCHAR(100) DEFAULT NULL COMMENT 'JOB CLASS NAME',
  SCHEDULE_NAME VARCHAR(200) DEFAULT NULL COMMENT 'EXECUTOR NAME',
  LAST_START_TIME TIMESTAMP COMMENT 'START TIME',
  LAST_END_TIME TIMESTAMP COMMENT 'END TIME',
  RUN_FLAG INT DEFAULT 0,
  TASKS_NUM INT DEFAULT 0,
  JOB_TYPE VARCHAR(50) DEFAULT 'NORMAL',
  PRIMARY KEY (JOB_NAME)
)DEFAULT CHARSET =utf8;
