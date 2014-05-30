package so.party.ladders.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import so.party.ladders.util.Logger;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class Ladder {

    private int axisCount = Rule.MIN_LADDER_CNT;
    private String[] participantNames = null;
    private List<LadderAxis> axisList = null;

    public Ladder(int axisCount) {
        if(axisCount >= this.axisCount)
            this.axisCount = axisCount;
        participantNames = new String[this.axisCount];
        generateAxes();
    }

    public Ladder(String[] participantNames) {
        this.participantNames = participantNames;
        this.axisCount = participantNames.length;
        generateAxes();
    }

    private void generateAxes() {
        axisList = new ArrayList<LadderAxis>();
        for (int i = 0; i < axisCount; i++) {
            axisList.add(new LadderAxis(participantNames[i] != null ? participantNames[i] : String.valueOf(i), String.valueOf(i+1)));
        }
    }

    public void generateActiveNote() {

        for (int i = 0; i < axisCount; i++) {

            LadderAxis axis = axisList.get(i);

            for (int j = 0; j < Rule.MAX_NODE_CNT; j++) {

                LadderNode node = axis.getNodeList().get(j);
                node.setLinked(false);
                node.setActive(false);

                if(i>0 && axisList.get(i-1).getNodeList().get(j).isActive())
                    node.setLinked(true);
                else {
                    if(i<axisCount-1)
                        node.setActive(new Random().nextInt(10) % Rule.COMPLEX_LEVEL == 0);
                }
            }
        }


    }

    public List<LadderAxis> getAxisList() {
        return axisList;
    }

    public int getAxisCount() {
        return axisCount;
    }
}

