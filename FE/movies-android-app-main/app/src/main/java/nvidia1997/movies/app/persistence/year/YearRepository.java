package nvidia1997.movies.app.persistence.year;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import nvidia1997.movies.app.core.year.YearDomain;
import nvidia1997.movies.app.persistence.Repository;

public class YearRepository extends Repository<YearDomain, Integer> {
    public static final String TABLE_NAME = "year";

    private final String COLUMN_VALUE = "value";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateTableSql() {
        return "CREATE TABLE " + getTableName() + " " +
                "(" + getColumnId() + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_VALUE + " INTEGER NOT NULL UNIQUE)";
    }

    public YearRepository(@Nullable Context context) {
        super(context);
    }

    @Override
    protected void mapContentValues(YearDomain yearDomain, ContentValues cv) {
        cv.put(COLUMN_VALUE, yearDomain.getValue());
    }

    @Override
    public YearDomain mapCursorValues(Cursor c) {
        YearDomain yearDomain = new YearDomain();
        yearDomain.setId(c.getInt(c.getColumnIndex(getColumnId())));
        yearDomain.setValue(c.getInt(c.getColumnIndex(COLUMN_VALUE)));

        return yearDomain;
    }

    public YearDomain findByValue(int value, boolean closeConnection) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = getReadableDatabase();

            String sql = "SELECT * FROM " + getTableName() +
                    " WHERE " + COLUMN_VALUE + "=" + value;

            c = db.rawQuery(sql, null);

            if (c.moveToFirst()) {
                return mapCursorValues(c);
            }

        } catch (Exception e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            if (c != null)
                c.close();

            closeConnection(closeConnection);
        }

        return null;
    }
}
