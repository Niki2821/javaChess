package com.figures;

public abstract class figure {
    
    public abstract boolean validateMove(int origX, int origY, int tarX, int tarY, boolean isKillingMove);
    public String displayFigure;
    public char team;
    public int currentX;
    public int currentY;
    public int startingX;
    public int startingY;
    public boolean isAlive;
    
    
    public figure(String displayFigure, char team, int currentX, int currentY, boolean isAlive) {
        this.displayFigure = displayFigure;
        this.team = team;
        this.currentX = currentX;
        this.currentY = currentY;
        this.isAlive = isAlive;
        
        this.startingY = currentY;
        this.startingX = currentX;
    }
    
}
