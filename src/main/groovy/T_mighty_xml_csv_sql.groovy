class T_mighty_xml_csv_sql extends T_xml_base_4_const{
    static final String GC_XML_FILE_PATH = "C:\\AP\\CATEGORIZED\\CITI\\IN\\"
    static final String GC_XML_FILE_NAME = "CardMasterExtract.xml"
    static final String GC_XML_FILE_NAME1 = "z.xml"
    static final String GC_XML_TAG_OPEN = "<"
    static final String GC_XML_TAG_CLOSE = ">"
    static final String GC_CHAR_SPACE = " "
    static final String GC_XML_TAG_END_BACKSLASH = "/"
    static final String GC_EMPTY_STRING = ""
    static final boolean GC_EXPECT_TAG_NAME_YES = true
    static final boolean GC_EXPECT_TAG_NAME_NO = false
    static final boolean GC_EXPECT_TAG_META_DATA_YES = true
    static final boolean GC_EXPECT_TAG_META_DATA_NO = false
    static final boolean GC_EXPECT_TAG_CONTENT_YES = true
    static final boolean GC_EXPECT_TAG_CONTENT_NO = false
    static final boolean GC_INSIDE_QUOTES_YES = true
    static final boolean GC_INSIDE_QUOTES_NO = false
    static final Object GC_NULL_OBJECT_REFERENCE = null
    static final Integer GC_FIRST_CHAR = 0
    static boolean p_expect_tag_name = GC_EXPECT_TAG_NAME_NO
    static boolean p_expect_tag_meta_data = GC_EXPECT_TAG_META_DATA_NO
    static boolean p_expect_tag_content = GC_EXPECT_TAG_CONTENT_NO
    static boolean p_inside_quotes = GC_INSIDE_QUOTES_NO
    static String p_tag_name = GC_EMPTY_STRING
    static String p_tag_meta_data = GC_EMPTY_STRING
    static String p_tag_value = GC_EMPTY_STRING
    static T_xml_tag p_current_tag = GC_NULL_OBJECT_REFERENCE as T_xml_tag

    static void process_char(char i_char) {
        //System.out.print(i_char)
        if (p_inside_quotes || (i_char != GC_XML_TAG_OPEN && i_char != GC_XML_TAG_CLOSE && i_char != GC_XML_TAG_END_BACKSLASH && !(i_char == GC_CHAR_SPACE && p_expect_tag_name))) {
            if (p_expect_tag_name) {
                p_tag_name = p_tag_name + i_char
            } else if (p_expect_tag_meta_data) {
                p_tag_meta_data = p_tag_meta_data + i_char
            } else if (p_expect_tag_content) {
                p_tag_value = p_tag_value + i_char
            }
            if (i_char == GC_XML_DOUBLE_QUOTE) {
                p_inside_quotes = not(p_inside_quotes)
            }
        } else if (i_char == GC_XML_TAG_OPEN) {
            p_expect_tag_name = GC_EXPECT_TAG_NAME_YES
            if (p_expect_tag_content) {
                p_expect_tag_content = GC_EXPECT_TAG_CONTENT_NO
                p_current_tag = new T_xml_tag(p_current_tag, p_tag_name, p_tag_meta_data, p_tag_value.trim())
                p_tag_value = GC_EMPTY_STRING
                p_tag_name = GC_EMPTY_STRING
                p_tag_meta_data = GC_EMPTY_STRING
            }
        } else if (i_char == GC_CHAR_SPACE && p_expect_tag_name) {
            p_expect_tag_name = GC_EXPECT_TAG_NAME_NO
            p_expect_tag_meta_data = GC_EXPECT_TAG_META_DATA_YES
        } else if (i_char == GC_XML_TAG_CLOSE) {
            if (p_expect_tag_meta_data || p_expect_tag_name) {
                p_expect_tag_meta_data = GC_EXPECT_TAG_META_DATA_NO
                p_expect_tag_name = GC_EXPECT_TAG_NAME_NO
                p_expect_tag_content = GC_EXPECT_TAG_CONTENT_YES
            }
        } else if (i_char == GC_XML_TAG_END_BACKSLASH && p_expect_tag_meta_data) {
            p_current_tag = new T_xml_tag(p_current_tag, p_tag_name, p_tag_meta_data, GC_EMPTY_STRING)
            p_tag_value = GC_EMPTY_STRING
            p_tag_name = GC_EMPTY_STRING
            p_tag_meta_data = GC_EMPTY_STRING
            p_expect_tag_name = GC_EXPECT_TAG_NAME_NO
            p_expect_tag_content = GC_EXPECT_TAG_CONTENT_NO
            p_expect_tag_meta_data = GC_EXPECT_TAG_META_DATA_NO
            p_current_tag.save_to_file()
            //p_current_tag.save_to_db()
            if (p_current_tag.p_parent_tag != null) {
                p_current_tag = p_current_tag.p_parent_tag
            } else {
                p_current_tag = GC_NULL_OBJECT_REFERENCE as T_xml_tag
            }
        } else if (i_char == GC_XML_TAG_END_BACKSLASH && !p_expect_tag_meta_data && !p_expect_tag_content) {
            p_expect_tag_name = GC_EXPECT_TAG_NAME_NO
            p_current_tag.save_to_file()
            //p_current_tag.save_to_db()
            if (p_current_tag.p_parent_tag != null) {
                p_current_tag = p_current_tag.p_parent_tag
            } else {
                p_current_tag = GC_NULL_OBJECT_REFERENCE as T_xml_tag
            }
        }
    }

    static void process_line(String i_line) {
        for (int l_char_index = GC_FIRST_CHAR; l_char_index < i_line.length(); l_char_index++) {
            process_char(i_line.charAt(l_char_index))
        }
    }

    static void xml_to_csv_sql() {
        String l_xml_file_name = GC_XML_FILE_PATH + GC_XML_FILE_NAME
        BufferedReader l_buffered_reader = new BufferedReader(new FileReader(new File(l_xml_file_name)))
        for (String l_line; (l_line = l_buffered_reader.readLine()) != null;) {
            //System.out.println(l_line)
            process_line(l_line)
        }
    }
}