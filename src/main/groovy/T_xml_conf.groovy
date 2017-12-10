import base.T_common_base_3_utils
import other.T_common_conf

class T_xml_conf extends T_common_conf {

    String GC_MYSQL_CONNECTION_STRING
    String GC_MYSQL_USERNAME
    String GC_MYSQL_PASSWORD
    String GC_MYSQL_DRIVER
    String GC_SQL_SESSION_REFRESH_INTERVAL_MILLISECONDS

    T_xml_conf(String i_conf_file_name) {
        super(i_conf_file_name)
    }

    @Override
    void refresh_config() {
        GC_MYSQL_CONNECTION_STRING = T_common_base_3_utils.nvl_empty_map(get_conf().mysql_connection_string, GC_MYSQL_CONNECTION_STRING)
        GC_MYSQL_USERNAME = T_common_base_3_utils.nvl_empty_map(get_conf().mysql_username, GC_MYSQL_USERNAME)
        GC_MYSQL_PASSWORD = T_common_base_3_utils.nvl_empty_map(get_conf().mysql_password, GC_MYSQL_PASSWORD)
        GC_MYSQL_DRIVER = T_common_base_3_utils.nvl_empty_map(get_conf().mysql_driver, GC_MYSQL_DRIVER)
        GC_SQL_SESSION_REFRESH_INTERVAL_MILLISECONDS = T_common_base_3_utils.nvl_empty_map(get_conf().sql_session_refresh_interval_milliseconds, GC_SQL_SESSION_REFRESH_INTERVAL_MILLISECONDS)
    }
}
