import java.util.List;

public final class DynamicProgrammingSolver {

    private SingleTireRun[] bestNoPitStops;
    private MultiTireRun[] bestPitStops;
    private double penalty;

    public Solution solve(Tire tire1, Tire tire2, int laps, double penalty) {
        this.penalty = penalty;
        bestNoPitStops = new SingleTireRun[laps + 1];
        for (int numOfLaps = 1; numOfLaps <= laps; numOfLaps++) {
            var tireRun1 = new SingleTireRun(tire1, numOfLaps);
            var tireRun2 = new SingleTireRun(tire2, numOfLaps);
            bestNoPitStops[numOfLaps] = tireRun1.getTime() < tireRun2.getTime() ? tireRun1 : tireRun2;
        }
        bestPitStops = new MultiTireRun[laps + 1];
        bestPitStops[1] = new MultiTireRun(List.of(bestNoPitStops[1]), penalty);
        run(laps);
        return new Solution(getClass().getName(), bestPitStops[laps]);
    }

    private MultiTireRun run(int totalLaps) {
        if (bestPitStops[totalLaps] != null) {
            return bestPitStops[totalLaps];
        }
        MultiTireRun bestRun = new MultiTireRun(bestNoPitStops[totalLaps], penalty);
        for (int lapsBeforePitting = 1; lapsBeforePitting <= totalLaps / 2; lapsBeforePitting++) {
            int lapsAfterPitting = totalLaps - lapsBeforePitting;
            var beforePitStop = run(lapsBeforePitting);
            var afterPitStop = run(lapsAfterPitting);

            if (beforePitStop.getTime() + afterPitStop.getTime() + penalty < bestRun.getTime()) {
                bestRun = beforePitStop.multiTireRunAppending(afterPitStop.getTireRuns());
            }
        }
        bestPitStops[totalLaps] = bestRun;
        return bestRun;
    }
}
