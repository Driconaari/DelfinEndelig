import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SubscriptionCalculator {
    private static final double BASE_COST_JUNIOR = 1000.0   ;
    private static final double BASE_COST_SENIOR = 1600.0;
    private static final double BASE_COST_PASSIVE = 500.0;
    private static final double DISCOUNT_PERCENTAGE_60_PLUS = 0.25;
    private static final double JUNIOR_DISCOUNT_PERCENTAGE = 0.25;

    public static double calculateSubscriptionCost(String dateOfBirth, boolean competitiveSwimmer) {
        boolean isPassive = isPassiveMember(competitiveSwimmer);
        boolean isJunior = isJuniorMember(dateOfBirth);
        boolean isSenior60Plus = isSeniorMemberOver60(dateOfBirth);

        if (isPassive) {
            return BASE_COST_PASSIVE * (isSenior60Plus ? (1 - DISCOUNT_PERCENTAGE_60_PLUS) : 1);
        } else if (isJunior) {
            return BASE_COST_JUNIOR * (competitiveSwimmer ? 1 : (1 - JUNIOR_DISCOUNT_PERCENTAGE));
        } else {
            return BASE_COST_SENIOR * (isSenior60Plus ? (1 - DISCOUNT_PERCENTAGE_60_PLUS) : 1);
        }
    }

    private static boolean isPassiveMember(boolean competitiveSwimmer) {
        return !competitiveSwimmer;
    }

    private static boolean isJuniorMember(String dateOfBirth) {
        int ageThreshold = 18;
        int age = calculateAge(dateOfBirth);
        return age < ageThreshold;
    }

    private static boolean isSeniorMemberOver60(String dateOfBirth) {
        int ageThreshold = 60;
        int age = calculateAge(dateOfBirth);
        return age >= ageThreshold;
    }

    private static int calculateAge(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return 0; // Handle the case where dateOfBirth is null or empty
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);
        LocalDate currentDate = LocalDate.now();

        return currentDate.getYear() - birthDate.getYear();
    }
}
