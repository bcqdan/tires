import java.util.List;

public final class DynamicProgrammingSolver {

    /**
     * bestSingleTire[x]: Best time that can be achieved on a single tire (the one that gives the
     * shortest time) for x laps.
     */
    private SingleTireRun[] bestSingleTire;

    /**
     * bestMultiTire[x]: Best time that can be achieved for x laps with any number of pit stops
     * and tire changes.
     */
    private MultiTireRun[] bestMultiTire;

    private double penalty;

    public Solution solve(Tire tire1, Tire tire2, int totalLaps, double penalty) {
        this.penalty = penalty;
        bestSingleTire = new SingleTireRun[totalLaps + 1];
        for (int numOfLaps = 1; numOfLaps <= totalLaps; numOfLaps++) {
            var tireRun1 = new SingleTireRun(tire1, numOfLaps);
            var tireRun2 = new SingleTireRun(tire2, numOfLaps);
            bestSingleTire[numOfLaps] = tireRun1.getTime() < tireRun2.getTime() ? tireRun1 : tireRun2;
        }
        bestMultiTire = new MultiTireRun[totalLaps + 1];
        bestMultiTire[1] = new MultiTireRun(List.of(bestSingleTire[1]), penalty);
        run(totalLaps);
        return new Solution(getClass().getName(), bestMultiTire[totalLaps]);
    }

    private MultiTireRun run(int totalLaps) {
        if (bestMultiTire[totalLaps] != null) {
            return bestMultiTire[totalLaps];
        }
        MultiTireRun bestRun = new MultiTireRun(bestSingleTire[totalLaps], penalty);
        for (int lapsBeforePitting = 1; lapsBeforePitting <= totalLaps / 2; lapsBeforePitting++) {
            int lapsAfterPitting = totalLaps - lapsBeforePitting;
            var beforePitStop = run(lapsBeforePitting);
            var afterPitStop = run(lapsAfterPitting);

            if (beforePitStop.getTime() + afterPitStop.getTime() + penalty < bestRun.getTime()) {
                bestRun = beforePitStop.multiTireRunByAppending(afterPitStop.getTireRuns());
            }
        }
        bestMultiTire[totalLaps] = bestRun;
        return bestRun;
    }
}
