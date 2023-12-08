import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SubscriptionCalculator {

    private static final double BASE_COST = 1600;
    private static final double DISCOUNT_PERCENTAGE = 0.25;
    private static final double JUNIOR_DISCOUNT_PERCENTAGE = 0.10; // Additional discount for junior members
    private static final int AGE_DISCOUNT_THRESHOLD = 45;

    public static double calculateSubscriptionCost(String dateOfBirth, boolean isCompetitiveSwimmer) {
        double baseCost = BASE_COST;

        if (isCompetitiveSwimmer) {
            // Apply a discount for competitive swimmers
            baseCost *= (1 - DISCOUNT_PERCENTAGE);
        }

        if (isOverAgeThreshold(dateOfBirth, AGE_DISCOUNT_THRESHOLD)) {
            baseCost *= (1 - DISCOUNT_PERCENTAGE); // Apply discount for members over the age threshold
        }

        if (isJuniorMember(dateOfBirth)) {
            baseCost *= (1 - JUNIOR_DISCOUNT_PERCENTAGE); // Apply additional discount for junior members
        }

        return baseCost;
    }

    private static boolean isOverAgeThreshold(String dateOfBirth, int ageThreshold) {
        int currentYear = LocalDate.now().getYear();
        int birthYear = getYearFromDateOfBirth(dateOfBirth);
        int age = currentYear - birthYear;

        return age > ageThreshold;
    }

    private static boolean isJuniorMember(String dateOfBirth) {
        int currentYear = LocalDate.now().getYear();
        int birthYear = getYearFromDateOfBirth(dateOfBirth);
        int age = currentYear - birthYear;

        return age < 18;
    }

    private static int getYearFromDateOfBirth(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);
        return birthDate.getYear();
    }
}
