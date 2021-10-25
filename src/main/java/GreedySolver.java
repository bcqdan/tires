import java.util.ArrayList;
import java.util.List;

public final class GreedySolver {

    public Solution solve(Tire tire1, Tire tire2, int laps, double penalty) {
        List<SingleTireRun> runs = new ArrayList<>();
        Tire bestTire = tire1.getLapTime() < tire2.getLapTime() ? tire1 : tire2;
        int lapsOnCurrentTire = 1;
        for (int lap = 2; lap <= laps; lap++) {
            double keepCurrentTireTime =
                    bestTire.totalTime(lapsOnCurrentTire + 1) - bestTire.totalTime(lapsOnCurrentTire);
            double changeTireTime = penalty + bestTire.totalTime(1);
            if (keepCurrentTireTime > changeTireTime) {
                runs.add(new SingleTireRun(bestTire, lapsOnCurrentTire));
                lapsOnCurrentTire = 1;
            } else {
                lapsOnCurrentTire++;
            }
        }
        SingleTireRun run = new SingleTireRun(bestTire, lapsOnCurrentTire);
        runs.add(run);
        return new Solution(getClass().getName(), new MultiTireRun(runs, penalty));
    }
}
