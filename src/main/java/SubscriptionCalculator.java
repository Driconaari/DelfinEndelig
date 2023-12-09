import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SubscriptionCalculator {
    private static final double BASE_COST_JUNIOR = 1000.0;
    private static final double BASE_COST_SENIOR = 1600.0;
    private static final double BASE_COST_PASSIVE = 500.0;
    private static final double DISCOUNT_PERCENTAGE_60_PLUS = 0.25;

    // Additional constant for passive discount
    private static final double PASSIVE_DISCOUNT_PERCENTAGE = 0.0;

    public static double calculateSubscriptionCost(String dateOfBirth, boolean competitiveSwimmer, String recordSwimmingTime) {
        boolean isPassive = isPassiveMember(competitiveSwimmer, recordSwimmingTime);
        boolean isJunior = isJuniorMember(dateOfBirth);
        boolean isSenior60Plus = isSeniorMemberOver60(dateOfBirth);

        if (isPassive) {
            // Apply discount only for passive members
            return BASE_COST_PASSIVE * (isSenior60Plus ? (1 - DISCOUNT_PERCENTAGE_60_PLUS) : (1 - PASSIVE_DISCOUNT_PERCENTAGE));
        } else if (isJunior) {
            return BASE_COST_JUNIOR;
        } else if (isSenior60Plus) {
            return BASE_COST_SENIOR * (1 - DISCOUNT_PERCENTAGE_60_PLUS);
        } else {
            return BASE_COST_SENIOR;  // For seniors under 60 without special conditions
        }
    }

    private static boolean isJuniorMember(String dateOfBirth) {
        int ageThreshold = 18;
        int age = calculateAge(dateOfBirth);
        return age < ageThreshold;
    }


    private static boolean isPassiveMember(boolean competitiveSwimmer, String recordSwimmingTime) {
        if (!competitiveSwimmer) {
            return true; // Non-competitive swimmers are always passive
        }

        // Check if the recordSwimmingTime is in the specific format like "1:52.32"
        return recordSwimmingTime == null || !recordSwimmingTime.matches("\\d+:\\d{2}\\.\\d{2}");
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
