package eli.tech.wallri.Like;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.widget.ImageView;

import com.wx.goodview.GoodView;

import java.util.ArrayList;
import java.util.List;

import eli.tech.wallri.DataBase.DataBaseManager;
import eli.tech.wallri.R;

/**
 * Created by 小雷 on 2018/3/31.
 */

public class LikeButton {
    private List<String> dataBaseTableNameList;
    private String dataBaseName;
    private Context context;
    private ImageView likeImage;
    private String tableName;
    private SQLiteDatabase writableDatabase;
    private String condition;


    public LikeButton(List<String> dataBaseTableNameList, String dataBaseName, Context context, ImageView likeImage, String tableName, String condition) {
        this.dataBaseTableNameList = dataBaseTableNameList;
        this.dataBaseName = dataBaseName;
        this.context = context;
        this.likeImage = likeImage;
        this.tableName = tableName;
        this.condition = condition;
        init();
    }

    private void init() {
        DataBaseManager dataBaseManager = new DataBaseManager(context, dataBaseName, dataBaseTableNameList, tableName);
        writableDatabase = dataBaseManager.getWritableDatabase();
    }

    //点心
    public void setLike(List<String> dataBaseTableDataList) {
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < dataBaseTableDataList.size(); i++) {
            contentValues.put(dataBaseTableNameList.get(i), dataBaseTableDataList.get(i));
        }
        writableDatabase.insert(tableName, null, contentValues);
        GoodView GoodView = new GoodView(context);
        likeImage.setImageResource(R.drawable.like_red);
        GoodView.setTextInfo("like", Color.parseColor("#f66467"), 12);
        GoodView.show(likeImage);
    }

    //判断该数据是否为喜欢
    public void getLike(String liked) {
        likeImage.setImageResource(R.drawable.like_white);
        Cursor cursor = writableDatabase.query(tableName, null, null, null, null, null, null);
        List<String> likeList = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                while (cursor.moveToNext()) {
                    String key = cursor.getString(cursor.getColumnIndex(condition));
                    likeList.add(key);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        for (int i = 0; i < likeList.size(); i++) {
            if (likeList.get(i).equals(liked)) {
                likeImage.setImageResource(R.drawable.like_red);
            }
        }

    }

    public Cursor getQueryData() {
        Cursor cursor = writableDatabase.query(tableName, null, null, null, null, null, null);
        return cursor;
    }


}
