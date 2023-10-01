package com.brayooteck.youssef.mcq;



public class mcqArrayAdapter {

    private int mQuestionNumber;
    private String mQuestion;
    private String mgetAnswerA;
    private String mgetAnswerB;
    private String mgetAnswerC;
    private String mgetAnswerD;
    private int mCorrectAnswer;



    public mcqArrayAdapter(int QuestionNumber, String Question, String AnswerA ,String AnswerB,String AnswerC,String AnswerD, int CorrectAnswer) {

        mQuestionNumber = QuestionNumber;
        mQuestion = Question;
        mgetAnswerA = AnswerA;
        mgetAnswerB = AnswerB;
        mgetAnswerC = AnswerC;
        mgetAnswerD = AnswerD;
        mCorrectAnswer = CorrectAnswer;
    }


    public  int getQuestionNumber() {
        return mQuestionNumber;
    }


    public  String getQuestion() {
        return mQuestion;
    }


    public String getAnswerA() {
        return mgetAnswerA;
    }

    public String getAnswerB() {
        return mgetAnswerB;
    }

    public String getAnswerC() {
        return mgetAnswerC;
    }

    public String getAnswerD() {
        return mgetAnswerD;
    }


    public int getCorrectAnswer() {
        return mCorrectAnswer;
    }




}