package com.cdh.quizzgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private Button button1, button2,button3,button4,button5,button6,button7,button8,button9,button0,selectedAnswerButton,correctAnswerButton;

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

        disableButtons();

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                selectedAnswerButton = findViewById(R.id.btn0);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
                selectedSolution = 0;
                Toast.makeText(getApplicationContext(),"The correct answer is"+solution,Toast.LENGTH_SHORT).show();


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }

                selectedSolution = 1;
                selectedAnswerButton = findViewById(R.id.btn1);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 2;
                selectedAnswerButton = findViewById(R.id.btn2);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                selectedSolution = 3;
                selectedAnswerButton = findViewById(R.id.btn3);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 4;
                selectedAnswerButton = findViewById(R.id.btn4);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 5;
                selectedAnswerButton = findViewById(R.id.btn5);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 6;
                selectedAnswerButton = findViewById(R.id.btn6);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 7;
                selectedAnswerButton = findViewById(R.id.btn7);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 8;
                selectedAnswerButton = findViewById(R.id.btn8);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 9;
                selectedAnswerButton = findViewById(R.id.btn9);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
            }
        });






        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = checkAnswers(solution,selectedSolution);
                if(status == true){
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.custom_toastlayout,
                            (ViewGroup)findViewById(R.id.toast_layout_root));

                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(view);
                    //toast.setText("Correct Answer!");
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                    submitButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);
                }else{
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                    Toast.makeText(getApplicationContext(),"Incorrect answer!",Toast.LENGTH_SHORT).show();
                }


            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentQuestionIndex++;
                disableButtons();

                questionImage.setImageResource(R.drawable.placeholder_image);
                nextButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                //correctAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                solution = -1;
                selectedSolution = -1;
                correctAnswerButtonId = -1;

                loadQuestion(currentQuestionIndex);

            }
        });

       //load question was here
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
                        enableButtons();

                        switch (solution) {
                            case 0:
                                correctAnswerButtonId = R.id.btn0;
                                break;
                            case 1:
                                correctAnswerButtonId = R.id.btn1;
                                break;
                            case 2:
                                correctAnswerButtonId = R.id.btn3;
                                break;
                            case 3:
                                correctAnswerButtonId = R.id.btn3;
                                break;
                            case 4:
                                correctAnswerButtonId = R.id.btn4;
                                break;
                            case 5:
                                correctAnswerButtonId = R.id.btn5;
                                break;
                            case 6:
                                correctAnswerButtonId = R.id.btn6;
                                break;
                            case 7:
                                correctAnswerButtonId = R.id.btn7;
                                break;
                            case 8:
                                correctAnswerButtonId = R.id.btn8;
                                break;
                            case 9:
                                correctAnswerButtonId = R.id.btn9;
                                break;
                        }



                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Picasso.get()
                        .load(R.drawable.error_image)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .fit()
                        .centerInside()
                        .into(questionImage);


                Toast.makeText(getApplicationContext(), "Error fetching data. Please check your network connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void disableButtons() {
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        submitButton.setEnabled(false);

        // Change the color of the buttons to brown
        button0.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button1.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button2.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button3.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button4.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button5.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button6.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button7.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button8.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        button9.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        submitButton.setBackgroundColor(getResources().getColor(R.color.screen_purple));
    }

    private void enableButtons() {
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        submitButton.setEnabled(true);

        button0.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button1.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button2.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button3.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button4.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button5.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button6.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button7.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button8.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        button9.setBackgroundColor(getResources().getColor(R.color.button_background_color));
        submitButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));
    }

    private boolean checkAnswers(int correctAnswer, int selectedAnswer){
//        int selectedRadioButtonId = answerGroup.getCheckedRadioButtonId();
//        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

            if (selectedAnswer == correctAnswer){
                correctAnswerButton = findViewById(correctAnswerButtonId);
                correctAnswerButton.setBackgroundColor(getResources().getColor(R.color.green_color));
                return true;
            }else{
                return false;
            }
        }
    }





