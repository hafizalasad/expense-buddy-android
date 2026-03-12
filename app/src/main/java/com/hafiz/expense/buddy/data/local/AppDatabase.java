package com.hafiz.expense.buddy.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hafiz.expense.buddy.data.local.dao.CategoryDao;
import com.hafiz.expense.buddy.data.local.dao.TransactionDao;
import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.data.local.entity.TransactionEntity;

@Database(
        entities = {
                TransactionEntity.class,
                CategoryEntity.class
        },
        version = 4)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "expense-buddy-db";
    private static Context mContext;
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    // Add logic to be executed when the database is created
                }

                @Override
                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
//                    SharedPreferenceManager.setPrefShouldSync(mContext, true);
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                }
            };
    private static AppDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                    //sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        mContext = context;
        return sInstance;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                .addCallback(sRoomDatabaseCallback)
                .build();
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }


    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();

    private void updateDatabaseCreated(Context context) {

        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
