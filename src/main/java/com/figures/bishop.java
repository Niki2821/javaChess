package com.figures;

public class bishop extends figure{
    
    public static String displayFigure = "b";

    public bishop(char team, int currentX, int currentY){
        super(displayFigure, team, currentX, currentY, true);
    }
    
    public boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove){ // doesnt work yet
        

        if(Math.abs(origX - tarX) != Math.abs(origY - tarY)){ //the diffrence between the the orig and tar must be the same for both axis, this checks if a move is really diagonal
            return false;
        }

        if(origX == tarX ){
            return false;
        }
        else if(origY == tarY){
            return false;
        }

        return true;
    }

}
