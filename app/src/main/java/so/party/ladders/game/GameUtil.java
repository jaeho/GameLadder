package so.party.ladders.game;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class GameUtil {

    public static Ladder generateNewLadder(int participantCount) {
        return new Ladder(participantCount);
    }

    public static Ladder generateNewLadder(String[] participantNames) {
        return new Ladder(participantNames);
    }

}
