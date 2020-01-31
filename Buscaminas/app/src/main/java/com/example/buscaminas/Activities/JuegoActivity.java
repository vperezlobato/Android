package com.example.buscaminas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.buscaminas.R;

public class JuegoActivity extends AppCompatActivity {

    int mRows = 7;
    int mCols = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        Intent intent = getIntent();

        ConstraintLayout layout = findViewById(R.id.layout);

        int color1 = getResources().getColor(android.R.color.holo_red_light);
        int color2 = getResources().getColor(android.R.color.holo_blue_light);
        TextView textView;
        ConstraintLayout.LayoutParams lp;
        int id;
        int idArray[][] = new int[mRows][mCols];
        ConstraintSet cs = new ConstraintSet();

        // Add our views to the ConstraintLayout.
        for (int iRow = 0; iRow < mRows; iRow++) {
            for (int iCol = 0; iCol < mCols; iCol++) {
                textView = new TextView(this);
                lp = new ConstraintLayout.LayoutParams(ConstraintSet.MATCH_CONSTRAINT,
                        ConstraintSet.MATCH_CONSTRAINT);
                id = View.generateViewId();
                idArray[iRow][iCol] = id;
                textView.setId(id);
                textView.setText(String.valueOf(id));
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(((iRow + iCol) % 2 == 0) ? color1 : color2);
                layout.addView(textView, lp);
            }
        }

        // Create horizontal chain for each row and set the 1:1 dimensions.
        // but first make sure the layout frame has the right ratio set.
        cs.clone(layout);
        cs.setDimensionRatio(R.id.gridFrame, mCols + ":" + mRows);
        for (int iRow = 0; iRow < mRows; iRow++) {
            for (int iCol = 0; iCol < mCols; iCol++) {
                id = idArray[iRow][iCol];
                cs.setDimensionRatio(id, "1:1");
                if (iRow == 0) {
                    // Connect the top row to the top of the frame.
                    cs.connect(id, ConstraintSet.TOP, R.id.gridFrame, ConstraintSet.TOP);
                } else {
                    // Connect top to bottom of row above.
                    cs.connect(id, ConstraintSet.TOP, idArray[iRow - 1][0], ConstraintSet.BOTTOM);
                }
            }
            // Create a horiontal chain that will determine the dimensions of our squares.
            // Could also be createHorizontalChainRtl() with START/END.
            cs.createHorizontalChain(R.id.gridFrame, ConstraintSet.LEFT,
                    R.id.gridFrame, ConstraintSet.RIGHT,
                    idArray[iRow], null, ConstraintSet.CHAIN_PACKED);
        }

        cs.applyTo(layout);
    }
}
