import java.io.*;


public class Main {

    public static void main(String[] args) {

        String csvFile = "C:\\Users\\tonyk\\Desktop\\SWT-Praktikum\\SP23_Gruppe4_sajjadi_salekim_emamideh_Kassisa\\spinfood\\teilnehmerliste.csv";
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                // Create a new Participant object from the data
                String id = data[0];
                String name = data[1];
                FoodPreference foodPreference= FoodPreference.valueOf(data[2]);
                int age = Integer.parseInt(data[3]);
                Sex sex = Sex.valueOf(data[4]);
                HasKitchen hasKitchen = HasKitchen.valueOf(data[5]);
                int kitchenStory = Integer.parseInt(data[6]);
                float kitchenLongitude = Float.parseFloat(data[7]);
                float kitchenLatitude = Float.parseFloat(data[8]);
                String id2 = data[9];
                String name2 = data[10];
                int age2 = Integer.parseInt(data[11]);
                Sex sex2 = Sex.valueOf(data[12]);

                Participant participant = new Participant(id, name, foodPreference, age, sex,
                        hasKitchen, kitchenStory, kitchenLatitude, kitchenLongitude,id2, name2, age2, sex2);

                // Do something with the participant object
                System.out.println(participant.getName() + " is " + participant.getAge() + " years old.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
