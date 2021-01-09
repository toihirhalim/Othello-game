/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Toihir
 */
public class GameSearch{
    
    static String player, opponent;
    
    static List<Move> moves;
    static List<String> boards;
    
    
    static int MAX = 1000; 
    static int MIN = -1000;
    
    static int evaluate(String [][] b) 
    { 
        int score = 0;
        for(int i = 0; i < b.length; i++){
            for(int j = 0; j < b.length; j++){
                if(b[i][j].equals(player)) score++;
            }
        }
        
        return score;
    }
    
    static boolean isMoveLeft(String [][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("_")) return true;
            }
        }
        return false;
    }
    
    static boolean gameOver(String [][] b){
        if(isMoveLeft(b)){
            if(possibleMoves(b, player).length > 0){
                return false;
            }
            
            if(possibleMoves(b, opponent).length > 0){
                return false;
            }
        }
        
        return true;
    }
    
    public static Move findBestMove(String [][] board, String color) 
{ 
    player = color;
    opponent = player.equals("b") ? "w" : "b";
    moves = new ArrayList();
    boards = new ArrayList();
    boards.add(boardToString(board));
    
    int bestVal = -1; 
    Move bestMove = null;
  
    // Traverse all cells, evaluate minimax function  
    // for all empty cells. And return the cell  
    // with optimal value. 
    Move [] possibleMoves = possibleMoves(board, player);
    for (int i = 0; i < possibleMoves.length; i++) 
    { 
        // Make the move 
        playMove(board, possibleMoves[i]);

        // compute evaluation function for this 
        // move. 
        int moveVal = minimax(board, 0, false, MIN, MAX); 
        
        
        // Undo the move 
         board = gameBack(board);
         

        // If the value of the current move is 
        // more than the best value, then update 
        // best/ 
        if (moveVal > bestVal) 
        { 
            bestMove = possibleMoves[i]; 
            bestVal = moveVal; 
        } 
    } 
    
    return bestMove; 
} 
    static int minimax(String [][] board, int depth, Boolean isMax, int alpha, int beta) 
    { 
        int score = evaluate(board); 
        
        if(depth > 5) return score;

        // if game over return the score
        if (gameOver(board)) 
            return score;

        // If this maximizer's move 
        if (isMax) 
        { 
            int best = -1000; 

            Move [] possibleMoves = possibleMoves(board, player);
            // Traverse all cells 
            for (int i = 0; i < possibleMoves.length; i++) 
            { 
                // Make the move 
                playMove(board, possibleMoves[i]);

                // Call minimax recursively and choose 
                // the maximum value 
                int val = minimax(board, depth + 1, !isMax, alpha, beta);
                
                best = Math.max(best, val); 
                alpha = Math.max(alpha, best);
                
                // Undo the move 
                board = gameBack(board);
                
                // Alpha Beta Pruning 
                if (beta <= alpha) 
                    break;
            } 
            return best; 
        } 

        // If this minimizer's move 
        else
        { 
            int best = 1000; 

            Move [] possibleMoves = possibleMoves(board, opponent);
            // Traverse all cells 
            for (int i = 0; i < possibleMoves.length; i++) 
            { 
                // Make the move 
                playMove(board, possibleMoves[i]); 

                // Call minimax recursively and choose 
                // the minimum value 
                int val = minimax(board, depth + 1, !isMax, alpha, beta);
                
                best = Math.min(best, val); 
                beta = Math.min(beta, best); 
  
                // Undo the move 
                board = gameBack(board); 
                
                // Alpha Beta Pruning 
                if (beta <= alpha) 
                    break;
                } 
            return best; 
        } 
    }
    
    
    public static boolean playMove(String [][] board, Move move){
        if (move == null){
            return false;
        }
        int i = move.i, j = move.j;
        String color = move.color;
        boolean test, previous = false;
        
        
        test = checkpossibilities(board, color, i, j, -1, -1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, -1, 1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, 1, -1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, 1, 1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, -1, 0, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, 0, -1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, 1, 0, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(board, color, i, j, 0, 1, 0, true);
        previous = test ? true : previous;
        
        //prendre la couleur oposant
        color = color.equals("b") ? "w" : "b";
        
        if(!previous) return false;
        
        moves.add(move);
        boards.add(boardToString(board));
        //blackPlayNow = !blackPlayNow;
            
        return previous;
    }
    public static Move [] possibleMoves(String [][] board, String color){
        List<Move> m = new ArrayList(); 
        
        for(int i = 0;  i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("_")){
                    if(checkpossibilities(board, color, i, j, -1, -1, 0, false)
                        || checkpossibilities(board, color, i, j, -1, 1, 0, false)
                        || checkpossibilities(board, color, i, j, 1, -1, 0, false)
                        || checkpossibilities(board, color, i, j, 1, 1, 0, false)
                        || checkpossibilities(board, color, i, j, -1, 0, 0, false)
                        || checkpossibilities(board, color, i, j, 0, -1, 0, false)
                        || checkpossibilities(board, color, i, j, 1, 0, 0, false)
                        || checkpossibilities(board, color, i, j, 0, 1, 0, false)){
                        m.add(new Move(i, j, color));
                    }
                }
            }
        }
        Move [] possibleMoves = new Move[m.size()];
        for(int i = 0; i < possibleMoves.length; i++){
            possibleMoves[i] = m.get(i);
        }
        
        return possibleMoves;
    }
    public static boolean checkpossibilities(String [][] board, String color, int i, int j, int iDir, int jDir, int round, boolean colorMode){
        
        i += iDir;
        j += jDir;
        if(i < 0 || i >= board.length || j < 0 || j >= board.length) return false;
        
        if(board[i][j].equals("_")) return false;
        
        if(round == 0 && board[i][j].equals(color)) return false;
        
        if(board[i][j].equals(color)){
            if(colorMode) board[i - iDir][j - jDir] = color;
            return true;
        }
        
        if(checkpossibilities(board, color, i, j, iDir, jDir, ++round, colorMode)){
            if(colorMode) board[i - iDir][j - jDir] = color;
            return true;
        }
        return false;
    }
    public static String boardToString(String [][] board){
        String str = "";
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j< board.length; j++){
                str += board[i][j];
            }
        }
        return str;
    }
    public static String [][] stringToBoard(String str){
        if(str.length() == 64){
            int k = 0;
            String [][] board = new String[8][8];
            for(int i = 0 ; i < board.length; i++){
                for(int j = 0; j< board.length; j++){
                    board[i][j] = "" + str.charAt(k++);
                }
            }
            return board;
        }
        return null;
    }
    public static String [][] gameBack(String [][] board){
        if(boards.size() > 1 && moves.size() > 0){
            
            boards.remove(boards.size() - 1);
            moves.remove(moves.size() - 1);
            
            return stringToBoard(boards.get(boards.size() - 1));
        }
        return board;
    }
    public static void print(String [][] board){
        
        System.out.println("________________");
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j< board.length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("________________");
    }


}
