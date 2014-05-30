package so.party.ladders.ui.view;

import so.party.ladders.game.LadderAxis;

/**
 * Created by jaehochoe on 2014. 3. 16..
 */
public interface OnTouchLadderView {

    public void onSelectedAxis(int position, LadderAxis axis);
    public void onSelectedBody();

}
