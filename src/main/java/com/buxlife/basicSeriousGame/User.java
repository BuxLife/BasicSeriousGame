package com.buxlife.basicSeriousGame;

import java.util.Comparator;

public class User implements Comparable<User>{

    private int score;
    private int attempts;
    private String name;


    public void setScore(int sc) {
        score = sc;
    }
    public void setAttempts(int at) {
        attempts = at;
    }
    public void setName(String nm) {
        name = nm;
    }
    public int getScore() {
        return score;
    }
    public int getAttempts() {
        return attempts;
    }
    public String getName() {
        return name;
    }

    public User()
    {
        name = "";
        score = 0;
        attempts = 3;
    }
    public User(int sc, int att, String nme)
    {
        setName(nme);
        setScore(sc);
        setAttempts(att);
    }

    @Override
    public int compareTo(User comparesTo){
        int compareScore=((User)comparesTo).getScore();
        return compareScore-this.score; //Flip for descending or ascending order.
    }
}

