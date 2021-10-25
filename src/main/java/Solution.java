public final class Solution {

    public final String solver;
    public final MultiTireRun run;

    public Solution(String solver, MultiTireRun run) {
        this.solver = solver;
        this.run = run;
    }

    @Override
    public String toString() {
        return solver + "\n" + run;
    }
}
