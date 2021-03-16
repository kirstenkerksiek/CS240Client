package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.Database;
import JSON.Location;
import JSON.Locations;
import JSON.Names;
import Model.Event;
import Model.Person;
import Model.User;
import Requests.FillRequest;
import Results.FillResult;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * fill a specified number of generations for a specified person
 */

public class FillService {

    private Names females;
    private Names males;
    private Names surnames;
    private Locations locations;
    private int numPersons;
    private int numEvents;
    private int generations;
    private Database db;
    private String message;
    private String username;

    public FillService(){}

    /**
     * check if username exits
     * valid auth token needed
     * find specified person
     * fill number of generations
     */
    public void FillService() {

//        FileReader fileReader;
//        try {
//            Gson gson = new Gson();
//            fileReader = new FileReader("json/fnames.json");
//            females = gson.fromJson(fileReader, Names.class);
//            fileReader = new FileReader("json/mnames.json");
//            males = gson.fromJson(fileReader, Names.class);
//            fileReader = new FileReader("json/snames.json");
//            surnames = gson.fromJson(fileReader, Names.class);
//            fileReader = new FileReader("json/locations.json");
//            locations = gson.fromJson(fileReader, Locations.class);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        numPersons = 0;
//        numEvents = 0;
//        generations = 0;
//
//        message = "No Errors";
    }


    public FillResult service(FillRequest request) {
        FileReader fileReader;
        try {
            Gson gson = new Gson();
            fileReader = new FileReader("json/fnames.json");
            females = gson.fromJson(fileReader, Names.class);
            fileReader = new FileReader("json/mnames.json");
            males = gson.fromJson(fileReader, Names.class);
            fileReader = new FileReader("json/snames.json");
            surnames = gson.fromJson(fileReader, Names.class);
            fileReader = new FileReader("json/locations.json");
            locations = gson.fromJson(fileReader, Locations.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        numPersons = 0;
        numEvents = 0;
        message = "No Errors";
        db = new Database();

        FillResult result = null;
        try {
            db.openConnection();
            username = request.getUsername();
            generations = request.getGenerations();
        } catch (DataAccessException e) {
            message = "Error: Internal Server Error : open connection in Serve @ FillService";
        }
        if(message.equals("No Errors")) {
            if (!isValid()) {
                message = "Error: invalid username or generations";
                result = new FillResult(message, numPersons, numEvents);
                return result;
            }
            int gen = 1;
            try {
                User user = db.userDao.find(username);
                Person person = db.personDao.find(user.getPersonID());
                db.personDao.deletePersonsByUsername(username);
                db.eventDao.deleteEventsByUsername(username);
                //add user and person
                db.personDao.insert(person);
                numPersons++;
                generateEventsByID(person.getPersonID());
                db.closeConnection(true);
                db.openConnection();

                fill(gen, person);
            }
            catch (DataAccessException e) {
                message = "Error: Internal server error : delete events @ fill";
            }
        }
        try {
            db.closeConnection(true);
        } catch (DataAccessException e) {
            message = "Error: Internal Server Error : close connection @ FillService";
        }
        result = new FillResult(message, numPersons, numEvents);
        return result;
    }

    private void generateEventsByID(String personID) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Random gen = new Random();
        int randomNum = gen.nextInt(80);
        year = year - randomNum; //currYear - age

        generateEvent(personID, "Birth", year);
    }

    public void fill(int givenGen, Person person) {
        if (givenGen <= generations) {
            Person[] parents = generateParents(person);
            Person father = parents[0];
            Person mother = parents[1];
            String personID = person.getPersonID();
            String fatherID = father.getPersonID();
            String motherID = mother.getPersonID();

            try {
                db.closeConnection(true);
                db.openConnection();
            } catch (DataAccessException e) {
                message = "Error: Internal Server Error w/ Opening & Closing";
            }


            //father
            try {
                Random indexGenerator = new Random();
                int randomIndex = indexGenerator.nextInt(22);
                randomIndex = randomIndex + 18;

                int personBirthYear = 0;
                Event[] events = db.eventDao.getEventsByPerson(personID); //TODO
                for (int i = 0; i < events.length; i++) {
                    if (events[i].getEventType().equals("Birth")) {
                        personBirthYear = events[i].getYear();
                    }
                }

                int parentBirthYear = personBirthYear - randomIndex;

                generateEvent(fatherID,"Birth",parentBirthYear);
                generateEvent(fatherID,"Marriage",parentBirthYear);
                generateEvent(fatherID, "Death", parentBirthYear);

            }
            catch(DataAccessException e) {
                message = "Error: Internal server error @ Generate Father Events @ FillService";
            }


            //mother
            try {
                Random indexGenerator = new Random();
                int randomIndex = indexGenerator.nextInt(22);
                randomIndex = randomIndex + 18;

                int personBirthYear = 0;
                Event[] events = db.eventDao.getEventsByPerson(personID); //TODO
                for (int i = 0; i < events.length; i++) {
                    if (events[i].getEventType().equals("Birth")) {
                        personBirthYear = events[i].getYear();
                    }
                }

                int parentBirthYear = personBirthYear - randomIndex;

                generateEvent(motherID,"Birth",parentBirthYear);

                generateEvent(motherID,"Marriage",parentBirthYear);
                generateEvent(motherID, "Death", parentBirthYear);

                //is the person dead
//                User user = db.userDao.find(username);
//
//                if(personID.equals(user.getPersonID())) {
//                    randomIndex = indexGenerator.nextInt(10);
//                    if (randomIndex < 5) {
//                        generateEvent(motherID, "Death", parentBirthYear);
//                    }
//                }
//                else {
//                    generateEvent(motherID, "Death", parentBirthYear);
//                }
            }
            catch(DataAccessException e) {
                message = "Error: Internal server error @ Generate Mother Events @ FillService";
            }

            fill(givenGen + 1, father);
            fill(givenGen + 1, mother);
        }
    }

    private Person[] generateParents(Person person) {
        Random gen = new Random();

        String descendant = username;

        UUID uuid = UUID.randomUUID();
        String fatherID = uuid.toString();
        int randomNum = gen.nextInt(males.data.length);
        String fatherFirst = males.data[randomNum];
        String fatherLast = person.getlName();

        uuid = UUID.randomUUID();
        String motherID = uuid.toString();
        randomNum = gen.nextInt(females.data.length);
        String motherFirst = females.data[randomNum];
        randomNum = gen.nextInt(surnames.data.length);
        String motherLast = surnames.data[randomNum];

        String fatherSpouseID = motherID;
        String motherSpouseID = fatherID;

        Person father = new Person(fatherID, username, fatherFirst, fatherLast, "m", null, null, fatherSpouseID);
        Person mother = new Person(motherID, username, motherFirst, motherLast, "f", null, null, motherSpouseID);
        Person[] parents = new Person[2];
        parents[0] = father;
        parents[1] = mother;

        //put into database
        try {
            db.personDao.update("Father", fatherID, person.getPersonID());
            db.personDao.update("Mother", motherID, person.getPersonID());
            db.personDao.insert(father);
            db.personDao.insert(mother);
            numPersons += 2;
        } catch (DataAccessException e) {
            message = "Error: Internal server error : update family members @ FillService";
        }
        return parents;
    }

    private boolean isValid() {
        User user;
        try {
            user = db.userDao.find(username);
            if (user == null) {
                message = "Error: Invalid username or generations parameter";
                return false;
            }
        } catch (DataAccessException e) {
            message = "Error: Internal server error in isValid @ FillService";
        }

        if (generations < 0) {
            message = "Error: Invalid username or generations parameter";
            return false;
        }
        return true;
    }

    private void generateEvent(String personID, String eventType, int birthYear) {
        int birth = birthYear;
        Event event = null;
        int year = 0;

        UUID uuid = UUID.randomUUID();
        String eventID = uuid.toString();
        String user = username;

        Random gen = new Random();
        int randomYear = gen.nextInt(locations.data.length);
        Location location = locations.data[randomYear];
        String country = location.country;
        String city = location.city;
        float longitude = location.longitude;
        float latitude = location.latitude;

        try {
            switch(eventType) {
                case "Birth":
                    year = 0;
                    break;
                case "Marriage":
                    Person person = db.personDao.find(personID);
                    String spouseID = person.getSpouseID();
                    randomYear = gen.nextInt(40);
                    year = randomYear + 18;
                    if (spouseID != null) {
                        Event[] events = db.eventDao.getEventsByPerson(spouseID);
                        if (events == null) {
                            year = 13 + gen.nextInt(40);
                        }
                        else {
                            for (int i = 0; i < events.length; i++) {
                                if (events[i].getEventType().equals("Marriage")) {
                                    longitude = events[i].getLongitude();
                                    latitude = events[i].getLatitude();
                                    country = events[i].getCountry();
                                    city = events[i].getCity();
                                    year = events[i].getYear();
                                    birth = 0;
                                }
                            }
                        }
                    }
                    break;
                case "Death":
                    randomYear = gen.nextInt(40);
                    year = randomYear + 60;
                    break;
                default:
                    System.err.println("Error: Invalid Event Type");
            }
            int eventYear = birth + year;
            if (eventType.equals("Marriage")) {
                if (eventYear - birthYear < 20) {
                     eventYear += 10;
                }
            }
            event = new Event(eventID, user, personID, latitude, longitude, country, city, eventType, birth + year);
            //add event to database
            db.eventDao.insert(event);
            numEvents++;
        } catch (DataAccessException e) {
            message = "Error: Internal Server Error @ generate events @ FillService";
        }
    }
}