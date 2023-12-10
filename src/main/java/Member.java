import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String name;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    private String discipline;
    private String recordSwimmingTime;
    private boolean isCompetitiveSwimmer;

    private boolean checkPayments;
    private int remainingPayments;

    //private String team:

    public Member(String name, String dateOfBirth, String email, String phoneNumber, String address, String team, String recordSwimmingTime, int remainingPayments) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = String.valueOf(phoneNumber);
        this.address = address;
        this.discipline = team;
        this.recordSwimmingTime = recordSwimmingTime;
        this.remainingPayments = remainingPayments;
        if (remainingPayments == 0) {
            checkPayments = true;
        }
    }

    public Member(String name, String dateOfBirth, String email, String phoneNumber, String address, String team, String recordSwimmingTime) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = String.valueOf(phoneNumber);
        this.address = address;
        this.discipline = team;
        this.recordSwimmingTime = recordSwimmingTime;
    }


    //Getters and setters for new fields
    public String getTeam() {
        return discipline;
    }

    //Additional setter methods
    public void setTeam(String team) {
        this.discipline = team;
    }

    public String getRecordSwimmingTime() {
        return recordSwimmingTime;
    }

    public void setRecordSwimmingTime(String recordSwimmingTime) {
        this.recordSwimmingTime = recordSwimmingTime;
    }

    //Convert Member to CSV format
    public String toCsvString() {
        return name + "," + dateOfBirth + "," + email + "," + phoneNumber + "," + address + "," + discipline + "," + recordSwimmingTime;
    }

    @Override
    public String toString() {
        return String.format("Member{%n" +
                "  %s%n" +
                "  %s%n" +
                "  %s%n" +
                "  %s%n" +
                "  %s%n" +
                "  %s%n" +
                "  recordSwimmingTime=%s%n" +
                "  competitiveSwimmer=%b%n" +
                "}", name, dateOfBirth, email, phoneNumber, address, discipline, (recordSwimmingTime != null ? "'" + recordSwimmingTime + "'" : "null"), isCompetitiveSwimmer());
    }

    public boolean isCompetitiveSwimmer() {
        //Check if the member has a non-null and non-empty record swimming time
        return recordSwimmingTime != null && !recordSwimmingTime.trim().isEmpty();
    }

    public void setCompetitiveSwimmer(boolean isCompetitiveSwimmer) {
        this.isCompetitiveSwimmer = isCompetitiveSwimmer;
    }

    public String getName() {
        return name;
    }

    //Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        //Assuming dateOfBirth is in the format 'yyyy-MM-dd'
        LocalDate birthDate = LocalDate.parse(dateOfBirth);
        LocalDate currentDate = LocalDate.now();

        //Calculate the age
        return Period.between(birthDate, currentDate).getYears();
    }

    public String getDiscipline() {
        return discipline;
    }

    public int getremainingPayments() {
        return remainingPayments;
    }
}