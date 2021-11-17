package MonteCarloAI;

import java.util.Collections;
import java.util.Comparator;

public class UCT {


    public static double UCTValue(int totalVisit, double nodeWinSocre, int nodeVisit){
        if(nodeVisit == 0)
            return Integer.MAX_VALUE;
        return ((double) nodeWinSocre / (double) nodeVisit)
                + 1.41 * Math.sqrt(Math.log(totalVisit)/ (double) nodeVisit);
    }

    public static Node findBestNodeWithUCT(Node node){
        int parentVisit = node.getState().getVisitCount();
        return Collections.max(node.getChildren(), Comparator.comparing(c -> UCTValue(parentVisit,
                c.getState().getWinScore(), c.getState().getVisitCount())));
    }
}
