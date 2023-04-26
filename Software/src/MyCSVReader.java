import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyCSVReader {
    public static void main(String[] args) {
        String csvFile = "C:\\Users\\salek\\OneDrive\\Dokumente\\Software praktikum\\teilnehmerliste.csv";
        String csvSplitBy = ",";

        try {
            Scanner scanner = new Scanner(new File(csvFile));


            String headerRow = scanner.nextLine();
            String[] headers = headerRow.split(csvSplitBy);
            System.out.println(String.join("\t,\t", headers));


            while (scanner.hasNextLine()) {
                String dataRow = scanner.nextLine();
                String[] data = dataRow.split(csvSplitBy);

                for (int i = 0; i < data.length; i++) {
                    if (data[i].isEmpty()) {
                        data[i] = "-";
                    }
                }

                System.out.println(String.join("\t,\t", data));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
