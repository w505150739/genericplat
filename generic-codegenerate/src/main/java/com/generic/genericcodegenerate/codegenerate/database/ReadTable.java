package com.generic.genericcodegenerate.codegenerate.database;

import com.generic.genericcodegenerate.codegenerate.pojo.Columnt;
import com.generic.genericcodegenerate.codegenerate.pojo.TableConvert;
import com.generic.genericcodegenerate.codegenerate.util.CodeResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ReadTable implements Serializable{

    private static final Logger log = Logger.getLogger(ReadTable.class);
    private static final long serialVersionUID = -5324160085184088010L;
    private Connection conn;
    private Statement stmt;
    private String sql;
    private ResultSet rs;

    public List<String> readAllTableNames()
            throws SQLException {
        List tableNames = new ArrayList(0);
        try {
            Class.forName(CodeResourceUtil.DIVER_NAME);
            this.conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
            this.stmt = this.conn.createStatement(1005,
                    1007);

            if (CodeResourceUtil.DATABASE_TYPE.equals("mysql")) {
                this.sql = MessageFormat.format("select distinct table_name from information_schema.columns where table_schema = {0}", new Object[]{TableConvert.getV(CodeResourceUtil.DATABASE_NAME)});
            }

            if (CodeResourceUtil.DATABASE_TYPE.equals("oracle")) {
                this.sql = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
            }

            if (CodeResourceUtil.DATABASE_TYPE.equals("postgresql")) {
                this.sql = "SELECT distinct c.relname AS  table_name FROM pg_class c";
            }

            if (CodeResourceUtil.DATABASE_TYPE.equals("sqlserver")) {
                this.sql = "select distinct c.name as  table_name from sys.objects c ";
            }

            this.rs = this.stmt.executeQuery(this.sql);
            while (this.rs.next()) {
                String tableName = this.rs.getString(1);
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (this.stmt != null) {
                    this.stmt.close();
                    this.stmt = null;
                    System.gc();
                }
                if (this.conn != null) {
                    this.conn.close();
                    this.conn = null;
                    System.gc();
                }
            } catch (SQLException se) {
                throw se;
            }
        } finally {
            try {
                if (this.stmt != null) {
                    this.stmt.close();
                    this.stmt = null;
                    System.gc();
                }
                if (this.conn != null) {
                    this.conn.close();
                    this.conn = null;
                    System.gc();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
        return tableNames;
    }

    public List<Columnt> readTableColumn(String tableName) throws Exception {
        return null;
    }

    public List<Columnt> readOriginalTableColumn(String tableName) throws Exception {
        return null;
    }

    public static String formatField(String field) {
        String[] strs = field.split("_");
        field = "";
        int m = 0;
        for (int length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m].toLowerCase();
                tempStr = tempStr.substring(0, 1).toUpperCase() +
                        tempStr.substring(1, tempStr.length());
                field = field + tempStr;
            } else {
                field = field + strs[m].toLowerCase();
            }
        }
        return field;
    }

    public static String formatFieldCapital(String field) {
        String[] strs = field.split("_");
        field = "";
        int m = 0;
        for (int length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m].toLowerCase();
                tempStr = tempStr.substring(0, 1).toUpperCase() +
                        tempStr.substring(1, tempStr.length());
                field = field + tempStr;
            } else {
                field = field + strs[m].toLowerCase();
            }
        }
        field = field.substring(0, 1).toUpperCase() + field.substring(1);
        return field;
    }

    public boolean checkTableExist(String tableName) {
        try {
            System.out.println("数据库驱动: " + CodeResourceUtil.DIVER_NAME);
            Class.forName(CodeResourceUtil.DIVER_NAME);
            this.conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
            this.stmt = this.conn.createStatement(1005,
                    1007);

            if (CodeResourceUtil.DATABASE_TYPE.equals("mysql")) {
                this.sql =
                        ("select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '" +
                                tableName.toUpperCase() + "'" +
                                " and table_schema = '" + CodeResourceUtil.DATABASE_NAME + "'");
            }

            if (CodeResourceUtil.DATABASE_TYPE.equals("oracle")) {
                this.sql =
                        ("select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '" +
                                tableName.toUpperCase() +
                                "'");
            }

            if (CodeResourceUtil.DATABASE_TYPE.equals("postgresql")) {
                this.sql = MessageFormat.format("SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ", new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }
            if (CodeResourceUtil.DATABASE_TYPE.equals("sqlserver")) {
                this.sql = MessageFormat.format("select cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type='''U''' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}", new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }

            this.rs = this.stmt.executeQuery(this.sql);
            this.rs.last();
            int fieldNum = this.rs.getRow();
            if (fieldNum > 0)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private void formatFieldClassType(Columnt columnt) {
        String fieldType = columnt.getFieldType();
        String scale = columnt.getScale();

        columnt.setClassType("inputxt");

        if ("N".equals(columnt.getNullable())) {
            columnt.setOptionType("*");
        }
        if (("datetime".equals(fieldType)) || (fieldType.contains("time")))
            columnt.setClassType("easyui-datetimebox");
        else if ("date".equals(fieldType))
            columnt.setClassType("easyui-datebox");
        else if (fieldType.contains("int"))
            columnt.setOptionType("n");
        else if ("number".equals(fieldType)) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                columnt.setOptionType("d");
        } else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType)))
            columnt.setOptionType("d");
        else if ("numeric".equals(fieldType))
            columnt.setOptionType("d");
    }

    private String formatDataType(String dataType, String precision, String scale) {
        if (dataType.contains("char"))
            dataType = "java.lang.String";
        else if (dataType.contains("int"))
            dataType = "java.lang.Integer";
        else if (dataType.contains("float"))
            dataType = "java.lang.Float";
        else if (dataType.contains("double"))
            dataType = "java.lang.Double";
        else if (dataType.contains("number")) {
            if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
                dataType = "java.math.BigDecimal";
            else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 10))
                dataType = "java.lang.Long";
            else
                dataType = "java.lang.Integer";
        } else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "java.util.Date";
        else if (dataType.contains("time")) {
            dataType = "java.util.Date";
        } else if (dataType.contains("blob"))
            dataType = "byte[]";
        else if (dataType.contains("clob"))
            dataType = "java.sql.Clob";
        else if (dataType.contains("numeric"))
            dataType = "BigDecimal";
        else {
            dataType = "java.lang.Object";
        }
        return dataType;
    }
}
