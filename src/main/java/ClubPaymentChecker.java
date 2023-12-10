import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClubPaymentChecker {

    public static void checkPayments() {
        // Specify the CSV file path
        String csvFilePath = "club_members.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Read the header line (skip it)
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                // Assuming the columns are in order: name, dateOfBirth, email, ..., balance
                String name = columns[0];
                String remainingPayment = columns[7];
                // Extract other relevant fields as needed

                // Extract balance (assuming it's in the last column)
                double balance = Double.parseDouble(columns[columns.length - 1]);

                // Check if the member is in arrears (owing money)
                if (balance < 0) {
                    System.out.println(name + " Balance is " + remainingPayment + " DKK");
                    // You can perform additional actions, such as notifying the member
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
