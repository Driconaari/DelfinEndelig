import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final ClubManagementSystemController clubSystem;
    private TopSwimmers topSwimmers;
    private List<Member> members;

    public UserInterface() {
        this.clubSystem = new ClubManagementSystemController();
        this.members = clubSystem.getMembers();  // Initialize members here
        this.topSwimmers = new TopSwimmers(members);  // Pass the initialized members list
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    clubSystem.viewMembers();
                    break;
                case 2:
                    clubSystem.createMember();
                    break;
                case 3:
                    clubSystem.editMember();
                    break;
                case 4:
                    double totalDuesIncome = clubSystem.calculateTotalDuesIncome();
                    System.out.println("Total expected dues income for the club: DKK" + totalDuesIncome);
                    break;
                case 5:
                    clubSystem.markMemberAsCompetitiveSwimmer();
                    break;
                case 6:
                    clubSystem.viewCompetitiveSwimmers();
                    break;
                case  7:
                clubSystem.calculateAndDisplaySubscriptionCostForAllMembers();
                break;
                case 8:
                    topSwimmers.viewTopSwimmers();
                    break;
                case 9:
                    clubSystem.viewTeamsAndCoaches(clubSystem.getCoaches(), clubSystem.getMembers());
                    break;
                case 10:
                    System.out.println("Exiting the system.");
                    clubSystem.saveMembersToCsv();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. View Members");
        System.out.println("2. Create Member");
        System.out.println("3. Edit Member");
        System.out.println("4. Calculate Dues Income");
        System.out.println("5. Mark Member As Competitive Swimmer");
        System.out.println("6. View Competitive Swimmers");
        System.out.println("7. Calculate and Display Subscription Cost for Each Member");
        System.out.println("8. View Top Swimmers");
        System.out.println("9. View Teams and Coaches");
        System.out.println("10. Exit");
    }


}