package sun.support.scheduler.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sun.support.scheduler.core.EntranceGuard;
import sun.support.scheduler.core.JobRunFlag;
import sun.support.scheduler.core.JobType;
import sun.support.scheduler.core.ScheduleStatus;
import sun.support.scheduler.domain.JobEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by yamorn on 2016/5/12.
 */
public class JobOperation extends AbstractDataAccessOperation<JobEntity, String> {

    public static final String TABLE_JOB_CONTROL = "JOB_CONTROL";

    private enum TableFields {
        JOB_NAME,
        JOB_STATUS,
        JOB_LOCK,
        RUN_FLAG,
        JOB_GROUP,
        JOB_CLASS_NAME,
        SCHEDULE_NAME,
        LAST_START_TIME,
        LAST_END_TIME,
        TASKS_NUM,
        JOB_TYPE;

        public static String[] toArray() {
            List<String> fields = new ArrayList<>();
            for (TableFields field : TableFields.values()) {
                fields.add(field.toString());
            }
            return fields.toArray(new String[TableFields.values().length]);
        }

        public static String toFields() {
            return Arrays.toString(TableFields.toArray()).replaceAll("(^\\[|\\]$)", "");
        }
    }


    private String tableName() {
        return this.tablePrefix + "_" + TABLE_JOB_CONTROL;
    }

    private JobEntity inflateEntity(ResultSet rs) throws SQLException {
        JobEntity entity = new JobEntity();
        entity.setJobName(rs.getString(TableFields.JOB_NAME.name()));
        entity.setJobStatus(rs.getString(TableFields.JOB_STATUS.name()));
        entity.setJobLock(rs.getInt(TableFields.JOB_LOCK.name()));
        entity.setRunFlag(rs.getInt(TableFields.RUN_FLAG.name()));
        entity.setJobGroup(rs.getString(TableFields.JOB_GROUP.name()));
        entity.setJobClassName(rs.getString(TableFields.JOB_CLASS_NAME.name()));
        entity.setScheduleName(rs.getString(TableFields.SCHEDULE_NAME.name()));
        entity.setLastStartTime(rs.getDate(TableFields.LAST_START_TIME.name()));
        entity.setLastEndTime(rs.getDate(TableFields.LAST_END_TIME.name()));
        entity.setTasksNum(rs.getInt(TableFields.TASKS_NUM.name()));
        entity.setJobType(rs.getString(TableFields.JOB_TYPE.name()));
        return entity;
    }

    @Override
    public JobEntity query(String id, boolean lock) {
        String sql = String.format("SELECT %s FROM %s WHERE %s = ? " + (lock ? "FOR UPDATE" : ""),
                TableFields.toFields(), tableName(), TableFields.JOB_NAME);
        try {
            return jdbcTemplate.queryForObject(sql, new RowMapper<JobEntity>() {
                @Override
                public JobEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return inflateEntity(rs);
                }
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<JobEntity> queryAll() {
        String sql = String.format("SELECT * FROM %s", tableName());

        return jdbcTemplate.query(sql, new RowMapper<JobEntity>() {
            @Override
            public JobEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                return inflateEntity(resultSet);
            }
        });
    }

    @Override
    public List<JobEntity> query(JobType jobType) {
        String sql = String.format("SELECT * FROM %s WHERE JOB_TYPE = '%s'", tableName(), jobType.name());
        return jdbcTemplate.query(sql, new RowMapper<JobEntity>() {
            @Override
            public JobEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                return inflateEntity(rs);
            }
        });
    }

    @Override
    public boolean insert(JobEntity entity) {
        final String sql = String.format("INSERT INTO %s (%s) VALUES (?,?,?,?,?,?,?,?,?,?,?)", tableName(),
                TableFields.toFields());
        final Object[] args = {entity.getJobName(), entity.getJobStatus(), entity.getJobLock(), entity.getRunFlag(),
                entity.getJobGroup(), entity.getJobClassName(), entity.getScheduleName(), null, null,
                entity.getTasksNum(), entity.getJobType()};

        return updateWithinTransaction(sql, args);
    }

    @Override
    public boolean updateLockStatus(final String id, final EntranceGuard lockStatus) {
        String timeField = lockStatus == EntranceGuard.RELEASE ? TableFields.LAST_START_TIME.name() :
                TableFields.LAST_END_TIME.name();
        final String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                tableName(), TableFields.JOB_LOCK, timeField, TableFields.JOB_NAME.name());

        return updateWithinTransaction(sql, new Object[]{lockStatus.getStatus(), new Date(), id});
    }

    @Override
    public boolean updateJobStatus(String id, ScheduleStatus scheduleStatus) {
        String timeField = scheduleStatus == ScheduleStatus.RUNNING ? TableFields.LAST_START_TIME.name() :
                TableFields.LAST_END_TIME.name();
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                tableName(), TableFields.JOB_STATUS, timeField, TableFields.JOB_NAME.name());
        return jdbcTemplate.update(sql, scheduleStatus.name(), new Date(), id) == 1;
    }

    @Override
    public boolean pessimisticLockQuery(final String id, final OperationCallback<JobEntity, Boolean> callback) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                boolean result = false;
                try {
                    JobEntity entity = query(id, true);
                    if (entity != null) {
                        result = callback.process(entity);
                    }
                } catch (Exception e) {
                    status.setRollbackOnly();
                }
                return result;
            }
        });
    }

    @Override
    public boolean releaseJobLock(String id) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                tableName(), TableFields.JOB_LOCK, TableFields.JOB_STATUS, TableFields.JOB_NAME);
        return updateWithinTransaction(sql,
                new Object[]{EntranceGuard.RELEASE.getStatus(), ScheduleStatus.WAIT.name(), id});
    }

    @Override
    public boolean hangup(String id, int flag) {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                tableName(), TableFields.RUN_FLAG, TableFields.JOB_NAME);
        return updateWithinTransaction(sql,
                new Object[]{(flag == 0 ? JobRunFlag.RUNNING.getValue() : JobRunFlag.HANGUP.getValue()), id});
    }

    @Override
    public void log(JobEntity entity) {

    }
}
