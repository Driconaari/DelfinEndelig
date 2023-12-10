import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriptionCalculatorTest {

    @Test
    public void testCalculateSubscriptionCostForPassiveSeniorMember() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("1960-01-01", false, null);
        assertEquals(375.0, cost, 0.01);
    }

    @Test
    public void testCalculateSubscriptionCostForPassiveJuniorMember() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("2010-01-01", false, null);
        assertEquals(500.0, cost, 0.01);
    }

    @Test
    public void testCalculateSubscriptionCostForCompetitiveJuniorMember() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("2010-01-01", true, "1:52.32");
        assertEquals(1000.0, cost, 0.01);
    }

    @Test
    public void testCalculateSubscriptionCostForNonCompetitiveJuniorMember() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("2010-01-01", false, "invalid_time");
        assertEquals(500, cost, 0.01);
    }

    @Test
    public void testCalculateSubscriptionCostForSeniorMemberOver60() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("1940-01-01", false, null);
        assertEquals(375, cost, 0.01);
    }

    @Test
    public void testCalculateSubscriptionCostForCompetitiveSeniorMemberOver60() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("1940-01-01", true, "1:52.32");
        assertEquals(1200, cost, 0.01);
    }

    @Test
    public void testCalculateSubscriptionCostForCompetitiveSeniorMemberUnder60() {
        double cost = SubscriptionCalculator.calculateSubscriptionCost("1994-01-01", true, "1:52.32");
        assertEquals(1600.0, cost, 0.01);
    }


}
