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

/*
classe utilisé pour afficher les parties enregistrés
sans pour autant importetr tous leurs conenus

il comprent les noms des joueurs et leurs scores 
et un attribut gameId qui va permettre de specifier
la partie dans la base de donnés
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
