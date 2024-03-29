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
    static int ids = 0;
    
    int id;
    String [][] board;
    public Player whitePlayer;
    public Player blackPlayer;
    public boolean playWithComputer = true;
    boolean blackPlayNow = true;
    GameTimer blackPlayerTimer;
    GameTimer whitePlayerTimer;
    Move [] playerPossibleMoves;
    Move lastMove;
    Move bestMove = null;
    List<Move> moves;
    List<String> boards;
    
    public boolean checkpossibilities(String color, int i, int j, int iDir, int jDir, int round, boolean colorMode){
        
        i += iDir;
        j += jDir;
        if(i < 0 || i >= board.length || j < 0 || j >= board.length) return false;
        
        if(board[i][j].equals("_")) return false;
        
        if(round == 0 && board[i][j].equals(color)) return false;
        
        if(board[i][j].equals(color)){
            if(colorMode) board[i - iDir][j - jDir] = color;
            return true;
        }
        
        if(checkpossibilities(color, i, j, iDir, jDir, ++round, colorMode)){
            if(colorMode) board[i - iDir][j - jDir] = color;
            return true;
        }
        return false;
    }
    public String [][] newGame(){
        newTimer();
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
        this.id = ++ids;
        this.board = newBoard;
        this.blackPlayNow = true;
        String color = blackPlayNow ? "b" : "w";
        String Player2 = playWithComputer ? "Computer" : "Player 2";
        whitePlayer = new Player(Player2, "w");
        blackPlayer = new Player("Player 1", "b");
        playerPossibleMoves = possibleMoves(color);
        this.moves = new ArrayList();
        this.boards = new ArrayList();
        boards.add(boardToString(this.board));
        evaluateScore();
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
    public Move [] possibleMoves(String color){
        List<Move> m = new ArrayList(); 
        
        for(int i = 0;  i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("_")){
                    if(checkpossibilities(color, i, j, -1, -1, 0, false)
                        || checkpossibilities(color, i, j, -1, 1, 0, false)
                        || checkpossibilities(color, i, j, 1, -1, 0, false)
                        || checkpossibilities(color, i, j, 1, 1, 0, false)
                        || checkpossibilities(color, i, j, -1, 0, 0, false)
                        || checkpossibilities(color, i, j, 0, -1, 0, false)
                        || checkpossibilities(color, i, j, 1, 0, 0, false)
                        || checkpossibilities(color, i, j, 0, 1, 0, false)){
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
    public boolean playMove(Move move){
        if (move == null){
            return false;
        }
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
        
        //prendre la couleur oposant
        color = color.equals("b") ? "w" : "b";
        
        playerPossibleMoves = possibleMoves(color);
        if(!previous) return false;
        
        evaluateScore();
        moves.add(move);
        boards.add(boardToString(board));
        lastMove = move;
        bestMove = null;
        blackPlayNow = !blackPlayNow;
            
        return previous;
    }
    public boolean gameOver(){
        if(gameFinished()) return true;
        
        String color = blackPlayNow ? "b" : "w";
        
        playerPossibleMoves = possibleMoves(color);
        
        if(playerPossibleMoves.length == 0){
            color = blackPlayNow ? "w" : "b";
            blackPlayNow = !blackPlayNow;
            playerPossibleMoves = possibleMoves(color);
            if(playerPossibleMoves.length == 0) return true;
        }
        
        return blackPlayerTimer.noTimeLeft() || whitePlayerTimer.noTimeLeft();
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
        if(blackPlayerTimer.noTimeLeft()) return blackPlayer.getName() + " lost with no time left.";
        if(whitePlayerTimer.noTimeLeft()) return whitePlayer.getName() + " lost with no time left.";

            
        String winner = whitePlayer.getScore() > blackPlayer.getScore() ? whitePlayer.getName() : whitePlayer.getScore() < blackPlayer.getScore() ? blackPlayer.getName() : "Draw !!!";
        return winner.equals("Draw !!!")? winner : winner + " won !!!";
    }
    public String getColor(int i, int j){
        return board[i][j];
    }
    public String getPlayerColor(){
        return blackPlayNow ? "b" : "w";
    }
    public String [][] getBoard(){
        return this.board;
    }
    public boolean blackPlayNow(){
        return blackPlayNow;
    }
    public Move [] getPossibleMoves(){
        return playerPossibleMoves;
    }
    public boolean isPossibleMove(int x, int y){
        for(int i = 0; i < playerPossibleMoves.length; i++){
            if(playerPossibleMoves[i].i== x && playerPossibleMoves[i].j == y) return true;
        }
        return false;
    }
    public boolean gameBack(){
        
        
        if(boards.size() > 1 && moves.size() > 0){
            
            boards.remove(boards.size() - 1);
            moves.remove(moves.size() - 1);
            
            this.board = stringToBoard(boards.get(boards.size() - 1));
            this.lastMove = moves.size() > 0 ? moves.get(moves.size() -1) : null;
            
            blackPlayNow = !blackPlayNow;
            playerPossibleMoves = possibleMoves(getPlayerColor());
            
            evaluateScore();
            return true;
        }
        return false;
    }
    public Move getLastMove(){
        return this.lastMove;
    }
    public Move findBestMove(){
        Move move = null;
        String b = boardToString(board);
        String [][] newBoard = stringToBoard(b);
        move = GameSearch.findBestMove(newBoard, getPlayerColor());
        
        if(move != null){
            move.color = getPlayerColor();
        
            this.bestMove = move;
        }
        
        return move;
    }
    public Move getBestMove(){
        return this.bestMove;
    }
    public String boardToString(String [][] board){
        String str = "";
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j< board.length; j++){
                str += board[i][j];
            }
        }
        return str;
    }
    public String [][] stringToBoard(String str){
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
    public void setPossibleMoves(){
        this.playerPossibleMoves = possibleMoves(getPlayerColor());
    }

    public GameTimer getBlackPlayerTimer() {
        return blackPlayerTimer;
    }
    public void setBlackPlayerTimer(GameTimer blackPlayerTimer) {
        this.blackPlayerTimer = blackPlayerTimer;
    }
    public GameTimer getWhitePlayerTimer() {
        return whitePlayerTimer;
    }
    public void setWhitePlayerTimer(GameTimer whitePlayerTimer) {
        this.whitePlayerTimer = whitePlayerTimer;
    }
    
    public void newTimer(){
        this.whitePlayerTimer = new GameTimer(5,0);
        this.blackPlayerTimer = new GameTimer(5,0);
    }
    
}
