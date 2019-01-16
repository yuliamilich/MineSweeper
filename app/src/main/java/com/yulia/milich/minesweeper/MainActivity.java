package com.yulia.milich.minesweeper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int columns = 8;
    private int mines = 10;
    private boolean flag = false;
    private ImageButton board[][] = new ImageButton[columns][columns];
    private CellData cellDataBoard[][] = new CellData[columns][columns];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBoard();
        setCellData();
        showBoard();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                int x = v.getId();
                int y = x % 100;
                x = x / 100;

                if (this.flag) {
                    if (cellDataBoard[x][y].getFlag()) {
                        cellDataBoard[x][y].setFlag(false);
                    } else if (cellDataBoard[x][y].getState() == CellData.states.Clear || cellDataBoard[x][y].getState() == CellData.states.Mine)
                        cellDataBoard[x][y].setFlag(true);
                } else {
                    if (cellDataBoard[x][y].getState() == CellData.states.Clear) {
                        cellDataBoard[x][y].setState(CellData.states.Exposed);
                    } else if (cellDataBoard[x][y].getState() == CellData.states.Mine) {
                        cellDataBoard[x][y].setState(CellData.states.Detonated);
                    }
                }
                showBoard();
        }

    }

    private void showBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (cellDataBoard[i][j].getFlag()) {
                    board[i][j].setBackgroundResource(R.mipmap.minesweeper_flag);
                } else {
                    if (cellDataBoard[i][j].getState() == CellData.states.Clear || cellDataBoard[i][j].getState() == CellData.states.Mine) {
                        board[i][j].setBackgroundResource(R.mipmap.minesweeper_unopened_square);
                    }
                    if (cellDataBoard[i][j].getState() == CellData.states.Exposed) {
                        switch (cellDataBoard[i][j].getNumOfMines()) {
                            case 0:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_zero);
                                break;
                            case 1:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_one);
                                break;
                            case 2:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_two);
                                break;
                            case 3:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_three);
                                break;
                            case 4:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_four);
                                break;
                            case 5:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_five);
                                break;
                            case 6:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_six);
                                break;
                            case 7:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_seven);
                                break;
                            case 8:
                                board[i][j].setBackgroundResource(R.mipmap.minesweeper_eight);
                                break;
                        }
                    }
                    if (cellDataBoard[i][j].getState() == CellData.states.Detonated) {
                        board[i][j].setBackgroundResource(R.mipmap.gnomine);
                    }
                }
            }
        }
    }

    private void createBoard() {
        TableLayout boardLayout = (TableLayout) findViewById(R.id.board);
        TableRow rows[] = new TableRow[columns];

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());

        for (int i = 0; i < columns; i++) {
            rows[i] = new TableRow(this); // creating the rows in the TableLayout
            for (int j = 0; j < columns; j++) {
                board[i][j] = new ImageButton(this); // creating the button and setting an id
                board[i][j].setId(i * 100 + j);

                board[i][j].setTag(""); // setting a tag - the tag represents the figure that is placed on the button
                board[i][j].setLayoutParams(new TableRow.LayoutParams(width, height)); // setting the size of the button

//                board[i][j].setScaleType(ImageView.ScaleType.CENTER); // that way the image on the button is in the right size
//                board[i][j].setImageResource(R.mipmap.minesweeper_flag);
                board[i][j].setBackgroundResource(R.mipmap.minesweeper_unopened_square);
                board[i][j].setOnClickListener(this);
                rows[i].addView(board[i][j]);
            }
            boardLayout.addView(rows[i]);
        }
    }

    private void setCellData() {
        boolean[][] Mines = randomMines();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                    cellDataBoard[i][j] = new CellData(numOfMines(i, j, Mines), Mines[i][j]);
            }
        }
    }

    private boolean[][] randomMines() {
        boolean[][] Mines = new boolean[columns][columns];

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < columns; j++) {
                Mines[i][j] = false;
            }
        }

        Random rnd = new Random();
        int rand1;
        int rand2;

        for (int i = 0; i < mines; i++) {
            rand1 = rnd.nextInt(columns);
            rand2 = rnd.nextInt(columns);
            if (Mines[rand1][rand2]) {
                i--;
            } else Mines[rand1][rand2] = true;
        }

        return Mines;
    }

    private int numOfMines(int x, int y, boolean[][] Mines) {
        int closeMines = 0;
        if (x + 1 < Mines.length && Mines[x + 1][y]) {
            closeMines++;
        }
        if (x + 1 < Mines.length && y + 1 < Mines.length && Mines[x + 1][y + 1]) {
            closeMines++;
        }
        if (y - 1 >= 0 && x + 1 < Mines.length && Mines[x + 1][y - 1]) {
            closeMines++;
        }
        if (y + 1 < Mines.length && Mines[x][y + 1]) {
            closeMines++;
        }
        if (y - 1 >= 0 && Mines[x][y - 1]) {
            closeMines++;
        }
        if (x - 1 >= 0 && Mines[x - 1][y]) {
            closeMines++;
        }
        if (x - 1 >= 0 && y - 1 >= 0 && Mines[x - 1][y - 1]) {
            closeMines++;
        }
        if (x - 1 >= 0 && y + 1 < Mines.length && Mines[x - 1][y + 1]) {
            closeMines++;
        }
        return closeMines;
    }

    private void changeFlag() {
        if (this.flag) {
            this.flag = false;
        } else this.flag = true;
    }
}

