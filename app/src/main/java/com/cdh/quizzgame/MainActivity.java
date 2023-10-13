package com.cdh.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    private ImageView questionImage;
    private Button submitButton,logoutButton;
    private Button nextButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private Button selectedAnswerButton;
    private int currentQuestionIndex = 1;
    private int currentScore = 0;
    private int correctAnswerCount;
    private int incorrectAnswerCount;
    int solution;
    int selectedSolution;
    private int correctAnswerButtonId;
    private TextView quizNo,score,userName;
    private CountDownTimer questionTimer;
    private AlertDialog restartDialog;
    private TextView remainingTime;
    private  final String baseUrl = "https://marcconrad.com/";
    ProgressBar timeBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        questionImage = findViewById(R.id.questionImage);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        logoutButton = findViewById(R.id.logout_button);
        quizNo = findViewById(R.id.quiz_no_text);
        score = findViewById(R.id.score_text);
        userName = findViewById(R.id.welcome_user_text);
        remainingTime = findViewById(R.id.remaining_time_text);
        timeBar = findViewById(R.id.time_bar);
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else{

            String uid = user.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + uid);
            databaseReference.child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        String name = task.getResult().getValue(String.class);
                        userName.setText(name.toString());


                    } else {
                        // Handle the error
                    }
                }
            });

        }

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



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedAnswerButton != null){
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                }
                selectedSolution = 0;
                selectedAnswerButton = findViewById(R.id.btn0);
                selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.primary_purple));

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

                if (questionTimer != null) {
                    questionTimer.cancel();
                   }
                boolean status = checkAnswers(solution,selectedSolution);
                if(status){
//                    if (questionTimer != null) {
//                        questionTimer.cancel();
//                    }
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.custom_toastlayout,
                            findViewById(R.id.toast_layout_root));

                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(view);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                    submitButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);
                }else{
                    selectedAnswerButton.setBackgroundColor(getResources().getColor(R.color.button_background_color));
                    Toast.makeText(getApplicationContext(),"Incorrect answer!",Toast.LENGTH_SHORT).show();
                    //new lines
                    submitButton.setVisibility(View.GONE);
                    nextButton.setVisibility(View.VISIBLE);
                }


            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentQuestionIndex++;
                quizNo.setText(String.valueOf(currentQuestionIndex));
                disableButtons();

                questionImage.setImageResource(R.drawable.placeholder_image);
                nextButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                solution = -1;
                selectedSolution = -1;
                correctAnswerButtonId = -1;


                loadQuestion(currentQuestionIndex);

            }
        });


        loadQuestion(currentQuestionIndex);


    }

    private void loadQuestion(int index) {

        if (questionTimer != null) {
            questionTimer.cancel();
        }


        remainingTime.setText(R.string.remaining_time_30_seconds);
        timeBar.setProgress(100);

        quizNo.setText(String.valueOf(index));
        score.setText(String.valueOf(currentScore));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        QuizApiService apiService = retrofit.create(QuizApiService.class);
        Call<QuizResponse> call = apiService.getQuizQuestions();
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuizResponse> call, @NonNull Response<QuizResponse> response) {
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

                        startTimer(30, timeBar);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<QuizResponse> call, @NonNull Throwable t) {
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

    private void startTimer(int seconds, ProgressBar progressBar) {
        questionTimer = new CountDownTimer(seconds * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {
                int remainingSeconds = (int) millisUntilFinished / 1000;
                String timerText = "Remaining time: " + remainingSeconds + " seconds";
                remainingTime.setText(timerText);

                int totalDuration = seconds * 1000;
                int elapsedTime = totalDuration - (int) millisUntilFinished;
                int progress = (int) (100 - ((float) elapsedTime / totalDuration * 100));
                progressBar.setProgress(progress);
            }

            public void onFinish() {
                showRestartDialog();
            }
        }.start();
    }

    private void showRestartDialog() {
        if (!isFinishing()) {
            if (restartDialog != null && restartDialog.isShowing()) {
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Time's Up! The correct answer is " + solution);
            builder.setMessage("Do you want to restart the quiz?");
            builder.setPositiveButton("Yes", (dialog, which) -> {

                currentQuestionIndex = 1;
                currentScore = 0;
                loadQuestion(currentQuestionIndex);
            });
            builder.setCancelable(false);
            restartDialog = builder.create();
            restartDialog.show();
        }

    }


    private boolean checkAnswers(int correctAnswer, int selectedAnswer){

            if (selectedAnswer == correctAnswer){
                currentScore += 10;
                score.setText(String.valueOf(currentScore));
                Button correctAnswerButton = findViewById(correctAnswerButtonId);
                correctAnswerButton.setBackgroundColor(getResources().getColor(R.color.green_color));
                return true;
            }else{
                return false;
            }
        }
    }





