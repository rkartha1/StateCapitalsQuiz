package edu.uga.cs.statecapitalsquiz;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";

    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_QUIZQUESTIONS = "quizquestions";
    public static final String QUIZQUESTIONS_COLUMN_ID = "_id";
    public static final String QUIZQUESTIONS_COLUMN_STATE = "state";
    public static final String QUIZQUESTIONS_COLUMN_CAPITAL_CITY = "capital_city";
    public static final String QUIZQUESTIONS_COLUMN_FIRST_CITY = "first_city";
    public static final String QUIZQUESTIONS_COLUMN_SECOND_CITY = "second_city";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final int QUIZZES_COLUMN_RESULT = 0;

    private static QuizDBHelper helperInstance;

    private static final String CREATE_QUIZQUESTIONS
            = "create table " + TABLE_QUIZQUESTIONS + " ("
            + QUIZQUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZQUESTIONS_COLUMN_STATE + " TEXT, "
            + QUIZQUESTIONS_COLUMN_CAPITAL_CITY + " TEXT, "
            + QUIZQUESTIONS_COLUMN_FIRST_CITY + " TEXT, "
            + QUIZQUESTIONS_COLUMN_SECOND_CITY + " TEXT"
            + ")";
    private static final String CREATE_QUIZZES
            = "create table " + TABLE_QUIZZES + " ("
            + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZZES_COLUMN_DATE + " TEXT, "
            + QUIZZES_COLUMN_RESULT + " INTEGER PRIMARY KEY AUTOINCREMENT"
           + ")";
    private QuizDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized QuizDBHelper getInstance(Context context) {
         if (helperInstance == null) {
            helperInstance = new QuizDBHelper(context.getApplicationContext());
        }
         return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_QUIZQUESTIONS);
    db.execSQL(CREATE_QUIZZES);
    Log.d(DEBUG_TAG, "Table " + TABLE_QUIZQUESTIONS + " created");
    Log.d(DEBUG_TAG, "Table " + TABLE_QUIZZES + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists " + TABLE_QUIZQUESTIONS);
    db.execSQL("drop table if exists " + TABLE_QUIZZES);
    onCreate(db);
    }

}