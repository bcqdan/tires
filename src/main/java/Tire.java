import javax.json.JsonObject;
import java.util.Objects;

public final class Tire {

    private final double lapTime;
    private final double degradationFactor;
    private final double[] performance;

    public Tire(double lapTime, double degradationFactor, int maxLaps) {
        this.lapTime = lapTime;
        this.degradationFactor = degradationFactor;
        performance = new double[maxLaps + 1];
        performance[1] = lapTime;
        for (int i = 2; i <= maxLaps; i++) {
            performance[i] = performance[i - 1] + lapTime * Math.pow(degradationFactor, i - 1);
        }
    }

    public double getLapTime() {
        return lapTime;
    }

    public double totalTime(int laps) {
        return performance[laps];
    }

    public static Tire fromJson(JsonObject jsonObject, int maxLaps) {
        return new Tire(jsonObject.getJsonNumber("lap_time").doubleValue(),
                jsonObject.getJsonNumber("degradation_factor").doubleValue(),
                maxLaps);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tire tire = (Tire) o;
        return Double.compare(tire.lapTime, lapTime) == 0
                && Double.compare(tire.degradationFactor, degradationFactor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lapTime, degradationFactor);
    }

    @Override
    public String toString() {
        return "Tire{" + lapTime + ", " + degradationFactor + '}';
    }
}
