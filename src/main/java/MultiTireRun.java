import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MultiTireRun implements Run {

    private final List<SingleTireRun> singleTireRuns;
    private final double penalty;
    private final double time;

    public MultiTireRun(List<SingleTireRun> singleTireRuns, double penalty) {
        this.singleTireRuns = Collections.unmodifiableList(singleTireRuns);
        this.penalty = penalty;
        this.time =
                singleTireRuns.stream().mapToDouble(Run::getTime).sum()
                        + (penalty * (singleTireRuns.size() - 1));
    }

    public MultiTireRun(SingleTireRun singleTireRun, double penalty) {
        this(List.of(singleTireRun), penalty);
    }

    public MultiTireRun multiTireRunByAppending(List<SingleTireRun> singleTireRuns) {
        return new MultiTireRun(
                Stream.concat(this.singleTireRuns.stream(), singleTireRuns.stream())
                        .collect(Collectors.toUnmodifiableList()),
                penalty);
    }

    @Override
    public double getTime() {
        return time;
    }

    public List<SingleTireRun> getTireRuns() {
        return singleTireRuns;
    }

    @Override
    public String toString() {
        var counts =
                singleTireRuns
                        .stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        StringBuilder b = new StringBuilder();
        counts.forEach((singleTireRun, count) -> {
            b.append(count.toString());
            b.append(" x {");
            b.append(singleTireRun.toString());
            b.append("}");
            b.append(" = ");
            b.append(singleTireRun.getTime() * count + (penalty * (count - 1)));
            b.append("\n");
        });
        b.append(time);
        return b.toString();
    }
}
