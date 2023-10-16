package com.cdh.quizzgame.Service;
import com.google.gson.annotations.SerializedName;

public class QuizResponse {
    @SerializedName("question")
    private String questionImageUrl;

    @SerializedName("solution")
    private int solution;

    public String getQuestionImageUrl() {
        return questionImageUrl;
    }

    public int getSolution() {
        return solution;
    }
}
