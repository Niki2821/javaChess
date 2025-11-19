package com.figures;

public class knight extends figure{
    
    public static String displayFigure = "k";

    public knight(char team, int currentX, int currentY){
        super(displayFigure, team, currentX, currentY, true);
    }
    
    public boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove){ 
        

        if(Math.abs(origX - tarX) == 2){ 
            if(Math.abs(origY - tarY) == 1){ 
                return true;
            }
        }
        if(Math.abs(origY - tarY) == 2){ 
            if(Math.abs(origX - tarX) == 1){ 
                return true;
            }
        }

        return false;
    }

}
