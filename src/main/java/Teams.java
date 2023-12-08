import java.util.List;
import java.util.stream.Collectors;

public class Teams {
   /* private List<Coach> coaches;
    private List<String> teams;



    private Teams(List<Coach> coaches, List<String> teams) {
        this.coaches = coaches;
        this.teams = teams;
    }

    public void viewTeamsAndCoaches(List<Coach> coaches, List<Member> members) {
        System.out.println("Teams and Coaches:");
        for (String team : getDistinctTeams(members)) {
            System.out.println("Team: " + team);

            // Get coaches for the current team
            List<Coach> teamCoaches = getCoachesByTeam(coaches, team);

            // Print coaches for the current team
            for (Coach coach : teamCoaches) {
                System.out.println("  Coach: " + coach.getName());
            }
        }
    }

    public static Teams readTeamsFromCsv(List<Member> members) {
        List<Coach> coaches = CoachCsvFileHandler.readCoachesFromCsv();
        List<String> teamList = getDistinctTeams(members);
        return new Teams(coaches, teamList);
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public List<String> getTeams() {
        return teams;
    }

    public List<Coach> getCoachesByTeam(String team) {
        return coaches.stream()
                .filter(coach -> coach.getTeam().equals(team))
                .collect(Collectors.toList());
    }

    private static List<String> getDistinctTeams(List<Member> members) {
        return members.stream()
                .map(Member::getTeam)
                .distinct()
                .collect(Collectors.toList());
    }*/
}