import groovy.sql.Sql

class T_xml_base_5_context extends T_xml_base_4_const {

    protected static ThreadLocal<T_xml_base_5_context> p_context_thread_local = new ThreadLocal<T_xml_base_5_context>()
    protected Sql p_sql = GC_NULL_OBJ_REF as Sql
    private T_xml_conf p_commons = GC_NULL_OBJ_REF as T_xml_conf
    protected Long p_sql_last_init_time_millis = GC_NULL_OBJ_REF as Long

    static void init_custom(String i_commons_conf_file_name) {
        get_context().p_commons = new T_xml_conf(i_commons_conf_file_name)
        //init_sql()
    }

    static void init_sql() {
        get_context().p_sql = Sql.newInstance(c().GC_MYSQL_CONNECTION_STRING, c().GC_MYSQL_USERNAME, c().GC_MYSQL_PASSWORD, c().GC_MYSQL_DRIVER)
        get_context().p_sql.getConnection().setAutoCommit(GC_FALSE)
        get_context().p_sql_last_init_time_millis = System.currentTimeMillis()
    }

    static T_xml_base_5_context get_context() {
        if (is_null(p_context_thread_local.get())) {
            p_context_thread_local.set(new T_xml_base_5_context())
        }
        return p_context_thread_local.get()
    }

    static T_xml_conf c() {
        return get_context().p_commons
    }

}
