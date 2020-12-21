/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import metier.Game;
import metier.Move;
import java.util.Scanner;
/**
 *
 * @author Toihir
 */
public class Othello {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        Game game = new Game();
        game.newGame();
        Move move;
        
        while(!game.gameOver()){
            game.print();
            move = new Move();
            move.color = game.getPlayerColor();
            System.out.print("next move for " +move.color + " : " );
            move.i = sc.nextInt();
            move.j = sc.nextInt();
            game.playMove(move);
        }
        
        game.print();
        System.out.println(game.winner() + " is the winner !!");
        //System.out.println("3 2 " + game.getColor(4,2) + " possible ?  " + game.checkpossibilities("w", 4, 2, 0, 1, 0, true));
        //game.print();
    }
    
}
