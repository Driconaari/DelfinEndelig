import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoachCsvFileHandler {
    private static final String COACH_CSV_FILE_PATH = "coaches.csv";

    public static List<Coach> readCoachesFromCsv() {
        List<Coach> coaches = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(COACH_CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Coach coach = new Coach(data[0], data[1]);
                coaches.add(coach);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coaches;
    }

    public static void writeCoachesToCsv(List<Coach> coaches) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COACH_CSV_FILE_PATH))) {
            for (Coach coach : coaches) {
                bw.write(coach.toCsvString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
