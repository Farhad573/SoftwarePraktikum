import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static List<List<String>> readCsv(File file) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
        //records.forEach(System.out::println);
        return records;
    }

    public static void main(String[] args) throws IOException {
        List<List<String>> records = readCsv(new File("src/main/resources/teilnehmerliste.csv"));
        List<Participant> list = new ArrayList<>();
        for (int i = 1; i < records.size();i++){
            Participant person = new Participant();
            Kitchen kitchen = new Kitchen();
            Location location = new Location();
            //boolean bKitchen = true;
            for (int j = 1; j< records.get(i).size();j++){
//                if(bKitchen == false)
//                    break;
                switch (j){
                    case 1:
                        person.setID(records.get(i).get(j));
                        break;
                    case 2:
                        person.setName(records.get(i).get(j));
                        break;
                    case 3:
                        person.setFoodPreference(FoodPreference.valueOf(records.get(i).get(j)));
                        break;
                    case 4:
                        person.setAge(Integer.parseInt(records.get(i).get(j)));
                        break;
                    case 5:
                        person.setSex(Sex.valueOf(records.get(i).get(j)));
                        break;
                    case 6:
                        Has_Kitchen hasKitchen = Has_Kitchen.valueOf(records.get(i).get(j)) ;
                        person.setHas_kitchen(hasKitchen);
//                        if(hasKitchen == Has_Kitchen.no)
//                            bKitchen = false;
                        break;
                    case 7:
                        person.setKitchen(kitchen);
                        String floor = records.get(i).get(j);
                        if(floor =="") {
                            person.getKitchen().setFloor(0);
                            break;
                        }else {
                            person.getKitchen().setFloor(Double.parseDouble(floor));
                        }
                        break;
                    case 8:
                        person.getKitchen().setLocation(location);
                        person.getKitchen().getLocation().setLongitude(Double.parseDouble(records.get(i).get(j)));
                        break;
                    case 9:
                        person.getKitchen().getLocation().setLatitude(Double.parseDouble(records.get(i).get(j)));
                        break;
                    case 10:
                        person.setID_2(records.get(i).get(j));
                        break;
                    case 11:
                        person.setName_2(records.get(i).get(j));
                        break;
                    case 12:
                        person.setAge_2(Double.parseDouble(records.get(i).get(j)));
                        break;
                    case 13:
                        person.setSex_2(Sex.valueOf(records.get(i).get(j)));
                        break;
                }
            }
            list.add(person);
        }

        for (Participant person : list){
            person.toString();
        }

    }
}
