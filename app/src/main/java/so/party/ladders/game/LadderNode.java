package so.party.ladders.game;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class LadderNode {

    private int position;
    private boolean isActive = false;
    private boolean isLinked = false;

    public LadderNode(int position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        this.isLinked = false;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean isLinked) {
        this.isLinked = isLinked;
    }
}
