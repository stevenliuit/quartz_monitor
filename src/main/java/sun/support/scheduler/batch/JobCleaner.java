package sun.support.scheduler.batch;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sun.support.scheduler.core.DBType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 2016/3/8.
 */
public class JobCleaner {
    private String tablePrefix;

    private String FIND_JOB_INSTANCE_ID_SQL = "select job_instance_id from %s_job_instance where job_name=?";

    private  String FIND_JOB_EXECUTION_ID_SQL = "select job_execution_id from %s_job_execution where job_instance_id=?";

    private  String FIND_STEP_EXECUTION_ID_SQL = "select step_execution_id from %s_step_execution where job_execution_id=?";

    private  String DEL_STEP_EXECUTION_SEQ_SQL_MYSQL = "delete from %s_step_execution_seq where id=?";

    private  String DEL_STEP_EXECUTION_CONTEXT_SQL = "delete from %s_step_execution_context where step_execution_id=?";

    private  String DEL_STEP_EXECUTION_SQL = "delete from %s_step_execution where job_execution_id=?";

    private  String DEL_JOB_EXECUTION_SEQ_SQL_MYSQL = "delete from %s_job_execution_seq where id=?";

    private  String DEL_JOB_EXECUTION_PARAMS_SQL = "delete from %s_job_execution_params where job_execution_id=?";

    private  String DEL_JOB_EXECUTION_CONTEXT_SQL = "delete from %s_job_execution_context where job_execution_id=?";

    private  String DEL_JOB_EXECUTION_SQL = "delete from %s_job_execution where job_instance_id=?";

    private  String DEL_JOB_INSTANCE_SQL = "delete from %s_job_instance where job_instance_id=?";

    private  String DEL_JOB_SEQ_SQL_MYSQL = "delete from %s_job_seq where id=?";

    // fix sql

    private  String INIT_STEP_EXECUTION_SEQ_SQL_MYSQL = "INSERT INTO %s_STEP_EXECUTION_SEQ values(?,0)";

    private  String INIT_JOB_EXECUTION_SEQ_SQL_MYSQL = "INSERT INTO %s_JOB_EXECUTION_SEQ values(?,0)";

    private  String INIT_JOB_SEQ_SQL_MYSQL = "INSERT INTO %s_JOB_SEQ values(?,0)";

    private  String DEL_INIT_STEP_EXECUTION_SEQ_SQL_MYSQL = "DELETE FROM %s_STEP_EXECUTION_SEQ WHERE ID=0";

    private  String DEL_INIT_JOB_EXECUTION_SEQ_SQL_MYSQL = "DELETE FROM %s_JOB_EXECUTION_SEQ WHERE ID=0";

    private  String DEL_INIT_JOB_SEQ_SQL_MYSQL = "DELETE FROM %s_JOB_SEQ WHERE ID=0";

    private JdbcTemplate jdbcTemplate;

    private DBType dbType;    // MySQL, Oracle

    public JobCleaner(){}

    public JobCleaner(JdbcTemplate jdbcTemplate, DBType dbType) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbType = dbType;
    }

    public JobCleaner(JdbcTemplate jdbcTemplate, DBType dbType, String tablePrefix) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbType = dbType;
        this.tablePrefix = tablePrefix;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    private String buildSQL(String sql){
        if (tablePrefix.endsWith("_")) {
            tablePrefix = tablePrefix.substring(0, tablePrefix.length() - 1);
        }
        return String.format(sql, tablePrefix);
    }

    public final void cleanBeforeJobLaunch(String jobName) {
        List<Long> jobInstances = findJobInstanceIdByName(jobName);
        if (jobInstances != null && jobInstances.size() > 0) {
            for (Long jobInstanceId : jobInstances) {
                Long jobExecutionId = findJobExecutionId(jobInstanceId);
                if (jobExecutionId != null) {
                    List<Long> stepExecutions = findStepExecutionId(jobExecutionId);
                    if (stepExecutions != null && stepExecutions.size() > 0) {
                        for (Long stepExecutionId : stepExecutions) {
                            if (dbType == DBType.MYSQL ) {
                                delete(buildSQL(DEL_STEP_EXECUTION_SEQ_SQL_MYSQL), stepExecutionId);
                            }
                            delete(buildSQL(DEL_STEP_EXECUTION_CONTEXT_SQL), stepExecutionId);
                        }
                    }
                    delete(buildSQL(DEL_STEP_EXECUTION_SQL), jobExecutionId);
                    if (dbType == DBType.MYSQL) {
                        delete(buildSQL(DEL_JOB_EXECUTION_SEQ_SQL_MYSQL), jobExecutionId);
                    }
                    delete(buildSQL(DEL_JOB_EXECUTION_PARAMS_SQL), jobExecutionId);
                    delete(buildSQL(DEL_JOB_EXECUTION_CONTEXT_SQL), jobExecutionId);
                }
                delete(buildSQL(DEL_JOB_EXECUTION_SQL), jobInstanceId);
                delete(buildSQL(DEL_JOB_INSTANCE_SQL), jobInstanceId);
                if (dbType == DBType.MYSQL) {
                    delete(buildSQL(DEL_JOB_SEQ_SQL_MYSQL), jobInstanceId);
                }
//                // init seq to fix  Exception: Duplicate entry '0' for key 'PRIMARY' when insert value to BATCH_JOB_INSTANCE
                if (dbType == DBType.MYSQL) {
                    jdbcTemplate.update(buildSQL(INIT_STEP_EXECUTION_SEQ_SQL_MYSQL), jobInstanceId);
                    jdbcTemplate.update(buildSQL(INIT_JOB_EXECUTION_SEQ_SQL_MYSQL), jobInstanceId);
                    jdbcTemplate.update(buildSQL(INIT_JOB_SEQ_SQL_MYSQL), jobInstanceId);
                }
            }
        }else{
            // init seq to fix  Exception: Duplicate entry '0' for key 'PRIMARY' when insert value to BATCH_JOB_INSTANCE
            if (dbType == DBType.MYSQL) {
                //delete
                jdbcTemplate.execute(buildSQL(DEL_INIT_STEP_EXECUTION_SEQ_SQL_MYSQL));
                jdbcTemplate.execute(buildSQL(DEL_INIT_JOB_EXECUTION_SEQ_SQL_MYSQL));
                jdbcTemplate.execute(buildSQL(DEL_INIT_JOB_SEQ_SQL_MYSQL));
                // insert
                jdbcTemplate.update(buildSQL(INIT_STEP_EXECUTION_SEQ_SQL_MYSQL), 0);
                jdbcTemplate.update(buildSQL(INIT_JOB_EXECUTION_SEQ_SQL_MYSQL), 0);
                jdbcTemplate.update(buildSQL(INIT_JOB_SEQ_SQL_MYSQL), 0);
            }
        }
    }

    private List<Long> findJobInstanceIdByName(String jobName) {
        return jdbcTemplate.queryForList(buildSQL(FIND_JOB_INSTANCE_ID_SQL), Long.TYPE, jobName);
    }

    private Long findJobExecutionId(long jobInstanceId) {
        return jdbcTemplate.queryForObject(buildSQL(FIND_JOB_EXECUTION_ID_SQL), new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong(1);
            }
        }, jobInstanceId);
    }

    private List<Long> findStepExecutionId(long jobExecutionId) {
        return jdbcTemplate.queryForList(buildSQL(FIND_STEP_EXECUTION_ID_SQL), Long.TYPE, jobExecutionId);
    }


    private void delete(String sql, long id) {
        jdbcTemplate.update(sql, id);
    }

}
