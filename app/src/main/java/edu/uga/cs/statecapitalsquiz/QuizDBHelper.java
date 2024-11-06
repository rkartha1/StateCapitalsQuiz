package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuizDBHelper";
    private static final String DB_NAME = "quiz.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_QUIZ_QUESTIONS = "quizquestions";
    public static final String QUIZ_QUESTIONS_COLUMN_ID = "_id";
    public static final String QUIZ_QUESTIONS_COLUMN_STATE = "state";
    public static final String QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY = "capital_city";
    public static final String QUIZ_QUESTIONS_COLUMN_FIRST_CITY = "first_city";
    public static final String QUIZ_QUESTIONS_COLUMN_SECOND_CITY = "second_city";
    public static final String QUIZ_QUESTIONS_COLUMN_USED = "used";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_RESULT = "result";
    public static final String QUIZZES_COLUMN_ANSWERED = "answered";

    public static final String TABLE_QUIZ_RESPONSES = "quiz_questions";
    public static final String QUIZ_RESPONSES_COLUMN_ID = "_id";
    public static final String QUIZ_RESPONSES_COLUMN_QUIZ_ID = "quiz_id";
    public static final String QUIZ_RESPONSES_COLUMN_QUESTION_ID = "question_id";
    public static final String QUIZ_RESPONSES_COLUMN_USER_ANSWER = "user_answer";

    private static QuizDBHelper helperInstance;

    private static final String CREATE_QUIZ_QUESTIONS = "create table " + TABLE_QUIZ_QUESTIONS + " ("
            + QUIZ_QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZ_QUESTIONS_COLUMN_STATE + " TEXT, "
            + QUIZ_QUESTIONS_COLUMN_CAPITAL_CITY + " TEXT, "
            + QUIZ_QUESTIONS_COLUMN_FIRST_CITY + " TEXT, "
            + QUIZ_QUESTIONS_COLUMN_SECOND_CITY + " TEXT, "
            + QUIZ_QUESTIONS_COLUMN_USED + " INTEGER DEFAULT 0"
            + ")";

    private static final String CREATE_QUIZZES = "create table " + TABLE_QUIZZES + " ("
            + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZZES_COLUMN_DATE + " TEXT, "
            + QUIZZES_COLUMN_RESULT + " INTEGER, "
            + QUIZZES_COLUMN_ANSWERED + " INTEGER"
            + ")";

    private static final String CREATE_QUIZ_RESPONSES = "create table  " + TABLE_QUIZ_RESPONSES + " ("
            + QUIZ_RESPONSES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZ_RESPONSES_COLUMN_QUIZ_ID + " INTEGER, "
            + QUIZ_RESPONSES_COLUMN_QUESTION_ID + " INTEGER, "
            + QUIZ_RESPONSES_COLUMN_USER_ANSWER + " TEXT, "
            + "FOREIGN KEY(" + QUIZ_RESPONSES_COLUMN_QUIZ_ID + ") REFERENCES " + TABLE_QUIZZES + "(" + QUIZZES_COLUMN_ID + "), "
            + "FOREIGN KEY(" + QUIZ_RESPONSES_COLUMN_QUESTION_ID + ") REFERENCES " + TABLE_QUIZ_QUESTIONS + "(" + QUIZ_QUESTIONS_COLUMN_ID + "))";

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
        db.execSQL(CREATE_QUIZ_QUESTIONS);
        db.execSQL(CREATE_QUIZZES);
        db.execSQL(CREATE_QUIZ_RESPONSES);
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZ_QUESTIONS + " created");
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZZES + " created");
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZ_RESPONSES + " created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_QUIZ_QUESTIONS + " ADD COLUMN " + QUIZ_QUESTIONS_COLUMN_USED + " INTEGER DEFAULT 0");
        }

        db.execSQL("drop table if exists " + TABLE_QUIZZES);
        db.execSQL("drop table if exists " + TABLE_QUIZ_RESPONSES);
        onCreate(db);
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZ_QUESTIONS + " upgraded");
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded");
        Log.d(DEBUG_TAG, "Table " + TABLE_QUIZ_RESPONSES + " upgraded");
    }
}