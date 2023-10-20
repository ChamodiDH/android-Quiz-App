package com.cdh.quizzgame.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cdh.quizzgame.Controller.QuizApiController;
import com.cdh.quizzgame.Data.DatabaseHandler;
import com.cdh.quizzgame.Service.QuizResponse;
import com.cdh.quizzgame.Model.ReportEntry;
import com.cdh.quizzgame.R;
import com.cdh.quizzgame.Utils.PreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ImageView questionImage;
    private Button submitButton,nextButton,button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,selectedAnswerButton;
    private ImageButton logoutButton;
    private int currentQuestionIndex = 1;
    private int currentScore = 0;
    private int correctAnswerCount;
    private int incorrectAnswerCount;
    private int totalAnsweredQuestionCount = 0;
    private int solution;
    private int selectedSolution = -1;
    private int correctAnswerButtonId;
    private int remainingTimeSeconds = 30;
    private TextView quizNo,score,userName;
    private CountDownTimer questionTimer;
    private AlertDialog restartDialog;
    private TextView remainingTime;
    private QuizApiController quizApiController;
    private ProgressBar timeBar;
    private DatabaseReference databaseRef;
    private ReportEntry reportEntry;
    private boolean tstate = false;
    private int currentProgress = 100;
    private int remainingSeconds;
    private DatabaseHandler databaseHandler;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Log.e("main_activity","Oncreate");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initUI();
            databaseHandler = new DatabaseHandler();

            quizApiController = new QuizApiController();

            if(!databaseHandler.checkUser()){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }

            databaseHandler.loadUserName(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        String name = task.getResult().getValue(String.class);
                        userName.setText(name);


                    } else {
                        String name = "Unable to load user name";
                        userName.setText(name);
                    }
                }
            });

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

            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PreferencesUtils.saveQuizState(
                            getApplicationContext(),
                            currentQuestionIndex,
                            currentScore,
                            correctAnswerCount,
                            incorrectAnswerCount
                    );


                    reportEntry = new ReportEntry(String.valueOf(totalAnsweredQuestionCount), String.valueOf(correctAnswerCount), String.valueOf(incorrectAnswerCount));
                    databaseHandler.saveReportEntry(reportEntry);


                    if (questionTimer != null) {
                        questionTimer.cancel();
                    }
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    startActivityForResult(intent, 1);
                }
            });


            button0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    if (selectedAnswerButton != null) {
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
                    disableButtons();
                    if (selectedSolution == -1) {
                        Toast.makeText(getApplicationContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                        enableButtons();
                        return;
                    }
                    totalAnsweredQuestionCount++;
                    if (questionTimer != null) {
                        questionTimer.cancel();
                    }
                    boolean status = checkAnswers(solution, selectedSolution);
                    if (status) {
                        correctAnswerCount++;
                        LayoutInflater inflater = getLayoutInflater();
                        View view = inflater.inflate(R.layout.custom_toastlayout,
                                findViewById(R.id.toast_layout_root));

                        Toast toast = new Toast(getApplicationContext());
                        toast.setView(view);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        submitButton.setVisibility(View.GONE);
                        nextButton.setVisibility(View.VISIBLE);
                    } else {
                        incorrectAnswerCount++;
                        selectedAnswerButton.setBackground(getResources().getDrawable(R.drawable.button_custom_background));
                        Toast.makeText(getApplicationContext(), "Incorrect answer!", Toast.LENGTH_SHORT).show();
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


            if (savedInstanceState == null) {

                loadQuestion(currentQuestionIndex);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initUI(){

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
        correctAnswerCount = 0;
        incorrectAnswerCount = 0;
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

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
               // startTimer(30, timeBar);
                startTimer(remainingTimeSeconds, timeBar);

                int savedQuestionIndex = PreferencesUtils.getCurrentQuestionIndex(this);;


                if (currentQuestionIndex != savedQuestionIndex) {

                    currentQuestionIndex = savedQuestionIndex;
                    loadQuestion(currentQuestionIndex);
                }
            }
        }
    }

    private void loadQuestion(int index) {

        if (questionTimer != null) {
            questionTimer.cancel();
        }

        quizNo.setText(" "+ index);
        score.setText(" "+ currentScore);
        quizApiController.getQuizQuestions(new Callback<QuizResponse>() {
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
                                .transform(new RoundedCornersTransformation(25, 0))
                                .fit()
                                .into(questionImage);

                        solution = quizResponse.getSolution();
                        enableButtons();
                        remainingTimeSeconds = 30;
                        currentProgress = 100;
                        startTimer( remainingTimeSeconds,timeBar);
                        remainingTime.setText(R.string.remaining_time_30_seconds);

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
                    Toast.makeText(getApplicationContext(), "Error loading data....", Toast.LENGTH_SHORT).show();
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
        Button[] buttons = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, submitButton};

        for (Button button : buttons) {
            button.setEnabled(false);
            button.setBackgroundColor(getResources().getColor(R.color.screen_purple));
        }
    }

    private void enableButtons() {
        Button[] buttons = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, submitButton};
        int buttonBackgroundColor = getResources().getColor(R.color.button_background_color);

        for (Button button : buttons) {
            button.setEnabled(true);
            button.setBackground(this.getResources().getDrawable(R.drawable.button_custom_background));
        }

        submitButton.setBackground(this.getResources().getDrawable(R.drawable.submit_next_button_baclground));
    }
    private void startTimer(int seconds, ProgressBar progressBar) {
        questionTimer = new CountDownTimer(seconds * 1000L, 1000) {
            public void onTick(long millisUntilFinished) {

                remainingSeconds = (int) millisUntilFinished / 1000;
                String timerText = "Remaining time: " + remainingSeconds + " seconds";
                remainingTime.setText(timerText);


                int totalDuration = seconds * 1000;


               int elapsedTime = totalDuration - (int) millisUntilFinished;

                int progress = (int) ((currentProgress - ((float) elapsedTime / totalDuration * currentProgress)));

                Log.e("progress value","progress:"+progress);
                System.out.println("progress value is:"+progress);

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
                correctAnswerCount = 0;
                incorrectAnswerCount = 0;
                totalAnsweredQuestionCount = 0;
                remainingTimeSeconds = 30;
                currentProgress = 100;
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("main_activity","Onpause");
        if (questionTimer != null) {
            questionTimer.cancel();
            remainingTimeSeconds = remainingSeconds;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("main_activity","Onresume");
        Log.e("remaining seconds","remainingseconds"+remainingTimeSeconds);


        if (questionTimer != null) {
            questionTimer.cancel();
            tstate = true;
            currentProgress = timeBar.getProgress();
            startTimer(remainingTimeSeconds, timeBar);

        }
    }
}