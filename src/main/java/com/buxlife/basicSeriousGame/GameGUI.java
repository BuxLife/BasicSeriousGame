package com.buxlife.basicSeriousGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameGUI extends JFrame implements ActionListener{

    /*TextFields*/
    private JTextField wordTxt = new JTextField(30);
    private JTextField questionText = new JTextField(30);
    private JTextArea helpTxt = new JTextArea(6, 30);

    /*Panels*/
    private JPanel textPanel = new JPanel();
    private JPanel controlPanel = new JPanel();

    /*Buttons*/
    private JButton startBtn = new JButton("Start");
    private JButton highScoreBtn = new JButton("View High Scores");
    private JButton helpBtn = new JButton("Help");
    private JButton exitBtn = new JButton("Close");
    private JButton saveBtn = new JButton("Save & Quit");
    private JButton nextBtn = new JButton("Next");
    /*Labels*/
    private JLabel difficultyLbl = new JLabel("Difficulty:      ");
    private JLabel attemptsLbl = new JLabel("Attempts Remaining: ");
    /*ComboBox*/
    private String[] difficultySettings = {"Easy", "Normal", "Hard"};
    private JComboBox<String> difficulty = new JComboBox<String>(difficultySettings);

    private JScrollPane scroll = new JScrollPane(helpTxt);

    /*Variables*/
    private ArrayList questionList = new ArrayList();
    private ArrayList wordList = new ArrayList();
    private ArrayList<QuestionSet> questionSets = new ArrayList<>();
    private int maxWords;
    private int currentQuestion = 0;
    private int curQ = 1;
    private boolean skips = false;
    private User newPlayer = new User();

    public GameGUI(String title){
        super(title);
        /*Layout*/
        setLayout(new BorderLayout());
        add(textPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        /*Control Panel*/
        controlPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints cpc = new GridBagConstraints();
        cpc.fill = GridBagConstraints.HORIZONTAL;
        cpc.gridx = 0;
        cpc.gridy = 0;
        controlPanel.add(attemptsLbl, cpc);
        attemptsLbl.setVisible(false);
        cpc.gridx = 0;
        cpc.gridy = 1;
        controlPanel.add(difficultyLbl, cpc);
        cpc.gridx = 0;
        cpc.gridy = 2;
        controlPanel.add(difficulty, cpc);
        cpc.gridx = 0;
        cpc.gridy = 3;
        controlPanel.add(startBtn, cpc);
        cpc.gridx = 0;
        cpc.gridy = 4;
        controlPanel.add(highScoreBtn, cpc);
        cpc.gridx = 0;
        cpc.gridy = 6;
        controlPanel.add(helpBtn, cpc);
        cpc.gridx = 0;
        cpc.gridy = 7;
        controlPanel.add(exitBtn, cpc);

        /*Text Panel*/
        textPanel.setBorder(BorderFactory.createTitledBorder("Quizz Master Pro"));
        textPanel.setLayout(new GridBagLayout());
        cpc.gridx = 0;
        cpc.gridy = 2;
        textPanel.add(questionText, cpc);
        cpc.gridx = 0;
        cpc.gridy = 3;
        textPanel.add(wordTxt, cpc);
        cpc.gridx = 0;
        cpc.gridy = 4;
        textPanel.add(helpTxt);

        cpc.anchor = GridBagConstraints.PAGE_END;
        cpc.weighty = 0.1;
        cpc.gridy = 5;
        textPanel.add(nextBtn, cpc);
        cpc.weighty = 0.1;
        cpc.gridy = 6;
        textPanel.add(saveBtn, cpc);

        helpTxt.setEditable(false);
        questionText.setEditable(false);
        helpTxt.setColumns(30);
        nextBtn.setEnabled(false);
        saveBtn.setEnabled(false);
        /*ActionListeners*/
        startBtn.addActionListener(this);
        helpBtn.addActionListener(this);
        highScoreBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        saveBtn.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == highScoreBtn){
            ArrayList<User> scoreList;
            WordHandler scoreHandler = new WordHandler();
            scoreHandler.openFile("Scores.txt");
            scoreList = scoreHandler.readScores();
            String strb = "";

            for (User str:scoreList) {
                strb = strb +  str.getName() + "\t \t" +  str.getScore() + "\n";

            } //implement above for loop to read lists.
            //helpTxt.setAutoscrolls(true);
            JOptionPane.showMessageDialog(super.rootPane, "User\t \tScore\n" + strb);
            scoreHandler.closeFile();
        }
        if (e.getSource() == startBtn) {
            try {
                    if (startBtn.getText().equals("Start")) {
                        newPlayer.setName(JOptionPane.showInputDialog(super.rootPane, "Please enter your name: "));
                        System.out.println(newPlayer.getName());
                        nextBtn.setEnabled(true);
                        saveBtn.setEnabled(true);
                        if ((newPlayer.getName().equals(null)) || (newPlayer.getName().equals("")))
                        {
                            newPlayer.setName("Player");
                        }

                        if (wordTxt.getText().equals("")) {
                            attemptsLbl.setText("Attempts Remaining: 3");
                            attemptsLbl.setVisible(true);

                            newPlayer.setScore(0);
                            nextBtn.setText("Begin");
                            startBtn.setEnabled(false);
                            wordTxt.setText("Please enter answer in this block.");
                            questionText.setText("Question appears in this block.");

                            helpTxt.setText("Welcome " + newPlayer.getName() +
                                "\nThe goal to answer the provided questions correctly." +
                                "\n Each correctly answered question will score you three points." +
                                "\nMinus one point for every incorrect attempt." +
                                "\nYou have three attempts." +
                                "\nGoodluck Have Fun." +
                                "\nClick Begin to start the game.");
                        }

                        int count = 0;

                        DifficultyGenerator currentDifficulty = new DifficultyGenerator();
                        WordHandler wordHandler = new WordHandler();
                        WordHandler questionHandler = new WordHandler();
                        currentDifficulty.generate(difficulty.getSelectedIndex());
                        maxWords = currentDifficulty.getWordLimit();

                        wordHandler.openFile("src/files/Answers.txt");
                        wordHandler.read("src/files/Answers.txt");
                        wordList.addAll(wordHandler.getWords());
                        wordHandler.closeFile();

                        questionHandler.openFile("src/files/Questions.txt");
                        questionHandler.read("src/files/Questions.txt");
                        questionHandler.closeFile();
                        questionList.addAll(questionHandler.getWords());

                        for (int c = 0; c < wordList.size(); c++) {
                            QuestionSet newQuestion = new QuestionSet(questionList.get(c).toString(), wordList.get(c).toString());
                            questionSets.add(newQuestion);
                            System.out.println(c + " " + newQuestion.toString());
                        }
                        System.out.println(questionSets.get(currentQuestion).getAnswer().toString());
                    }

                    if (startBtn.getText().equals("Submit")){
                    //Game operations go here.
                    System.out.println(questionSets.get(currentQuestion).getAnswer());
                    if (wordTxt.getText().equalsIgnoreCase(questionSets.get(currentQuestion).getAnswer())) {
                        JOptionPane.showMessageDialog(super.rootPane,  "Correct");
                        wordTxt.setBackground(Color.white);
                        currentQuestion++;

                        if(newPlayer.getAttempts() == 3){newPlayer.setScore(newPlayer.getScore()+3);}
                        if(newPlayer.getAttempts() == 2){newPlayer.setScore(newPlayer.getScore()+2);}
                        if(newPlayer.getAttempts() == 1){newPlayer.setScore(newPlayer.getScore()+1);}
                        if(newPlayer.getAttempts() == 0){newPlayer.setScore(newPlayer.getScore()+1);}

                        if ((currentQuestion <= maxWords - 1) && (newPlayer.getAttempts() > 0) ){
                            ++curQ;
                            helpTxt.setText("Question: " + curQ);

                            questionText.setText(questionSets.get(currentQuestion).getQuestion());
                            wordTxt.setText("");
                            //wordTxt.setText(questionSets.get(currentQuestion).getAnswer());
                        }
                        else {
                            JOptionPane.showMessageDialog(super.rootPane, "Game Over " + newPlayer.getName() + ", thank you for playing." +
                                    "You scored: " + newPlayer.getScore());
                            nextBtn.setEnabled(false);
                            if (newPlayer.getScore() > 0) {
                                WordHandler scoreHandler = new WordHandler();
                                scoreHandler.openFile("Scores.txt");
                                scoreHandler.write("Scores.txt", newPlayer);
                                if (scoreHandler != null) {
                                    scoreHandler.closeFile();
                                }
                                System.exit(0);
                            }
                        }
                        skips = false;
                    }
                    else {
                        if (newPlayer.getAttempts() > 0) {
                            newPlayer.setAttempts(newPlayer.getAttempts() - 1);
                            helpTxt.setText("Incorrect");
                            attemptsLbl.setText("Attempts Remaining: " + newPlayer.getAttempts());
                            System.out.println(questionSets.get(currentQuestion).getAnswer());
                            wordTxt.setBackground(Color.red);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Game Over "+ newPlayer.getName() +", thank you for playing." +
                                    "You scored: " + newPlayer.getScore());
                            nextBtn.setEnabled(false);
                            if (newPlayer.getScore() > 0) {
                                WordHandler scoreHandler = new WordHandler();
                                scoreHandler.openFile("Scores.txt");
                                scoreHandler.write("Scores.txt", newPlayer);
                                scoreHandler.closeFile();
                                System.exit(0);
                            }
                        }
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if (e.getSource() == nextBtn) {
            if (nextBtn.getText().equals("Begin")) {
                helpTxt.setText("Question " + curQ);
                nextBtn.setText("Next");
                startBtn.setText("Submit");
                difficulty.setEnabled(false);
                startBtn.setEnabled(true);

                questionText.setText(questionSets.get(currentQuestion).getQuestion());
                wordTxt.setText("");
                //wordTxt.setText(questionSets.get(currentQuestion).getAnswer());
            }
            else if (nextBtn.getText().equals("Next")) {
                if (skips == false) {
                    currentQuestion++;
                    System.out.println(questionSets.get(currentQuestion).getAnswer().toString());
                    questionText.setText(questionSets.get(currentQuestion).getQuestion());
                    //wordTxt.setText(questionSets.get(currentQuestion).getAnswer());
                    skips = true;
                }
            }
        }
        if (e.getSource() == saveBtn) {
            if (newPlayer.getScore() > 0) {

                if ((newPlayer.getName().equals(null)) || (newPlayer.getName().equals("")))
                {
                    newPlayer.setName(JOptionPane.showInputDialog(super.rootPane, "Please enter your name: "));
                    if ((newPlayer.getName().equals(null)) || (newPlayer.getName().equals(""))){
                        newPlayer.setName("UNKNOWN");
                    }
                }
                JOptionPane.showMessageDialog(super.rootPane, "Thank you for playing "
                        + newPlayer.getName()
                        + ". Your score will be saved."
                        + "\nYou scored: "
                        + newPlayer.getScore());
                nextBtn.setEnabled(false);
                WordHandler scoreHandler = new WordHandler();
                scoreHandler.write("Scores.txt", newPlayer);
                scoreHandler.closeFile();
                System.exit(0);
            }
            else{
                JOptionPane.showMessageDialog(super.rootPane, "Thank you for playing ");
                System.exit(0);
            }
        }
        if (e.getSource() == helpBtn){
            helpTxt.setText("\nThe goal to answer the provided questions correctly." +
                    "\n Each correctly answered question will score you three points." +
                    "\nMinus one point for every incorrect attempt." +
                    "\nYou have three attempts." +
                    "\nGoodluck Have Fun." +
                    "\nClick Begin to start the game.");
        }
        if (e.getSource() == exitBtn) {
            int result = JOptionPane.showConfirmDialog(super.rootPane, "Progress will not be saved.", "Are you Sure?", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {System.exit(0);}
        }
    }
}
