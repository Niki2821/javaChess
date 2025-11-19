package com.figures;

public class king extends figure{
    
    public static String displayFigure = "K";


    public king(char team, int currentX, int currentY){
        super(displayFigure, team, currentX, currentY, true);

    }
    
    public boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove){
        if(Math.abs(origX - tarX) > 1){
            return false;
        }
        else if(Math.abs(origY - tarY) > 1){
            return false;
        }

        return true;
    }

}
