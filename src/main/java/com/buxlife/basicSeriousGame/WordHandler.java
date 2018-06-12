package com.buxlife.basicSeriousGame;

import java.io.*;
import java.util.*;

public class WordHandler{

    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<User> users = new ArrayList<>();
    private Scanner input;
    private String inputLine;

    public void openFile(String filename){
        try
        {
            input = new Scanner(new File(filename));
        }
        catch(FileNotFoundException fnf)
        {
            System.out.println("File not found..." + fnf.getMessage());
            System.exit(1);
        }
    }

    public void read(String file){
            /*
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((curLine = bufferedReader.readLine()) != null){
                System.out.println(curLine);
                String word = curLine;
                words.add(word);
            }
            */
            while(input.hasNext())
            {
                try
                {
                    inputLine = input.nextLine();
                    words.add(inputLine);
                }
                catch(InputMismatchException ime)
                {
                    System.out.print("ERROR..." + ime.getMessage());
                }

            }

    }


    public void closeFile()
    {
            input.close();
    }

    public ArrayList<User> readScores(){
        ArrayList<User> highscoreList = new ArrayList();

        String[] scoreSplit = new String[1];

        while(input.hasNext()) {
            User newUser = new User();
            inputLine = input.nextLine();
            scoreSplit = inputLine.split("#");
            newUser.setName(scoreSplit[0]);
            newUser.setScore(Integer.parseInt(scoreSplit[1]));
            highscoreList.add(newUser);
        }
        Collections.sort(highscoreList);
        return highscoreList;



    }

    public  void write(String file, User currentuser){
        try{
            if (file.isEmpty()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                String scorecard = currentuser.getName() + "#" + currentuser.getScore() + "\n";
                bw.append(scorecard);
                bw.close();
            }
            else
            {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                String scorecard = currentuser.getName() + "#" + currentuser.getScore();
                bw.append("\n");
                bw.append(scorecard);
                bw.close();

            }
        }
        catch (IOException ex){

            ex.printStackTrace();
        }
    }
    public ArrayList getWords() {
        return words;
    }


}
