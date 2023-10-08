package com.cdh.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private ImageView questionImage;
    private RadioGroup answerGroup;
    private Button submitButton;
    private Button nextButton;

    private String baseUrl = "https://marcconrad.com/";
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionImage = findViewById(R.id.questionImage);
        answerGroup = findViewById(R.id.answerGroup);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the "Next" button click
                currentQuestionIndex++;
                loadQuestion(currentQuestionIndex);

                // Hide the "Next" button and show the "Submit" button for the next question
                nextButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
            }
        });

        loadQuestion(currentQuestionIndex);


    }

    private void loadQuestion(int index) {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a service instance
        QuizApiService apiService = retrofit.create(QuizApiService.class);

        // Make the API request to fetch a question
        Call<QuizResponse> call = apiService.getQuizQuestions();
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful()) {
                    QuizResponse quizResponse = response.body();
                    if (quizResponse != null) {
                        // Load the question image from the API response
                        String imageUrl = quizResponse.getQuestionImageUrl();
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.placeholder_image)
                                .error(R.drawable.error_image)
                                .fit()
                                .centerInside()
                                .into(questionImage);

                        // Get the solution for the current question
                        int solution = quizResponse.getSolution();

                        // Set the answer options for the current question in the RadioGroup
                        List<String> answerOptions = Arrays.asList("1", "2", "3", "4", "5");
                        setupAnswerOptions(answerOptions);
                    }
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                // Handle network or API request failure
            }
        });
    }

    private void setupAnswerOptions(List<String> answerOptions) {
        // Clear any previous radio buttons in the RadioGroup
        answerGroup.removeAllViews();

        // Add new RadioButtons based on answerOptions
        for (String optionText : answerOptions) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(optionText);
            answerGroup.addView(radioButton);
        }
    }

}