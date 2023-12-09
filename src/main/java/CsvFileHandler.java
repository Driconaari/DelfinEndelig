import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHandler {
    private static final String CSV_FILE_PATH = "club_members.csv";

    public static List<Member> readMembersFromCsv() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length >= 7) {
                    String name = data[0].trim();
                    String dateOfBirth = data[1].trim();
                    String email = data[2].trim();
                    String phoneNumber = data[3].trim();
                    String address = data[4].trim();
                    String team = data[5].trim();
                    String recordSwimmingTime = data[6].trim();

                    Member member = new Member(name, dateOfBirth, email, phoneNumber, address, team);
                    member.setTeam(team);
                    member.setRecordSwimmingTime(recordSwimmingTime);

                    members.add(member);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public static void writeMembersToCsv(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write header
            writer.println("Name,DateOfBirth,Email,PhoneNumber,Address,Team,RecordSwimmingTime");

            // Write member data
            for (Member member : members) {
                writer.println(
                        member.getName() + "," +
                                member.getDateOfBirth() + "," +
                                member.getEmail() + "," +
                                member.getPhoneNumber() + "," +
                                member.getAddress() + "," +
                                member.getTeam() + "," +
                                member.getRecordSwimmingTime()
                );
            }

            System.out.println("Members written to CSV successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write members to CSV.");
        }
    }
}