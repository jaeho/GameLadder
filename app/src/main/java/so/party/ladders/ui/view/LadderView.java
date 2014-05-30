package so.party.ladders.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import so.party.ladders.game.Ladder;
import so.party.ladders.game.LadderAxis;
import so.party.ladders.game.LadderNode;
import so.party.ladders.game.Rule;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class LadderView extends View {

    private Ladder ladder;
    private int screenWidth = -1;
    private Paint lineColor;
    private Paint representColor;
    private Paint fontColor;
    private Paint selectedAxisColor;
    private boolean verticalOrientation = true;

    private int lineGap = -1;
    private int nodeGap = -1;

    private OnTouchLadderView listener;
    private LadderAxis selectedAxis;
    private int selectedAxisPosition = 0;

    public LadderView(Context context) {
        super(context);
        init();
    }

    public LadderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LadderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        lineColor = new Paint();
        lineColor.setColor(Color.WHITE);
        lineColor.setAntiAlias(true);

        representColor = new Paint();

        fontColor = new Paint();
        fontColor.setColor(Color.WHITE);
        fontColor.setAntiAlias(true);
        fontColor.setStrokeWidth(10.f);
        fontColor.setTextSize(Rule.RESULT_TEXT_SIZE);

        selectedAxisColor = new Paint();
        selectedAxisColor.setAlpha(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (screenWidth == -1) {
            screenWidth = getWidth();
            verticalOrientation = !(getWidth() > getHeight());
        }

        if (ladder == null)
            return;

        if (lineGap == -1) {
            lineGap = screenWidth / (ladder.getAxisCount() + 1);
            nodeGap = getHeight() / (Rule.MAX_NODE_CNT + (verticalOrientation ? 2 : 3));
        }

        for (int i = 0; i < ladder.getAxisCount(); i++)
            drawAxis(canvas, i);

        if (selectedAxis != null)
            drawSelectedPath(canvas);
    }

    private int getFromX(int position) {
        return lineGap * (position + 1);
    }

    private int getToX(int fromX) {
        return fromX + Rule.AXIS_WIDTH;
    }

    private int getFromY(int position) {
        return nodeGap * (position + 1) - Rule.AXIS_WIDTH / 4;
    }

    private int getToY(int position) {
        return getHeight() - (nodeGap * (verticalOrientation ? 1 : 2)) - (nodeGap * (Rule.MAX_NODE_CNT - position) + Rule.AXIS_WIDTH) + (int) Rule.NODE_RADIUS / 2;
    }

    private void drawAxis(Canvas canvas, int position) {
        LadderAxis axis = ladder.getAxisList().get(position);
        if (axis == selectedAxis)
            selectedAxisPosition = position;
        int rFromX = lineGap * (position + 1) - Rule.REPRESENT_COLOR_SIZE / 2 + Rule.AXIS_WIDTH / 2;
        int rToX = rFromX + Rule.REPRESENT_COLOR_SIZE;
        int rFromY = 0;
        int rToY = Rule.REPRESENT_COLOR_SIZE;

        representColor.setColor(Rule.REPRESENT_COLORS[position]);
        canvas.drawRect(rFromX, rFromY, rToX, rToY, representColor);

        int fromX = getFromX(position);
        int toX = getToX(fromX);
        int fromY = nodeGap / 2 + Rule.REPRESENT_COLOR_SIZE / 2;
        int toY = getHeight() - (nodeGap * (verticalOrientation ? 1 : 2));


        axis.setRepresentRect(rFromX - Rule.REPRESENT_COLOR_SIZE, rFromY, rToX + Rule.REPRESENT_COLOR_SIZE, rToY + Rule.REPRESENT_COLOR_SIZE);
        canvas.drawRect(fromX, fromY, toX, toY, lineColor);
        drawNodeLines(canvas, axis, fromX, toX, false);

        canvas.drawText(axis.getResult(), 0, axis.getResult().length(), fromX - Rule.AXIS_WIDTH / 2, getHeight() - Rule.RESULT_TEXT_SIZE / 2, fontColor);
    }

    private void drawNodeLines(Canvas canvas, LadderAxis axis, int fromX, int toX, boolean isSelectedAxis) {
        for (int j = 0; j < Rule.MAX_NODE_CNT; j++) {
            LadderNode node = axis.getNodeList().get(j);
            if (node.isActive())
                drawNodeLine(canvas, axis, j, fromX, toX, isSelectedAxis, false);
        }
    }

    private void drawNodeLine(Canvas canvas, LadderAxis axis, int position, int fromX, int toX, boolean isSelectedAxis, boolean reverse) {
        int cX = reverse ? fromX - lineGap : (fromX + toX) / 2;
        int cToX = reverse ? fromX : cX + lineGap;
        int cY = nodeGap * (position + 1);

        canvas.drawRect(cX, cY - Rule.AXIS_WIDTH / 4, cToX, cY + Rule.NODE_RADIUS / 4, (isSelectedAxis ? selectedAxisColor : lineColor));
    }

    private void drawSelectedPath(Canvas canvas) {
        int position = selectedAxisPosition;
        LadderAxis axis = selectedAxis;
        selectedAxisColor.setColor(Rule.REPRESENT_COLORS[selectedAxisPosition]);
        int sFromX = getFromX(position);
        int sToX = getToX(sFromX);
        int sFromY = getFromY(0);
        int sToY = 0;

        canvas.drawRect(sFromX, nodeGap / 2 + Rule.REPRESENT_COLOR_SIZE / 2, sToX, getToY(0), selectedAxisColor);

        int nodePosition = 0;
        LadderNode node = axis.getNodeList().get(nodePosition);
        while (true) {
            if (nodePosition == Rule.MAX_NODE_CNT) {
                canvas.drawRect(sFromX, sFromY, sToX, getHeight() - (nodeGap * (verticalOrientation ? 1 : 2)), selectedAxisColor);
                break;
            }

            node = axis.getNodeList().get(nodePosition);

            if (node.isActive() || node.isLinked()) {
                sToY = getToY(nodePosition);
                canvas.drawRect(sFromX, sFromY, sToX, sToY, selectedAxisColor);
                drawNodeLine(canvas, selectedAxis, nodePosition, sFromX, sToX, true, node.isLinked());

                if (node.isActive())
                    position++;
                else if (node.isLinked())
                    position--;

                axis = ladder.getAxisList().get(position);
                sFromX = getFromX(position);
                sToX = getToX(sFromX);
                sFromY = getFromY(nodePosition);
            }
            nodePosition++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (listener != null && ladder != null) {
            for (int i = 0; i < ladder.getAxisCount(); i++) {
                LadderAxis axis = ladder.getAxisList().get(i);
                if (axis.getRepresentRect().contains((int) event.getX(), (int) event.getY())) {
                    listener.onSelectedAxis(i, axis);
                    return true;
                }
            }

            listener.onSelectedBody();
        }

        return super.onTouchEvent(event);
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
        this.screenWidth = -1;
        this.lineGap = -1;
        this.selectedAxis = null;
    }

    public OnTouchLadderView getListener() {
        return listener;
    }

    public void setListener(OnTouchLadderView listener) {
        this.listener = listener;
    }

    public LadderAxis getSelectedAxis() {
        return selectedAxis;
    }

    public void setSelectedAxis(int position, LadderAxis selectedAxis) {
        this.selectedAxis = selectedAxis;
        this.selectedAxisPosition = position;
    }
}
