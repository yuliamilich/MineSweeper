package com.yulia.milich.minesweeper;


public class CellData {
    enum states{
        Clear, Mine, Exposed, Detonated;
    }
    private int numOfMines;
    private boolean flag;
    private states state;

    public CellData(int numOfMines, boolean mine){
        this.flag = false;
        this.numOfMines = numOfMines;
        if (mine){
            this.state = states.Mine;
        }
        else this.state = states.Clear;
    }

    public states getState(){
        return state;
    }

    public void setState(states state){
        this.state = state;
    }

    public int getNumOfMines(){
        return numOfMines;
    }

    public boolean getFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
