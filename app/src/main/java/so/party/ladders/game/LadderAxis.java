package so.party.ladders.game;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class LadderAxis {

    private String name = null;
    private String result = null;
    private List<LadderNode> nodeList = null;
    private Rect representRect = new Rect();

    public LadderAxis(String name, String result) {
        this.name = name;
        this.result = result;

        nodeList = new ArrayList<LadderNode>();
        for(int i = 0 ; i < Rule.MAX_NODE_CNT; i++) {
            nodeList.add(new LadderNode(i));
        }
    }

    public List<LadderNode> getNodeList() {
        return nodeList;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public Rect getRepresentRect() {
        return representRect;
    }

    public void setRepresentRect(int left, int top, int right, int bottom) {
        this.representRect.set(left, top, right, bottom);
    }

    public void setRepresentRect(Rect representRect) {
        this.representRect = representRect;
    }
}
