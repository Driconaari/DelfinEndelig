import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SubscriptionCalculator {

    private static final double BASE_COST = 240.0;
    private static final double DISCOUNT_PERCENTAGE = 0.25;
    private static final int AGE_DISCOUNT_THRESHOLD = 45;

    public static double calculateSubscriptionCost(String dateOfBirth) {
        double baseCost = BASE_COST;

        if (isOverAgeThreshold(dateOfBirth, AGE_DISCOUNT_THRESHOLD)) {
            baseCost *= (1 - DISCOUNT_PERCENTAGE); // Apply discount for members over the age threshold
        }

        return baseCost;
    }

    private static boolean isOverAgeThreshold(String dateOfBirth, int ageThreshold) {
        int currentYear = LocalDate.now().getYear();
        int birthYear = getYearFromDateOfBirth(dateOfBirth);
        int age = currentYear - birthYear;

        return age > ageThreshold;
    }

    private static int getYearFromDateOfBirth(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter);
        return birthDate.getYear();
    }
}
