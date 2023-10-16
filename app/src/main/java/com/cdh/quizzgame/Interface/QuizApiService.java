package com.cdh.quizzgame.Interface;

import com.cdh.quizzgame.Service.QuizResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizApiService {
    @GET("uob/smile/api.php")
    Call<QuizResponse> getQuizQuestions();
}
