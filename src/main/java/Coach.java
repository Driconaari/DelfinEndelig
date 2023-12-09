import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Coach {
    private String name;
    private String team;

    public Coach(String name, String team) {
        this.name = name;
        this.team = team;
    }

    public void addCoach(List<Coach> coaches) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Coach Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Coach Team:");
        String team = scanner.nextLine();

        Coach newCoach = new Coach(name, team);
        coaches.add(newCoach);
        CoachCsvFileHandler.writeCoachesToCsv(coaches);

        System.out.println("Coach added successfully.");
    }

    public void viewTeamsAndCoaches(List<Coach> coaches, List<Member> members) {
        System.out.println("Teams and Coaches:");
        for (String team : getDistinctTeams(members)) {
            System.out.println("Team: " + team);
            List<Coach> teamCoaches = getCoachesByTeam(coaches, team);
            for (Coach coach : teamCoaches) {
                System.out.println("  " + coach.getName());
            }
        }
    }

    String getName() {
        return name;
    }

    private String[] getDistinctTeams(List<Member> members) {
        return members.stream()
                .map(Member::getTeam)
                .distinct()
                .toArray(String[]::new);
    }

    private List<Coach> getCoachesByTeam(List<Coach> coaches, String team) {
        return coaches.stream()
                .filter(coach -> coach.getTeam().equals(team))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Coach{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                '}';
    }

    public String getTeam() {
        return team;
    }

    public String toCsvString() {
        return name + "," + team;
    }
}