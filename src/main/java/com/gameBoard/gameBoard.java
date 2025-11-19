package com.gameBoard;
public class gameBoard {
    //region ARRAYS

    private String[][] gameBoard = {{"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}, 
                                    {"[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]", "[", " ", "]"}};
    
    public String[][] gameBoardMapping = { {"0,1", "0,4", "0,7", "0,10", "0,13", "0,16", "0,19", "0,22"},
                                            {"1,1", "1,4", "1,7", "1,10", "1,13", "1,16", "1,19", "1,22"},
                                            {"2,1", "2,4", "2,7", "2,10", "2,13", "2,16", "2,19", "2,22"},                               
                                            {"3,1", "3,4", "3,7", "3,10", "3,13", "3,16", "3,19", "3,22"},
                                            {"4,1", "4,4", "4,7", "4,10", "4,13", "4,16", "4,19", "4,22"},
                                            {"5,1", "5,4", "5,7", "5,10", "5,13", "5,16", "5,19", "5,22"},
                                            {"6,1", "6,4", "6,7", "6,10", "6,13", "6,16", "6,19", "6,22"},
                                            {"7,1", "7,4", "7,7", "7,10", "7,13", "7,16", "7,19", "7,22"}};


    //region FUNCTIONS

    public void mapToBoard(String figure,int targetX, int targetY){
        String gameboardCoords = gameBoardMapping[targetX][targetY];
        String gameboardX = gameboardCoords.split(",")[0];
        String gameboardY = gameboardCoords.split(",")[1];
        gameBoard[Integer.parseInt(gameboardX)][Integer.parseInt(gameboardY)] = figure;
    }

    public String getFromBoard(int targetX, int targetY){
        String gameboardCoords = gameBoardMapping[targetX][targetY];
        String gameboardX = gameboardCoords.split(",")[0];
        String gameboardY = gameboardCoords.split(",")[1];
        return gameBoard[Integer.parseInt(gameboardX)][Integer.parseInt(gameboardY)];
    }

    public void printBoard(){
        for(int row = 0; row < gameBoard.length; row++){
            for(int col = 0; col < gameBoard[row].length; col++){
                System.out.print(gameBoard[row][col]);
            }
            System.out.println();
        }
    }

    //region GETTER/SETTTER
                                            
    public String[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public String[][] getGameBoardMapping() {
        return gameBoardMapping;
    }

    public void setGameBoardMapping(String[][] gameBoardMapping) {
        this.gameBoardMapping = gameBoardMapping;
    }
    
                                            
                                            
}
