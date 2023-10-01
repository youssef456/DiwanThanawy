package com.brayooteck.youssef.mcq;



public class mcqQuizAdapter {

    private int mQuizNumber;
    private String mQuizName;
    private String mQuizLink;




    public mcqQuizAdapter(int QuizNumber,String QuizName,String QuizLink) {
        mQuizNumber = QuizNumber;
        mQuizName = QuizName;
        mQuizLink = QuizLink;

    }



    public int getQuizNumber() {
        return mQuizNumber;
    }
    public String getQuizName() {

        return mQuizName;
    }
    public String getQuizLink() {

        return mQuizLink;
    }

}