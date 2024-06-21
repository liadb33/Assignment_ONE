package com.example.assignment_one_n.Logic;


import java.util.Random;

public class GameManager {


    private static final int ROWS = 8;

    private static final int COLS =  3;
    private int[][] obstacleBoard;
    private int[] dodgeBoard;

    private int life;
    private int hitNum;

    private int currentCol;

    private int cycleCount;
    private Random random;

    public GameManager() { this(3);}

    public GameManager(int life) {

        obstacleBoard = new int[ROWS][COLS];
        for (int i = 0; i < obstacleBoard.length; i++) {
            for (int j = 0 ; j < obstacleBoard[0].length; j++)
                obstacleBoard[i][j] = 0;
        }

        hitNum = 0;
        cycleCount = 0;
        random = new Random();
        this.life = life;
        dodgeBoard = new int[]{0,1,0};
    }


    public void updateGameLogic() {

        for (int col = 0; col < obstacleBoard[0].length; col++)
            if (obstacleBoard[obstacleBoard.length - 1][col] == 1)
                obstacleBoard[obstacleBoard.length - 1][col] = 0;

        for (int row = obstacleBoard.length - 1; row > 0; row--) {
            for (int col = 0; col < obstacleBoard[0].length; col++) {
                if (obstacleBoard[row - 1][col] == 1) {
                    obstacleBoard[row - 1][col] = 0;
                    obstacleBoard[row][col] = 1;
                }
            }
        }

        if (cycleCount % 3 == 0) {
            currentCol = random.nextInt(3);
            obstacleBoard[0][currentCol] = 1;
        }

        cycleCount++;
    }

    public void dodgeMovement(int direction){

        boolean flag = false;
        int newPosition;
        int currentPosition = -1;

        for (int i = 0; i < dodgeBoard.length && !flag; i++) {
            if (dodgeBoard[i] == 1) {
                currentPosition = i;
                flag = true;
            }
        }

        newPosition = currentPosition + direction;
        if (newPosition >= dodgeBoard.length || newPosition < 0 || currentPosition == -1)
            return;

        for (int i = 0; i < dodgeBoard.length; i++) {
            if (i == newPosition)
                dodgeBoard[i] = 1;
            else
                dodgeBoard[i] = 0;
        }
    }

    public boolean isGameLost(){
        return getHitNum() == getLife();
    }


    public boolean collisionDetection(int col){
        return obstacleBoard[obstacleBoard.length - 2][col] == 1 && dodgeBoard[col] == 1;
    }

    public void updateHits(){
        if(hitNum < life)
            hitNum++;
    }

    public int getLife() {
        return life;
    }

    public int[][] getObstacleBoard() {
        return obstacleBoard;
    }

    public int[] getDodgeBoard() {
        return dodgeBoard;
    }

    public GameManager setObstacleBoard(int[][] obstacleBoard) {
        this.obstacleBoard = obstacleBoard;
        return this;
    }

    public GameManager setDodgeBoard(int[] dodgeBoard) {
        this.dodgeBoard = dodgeBoard;
        return this;
    }

    public int getHitNum() {
        return hitNum;
    }
}
