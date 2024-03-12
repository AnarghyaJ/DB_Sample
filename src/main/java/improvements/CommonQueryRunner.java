package improvements;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.StatementConfiguration;

import javax.sql.DataSource;
import java.sql.*;

public class CommonQueryRunner {

    protected void close(Connection conn) throws SQLException {
        DbUtils.close(conn);
    }

    public void fillStatement(PreparedStatement stmt, Object... params) throws SQLException {
        ParameterMetaData pmd = null;
    }

}
