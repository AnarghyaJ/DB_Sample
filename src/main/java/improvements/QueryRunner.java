package improvements;

import org.apache.commons.dbutils.StatementConfiguration;

import javax.sql.DataSource;
import java.util.Properties;

public class QueryRunner {
    DataSource ds;
    StatementConfiguration stmtConfig;
    Properties driverProperties = new Properties();


    public QueryRunner withDataSource(DataSource ds) {
        this.ds = ds;
        return this;
    }

    public QueryRunner usingStatementConfig(StatementConfiguration stmtConfig) {
        this.stmtConfig = stmtConfig;
        return this;
    }

    public QueryRunner withDriverProperties(Properties driverProperties) {
        this.driverProperties = driverProperties;
        return this;
    }

    public QueryRunner validate() {
        //Validate the properties
        if (ds == null) {
            throw new IllegalArgumentException("DataSource is required");
        }
        return this;
    }
}
