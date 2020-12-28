/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Random;

/**
 *
 * @author Toihir
 */
public class Player {
    private String name;
    private String color;
    private int score;
    
    static int numPlayers = 1;
    public static int bestScore;
        
    public Player(){
        this.name = "Player : " + numPlayers++;
    }
    
    public Player(String color){
        this.color = color;
        this.name = "Player : " + numPlayers++;
        this.score = 0;
    }
    
   
    public Player(String name, String color){
        this.color = color;
        this.name = name;
        this.score = 0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public Move play(String [][] board, Move [] possibleMoves){
        Random rd = new Random();
        
        int size = possibleMoves.length;
        if(size == 0) return null;
        return possibleMoves[rd.nextInt(size)];
        //return GameSearch.findBestMove(board, color);
    }
    public String toString(){
        return this.name + " {color : " + this.color + ", " + this.score + " ]";
    }
}
