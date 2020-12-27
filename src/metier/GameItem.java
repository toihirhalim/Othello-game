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
public class GameItem {
    public int gameId;
    public String blackPlayerName;
    public String whitePlayerName;
    public int blackPlayerScore;
    public int whitePlayerScore;
    
    public GameItem(){}

    public GameItem(int gameId, String blackPlayerName, String whitePlayerName, int blackPlayerScore, int whitePlayerScore) {
        this.gameId = gameId;
        this.blackPlayerName = blackPlayerName;
        this.whitePlayerName = whitePlayerName;
        this.blackPlayerScore = blackPlayerScore;
        this.whitePlayerScore = whitePlayerScore;
    }
    
}
