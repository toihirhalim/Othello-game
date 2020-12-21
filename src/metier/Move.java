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
public class Move {
    public int i;
    public int j;
    
    public String color;

    public Move(int i, int j, String color) {
        this.i = i;
        this.j = j;
        this.color = color;
    }
    
    public Move() {
    }
    
    public String toString(){
        return "{" + i + ", " + j + "}";
    }
}
