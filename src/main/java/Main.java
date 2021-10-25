import javax.json.Json;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class Main {

    public static void main(String[] args) throws FileNotFoundException {
        try (var reader = Json.createReader(new FileInputStream("tires.json"))) {
            var contents = reader.readObject();
            var laps = contents.getInt("laps");
            var tire1 = Tire.fromJson(contents.getJsonObject("tire1"), laps);
            var tire2 = Tire.fromJson(contents.getJsonObject("tire2"), laps);
            var penalty = contents.getJsonNumber("pit_stop_penalty").doubleValue();

            var dynamicProgrammingSolution = new DynamicProgrammingSolver()
                    .solve(tire1, tire2, laps, penalty);
            var greedySolution = new GreedySolver().solve(tire1, tire2, laps, penalty);
            System.out.println();
            System.out.println(contents);
            System.out.println();
            System.out.println(dynamicProgrammingSolution);
            System.out.println();
            System.out.println(greedySolution);
        }
    }
}