import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;


public class ClubManagementSystemController {
    private final TopSwimmers topSwimmers;
    private List<Member> members;
    private List<Coach> coaches; // Use List instead of Arrays
    private List<String> teams;


    public void start() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
            UserInterface.displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    viewMembers();
                    break;
                case 2:
                    createMember();
                    break;
                case 3:
                    editMember();
                    break;
                case 4:
                    double totalDuesIncome = calculateTotalDuesIncome();
                    System.out.println("Total expected dues income for the club: DKK" + totalDuesIncome);
                    break;
                case 5:
                    markMemberAsCompetitiveSwimmer();
                    break;
                case 6:
                    viewCompetitiveSwimmers();
                    break;
                case  7:
                    calculateAndDisplaySubscriptionCostForAllMembers();
                    break;
                case 8:
                    topSwimmers.viewTopSwimmers();
                    break;
                case 9:
                    viewTeamsAndCoaches(getCoaches(), getMembers());
                    break;
                case 10:
                    System.out.println("Exiting the system.");
                    saveMembersToCsv();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please try again.");
                scanner.nextLine();
            }
       }
    }

    public ClubManagementSystemController() {
        this.members = CsvFileHandler.readMembersFromCsv();
        this.topSwimmers = new TopSwimmers(members);
        this.coaches = CoachCsvFileHandler.readCoachesFromCsv();
        this.teams = getTeams();
    }

    private double calculateSubscriptionCost(Member member) {
        return SubscriptionCalculator.calculateSubscriptionCost(
                member.getDateOfBirth(),
                member.isCompetitiveSwimmer()
        );
    }



    public void viewTeamsAndCoaches(List<Coach> coaches, List<Member> members) {
        System.out.println("Teams and Coaches:");
        for (String team : getDistinctTeams(members)) {
            System.out.println("Team: " + team);

            //Get coaches for the current team
            List<Coach> teamCoaches = getCoachesByTeam(coaches, team);

            //Print coaches for the current team
            for (Coach coach : teamCoaches) {
                System.out.println("  Coach: " + coach.getName());
            }
        }
    }


    //Helper method to get coaches for a specific team
    private List<Coach> getCoachesByTeam(List<Coach> coaches, String team) {
        return this.coaches.stream()
                .filter(coach -> coach.getTeam().equals(team))
                .collect(Collectors.toList());
    }

    void viewCompetitiveSwimmers() {
        System.out.println("Competitive Swimmers:");
        for (Member member : members) {
            if (member.isCompetitiveSwimmer()) {
                System.out.println(memberToString(member));
            }
        }
    }

    private String memberToString(Member member) {
        String baseString = member.toString();

        String additionalInfo = String.format("  Additional Information: [isCompetitiveSwimmer=%b]%n", member.isCompetitiveSwimmer());

        return baseString + additionalInfo;
    }

    private String[] getDistinctTeams(List<Member> members) {
        List<String> teamList = this.members.stream()
                .map(Member::getTeam)
                .distinct()
                .collect(Collectors.toList());

        return teamList.toArray(new String[0]);
    }


    void markMemberAsCompetitiveSwimmer() {
        Scanner scanner = new Scanner(System.in);

        // Display a list of members with indices
        System.out.println("List of Members:");
        for (int i = 0; i < members.size(); i++) {
            System.out.println(i + 1 + ". " + members.get(i).getName());
        }

        // Ask the user to enter the name of the member
        System.out.print("Enter the name of the member to mark as a competitive swimmer: ");
        String selectedName = scanner.nextLine();

        // Find the member with the entered name
        Member selectedMember = findMemberByName(selectedName);

        if (selectedMember != null) {
            // Mark the selected member as a competitive swimmer
            selectedMember.setCompetitiveSwimmer(true);
            System.out.println(selectedMember.getName() + " has been marked as a competitive swimmer.");
        } else {
            System.out.println("Member not found. Please enter a valid name.");
        }
    }

    private Member findMemberByName(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }


    double calculateTotalDuesIncome() {
        double totalDuesIncome = 0.0;

        for (Member member : members) {
            double subscriptionCost = calculateSubscriptionCost(member);
            totalDuesIncome += subscriptionCost;
        }

        return totalDuesIncome;
    }


    void viewMembers() {
        System.out.println("Members:");
        for (Member member : members) {
            System.out.println(member);
        }
    }


    void createMember() {
        Scanner scanner = new Scanner(System.in);

        try {
            String name;
            do {
                System.out.println("Enter Name:");
                name = scanner.nextLine();
                if (!name.matches("[a-zA-Z]+")){
                    System.out.println("Invalid, try again!");
                }
            } while (!name.matches("[a-zA-Z]+"));


            String dateOfBirth;
            do {
                System.out.println("Enter Date of Birth 'yyyy-MM-dd':");
                dateOfBirth = scanner.nextLine();
                if (!dateOfBirth.matches("\\d{8}")) {
                    System.out.println("Invalid, try again!");
                }
            } while (!dateOfBirth.matches("\\d{8}"));


            String email;
            do {
                System.out.println("Enter Email:");
                email = scanner.nextLine();
                if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                    System.out.println("Invalid, try again!");
                }
            } while (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"));


            String phoneNumber;
            do {
                System.out.println("Enter Phone Number:");
                phoneNumber = scanner.nextLine();
                if (!phoneNumber.matches("\\d{8}")) {
                    System.out.println("Invalid, try again!");
                }
            } while (!phoneNumber.matches("\\d{8}"));


            String address;
                System.out.println("Enter Address:");
                address = scanner.nextLine();


            String team;
            do {
                System.out.println("Enter Team (Freestyle, Backstroke, Breaststroke, Butterfly):");
                team = scanner.nextLine().toLowerCase();
                if (!team.matches("freestyle|backstroke|breaststroke|butterfly")) {
                    System.out.println("Invalid input. Please pick one of the styles above!");
                }
            } while (!team.matches("freestyle|backstroke|breaststroke|butterfly"));


            String recordSwimmingTime;
                System.out.println("Enter Record Swimming Time:");
                recordSwimmingTime = scanner.nextLine();


            Member newMember = new Member(name, dateOfBirth, email, phoneNumber, address);
            members.add(newMember);
            CsvFileHandler.writeMembersToCsv(members);

            System.out.println("Member created successfully.");
        } catch (Exception e) {
            System.out.println("Invalid Input. " + e.getMessage() + " Please try again.");
        }
    }

//-------------------------------------------------

    private void addCoach() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Coach Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Coach Team:");
        String team = scanner.nextLine();

        Coach newCoach = new Coach(name, team);
        coaches.add(newCoach);

        System.out.println("Coach added successfully.");
    }


    void editMember() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the member to edit:");
        String memberName = scanner.nextLine();

        Optional<Member> optionalMember = members.stream()
                .filter(member -> member.getName().equalsIgnoreCase(memberName))
                .findFirst();

        if (optionalMember.isPresent()) {
            Member memberToEdit = optionalMember.get();

            System.out.println("Editing Member: " + memberToEdit);

            System.out.println("Enter the field to edit (Name, Date of Birth, Email, Phone Number, Address, Team, Record Swimming Time):");
            String fieldToEdit = scanner.nextLine().trim();

            switch (fieldToEdit.toLowerCase()) {
                case "name":
                    System.out.println("Enter new Name:");
                    memberToEdit.setName(scanner.nextLine());
                    break;
                case "date of birth":
                    System.out.println("Enter new Date of Birth:");
                    memberToEdit.setDateOfBirth(scanner.nextLine());
                    break;
                case "email":
                    System.out.println("Enter new Email:");
                    memberToEdit.setEmail(scanner.nextLine());
                    break;
                case "phone number":
                    System.out.println("Enter new Phone Number:");
                    memberToEdit.setPhoneNumber(scanner.nextLine());
                    break;
                case "address":
                    System.out.println("Enter new Address:");
                    memberToEdit.setAddress(scanner.nextLine());
                    break;
                case "team":
                    System.out.println("Enter new Team:");
                    memberToEdit.setTeam(scanner.nextLine());
                    break;
                case "record swimming time":
                    System.out.println("Enter new Record Swimming Time:");
                    memberToEdit.setRecordSwimmingTime(scanner.nextLine());
                    break;
                default:
                    System.out.println("Invalid field. No changes made.");
                    return;
            }

            CsvFileHandler.writeMembersToCsv(members);
            System.out.println("Member edited successfully.");
        } else {
            System.out.println("No member found with the given name.");
        }
    }
    public void calculateAndDisplaySubscriptionCostForAllMembers() {
        System.out.println("Subscription Costs:");
        for (Member member : members) {
            double subscriptionCost = SubscriptionCalculator.calculateSubscriptionCost(
                    member.getDateOfBirth(),
                    member.isCompetitiveSwimmer()
            );
            System.out.println(member.getName() + ": DKK" + subscriptionCost);
        }
    }

    public void saveMembersToCsv() {
        CsvFileHandler.writeMembersToCsv(members);
    }

    public List<Coach> getCoaches() {
        return this.coaches;
    }

    public List<Member> getMembers() {
        return this.members;
    }

    public List<String> getTeams() {
        return teams;
    }

}