package so.party.ladders.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Random;

import so.party.ladders.R;
import so.party.ladders.game.GameUtil;
import so.party.ladders.game.Ladder;
import so.party.ladders.game.LadderAxis;
import so.party.ladders.game.Rule;
import so.party.ladders.ui.view.LadderView;
import so.party.ladders.ui.view.OnTouchLadderView;
import so.party.ladders.util.Logger;


public class MainActivity extends Activity implements OnTouchLadderView {

    private LadderView ladderView;
    private Ladder ladder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ladderView = (LadderView) findViewById(R.id.ladderview);
        ladderView.setListener(this);
        init(new Random().nextInt(Rule.MAX_LADDER_CNT));
    }

    private void init(int count) {
        ladder = GameUtil.generateNewLadder(count);
        ladderView.setLadder(ladder);
        ladderView.invalidate();
    }

    private void show() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle(R.string.number_dialog_title);
        dialog.setContentView(R.layout.dialog_number);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        np.setMaxValue(Rule.MAX_LADDER_CNT);
        np.setMinValue(Rule.MIN_LADDER_CNT);
        np.setValue(ladder.getAxisCount());
        np.setWrapSelectorWheel(false);
//        np.setOnValueChangedListener(this);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init(np.getValue());
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onSelectedAxis(int position, LadderAxis axis) {
        Logger.x(axis.getName());
        ladderView.setSelectedAxis(position, axis);
        ladderView.invalidate();
    }

    @Override
    public void onSelectedBody() {
        Logger.x("onBodySeleted");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_example:
                ladder.generateActiveNote();
                ladderView.invalidate();
                break;

            case R.id.action_settings:
                show();
                break;
        }
        return super.onOptionsItemSelected(item

        );
    }

}
