package sg.edu.np.week_6_whackamole_3_0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WhackAMole.db";
    private static final Integer DATABASE_VERSION = 1;
    public static final String TABLE_USER = "userdata";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_SCORE = "Score";
    /*
        The Database has the following properties:
        1. Database name is WhackAMole.db
        2. The Columns consist of
            a. Username
            b. Password
            c. Level
            d. Score
        3. Add user method for adding user into the Database.
        4. Find user method that finds the current position of the user and his corresponding
           data information - username, password, level highest score for each level
        5. Delete user method that deletes based on the username
        6. To replace the data in the database, we would make use of find user, delete user and add user

        The database shall look like the following:

        Username | Password | Level | Score
        --------------------------------------
        User A   | XXX      | 1     |    0
        User A   | XXX      | 2     |    0
        User A   | XXX      | 3     |    0
        User A   | XXX      | 4     |    0
        User A   | XXX      | 5     |    0
        User A   | XXX      | 6     |    0
        User A   | XXX      | 7     |    0
        User A   | XXX      | 8     |    0
        User A   | XXX      | 9     |    0
        User A   | XXX      | 10    |    0
        User B   | YYY      | 1     |    0
        User B   | YYY      | 2     |    0

     */

    private static final String FILENAME = "MyDBHandler.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        /* HINT:
            This is used to init the database.
         */
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_LEVEL + " INTEGER," +
                COLUMN_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
        Log.v(TAG, "DB Created: " + CREATE_USER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
        /* HINT:
            This is triggered if there is a new version found. ALL DATA are replaced and irreversible.
         */
    }

    public void addUser(UserData userData)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, userData.getMyUserName());
        values.put(COLUMN_PASSWORD, userData.getMyPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < userData.getLevels().size(); i++){
            values.put(COLUMN_LEVEL, userData.getLevels().get(i));
            values.put(COLUMN_SCORE, userData.getScores().get(i));
            db.insert(TABLE_USER, null, values);
            Log.v(TAG, FILENAME + ": Adding data for Database: " + values.toString());
        }
        db.close();
    }

    public UserData findUser(String username)
    {
        String FIND_USER_QUERY = "SELECT * FROM " + TABLE_USER + " WHERE " +  COLUMN_USERNAME + " = \"" + username + "\"";
        Log.v(TAG, FILENAME + ":Find user from database: " + FIND_USER_QUERY);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(FIND_USER_QUERY, null);

        UserData userData = new UserData();

        if (cursor.moveToFirst()){
            userData.setMyUserName(cursor.getString(0));
            userData.setMyPassword(cursor.getString(1));
            ArrayList<Integer> levels = new ArrayList<>();
            ArrayList<Integer> scores = new ArrayList<>();

            do{
                levels.add(Integer.parseInt(cursor.getString(2)));
                scores.add(Integer.parseInt(cursor.getString(3)));
            } while (cursor.moveToNext());

            userData.setLevels(levels);
            userData.setScores(scores);

            Log.v(TAG, FILENAME + ": QueryData: " + userData.getLevels().toString() + userData.getScores().toString());
        }
        else{
            userData = null;
            Log.v(TAG, FILENAME + ": No data found!");
        }

        return userData;
    }
        /* HINT:
            This finds the user that is specified and returns the data information if it is found.
            If not found, it will return a null.
            Log.v(TAG, FILENAME +": Find user form database: " + query);

            The following should be used in getting the query data.
            you may modify the code to suit your design.

            if(cursor.moveToFirst()){
                do{
                    ...
                    .....
                    ...
                }while(cursor.moveToNext());
                Log.v(TAG, FILENAME + ": QueryData: " + queryData.getLevels().toString() + queryData.getScores().toString());
            }
            else{
                Log.v(TAG, FILENAME+ ": No data found!");
            }
         */


    public boolean deleteAccount(String username) {
        /* HINT:
            This finds and delete the user data in the database.
            This is not reversible.
            Log.v(TAG, FILENAME + ": Database delete user: " + query);
         */
        boolean result=false;
        String DELETE_ACCOUNT_QUERY = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(DELETE_ACCOUNT_QUERY, null);
        UserData userData = new UserData();

        if (cursor.moveToFirst()){
            userData.setMyUserName(cursor.getString(0));
            db.delete(TABLE_USER, COLUMN_USERNAME + " = ?",
                    new String[] {
                            String.valueOf(userData.getMyUserName())
                    });
            result = true;
            Log.v(TAG, FILENAME + ": Database delete user: " + DELETE_ACCOUNT_QUERY);
        }
        db.close();
        return result;
    }
}
