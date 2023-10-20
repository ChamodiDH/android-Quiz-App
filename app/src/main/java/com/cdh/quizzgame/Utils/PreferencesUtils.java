package com.cdh.quizzgame.Utils;
import android.content.Context;
import android.content.SharedPreferences;
public class PreferencesUtils {
    private static final String PREFERENCES_NAME = "QuizPreferences";
    private static final String KEY_CURRENT_QUESTION_INDEX = "currentQuestionIndex";
    private static final String KEY_CURRENT_SCORE = "currentScore";
    private static final String KEY_CORRECT_ANSWER_COUNT = "correctAnswerCount";
    private static final String KEY_INCORRECT_ANSWER_COUNT = "incorrectAnswerCount";

    public static void saveQuizState(Context context, int currentQuestionIndex, int currentScore, int correctAnswerCount, int incorrectAnswerCount) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_CURRENT_QUESTION_INDEX, currentQuestionIndex);
        editor.putInt(KEY_CURRENT_SCORE, currentScore);
        editor.putInt(KEY_CORRECT_ANSWER_COUNT, correctAnswerCount);
        editor.putInt(KEY_INCORRECT_ANSWER_COUNT, incorrectAnswerCount);
        editor.apply();
    }

    public static void clearQuizState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static int getCurrentQuestionIndex(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_CURRENT_QUESTION_INDEX, 1); // Default value 1 if not found
    }

    public static int getCurrentScore(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_CURRENT_SCORE, 0); // Default value 0 if not found
    }

    public static int getCorrectAnswerCount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_CORRECT_ANSWER_COUNT, 0); // Default value 0 if not found
    }

    public static int getIncorrectAnswerCount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(KEY_INCORRECT_ANSWER_COUNT, 0); // Default value 0 if not found
    }
}
