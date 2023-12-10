import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopSwimmers {

    private List<Member> members;

    public TopSwimmers(List<Member> members) {
        this.members = members;
    }

    public TopSwimmers(String csvFilePath) {
        this.members = readMembersFromCSV(csvFilePath);
    }

    private List<Member> readMembersFromCSV(String csvFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            return reader.lines().skip(1)
                    .map(line -> {
                        String[] columns = line.split(",");
                        return new Member(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5], columns[6]);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Return an empty list in case of an error
        }
    }

    public void viewTopSwimmersByDiscipline() {
        System.out.println("Top Swimmers by Discipline:");
        displayTopSwimmersByDiscipline("backstroke");
        displayTopSwimmersByDiscipline("butterfly");
        displayTopSwimmersByDiscipline("breaststroke");
        displayTopSwimmersByDiscipline("freestyle");
    }

    private void displayTopSwimmersByDiscipline(String discipline) {
        System.out.println("Discipline: " + discipline);
        List<Member> topSwimmers = getTopSwimmersByDiscipline(discipline);

        if (topSwimmers.isEmpty()) {
            System.out.println("Not enough competitive swimmers in this discipline.");
        } else {
            displayTopSwimmers(topSwimmers);
        }

        System.out.println();
    }

    private List<Member> getTopSwimmersByDiscipline(String discipline) {
        List<Member> swimmersByDiscipline = members.stream()
                .filter(member -> {
                    String memberDiscipline = member.getDiscipline();
                    return memberDiscipline != null && memberDiscipline.equalsIgnoreCase(discipline);
                })
                .collect(Collectors.toList());

        swimmersByDiscipline.sort(Comparator.comparing(Member::getRecordSwimmingTime));

        return swimmersByDiscipline.subList(0, Math.min(swimmersByDiscipline.size(), 5));
    }

    private void displayTopSwimmers(List<Member> topSwimmers) {
        for (Member member : topSwimmers) {
            if (member.isCompetitiveSwimmer() && member.getRecordSwimmingTime() != null) {
                System.out.println(memberToString(member));
            }
        }
    }

    private String memberToString(Member member) {
        return member.toString();
    }

    public void viewTopSwimmers() {
        System.out.println("Top Swimmers by Discipline:");

        Map<String, List<Member>> swimmersByDiscipline = groupSwimmersByDiscipline();

        for (Map.Entry<String, List<Member>> entry : swimmersByDiscipline.entrySet()) {
            String discipline = entry.getKey();
            List<Member> topSwimmers = entry.getValue();

            System.out.println(discipline + ":");
            displayTopSwimmers(topSwimmers);
        }
    }

    private Map<String, List<Member>> groupSwimmersByDiscipline() {
        Map<String, List<Member>> swimmersByDiscipline = members.stream()
                .filter(Member::isCompetitiveSwimmer)
                .filter(member -> member.getDiscipline() != null && !member.getDiscipline().trim().isEmpty())
                .collect(Collectors.groupingBy(Member::getDiscipline));

        return swimmersByDiscipline;
    }
}
