package com.figures;

public class tower extends figure{
    
    public static String displayFigure = "t";

    public tower(char team, int currentX, int currentY){
        super(displayFigure, team, currentX, currentY, true);
    }
    
    public boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove){ // doesnt work yet
        
        if(origX != tarX){
            if(origY != tarY){
                return false;
            }
        }
        else if(origY != tarY){
            if(origX != tarX){
                return false;
            }
        }
        return true;
    }

}
