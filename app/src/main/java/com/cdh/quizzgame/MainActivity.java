package com.cdh.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

    private Button button1, button2,button3,button4,button5,button6,button7,button8,button9,button0;

    private String baseUrl = "https://marcconrad.com/";
    private int currentQuestionIndex = 0;

    int solution;

    int selectedSolution;
    private int correctAnswerButtonId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionImage = findViewById(R.id.questionImage);
        //answerGroup = findViewById(R.id.answerGroup);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        button4 = findViewById(R.id.btn4);
        button5 = findViewById(R.id.btn5);
        button6 = findViewById(R.id.btn6);
        button7 = findViewById(R.id.btn7);
        button8 = findViewById(R.id.btn8);
        button9 = findViewById(R.id.btn9);
        button0 = findViewById(R.id.btn0);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 0;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 1;
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 2;
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 3;
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 4;
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 5;
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 6;
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 7;
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 8;
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSolution = 9;
            }
        });






        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = checkAnswers(solution,selectedSolution);
                if(status == true){
                    submitButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(),"Incorrect answer!",Toast.LENGTH_SHORT).show();
                }

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


        QuizApiService apiService = retrofit.create(QuizApiService.class);
        Call<QuizResponse> call = apiService.getQuizQuestions();
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful()) {
                    QuizResponse quizResponse = response.body();
                    if (quizResponse != null) {
                        String imageUrl = quizResponse.getQuestionImageUrl();
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.placeholder_image)
                                .error(R.drawable.error_image)
                                .fit()
                                .centerInside()
                                .into(questionImage);

                        solution = quizResponse.getSolution();

                        switch (solution) {
                            case 0:
                                correctAnswerButtonId = R.id.btn0;
                                break;
                            case 1:
                                correctAnswerButtonId = R.id.btn1;
                                break;
                            case 2:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 3:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 4:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 5:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 6:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 7:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 8:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                            case 9:
                                correctAnswerButtonId = R.id.btn2;
                                break;
                        }



                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {

            }
        });
    }

//    private void setupAnswerOptions(List<String> answerOptions) {
//
//        answerGroup.removeAllViews();
//
//        for (String optionText : answerOptions) {
//            RadioButton radioButton = new RadioButton(this);
//            radioButton.setText(optionText);
//            answerGroup.addView(radioButton);
//        }
//    }

    private boolean checkAnswers(int correctAnswer, int selectedAnswer){
//        int selectedRadioButtonId = answerGroup.getCheckedRadioButtonId();
//        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

            if (selectedAnswer == correctAnswer){
                Button correctAnswerButton = findViewById(correctAnswerButtonId);
                correctAnswerButton.setBackgroundColor(getResources().getColor(R.color.green_color));
                return true;
            }else{
                return false;
            }
        }
    }



