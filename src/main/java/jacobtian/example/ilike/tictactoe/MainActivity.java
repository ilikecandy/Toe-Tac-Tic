package jacobtian.example.ilike.tictactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Creates new array 2D 3x3
    private Button[][] buttons = new Button[3][3];

    private boolean playerturn1 = true;

    private int roundcount;

    private int player1points;
    private int player2points;

    private TextView textViewplayer1;
    private TextView textViewplayer2;

    private TextView playerturn;

    private MediaPlayer ticMP = new MediaPlayer();
    private MediaPlayer restartMP = new MediaPlayer();

    private int firstMove = 1;  //1 - X went first  2 - player 2 went first

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewplayer1 = findViewById(R.id.text_view_p1);
        textViewplayer2 = findViewById(R.id.text_view_p2);

        ticMP = MediaPlayer.create(this, R.raw.tic);
        restartMP = MediaPlayer.create(this, R.raw.trashrestart);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                updatePointsText();
                restartMP.start();
            }

        });
        Button newgame = findViewById(R.id.new_game);
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                updatePointsText();
                restartMP.start();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals(""))
            return;
            if (playerturn1) {
                ((Button) v).setText("X");
            } else {
                ((Button) v).setText("O");
            }
            ticMP.start();

        roundcount++;

        if (checkForWin()) {
            if (playerturn1) {
                player1Wins();

            } else {
                player2Wins();
            }
        } else if (roundcount == 9) {
            draw();
        } else {
            playerturn1 = !playerturn1;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }


    private void player1Wins() {
        player1points++;
        Toast.makeText(this, "X Wins! O to move.", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
        playerturn1 = false;
        firstMove = 2;
    }

    private void player2Wins() {
        player2points++;
        Toast.makeText(this, "O Wins! X to move.", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
        playerturn1 = true;
        firstMove = 1;
    }

    private void draw() {
        resetBoard();
        if (firstMove == 1) {
            playerturn1 = false;
            firstMove = 2;
            Toast.makeText(this, "Draw! O to move.", Toast.LENGTH_SHORT).show();

        } else {
            playerturn1 = true;
            firstMove = 1;
            Toast.makeText(this, "Draw! X to move.", Toast.LENGTH_SHORT).show();

        }
    }

    private void updatePointsText(){
        textViewplayer1.setText("Player X: " +player1points);
        textViewplayer2.setText("Player O: " +player2points);
    }
    private void resetBoard(){
    for (int i = 0; i < 3; i++){
        for (int j = 0; j < 3; j++){
            buttons[i][j].setText("");
        }
    }
    roundcount = 0;

    }

    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundcount",roundcount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("playerturn1",playerturn1);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundcount = savedInstanceState.getInt("roundcount");
        player1points = savedInstanceState.getInt("player1points");
        player2points = savedInstanceState.getInt("player2points");
        playerturn1 = savedInstanceState.getBoolean("playerturn1");
    }


}