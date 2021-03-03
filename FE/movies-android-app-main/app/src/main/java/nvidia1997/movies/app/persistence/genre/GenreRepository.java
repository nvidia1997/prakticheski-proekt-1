package nvidia1997.movies.app.persistence.genre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import nvidia1997.movies.app.core.genre.GenreDomain;
import nvidia1997.movies.app.persistence.Repository;

public class GenreRepository extends Repository<GenreDomain, Integer> {
    public static final String TABLE_NAME = "genre";

    private final String COLUMN_NAME = "name";
    private final String COLUMN_ORIGINAL_ID = "originalId";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateTableSql() {
        return "CREATE TABLE " + getTableName() + " " +
                "(" + getColumnId() + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " varchar(30) NOT NULL UNIQUE," +
                COLUMN_ORIGINAL_ID + " INTEGER)";
    }

    public GenreRepository(@Nullable Context context) {
        super(context);
    }

    @Override
    protected void mapContentValues(GenreDomain genreDomain, ContentValues cv) {
        cv.put(COLUMN_NAME, genreDomain.getName());
        cv.put(COLUMN_ORIGINAL_ID, genreDomain.getOriginalId());
    }

    @Override
    public GenreDomain mapCursorValues(Cursor c) {
        GenreDomain genreDomain = new GenreDomain();
        genreDomain.setId(c.getInt(c.getColumnIndex(getColumnId())));
        genreDomain.setOriginalId(c.getInt(c.getColumnIndex(COLUMN_ORIGINAL_ID)));
        genreDomain.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));

        return genreDomain;
    }

    public GenreDomain findByName(String name, boolean closeConnection) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = getReadableDatabase();

            String sql = "SELECT * FROM " + getTableName() +
                    " WHERE " + COLUMN_NAME + " = '" + name + "'";

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

    public GenreDomain findByOriginalId(int originalId, boolean closeConnection) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = getReadableDatabase();

            String sql = "SELECT * FROM " + getTableName() +
                    " WHERE " + COLUMN_ORIGINAL_ID + " = '" + originalId + "'";

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
