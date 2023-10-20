package com.cdh.quizzgame.Controller;

import com.cdh.quizzgame.Interface.QuizApiService;
import com.cdh.quizzgame.Service.QuizResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class QuizApiController {
    private final String baseUrl = "https://marcconrad.com/";
    private final QuizApiService apiService;

    public QuizApiController() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(QuizApiService.class);
    }

    public void getQuizQuestions(Callback<QuizResponse> callback) {
        Call<QuizResponse> call = apiService.getQuizQuestions();
        call.enqueue(callback);
    }
}
