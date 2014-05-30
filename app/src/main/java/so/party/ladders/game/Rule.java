package so.party.ladders.game;

import android.graphics.Color;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class Rule {

    // Default
    public static final int MIN_LADDER_CNT = 2;
    public static final int MAX_LADDER_CNT = 10;
    public static final int MAX_NODE_CNT = 10;

    // UI
    public static final int AXIS_WIDTH = 10;
    public static final float NODE_RADIUS = 13.0f;
    public static final int REPRESENT_COLOR_SIZE = 35;
    public static final float REPRESENT_COLOR_STROKE_SIZE = 5.0f;
    public static final int RESULT_TEXT_SIZE = 40;

    // Setting
    public static final int COMPLEX_LEVEL = 3;

    public static final int[] REPRESENT_COLORS = new int[] {
            Color.RED,
            Color.rgb(71,200,62),
            Color.BLUE,
            Color.rgb(255, 94, 0),
            Color.rgb(61,183,204),
            Color.MAGENTA,
            Color.YELLOW,
            Color.DKGRAY,
            Color.rgb(167, 72, 255),
            Color.rgb(255, 0, 127)
    };

}