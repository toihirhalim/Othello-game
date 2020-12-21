/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Toihir
 */
public class Game {
    String [][] board;
    Player whitePlayer;
    Player blackPlayer;
    boolean blackPlayNow = true;
    Move [] whitePlayerPossibleMoves;
    Move [] blackPlayerPossibleMoves;
    List<Move> moves;
    List<String [][]> boards;
    
    public boolean checkpossibilities(String color, int i, int j, int iDir, int jDir, int round, boolean colorMode){
        i += iDir;
        j += jDir;
        if(i < 0 || i >= board.length || j < 0 || j >= board.length) return false;
        
        if(board[i][j].equals("_")) return false;
        
        if(round == 0 && board[i][j].equals(color)) return false;
        
        if(board[i][j].equals(color)) return true;
        
        if(checkpossibilities(color, i, j, iDir, jDir, ++round, colorMode)){
            if(colorMode) board[i-1][j-1] = color;
            return true;
        }
        return false;
    }
    public String [][] newGame(){
        String [][] newBoard = {
            {"_", "_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "w", "b", "_", "_", "_"},
            {"_", "_", "_", "b", "w", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_", "_"}
        };
        this.board = newBoard;
        this.blackPlayNow = blackPlayNow = true;
        return newBoard;
    }
    public void evaluateScore(){
        int whitePoints = 0, blackPoints = 0;
        
        for(int i = 0;  i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("w")) whitePoints++;
                else if(board[i][j].equals("b")) blackPoints++;
            }
        }
        whitePlayer.setScore(whitePoints);
        blackPlayer.setScore(blackPoints);
    }
    public boolean gameFinished(){
        for(int i = 0;  i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("_")) return false;
            }
        }
        return true;
    }
    public Move [] emptyMoves(){
        Move [] max = new Move[board.length];
        
        int count = 0;
        for(int i = 0;  i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("_")) max[count++] = new Move(i, j, "_");
            }
        }
        Move [] emptyMoves = new Move[--count];
       for(int i = 0;  i < count; i++){
            emptyMoves[i] = max[i];
        }
       return emptyMoves.length != 0 ? emptyMoves : null;
    }
    public Move [] possibleMoves(Player player){
        List<Move> m = new ArrayList(); 
        
        for(int i = 0;  i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("_")){
                    if(checkpossibilities(player.getColor(), i, j, -1, -1, 0, false)
                        || checkpossibilities(player.getColor(), i, j, -1, 1, 0, false)
                        || checkpossibilities(player.getColor(), i, j, 1, -1, 0, false)
                        || checkpossibilities(player.getColor(), i, j, 1, 1, 0, false)
                        || checkpossibilities(player.getColor(), i, j, -1, 0, 0, false)
                        || checkpossibilities(player.getColor(), i, j, 0, -1, 0, false)
                        || checkpossibilities(player.getColor(), i, j, 1, 0, 0, false)
                        || checkpossibilities(player.getColor(), i, j, 0, 1, 0, false))
                        m.add(new Move(i, j, player.getColor()));
                }
            }
        }
        Move [] possibleMoves = new Move[m.size()];
        for(int i = 0; i < possibleMoves.length; i++){
            possibleMoves[i] = m.get(i);
        }
        
        return possibleMoves;
    }
    public boolean playMove(Move move){
        int i = move.i, j = move.j;
        String color = move.color;
        boolean test, previous = false;
        
        if((blackPlayNow && !color.equals("b")) || !blackPlayNow && !color.equals("w")) return false;
        
        test = checkpossibilities(color, i, j, -1, -1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, -1, 1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, 1, -1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, 1, 1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, -1, 0, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, 0, -1, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, 1, 0, 0, true);
        previous = test ? true : previous;
        test = checkpossibilities(color, i, j, 0, 1, 0, true);
        previous = test ? true : previous;
        
        evaluateScore();
        moves.add(move);
        boards.add(board);

        if(blackPlayNow){
            whitePlayerPossibleMoves = possibleMoves(whitePlayer);
            blackPlayerPossibleMoves = new Move[0];
        }else {
            blackPlayerPossibleMoves = possibleMoves(blackPlayer);
            whitePlayerPossibleMoves = new Move[0];
        }
        blackPlayNow = !blackPlayNow;
            
            
        return previous;
    }
    public boolean gameOver(){
        if(gameFinished()) return true;
        
        whitePlayerPossibleMoves = possibleMoves(whitePlayer);
        blackPlayerPossibleMoves = possibleMoves(blackPlayer);
        
        if(whitePlayerPossibleMoves.length == 0 && blackPlayerPossibleMoves.length == 0) return true;
        
        return false;
    }
    public void print(){
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j< board.length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    public String winner(){
        return whitePlayer.getScore() > blackPlayer.getScore() ? "WhitePlayer" : whitePlayer.getScore() > blackPlayer.getScore() ? "BlackPlayer" : "Draw";
    }
}
