package com.cdh.quizzgame;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizApiService {
    @GET("uob/smile/api.php")
    Call<QuizResponse> getQuizQuestions();
}
