/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

/**
 *
 * @author Toihir
 */
public class Player {
    private String color;
    private String name;
    private int score;
    
    static int numPlayers = 1;
    public static int bestScore;
    
    public Player(){
        this.name = "Player : " + numPlayers++;
    }
    
    public Player(String color){
        this.color = color;
        this.name = "Player : " + numPlayers++;
    }
    
    public Player(String color, String name){
        this.color = color;
        this.name = name;
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
    
    
}
