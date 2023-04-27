import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ParticipantModel {
    List<Participant> participantList = new ArrayList<>();

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public ParticipantModel(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public ParticipantModel() {
    }

    public void readCSV(String path) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        }
        for (int i = 1; i < records.size(); i++) {
            Participant person = new Participant();
            person.setID_2("0");
            Location location = new Location(0.0, 0.0);
            Kitchen kitchen = new Kitchen(0, location);

            //boolean bKitchen = true;
            for (int j = 1; j < records.get(i).size(); j++) {
//                if(bKitchen == false)
//                    break;
                switch (j) {
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
                        Has_Kitchen hasKitchen = Has_Kitchen.valueOf(records.get(i).get(j));
                        person.setHas_kitchen(hasKitchen);
//                        if(hasKitchen == Has_Kitchen.no)
//                            bKitchen = false;
                        break;
                    case 7:
                        person.setKitchen(kitchen);
                        String floor = records.get(i).get(j);
                        if (floor.isEmpty()) {
                            person.getKitchen().setFloor(0);
                            break;
                        } else {
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
            this.participantList.add(person);
            if (person.getKitchen() == null) {
                person.setKitchen(kitchen);
            } else if (person.getKitchen().getLocation() == null) {
                person.getKitchen().setLocation(location);
//            } else if (person.getID_2() == null) {
//                person.setID_2("0");
//            }

//            } else if (Double.valueOf(person.getKitchen().getFloor()).equals(null)) {
//
//            }
            }


        }
    }
}
