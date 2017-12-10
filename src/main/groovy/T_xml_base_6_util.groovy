import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import java.sql.SQLException

class T_xml_base_6_util extends T_xml_base_5_context {

    static void sql_update(String i_sql_string, Object[] i_bind_variables = GC_SKIPPED_ARGS) {
        if (method_arguments_present(i_bind_variables)) {
            get_sql().executeUpdate(i_sql_string, i_bind_variables)
        } else {
            get_sql().executeUpdate(i_sql_string)
        }
        commit()
    }

    static void commit() {
        get_sql().commit()
    }

    static Sql get_sql() {
        if ((System.currentTimeMillis() - get_context().p_sql_last_init_time_millis) >= new Long(c().GC_SQL_SESSION_REFRESH_INTERVAL_MILLISECONDS)) {
            init_sql()
        }
        return get_context().p_sql
    }

    static void each_row(String i_sql, Closure i_closure) throws SQLException {
        get_sql().eachRow(i_sql, i_closure)
    }

    static GroovyRowResult first_row(String i_sql) throws SQLException {
        return get_sql().firstRow(i_sql)
    }


}
