package nvidia1997.movies.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import nvidia1997.movies.app.core.Domain;

import static nvidia1997.movies.app.persistence.DBConstants.DB_NAME;
import static nvidia1997.movies.app.persistence.DBConstants.DB_VERSION;

public abstract class Repository<T, TId> extends SQLiteOpenHelper {
    private boolean _isCreating = false;
    private SQLiteDatabase _db = null;
    public static final String COLUMN_ID = "id";

    public Repository(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    protected abstract String getTableName();

    protected abstract String getCreateTableSql();

    protected String getColumnId() {
        return COLUMN_ID;
    }

    protected String getGeneralErrorId() {
        return getTableName().toUpperCase() + "_ERROR";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTableIfExists(db, false);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        _isCreating = true;
        _db = db;

        dropTableIfExists(db, false);
        db.execSQL(getCreateTableSql());
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        if (_isCreating && _db != null) {
            return _db;
        }

        _db = super.getWritableDatabase();
        return _db;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        if (_isCreating && _db != null) {
            return _db;
        }

        _db = super.getReadableDatabase();
        return _db;
    }

    public void dropTableIfExists(SQLiteDatabase db, boolean closeConnection) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + getTableName());
        } catch (SQLException e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            closeConnection(closeConnection);
        }
    }

    public abstract T mapCursorValues(Cursor c);

    protected abstract void mapContentValues(T domain, ContentValues cv);

    public boolean add(T domain) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            mapContentValues(domain, cv);

            if (db.insert(getTableName(), null, cv) != -1) {
                return true;
            }
        } catch (SQLException e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            closeConnection(true);
        }

        return false;
    }

    public boolean addMany(T[] domains) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            for (T domain : domains) {
                ContentValues cv = new ContentValues();
                mapContentValues(domain, cv);

                db.insertWithOnConflict(getTableName(), null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            }

            return true;
        } catch (SQLException e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            closeConnection(true);
        }

        return false;
    }

    public void removeById(TId id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String where = getColumnId() + "=?";
            String[] whereArgs = {String.valueOf(id)};

            db.delete(getTableName(), where, whereArgs);

        } catch (SQLException e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            closeConnection(true);
        }
    }

    public void update(T domain) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String where = getColumnId() + "=?";
            String[] whereArgs = {String.valueOf(((Domain<TId>) domain).getId())};

            ContentValues cv = new ContentValues();
            mapContentValues(domain, cv);

            db.update(getTableName(), cv, where, whereArgs);

        } catch (SQLException e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            closeConnection(true);
        }
    }

    public T findById(TId id, boolean closeConnection) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = getReadableDatabase();

            String sql = "SELECT * FROM " + getTableName() +
                    " WHERE " + getColumnId() + " = '" + id + "'";

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

    public List<T> findAll() {
        List<T> list = null;
        Cursor c = null;

        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT * FROM " + getTableName() + " LIMIT 60";
            c = db.rawQuery(sql, null);

            if (c.moveToFirst()) {
                list = new ArrayList<>();

                do {
                    list.add(mapCursorValues(c));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.wtf(getGeneralErrorId(), e.getMessage());
        } finally {
            if (c != null)
                c.close();

            closeConnection(true);
        }

        return list;
    }

    protected void closeConnection(boolean closeConnection) {
        /*if (_db != null && closeConnection) {
            _db.close();
        }*/
    }
}
