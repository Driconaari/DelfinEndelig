import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ClubManagementSystemController {
    private static Scanner scanner = new Scanner(System.in);
    private static String recordSwimmingTime;
    private static List<Member> members;
    private TopSwimmers topSwimmers;
    private List<Coach> coaches;
    private List<String> teams;


    public ClubManagementSystemController() {
        this.members = CsvFileHandler.readMembersFromCsv();
        this.topSwimmers = new TopSwimmers(members);
        this.coaches = CoachCsvFileHandler.readCoachesFromCsv();
        this.teams = getTeams();
    }

    static void createMember() {
        try {
            String name = getValidatedString("Enter Name:", "[a-zA-Z]+");

            String dateOfBirth;
            do {
                System.out.println("Enter Date of Birth 'yyyy-MM-dd':");
                dateOfBirth = scanner.nextLine();
                if (!isValidDateOfBirthFormat(dateOfBirth)) {
                    System.out.println("Invalid format, try again!");
                }
            } while (!isValidDateOfBirthFormat(dateOfBirth));

            String email = getValidatedString("Enter Email:", "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
            String phoneNumber = getValidatedString("Enter Phone Number:", "\\d{8}");

            String address;
            do {
                System.out.println("Enter Address:");
                address = scanner.nextLine();
                if (address.trim().isEmpty()) {
                    System.out.println("Address cannot be empty. Try again!");
                }
            } while (address.trim().isEmpty());

            String team;
            do {
                System.out.println("Enter Discipline and Team (Freestyle, Backstroke, Breaststroke, Butterfly):");
                team = scanner.nextLine().toLowerCase();
                if (!team.matches("freestyle|backstroke|breaststroke|butterfly")) {
                    System.out.println("Invalid input. Please pick one of the styles above!");
                }
            } while (!team.matches("freestyle|backstroke|breaststroke|butterfly"));

            Member newMember = new Member(name, dateOfBirth, email, phoneNumber, address, team, recordSwimmingTime);
            members.add(newMember);
            CsvFileHandler.writeMembersToCsv(members);

            System.out.println("Member created successfully.");
        } catch (Exception e) {
            System.out.println("Invalid Input. " + e.getMessage() + " Please try again.");
        }
    }

    private static String getValidatedString(String prompt, String regex) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (!input.matches(regex)) {
                System.out.println("Invalid, try again!");
            }
        } while (!input.matches(regex));
        return input;
    }

    private static boolean isValidDateOfBirthFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(date);
            return parsedDate != null;
        } catch (ParseException e) {
            return false;
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                UserInterface.displayMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

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
                    case 7:
                        calculateAndDisplaySubscriptionCostForAllMembers();
                        break;
                    case 8:
                        topSwimmers.viewTopSwimmersByDiscipline();
                        break;
                    case 9:
                        viewTeamsAndCoaches(getCoaches(), getMembers());
                        break;
                    case 10:
                        ClubPaymentChecker.checkPayments();
                        break;
                    case 11:
                        System.out.println("Exiting the system.");
                        saveMembersToCsv();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please try again.");
                scanner.nextLine();
            }
        }
    }

    private double calculateSubscriptionCost(Member member) {
        return SubscriptionCalculator.calculateSubscriptionCost(
                member.getDateOfBirth(),
                member.isCompetitiveSwimmer(),
                member.getRecordSwimmingTime()
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

        //Display a list of members with indices
        System.out.println("List of Members:");
        for (int i = 0; i < members.size(); i++) {
            System.out.println(i + 1 + ". " + members.get(i).getName());
        }

        //Ask the user to enter the name of the member
        System.out.print("Enter the name of the member to mark as a competitive swimmer: ");
        String selectedName = scanner.nextLine();

        //Find the member with the entered name
        Member selectedMember = findMemberByName(selectedName);

        if (selectedMember != null) {
            //Mark the selected member as a competitive swimmer
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
                    member.isCompetitiveSwimmer(),
                    member.getRecordSwimmingTime());
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