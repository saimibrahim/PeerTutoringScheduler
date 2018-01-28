import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.Date;
import java.text.DateFormat;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

/**
 * A user interactive console-based interface.
 */
public class ConsoleInterface {
    // class fields
    private static final String[] ADD_MENU = new String[]{"", "Press 1 to add a tutor", "Press 2 to add a tutee",
            "Press 3 to add from a file", "Press 4 to remove a tutee or tutor", "", "Press 0 to go back", ""};
    private static final String[] DAYS_OF_SCHOOL_WEEK = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
            "FRIDAY"};
    private static final String DONE = "DONE";
    private static final String[] ERROR_MESSAGE = new String[]{"", "Error: Your entry was invalid."};
    private static final String[] EXIT_MENU = new String[]{"", "Press 1 to save this scheduler and exit", "Press 2 " +
            "to terminate the program", "", "Press 0 to go back", ""};
    private static final String OPENING_MESSAGE[] = new String[]{"", "Welcome to the Peer Tutoring Schedule2" +
            "r", "",
            "Press 1 to load a saved file", "Press 2 to start a new scheduler", "", "Press 0 to exit", ""};
    private static final String[] MAIN_MENU = new String[]{"", "Main Menu", "", "Press 1 to view sessions for today",
            "Press 2 to view sessions for any day of the week and the tutors and tutees available",
            "Press 3 to manage students", "Press 4 to create a schedule", "Press 5 to exit", ""};
    private static final String NO = "N";
    private static final String SAVE_FILE_NAME = "peerTutoring.ser";
    private static final String[] SPECIFIC_SESSION_MENU = new String[]{"", "Press 1 to see Monday's sessions",
            "Press 2 to see Tuesday's sessions", "Press 3 to see Wednesday's sessions",
            "Press 4 to see Thursday's sessions", "Press 5 to see Friday's sessions",
            "Press 6 to see the pool of available tutors", "Press 7 to see the tutees with no tutors", "",
            "Press 0 to go back", ""};
    private static final String YES = "Y";

    public static void main(String[] argument) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Scheduler database = new Scheduler();

        // Establish a connection to the console.
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String lineOfText;
        int input = 0;
        boolean validInput = false;

        System.out.println(dateFormat.format(new Date()));
        stringArrayFormatter(OPENING_MESSAGE);

        do {
            System.out.print("Please enter your selection: ");
            lineOfText = console.readLine();
            try {
                input = Integer.parseInt(lineOfText);
                if (input >= 0 && input < 3) {
                    switch (input) {
                        case 0:
                            System.exit(0);
                        case 1:
                            System.out.println("\f");
                            do {
                                System.out.println("");
                                System.out.println("Enter the path for the saved file: ");
                                lineOfText = console.readLine();
                                database = readFromFile(lineOfText);
                                if (database.equals(new Scheduler())) {
                                    stringArrayFormatter(ERROR_MESSAGE);
                                }
                            }
                            while (database.equals(new Scheduler()));
                            System.out.println("\f");
                            System.out.println("Save file successfully loaded.");
                            break;
                        case 2:
                            System.out.println("\f");
                            System.out.println("New scheduler successfully created.");
                            break;
                    }
                    validInput = true;
                } else {
                    stringArrayFormatter(ERROR_MESSAGE);
                }
            } catch (NumberFormatException error) {
                stringArrayFormatter(ERROR_MESSAGE);
            }
        }
        while (!validInput);

        do {
            System.out.println("********************************************************************************" +
                    "**************");
            System.out.println("");
            System.out.println(dateFormat.format(new Date()));
            System.out.println("");
            if (!database.getDate().equals("")) {
                System.out.println("Last save: " + database.getDate());
            }
            stringArrayFormatter(MAIN_MENU);
            System.out.print("Please enter your selection: ");
            lineOfText = console.readLine();
            try {
                input = Integer.parseInt(lineOfText);
                if (input > 0 && input < 6) {
                    boolean isValid = false;
                    switch (input) {
                        case 1:
                            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
                            String today = simpleDateformat.format(new Date()).toString().toUpperCase();
                            if (today.equals(DAYS_OF_SCHOOL_WEEK[0])) {
                                System.out.println("\f");
                                if (database.getMondaySession().size() != 0) {
                                    stringArrayFormatter(tutorListFormatter(database.getMondaySession()));
                                } else {
                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[0]);
                                }
                            } else if (today.equals(DAYS_OF_SCHOOL_WEEK[1])) {
                                System.out.println("\f");
                                if (database.getTuesdaySession().size() != 0) {
                                    stringArrayFormatter(tutorListFormatter(database.getTuesdaySession()));
                                } else {
                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[1]);
                                }
                            } else if (today.equals(DAYS_OF_SCHOOL_WEEK[2])) {
                                System.out.println("\f");
                                if (database.getWednesdaySession().size() != 0) {
                                    stringArrayFormatter(tutorListFormatter(database.getWednesdaySession()));
                                } else {
                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[2]);
                                }
                            } else if (today.equals(DAYS_OF_SCHOOL_WEEK[3])) {
                                System.out.println("\f");
                                if (database.getThursdaySession().size() != 0) {
                                    stringArrayFormatter(tutorListFormatter(database.getThursdaySession()));
                                } else {
                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[3]);
                                }
                            } else if (today.equals(DAYS_OF_SCHOOL_WEEK[4])) {
                                System.out.println("\f");
                                if (database.getFridaySession().size() != 0) {
                                    stringArrayFormatter(tutorListFormatter(database.getFridaySession()));
                                } else {
                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[0]);
                                }
                            } else {
                                System.out.println("\f");
                                System.out.println("No sessions today! It's " + today + ".");
                            }
                            break;

                        case 2:
                            isValid = false;
                            System.out.println("\f");
                            do {
                                stringArrayFormatter(SPECIFIC_SESSION_MENU);
                                System.out.print("Please enter your selection: ");
                                lineOfText = console.readLine();
                                try {
                                    input = Integer.parseInt(lineOfText);
                                    if (input >= 0 && input < 8) {
                                        switch (input) {
                                            case 0:
                                                System.out.println("\f");
                                                isValid = true;
                                                break;

                                            case 1:
                                                System.out.println("\f");
                                                if (database.getMondaySession().size() != 0) {
                                                    stringArrayFormatter(tutorListFormatter
                                                            (database.getMondaySession()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[0]);
                                                    isValid = true;
                                                }
                                                break;

                                            case 2:
                                                System.out.println("\f");
                                                if (database.getTuesdaySession().size() != 0) {
                                                    stringArrayFormatter(tutorListFormatter
                                                            (database.getTuesdaySession()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[1]);
                                                    isValid = true;
                                                }
                                                break;

                                            case 3:
                                                System.out.println("\f");
                                                if (database.getWednesdaySession().size() != 0) {
                                                    stringArrayFormatter(tutorListFormatter
                                                            (database.getWednesdaySession()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[2]);
                                                    isValid = true;
                                                }
                                                break;

                                            case 4:
                                                System.out.println("\f");
                                                if (database.getThursdaySession().size() != 0) {
                                                    stringArrayFormatter(tutorListFormatter
                                                            (database.getThursdaySession()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[3]);
                                                    isValid = true;
                                                }
                                                break;

                                            case 5:
                                                System.out.println("\f");
                                                if (database.getFridaySession().size() != 0) {
                                                    stringArrayFormatter(tutorListFormatter
                                                            (database.getFridaySession()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No sessions on " + DAYS_OF_SCHOOL_WEEK[4]);
                                                    isValid = true;
                                                }
                                                break;

                                            case 6:
                                                System.out.println("\f");
                                                if (database.getTutorPool().size() != 0) {
                                                    stringArrayFormatter(tutorListFormatter(database.getTutorPool()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No tutors in the pool.");
                                                    isValid = true;
                                                }
                                                break;

                                            case 7:
                                                System.out.println("\f");
                                                if (database.getTuteeQueue().size() != 0) {
                                                    stringArrayFormatter(tuteeListFormatter(database.getTuteeQueue()));
                                                    isValid = true;
                                                } else {
                                                    System.out.println("");
                                                    System.out.println("No tutees in the queue.");
                                                    isValid = true;
                                                }
                                                break;
                                        }
                                    } else {
                                        System.out.println("\f");
                                        stringArrayFormatter(ERROR_MESSAGE);
                                    }
                                } catch (NumberFormatException error) {
                                    System.out.println("\f");
                                    stringArrayFormatter(ERROR_MESSAGE);
                                }
                            }
                            while (!isValid);
                            break;

                        case 3:
                            isValid = false;
                            System.out.println("\f");
                            do {
                                stringArrayFormatter(ADD_MENU);
                                System.out.print("Please enter your selection: ");
                                lineOfText = console.readLine();
                                try {
                                    int localInput = 0;
                                    input = Integer.parseInt(lineOfText);
                                    if (input >= 0 && input < 5) {
                                        switch (input) {
                                            case 0:
                                                System.out.println("\f");
                                                isValid = true;
                                                break;

                                            case 1:
                                                isValid = false;
                                                Tutor tutor = new Tutor();
                                                System.out.print("Please enter the tutor's family name: ");
                                                tutor.setFamilyName(console.readLine());
                                                System.out.print("Please enter the tutor's given name: ");
                                                tutor.setGivenName(console.readLine());
                                                do {
                                                    System.out.print("Please enter the tutor's grade " +
                                                            "(between 9 and 12): ");
                                                    lineOfText = console.readLine();
                                                    try {
                                                        localInput = Integer.parseInt(lineOfText);
                                                        if (localInput > 8 && localInput < 13) {
                                                            tutor.setGrade(localInput);
                                                            isValid = true;
                                                        } else {
                                                            System.out.println("\f");
                                                            System.out.println("Invalid grade entered.");
                                                        }
                                                    } catch (NumberFormatException error) {
                                                        System.out.println("\f");
                                                        stringArrayFormatter(ERROR_MESSAGE);
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutor available on Monday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutor.setSpecificAvailability(true, 2);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutor available on Tuesday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutor.setSpecificAvailability(true, 3);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutor available on Wednesday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutor.setSpecificAvailability(true, 4);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutor available on Thursday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutor.setSpecificAvailability(true, 5);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutor available on Friday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutor.setSpecificAvailability(true, 6);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                while (!lineOfText.equals(DONE)) {
                                                    System.out.print("Please enter a subject the tutor can teach" +
                                                            " (enter \"done\" when finished): ");
                                                    lineOfText = console.readLine().toUpperCase();
                                                    if (!lineOfText.equals(DONE)) {
                                                        tutor.addSubject(lineOfText);
                                                    }
                                                }
                                                database.addTutorToPool(tutor);
                                                System.out.println("\f");
                                                System.out.println("Tutor successfully added!");
                                                break;

                                            case 2:
                                                isValid = false;
                                                Tutee tutee = new Tutee();
                                                System.out.print("Please enter the tutee's family name: ");
                                                tutee.setFamilyName(console.readLine());
                                                System.out.print("Please enter the tutee's given name: ");
                                                tutee.setGivenName(console.readLine());
                                                do {
                                                    System.out.print("Please enter the tutee's grade " +
                                                            "(between 9 and 12): ");
                                                    lineOfText = console.readLine();
                                                    try {
                                                        localInput = Integer.parseInt(lineOfText);
                                                        if (localInput > 8 && localInput < 13) {
                                                            tutee.setGrade(localInput);
                                                            isValid = true;
                                                        } else {
                                                            System.out.println("\f");
                                                            System.out.println("Invalid grade entered.");
                                                        }
                                                    } catch (NumberFormatException error) {
                                                        System.out.println("\f");
                                                        stringArrayFormatter(ERROR_MESSAGE);
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutee available on Monday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutee.setSpecificAvailability(true, 2);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutee available on Tuesday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutee.setSpecificAvailability(true, 3);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutee available on Wednesday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutee.setSpecificAvailability(true, 4);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutee available on Thursday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutee.setSpecificAvailability(true, 5);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                isValid = false;
                                                do {
                                                    System.out.print("Is the tutee available on Friday? (Y/N): ");
                                                    lineOfText = console.readLine();
                                                    if (lineOfText.toUpperCase().equals(YES)) {
                                                        tutee.setSpecificAvailability(true, 6);
                                                        isValid = true;
                                                    } else if (lineOfText.toUpperCase().equals(NO)) {
                                                        isValid = true;
                                                    } else {
                                                        System.out.println("\f");
                                                        System.out.println("Please enter Y for yes or N for no.");
                                                    }
                                                }
                                                while (!isValid);
                                                System.out.print("Please enter the subject the tutee is seeking help for: ");
                                                tutee.setSubject(console.readLine());
                                                database.queueTutee(tutee);
                                                System.out.println("\f");
                                                System.out.println("Tutee successfully added!");
                                                break;

                                            case 3:
                                                Boolean success = false;
                                                System.out.println("\f");
                                                do {
                                                    System.out.println("");
                                                    System.out.println("Enter the path for the file: ");
                                                    lineOfText = console.readLine();
                                                    success = database.populateFromFile(lineOfText);
                                                    if (!success) {
                                                        stringArrayFormatter(ERROR_MESSAGE);
                                                    }
                                                }
                                                while (!success);
                                                System.out.println("\f");
                                                System.out.println("Students successfully loaded from file.");
                                                break;

                                            case 4:
                                                isValid = false;
                                                System.out.println("Enter the  first or last name of the student you" +
                                                        " would like to remove from the scheduler: ");
                                                lineOfText = console.readLine().toUpperCase();
                                                ArrayList<Student> searchResult = database.searchByName(lineOfText);
                                                if (searchResult.size() != 0) {
                                                    int count = 0;
                                                    for (Student student : searchResult) {
                                                        count++;
                                                        if (Tutor.isTutor(student)) {
                                                            System.out.println("# " + count + " " + "Tutor: " +
                                                                    student.getFamilyName() + ", " +
                                                                    student.getGivenName());
                                                        } else if (Tutee.isTutee(student)) {
                                                            System.out.println("# " + count + " " + "Tutee: " +
                                                                    student.getFamilyName() + ", " +
                                                                    student.getGivenName());
                                                        }
                                                    }
                                                    do {
                                                        System.out.println("");
                                                        System.out.println("Enter the number of the student you " +
                                                                "would like to remove: ");
                                                        lineOfText = console.readLine();
                                                        try {
                                                            localInput = Integer.parseInt(lineOfText);
                                                            if (localInput > 0 && localInput <= searchResult.size()) {
                                                                if (Tutor.isTutor(searchResult.get(localInput - 1))) {
                                                                    Tutor toBeRemoved = (Tutor)
                                                                            searchResult.get(localInput - 1);
                                                                    database.removeTutor(toBeRemoved);
                                                                    isValid = true;
                                                                } else if (Tutee.isTutee(searchResult.
                                                                        get(localInput - 1))) {
                                                                    Tutee toBeRemoved = (Tutee)
                                                                            searchResult.get(localInput - 1);
                                                                    database.removeTutee(toBeRemoved);
                                                                    isValid = true;
                                                                }

                                                                System.out.println("\f");
                                                                System.out.println("Student successfully removed.");
                                                            } else {
                                                                System.out.println("");
                                                                System.out.println("Please enter a valid number.");
                                                            }
                                                        } catch (NumberFormatException error) {
                                                            System.out.println("\f");
                                                            stringArrayFormatter(ERROR_MESSAGE);
                                                        }
                                                    }
                                                    while (!isValid);
                                                } else {
                                                    System.out.println("\f");
                                                    System.out.println("");
                                                    System.out.println("No one with that name was found. ");
                                                }
                                                break;
                                        }
                                        isValid = true;
                                    } else {
                                        System.out.println("\f");
                                        stringArrayFormatter(ERROR_MESSAGE);
                                    }
                                } catch (NumberFormatException error) {
                                    System.out.println("\f");
                                    stringArrayFormatter(ERROR_MESSAGE);
                                }

                            }
                            while (!isValid);
                            break;

                        case 4:
                            boolean success = database.matchMaker();
                            if (success) {
                                System.out.println("\f");
                                System.out.println("Schedule successfully created");
                            } else {
                                System.out.println("\f");
                                System.out.println("The scheduler does not contain any tutors and/or tutees. " +
                                        "Unable to create a schedule.");
                                System.out.println("Please add students to the scheduler.");
                            }
                            break;

                        case 5:
                            isValid = false;
                            do {
                                System.out.println("\f");
                                stringArrayFormatter(EXIT_MENU);
                                System.out.print("Please enter your selection: ");
                                lineOfText = console.readLine();
                                try {
                                    input = Integer.parseInt(lineOfText);
                                    if (input >= 0 && input < 3) {
                                        switch (input) {
                                            case 0:
                                                System.out.println("\f");
                                                isValid = true;
                                                break;
                                            case 1:
                                                DateFormat localDateFormat =
                                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                database.setDate(localDateFormat.format(new Date()));
                                                writeToFile(new File(SAVE_FILE_NAME), database);
                                                System.exit(0);
                                            case 2:
                                                System.exit(0);
                                        }
                                    } else {
                                        System.out.println("\f");
                                        stringArrayFormatter(ERROR_MESSAGE);
                                    }
                                } catch (NumberFormatException error) {
                                    System.out.println("\f");
                                    stringArrayFormatter(ERROR_MESSAGE);
                                }
                            }
                            while (!isValid);
                            break;
                    }
                } else {
                    System.out.println("\f");
                    stringArrayFormatter(ERROR_MESSAGE);
                }
            } catch (NumberFormatException error) {
                System.out.println("\f");
                stringArrayFormatter(ERROR_MESSAGE);
            }
        }
        while (true);
    }

    /**
     * Returns a scheduler retrieved from a file.
     *
     * @param savedSchedulerPath the file path to be saved scheduler
     * @return the retrieved scheduler
     */
    public static Scheduler readFromFile(String savedSchedulerPath) {
        savedSchedulerPath = savedSchedulerPath.replace("\\", "/");
        Scheduler save = new Scheduler();
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(savedSchedulerPath));
            try {
                save = (Scheduler) input.readObject();
                input.close();
                return save;
            } catch (FileNotFoundException error) {
                System.out.println("The file path was invalid");
            } catch (EOFException error) {
            }
        } catch (IOException error) {
        } catch (ClassNotFoundException error) {
        }
        return new Scheduler();
    }

    /**
     * Writes this scheduler to a file.
     *
     * @param file     the file to be written to
     * @param database the scheduler being written to the file
     */
    public static void writeToFile(File file, Scheduler database) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(database);
            output.close();
        } catch (IOException error) {
        }
    }

    // Private methods 
    /*
     * Formats the elements of tutee list in a readable manner.
     */
    private static String[] tuteeListFormatter(LinkedList<Tutee> list) {
        ArrayList<String> formattedList = new ArrayList();
        if (list != null) {
            if (list.size() != 0) {
                for (Tutee tutee : list) {
                    String familyName = tutee.getFamilyName();
                    String givenName = tutee.getGivenName();
                    String subject = tutee.getSubject();
                    String line = familyName + ", " + givenName + " requires assistance in " + subject;
                    formattedList.add(line);
                }
                String output[] = new String[formattedList.size()];
                return output = formattedList.toArray(output);
            }
        }
        return new String[0];
    }

    /*
     * Formats the elements of a tutor ArrayList in a readable manner.
     */
    private static String[] tutorListFormatter(ArrayList<Tutor> list) {
        ArrayList<String> formattedList = new ArrayList<>();
        if (list != null) {
            if (list.size() != 0) {
                for (Tutor tutor : list) {
                    String familyName = tutor.getFamilyName();
                    String givenName = tutor.getGivenName();
                    if (tutor.getHasTutee()) {
                        String tuteeGivenName = tutor.getAssignment().getGivenName();
                        String tuteeFamilyName = tutor.getAssignment().getFamilyName();
                        String tuteeSubject = tutor.getAssignment().getSubject();
                        String line = familyName + ", " + givenName + " is teaching " + tuteeFamilyName + ", " +
                                tuteeGivenName + " " + "Subject: " + tuteeSubject;
                        formattedList.add(line);
                    } else {
                        String subjects = "";
                        if (tutor.getTeachable().size() != 0) {
                            for (String subject : tutor.getTeachable()) {
                                if (subjects.equals("")) {
                                    subjects = subject;
                                } else {
                                    subjects = subjects + ", " + subject;
                                }
                            }
                        } else {
                            subjects = "This tutor has not teachables.";
                        }
                        String line = familyName + ", " + givenName + " can teach: " + subjects;
                        formattedList.add(line);
                    }
                }
                String output[] = new String[formattedList.size()];
                return output = formattedList.toArray(output);
            }
        }
        return new String[0];
    }

    /*
     * Formats the elements of a string array.
     */
    private static void stringArrayFormatter(String[] array) {
        for (String line : array) {
            System.out.println(line);
        }
    }
}