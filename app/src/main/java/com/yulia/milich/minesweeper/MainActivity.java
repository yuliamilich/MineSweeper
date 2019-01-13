package com.yulia.milich.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton board[][] = new ImageButton[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBoard();

    }

    @Override
    public void onClick(View v) {

    }

    public void createBoard(){
        TableLayout boardLayout = (TableLayout) findViewById(R.id.board);
        TableRow rows[] = new TableRow[8];

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44, getResources().getDisplayMetrics());

        for (int i=0; i<8; i++){
            rows[i] = new TableRow(this); // creating the rows in the TableLayout
            for(int j=0; j<8; j++){
                board[i][j] = new ImageButton(this); // creating the button and setting an id
                board[i][j].setId(i*100+j);

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
}
