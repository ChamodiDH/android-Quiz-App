package com.cdh.quizzgame.Service;

public class ReportEntry {

    private String totalQuestionsAnswered;
    private String correctQuestions;
    private String incorrectQuestions;

    public ReportEntry() {

    }


    public ReportEntry(String totalQuestionsAnswered, String correctQuestions, String incorrectQuestions) {
        this.totalQuestionsAnswered = totalQuestionsAnswered;
        this.correctQuestions = correctQuestions;
        this.incorrectQuestions = incorrectQuestions;
    }

    public String getTotalQuestionsAnswered() {
        return totalQuestionsAnswered;
    }

    public String getCorrectQuestions() {
        return correctQuestions;
    }

    public String getIncorrectQuestions() {
        return incorrectQuestions;
    }
}
