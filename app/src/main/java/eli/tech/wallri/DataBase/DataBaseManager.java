package eli.tech.wallri.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by 小雷 on 2018/3/31.
 */

public class DataBaseManager extends SQLiteOpenHelper {


    private String databaseName;
    private Context context;
    private String sql;
    private List<String> columnNameList;
    private String tableName;

    public DataBaseManager(Context context, String name, List<String> columnNameList, String tableName) {
        super(context, name, null, 1);
        this.databaseName = name;
        this.context = context;
        this.columnNameList = columnNameList;
        this.tableName = tableName;
        createSql();
    }


    private void createSql() {
        sql = "create table " + tableName + "(id integer primary key autoincrement,";
        if (columnNameList.size() > 0) {
            for (int i = 0; i < columnNameList.size(); i++) {
                if (i == columnNameList.size() - 1) {
                    sql = sql + columnNameList.get(i) + " text)";
                    continue;
                }
                sql = sql + columnNameList.get(i) + " text,";
            }
        }


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

