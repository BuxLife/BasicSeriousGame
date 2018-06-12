package com.buxlife.basicSeriousGame;

public class QuestionSet {
    private String question, answer;

    public QuestionSet( String q, String a){
        this.question = q;
        this.answer = a;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String toString(){
        String str = (question + " " + answer);
         return str;
    }
}
