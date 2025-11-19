package com.figures;

public class queen extends figure{
    
    public static String displayFigure = "Q";

    public queen(char team, int currentX, int currentY){
        super(displayFigure, team, currentX, currentY, true);
    }
    
    public boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove){ // doesnt work yet
        

        if(Math.abs(origX - tarX) == Math.abs(origY - tarY)){ //the diffrence between the the orig and tar must be the same for both axis, this checks if a move is really diagonal
            return true;
        }

        else if(origX == tarX){
            return true;
        }
        else if(origY == tarY){
            return true;
        }

        return false;
    }

}
