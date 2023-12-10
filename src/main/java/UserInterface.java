import java.util.List;

public class UserInterface {
    private final ClubManagementSystemController clubSystem;
    private TopSwimmers topSwimmers;
    private List<Member> members;

    public UserInterface() {
        this.clubSystem = new ClubManagementSystemController();
        this.members = clubSystem.getMembers();  // Initialize members here
        this.topSwimmers = new TopSwimmers(members);  // Pass the initialized members list
    }


    public static void displayMainMenu() {
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
        System.out.println("10. Check Payments");
        System.out.println("11. Exit");
    }


}