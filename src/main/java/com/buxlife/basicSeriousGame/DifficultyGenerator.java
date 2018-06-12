package com.buxlife.basicSeriousGame;

import java.util.ArrayList;



public class DifficultyGenerator extends WordHandler implements DifficultyInterface
{
    int wordLimit;


    public DifficultyGenerator(){

    }

    public void generate(int difficulty){
        if (difficulty == 0){
            wordLimit = 5;
        }
        else if(difficulty == 1){
            wordLimit = 10;
        }
        else{
            wordLimit = 15;
        }
    }

    public int getWordLimit() {
        return wordLimit;
    }
}
