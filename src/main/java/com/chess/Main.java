package com.chess;
import java.util.*;
import java.util.regex.Pattern;

import com.gameBoard.gameBoard;
import com.figures.*;

public class Main {
    public static void main(String[] args) {
        gameBoard gb = new gameBoard();
        
        //initialize gameplay variables
        boolean gameFinished = false;
        char movingTeam = 'w';
        
        // region initialize figures 
        figure[] figures = {
            //white team

            //pawns
            new pawn('w', 1,0), 
            new pawn('w', 1,1), 
            new pawn('w', 1,2), 
            new pawn('w', 1,3), 
            new pawn('w', 1,4), 
            new pawn('w', 1,5), 
            new pawn('w', 1,6), 
            new pawn('w', 1,7), 

            new king('w', 0, 3),
            new queen('w', 0, 4),

            new tower('w', 0, 0),
            new tower('w', 0, 7),

            new bishop('w', 0, 2),
            new bishop('w', 0, 5),

            new knight('w', 0, 1),
            new knight('w', 0, 6),
            
            //black team
            
            //pawns
            new pawn('b', 6,0), 
            new pawn('b', 6,1), 
            new pawn('b', 6,2), 
            new pawn('b', 6,3), 
            new pawn('b', 6,4), 
            new pawn('b', 6,5), 
            new pawn('b', 6,6), 
            new pawn('b', 6,7), 
            
            new king('b', 7, 3),
            new queen('b', 7, 4),
            
            new tower('b', 7, 0),
            new tower('b', 7, 7),
            
            new bishop('b', 7, 2),
            new bishop('b', 7, 5),

            new knight('b', 7, 1),
            new knight('b', 7, 6),
        };
        
        refreshBoard(gb, figures);
        
        // region initialize game loop

        while(!gameFinished){
            //show alive figures:
            countFigures(figures);
            //print currently moving team
            System.out.println("Currently moving team: " + movingTeam);
            //get move coordiantes in following format: {[0]selectedFigX, [1]selectedFigY, [2]targetPosX, [3]targetPosY}
            int[] move = getPlayerMove();
            int selectedFigure = getFigureFromPos(figures, move[0], move[1], movingTeam);
            //check if the move is a castling move by checking if the coordinates returned from the move function equal a code
            if(Arrays.equals(move ,new int[]{101, 100, 100, 100})){
                figures = tryCastling(figures, movingTeam, gb);
                refreshBoard(gb, figures);
                continue;
            }
            //check if a figure is actually standing at the coordianates the player speciffied
            if(selectedFigure == -1){
                refreshBoard(gb, figures);
                System.out.println("No figure found at this position for this team. Please try again..");
                continue;
            }
            
            // first check if move kills or the target position is occupied, then validate if the move is valid for the figure, than change position
            short checkKillreturn = checkKill(figures, move[2], move[3], selectedFigure);
            
            if(checkKillreturn == 1){
                if(!figures[selectedFigure].validateMove(move[0], move[1], move[2], move[3], false)){
                    refreshBoard(gb, figures);
                    System.out.println("This move is invalid, please try again..");
                    continue;
                }
            }
            else if(checkKillreturn == 2){
                if(!figures[selectedFigure].validateMove(move[0], move[1], move[2], move[3], true)){
                    refreshBoard(gb, figures);
                    System.out.println("This move is invalid, please try again..");
                    continue;
                }
            }
            else if(checkKillreturn == 0){
                refreshBoard(gb, figures);
                System.out.println("There is a figure from your team already there! try again.."); // this doesnt work yet
                continue;
            }
            //move figure if move and kill checks a are successfull:
            figures[selectedFigure].currentX = move[2];
            figures[selectedFigure].currentY = move[3];
            System.out.println("Move valid");
            //fill old position with space on the board:
            gb.mapToBoard(" ", move[0], move[1]);
            //change moving team and safe before moving team to check checkmate
            char oldMT = movingTeam;
            movingTeam = changeteam(movingTeam);
            //check if a King hast been killed
            if (checkMate(figures, movingTeam)){
                System.out.println(oldMT + " won! Check Mate!");
                break;
            };

            refreshBoard(gb, figures);
        }

    }
    
    //region Utility Functions
    public static int[] getPlayerMove(){
        Scanner sc = new Scanner(System.in);
        final String coordiantesRegex = "^\\d+,\\d+$";
        String targetFigure;
        String targetPosition;

        System.out.println("Please enter the coordinates from the figure you want to move (Format: \"x,y\"), or type 'help' for possible commands:");
        do {
            targetFigure = sc.nextLine();
            if(targetFigure.equals("re")){ //you can restart the move process by typing in "re"
                getPlayerMove();
            }
            if(targetFigure.equals("help")){
                showHelp();
                getPlayerMove();
            }
            if(targetFigure.equals("castle")){
                //start catling move in the main gameloop by returning impossible coordiantes as a code
                System.out.println("trying castling...");
                return new int[]{101, 100, 100, 100};
            }
            if(!Pattern.matches(coordiantesRegex, targetFigure)){
                System.out.println("Wrong Format, please try again..");
            }
            
        }while(!Pattern.matches(coordiantesRegex, targetFigure));
        
        int targetFigureX = Integer.parseInt(targetFigure.split(",")[0]);
        int targetFigureY = Integer.parseInt(targetFigure.split(",")[1]);
        
        System.out.println("Please enter the coordinates where you want to the figure to move to (Format: \"x,y\"):");
        do {
            targetPosition = sc.nextLine();
            if(targetFigure.equals("re")){ //you can restart the move process by typing in "re"
                getPlayerMove();
            }
            if(!Pattern.matches(coordiantesRegex, targetPosition)){
                System.out.println("Wrong Format, please try again..");
            }
            
        }while(!Pattern.matches(coordiantesRegex, targetPosition));
        
        int targetPositionX = Integer.parseInt(targetPosition.split(",")[0]);
        int targetPositionY = Integer.parseInt(targetPosition.split(",")[1]);
        
        int[] positions = {targetFigureX, targetFigureY, targetPositionX, targetPositionY};

        return positions;
    }

    public static void refreshBoard(gameBoard gb, figure[] figures){
        //define team colors
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

        for(figure figure : figures){
            if(figure.isAlive){
                if(figure.team == 'w'){
                    gb.mapToBoard(ANSI_WHITE_BACKGROUND + figure.displayFigure + ANSI_RESET, figure.currentX, figure.currentY);
                }
                else{
                    gb.mapToBoard(figure.displayFigure, figure.currentX, figure.currentY);
                }
            }
        }
        gb.printBoard();
    }
    
    public static int getFigureFromPos(figure[] figures, int targetX, int targetY, char team){
        for(int i = 0; i < figures.length; i++){
                if(figures[i].currentX == targetX && figures[i].currentY == targetY && figures[i].team == team){
                    return i;
                }
            }
        return -1;
    }

    public static short checkKill(figure[] figures, int targetX, int targetY, int killerFigure){
        //this function essentially only returns if you can move to the target coordiantes and if you kill an enemy by doing so, it takes care of the according enemy figure attributes.
        for(figure figure : figures){
            if(figure.currentX == targetX && figure.currentY == targetY){
                //figure at target move position found, check if kill is valid by comparing attacking team to target position
                if(figures[killerFigure].team == 'w' && figure.team == 'b'){
                    figure.isAlive = false;
                    return 2;
                }
                else if(figures[killerFigure].team == 'b' && figure.team == 'w'){
                    figure.isAlive = false;
                    return 2;
                }
                else if(figures[killerFigure].team == figure.team){
                    return 0;
                }
            }
        }
        return 1;
    }

    public static void countFigures(figure[] figures){
        //this function counts ONLY ALIVE figures from both teams and prints the numbers.
        int blackFigureCount = 0;
        int whiteFigureCount = 0;
        for(figure figure : figures){
            if(figure.team == 'w' && figure.isAlive == true){
                whiteFigureCount++;
            }
            if(figure.team == 'b' && figure.isAlive == true){
                blackFigureCount++;
            }
        }
        System.out.println("Current white figures alive: "+ whiteFigureCount);
        System.out.println("Current black figures alive: "+ blackFigureCount);

    }

    public static boolean checkMate(figure[] figures, char movingTeam){
        for (figure figure : figures) {
            if(figure.displayFigure == "K" && figure.team == movingTeam && figure.isAlive == false){
                return true;
            }
        }
        return false;
    }

    public static void showHelp(){
        System.out.println("Possible Commands are: ");
        System.out.println("'re'        for restarting the current move");
        System.out.println("'castle'    for doing the castling move in your team");
        System.out.println("'x,y'       Coordinate format of target figure");
    }
    
    public static figure[] tryCastling(figure[] figures, char movingTeam, gameBoard gb){
        //WIP

        int kingX = -1;
        int kingY = -1;
        int kingStartingX = -2;
        int kingStartingY = -2;


        for(figure figure : figures){
            if(figure.displayFigure == "K" && figure.team == movingTeam){
                kingX = figure.currentX;
                kingY = figure.currentY;

                kingStartingX = figure.startingX;
                kingStartingY = figure.startingY;
            }
        }
        
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the coordinates from the tower you want to castle with:  (Format: \"x,y\")");
        String towerCoord = sc.nextLine();
        
        
        int towerX = Integer.parseInt(towerCoord.split(",")[0]);
        int towerY = Integer.parseInt(towerCoord.split(",")[1]);
        
        int selectedFigure = getFigureFromPos(figures, towerX, towerY, movingTeam);
        
        if(selectedFigure != -1){
            if(!figures[selectedFigure].displayFigure.equals("t") || figures[selectedFigure].team != movingTeam){
                System.out.println("Not Tower found at this position, try again..");
                tryCastling(figures, movingTeam, gb);
                return figures;
            }
        }
        else{
            System.out.println("Not Tower found at this position, try again..");
            tryCastling(figures, movingTeam, gb);
            return figures;
        }

        int towerStartingX = figures[selectedFigure].startingX;
        int towerStartingY = figures[selectedFigure].startingY;


        if(kingX == kingStartingX && kingY == kingStartingY && towerX == towerStartingX && towerY == towerStartingY){ 
            //check if king or tower has moved at all in game
            if(towerY < kingY){ //check id there are any figures between the tower and the king
                int counter = towerY +1;
                while(counter != kingY){
                    if(gb.getFromBoard(counter, towerY).equals(" ")){
                        counter++;
                    }
                    else{
                        System.out.println("There a figures blocking the castling move!");
                        return figures;
                    }
                }
            }
            else if(towerY > kingY){
                int counter = towerY -1;
                while(counter != kingY){
                    if(gb.getFromBoard(counter, towerY).equals(" ")){
                        counter--;
                    }
                    else{
                        System.out.println("There a figures blocking the castling move!");
                        return figures;
                    }
                }
            }
            
            int kingIndex = getFigureFromPos(figures, kingX, kingY, movingTeam);
            int towerIndex = getFigureFromPos(figures, towerX, towerY, movingTeam);

            figures[kingIndex].currentX = towerX;
            figures[kingIndex].currentY = towerY;
            
            figures[towerIndex].currentX = kingX;
            figures[towerIndex].currentY = kingY;
            System.out.println("Castling succeded.");

            return figures;
        }
        System.out.println("king or tower not at starting position!");
        return figures;
    }

    public static char changeteam(char movingTeam){
        if(movingTeam == 'w'){
            movingTeam = 'b';
        }
        else if(movingTeam == 'b'){
            movingTeam = 'w';
        }
        else{
            System.out.println("error changing team.");
        }
        return movingTeam;
    }
}