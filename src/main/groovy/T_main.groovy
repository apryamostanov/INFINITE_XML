import other.E_application_exception

class T_main extends T_xml_base_6_util{

    static void main(String... i_args) {
        if (!method_arguments_present(i_args)) {
            throw new E_application_exception(s.Missing_command_line_argument_Main_configuration_file_name)
        }
        String l_conf_file_name = i_args[GC_FIRST_INDEX]
        init_custom(l_conf_file_name)
        T_mighty_xml_csv_sql.xml_to_csv_sql()

        System.out.println(Thread.currentThread().getId())
    }
}

