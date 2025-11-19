package com.figures;

public class pawn extends figure{
    
    public static String displayFigure = "p";

    public pawn(char team, int currentX, int currentY){
        super(displayFigure, team, currentX, currentY, true);
    }
    
    public boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove){
        if(!isKillingMove){
            if(origY != tarY){
                return false;
            }
        }
        
        if(team == 'b'){
            if(origX < tarX){
                return false;
            }
        }
        else if(team == 'w'){
            if(origX > tarX){
                return false;
            }
        }
        if(Math.abs(super.startingX - tarX) > 2){ //check if its the first move
            if(Math.abs(origX - tarX) > 1){
                return false;
            }
        }
        else if(Math.abs(origX - tarX) > 2){
            return false;
        }
        return true;
    }

}
