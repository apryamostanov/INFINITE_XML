import static T_mighty_xml_csv_sql.GC_NULL_OBJECT_REFERENCE

class T_xml_tag extends T_xml_base_6_util {
    static final String GC_CSV_FILE_PATH = "C:\\AP\\CATEGORIZED\\CITI\\OUT\\"
    static final String GC_NO_TAG_NAME = "NO_TAG"
    static final Integer GC_NO_TAG_ID = -1
    static final String GC_NO_TAG_META_DATA = "NO_TAG_META_DATA"
    static final String GC_NO_TAG_VALUE = "NO_TAG_VALUE"
    static final Integer GC_INITIAL_TAG_ID = 1000000002
    static final Integer GC_ID_INCREMENT = 2
    static final String GC_CSV_SEPARATOR = ";"
    static final String GC_NEW_LINE = "\n"
    static Integer gv_tag_id = GC_INITIAL_TAG_ID
    T_xml_tag p_parent_tag = GC_NULL_OBJECT_REFERENCE as T_xml_tag
    String p_tag_name = GC_NO_TAG_NAME
    Integer p_tag_id = GC_NO_TAG_ID
    String p_tag_meta_data = GC_NO_TAG_META_DATA
    String p_tag_value = GC_NO_TAG_VALUE
    static HashMap<String, FileWriter> p_file_writer_per_tag_name = new HashMap<String, FileWriter>()
    static HashMap<String, String> p_table_by_tag_name = new HashMap<String, String>()

    T_xml_tag(
            T_xml_tag i_parent_tag
            , String i_tag_name
            , String i_tag_meta_data
            , String i_tag_value
    ) {
        gv_tag_id = gv_tag_id + GC_ID_INCREMENT
        p_parent_tag = i_parent_tag
        p_tag_name = i_tag_name
        p_tag_id = gv_tag_id
        p_tag_meta_data = i_tag_meta_data
        p_tag_value = i_tag_value
    }

    void save_to_db() {
        final String LC_CSV_PLACEHOLDER = "[CSVCHR]"
        String l_parent_tag_name = GC_NO_TAG_NAME
        String l_parent_tag_id = GC_NO_TAG_ID
        String l_sql_line
        String l_table_name
        if (p_parent_tag != null) {
            l_parent_tag_name = p_parent_tag.p_tag_name
            l_parent_tag_id = p_parent_tag.p_tag_id
        }
        l_table_name = p_table_by_tag_name.get(p_tag_name)
        if (is_null(l_table_name)) {
            l_table_name = "Z_" + p_tag_name
            String l_table_sql = "create table $l_table_name (own_id int not null, parent_tag_name varchar(256), parent_id int, meta_data longtext, own_value longtext)"
            System.out.println(l_table_sql)
            get_sql().execute(l_table_sql)
            p_table_by_tag_name.put(p_tag_name, l_table_name)
        }
        l_sql_line = "insert into $l_table_name values (?, ?, ?, ?, ?)"
        get_sql().executeInsert(l_sql_line, [p_tag_id, l_parent_tag_name, l_parent_tag_id, p_tag_meta_data.replace(GC_CSV_SEPARATOR, LC_CSV_PLACEHOLDER), p_tag_value.replace(GC_CSV_SEPARATOR, LC_CSV_PLACEHOLDER)])
        get_sql().commit()
    }

    void save_to_file() {
        final String LC_CSV_PLACEHOLDER = "[CSVCHR]"
        String l_parent_tag_name = GC_NO_TAG_NAME
        String l_parent_tag_id = GC_NO_TAG_ID
        String l_file_line
        FileWriter l_file_writer
        if (p_parent_tag != null) {
            l_parent_tag_name = p_parent_tag.p_tag_name
            l_parent_tag_id = p_parent_tag.p_tag_id
        }
        l_file_writer = p_file_writer_per_tag_name.get(p_tag_name)
        l_file_line = p_tag_id + GC_CSV_SEPARATOR + l_parent_tag_name + GC_CSV_SEPARATOR + l_parent_tag_id + GC_CSV_SEPARATOR + p_tag_meta_data.replace(GC_CSV_SEPARATOR, LC_CSV_PLACEHOLDER) + GC_CSV_SEPARATOR + p_tag_value.replace(GC_CSV_SEPARATOR, LC_CSV_PLACEHOLDER) + GC_CSV_SEPARATOR + GC_NEW_LINE
        if (l_file_writer != GC_NULL_OBJECT_REFERENCE) {
            l_file_writer.write(l_file_line)
            l_file_writer.flush()
        } else {
            l_file_writer = new FileWriter(new File(GC_CSV_FILE_PATH + p_tag_name))
            p_file_writer_per_tag_name.put(p_tag_name, l_file_writer)
            l_file_writer.write(l_file_line)
            l_file_writer.flush()
        }
    }
}