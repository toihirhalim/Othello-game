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
public class GameTimer {
    public int min;
    public int sec;
    
    public GameTimer(int min, int sec){
        this.min = min;
        this.sec = sec;
    }
    
    public boolean noTimeLeft(){
        return min <= 0 && sec <= 0;
    }
    
    public boolean removeSec(){
        if(this.sec > 0){
            this.sec--;
            return true;
        }else if(min > 0) {
            min--;
            this.sec = 59;
            return true;
        }
        this.min = 0;
        this.sec = 0;
        return false;
    }
    
    public String toString(){
        String minString = this.min >= 10 ? "" + this.min : "0" + this.min;
        String secString = this.sec >= 10 ? "" + this.sec : "0" + this.sec;
        return minString + " : " + secString;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }
}
