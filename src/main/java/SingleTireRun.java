import java.util.Objects;

public final class SingleTireRun implements Run {

    private final Tire tire;
    private final int laps;
    private final double time;

    public SingleTireRun(Tire tire, int laps) {
        this.tire = tire;
        this.laps = laps;
        this.time = tire.totalTime(laps);
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleTireRun that = (SingleTireRun) o;
        return laps == that.laps && tire.equals(that.tire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tire, laps);
    }

    @Override
    public String toString() {
        return laps + " laps on " + tire.toString() + " = " + this.time;
    }


}
