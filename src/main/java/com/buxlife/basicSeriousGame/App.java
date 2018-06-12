package com.buxlife.basicSeriousGame;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        SwingUtilities.invokeLater(new Runnable(){

            public void run(){
                GameGUI basicSeriousGame = new GameGUI("Quiz Master");

                JFrame.setDefaultLookAndFeelDecorated(true);
                basicSeriousGame.setSize(550, 250);
                basicSeriousGame.setResizable(false);
                basicSeriousGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                basicSeriousGame.setVisible(true);
            }
        });

    }
}
