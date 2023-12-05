import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopSwimmers {

    private List<Member> members;

    public TopSwimmers(List<Member> members) {
        this.members = members;
    }

    public void viewTopSwimmers() {
        System.out.println("Top 5 Swimmers:");

        // Display top 5 swimmers for junior category
        System.out.println("Junior Category:");
        displayTopSwimmers(getTopSwimmers("junior"));

        // Display top 5 swimmers for senior category
        System.out.println("Senior Category:");
        displayTopSwimmers(getTopSwimmers("senior"));
    }

    private List<Member> getTopSwimmers(String category) {
        // Implement logic to retrieve the top 5 swimmers for the specified category
        // You can sort the competitive swimmers based on their performance and return the top 5
        // For simplicity, I'll assume that the members list contains only competitive swimmers
        List<Member> competitiveSwimmers = new ArrayList<>(members);

        // Sort competitiveSwimmers based on performance (you may need to modify this based on your data model)
        competitiveSwimmers.sort(Comparator.comparing(Member::getRecordSwimmingTime));

        // Filter swimmers by category
        return competitiveSwimmers.stream()
                .filter(member -> (category.equals("junior") && member.getAge() < 18) ||
                        (category.equals("senior") && member.getAge() >= 18))
                .limit(5)
                .collect(Collectors.toList());
    }

    private void displayTopSwimmers(List<Member> topSwimmers) {
        for (Member member : topSwimmers) {
            System.out.println(memberToString(member));
        }
        System.out.println();
    }

    private String memberToString(Member member) {
        // Implement logic to convert a member to a string representation
        // You can use member.toString() or customize it based on your needs
        return member.toString();
    }
}
