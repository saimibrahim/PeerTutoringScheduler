import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Creates a schedule tutoring sessions.
 */
public class Scheduler implements java.io.Serializable {
    // class fields
    private static int FIRST_ELEMENT_OF_COLLECTION = 0;
    private static final int DAYS_IN_SCHOOL_WEEK = 5;
    private static String TUTEE = "TUTEE";
    private static String TUTOR = "TUTOR";

    // instance variables
    private String date;
    private ArrayList<Tutor> fridaySession;
    private ArrayList<Tutor> mondaySession;
    private ArrayList<Tutor> thursdaySession;
    private ArrayList<Tutor> tuesdaySession;
    private LinkedList<Tutee> tuteeQueue;
    private ArrayList<Tutor> tutorPool;
    private ArrayList<Tutor> wednesdaySession;

    // Constructors
    /**
     * Constructs a scheduler with default values.
     */
    public Scheduler() {
        date = "";
        fridaySession = new ArrayList<>();
        mondaySession = new ArrayList<>();
        thursdaySession = new ArrayList<>();
        tuesdaySession = new ArrayList<>();
        tuteeQueue = new LinkedList<>();
        tutorPool = new ArrayList<>();
        wednesdaySession = new ArrayList<>();
    }

    // Accessors
    /**
     * Compares this scheduler to the specified scheduler.
     * 
     * @param object the object to which this scheduler is compared
     * @return true if this scheduler is the same as object, otherwise false
     */
    @Override
    public boolean equals(Object object)
    {
        if(object instanceof Scheduler) {
            Scheduler specifiedScheduler = (Scheduler) object;
            return this.date.equals(specifiedScheduler.getDate()) &&
                    this.fridaySession.equals(specifiedScheduler.getFridaySession()) &&
                    this.mondaySession.equals(specifiedScheduler.getMondaySession()) &&
                    this.thursdaySession.equals(specifiedScheduler.getThursdaySession()) &&
                    this.tuesdaySession.equals(specifiedScheduler.getTuesdaySession()) &&
                    this.tuteeQueue.equals(specifiedScheduler.getTuteeQueue()) &&
                    this.tutorPool.equals(specifiedScheduler.getTutorPool()) &&
                    this.wednesdaySession.equals(specifiedScheduler.getWednesdaySession());
        }
        return false;
    }

    /**
     * Return the date of this scheduler.
     * 
     * @return the date of this scheduler
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the tutors teaching on Fridays.
     * 
     * @return the tutors teaching on Fridays
     */
    public ArrayList<Tutor> getFridaySession() {
        return fridaySession;
    }

    /**
     * Returns the tutors teaching on Mondays.
     * 
     * @return the tutors teaching on Mondays
     */
    public ArrayList<Tutor> getMondaySession() {
        return mondaySession;
    }

    /**
     * Returns the tutors teaching on Thursdays.
     * 
     * @return the tutors teaching on Thursdays
     */
    public ArrayList<Tutor> getThursdaySession() {
        return thursdaySession;
    }

    /**
     * Returns the tutors teaching on Tuesdays.
     * 
     * @return the tutors teaching on Tuesdays
     */
    public ArrayList<Tutor> getTuesdaySession() {
        return tuesdaySession;
    }

    /**
     * Returns the queue of tutees.
     * 
     * @return the queue of tutees
     */
    public LinkedList<Tutee> getTuteeQueue() {
        return tuteeQueue;
    }

    /**
     * Returns the pool of available tutors.
     * 
     * @return the pool of available tutors.
     */
    public ArrayList<Tutor> getTutorPool() {
        return tutorPool;
    }

    /**
     * Returns the tutors teaching on Wednesdays.
     * 
     * @return the tutors teaching on Wednesdays
     */
    public ArrayList<Tutor> getWednesdaySession() {
        return wednesdaySession;
    }

    /**
     * Returns a string representation of this scheduler.
     * 
     * @return a string representing this scheduler
     */
    @Override
    public String toString() {
        return
                getClass().getName()
                        + "["
                        + "Friday Session: " + fridaySession
                        + ", Monday Session: " + mondaySession
                        + ", Thursday Session: " + thursdaySession
                        + ", Tuesday Session: " + tuesdaySession
                        + ", Tutee Queue: " + tuteeQueue
                        + ", Tutor Pool: " + tutorPool
                        + ", Wednesday Session: " + wednesdaySession
                        + "]";
    }

    /**
     * Adds a tutor to the tutor pool.
     * 
     * @param tutor the tutor to be added to the pool of tutors<br>
     * <i>precondition:</i> <code>tutor</code> cannot be null
     * @return the success of method call 
     */
    public boolean addTutorToPool(Tutor tutor) {
        if (tutor != null) {
            tutorPool.add(tutor);
            return true;
        }
        return false;
    }

    /**
     * Matches all tutees with a tutor.
     * 
     * @return the success of the method call 
     */
    public boolean matchMaker() {
        LinkedList<Tutee> unmatched = new LinkedList<>();
        if (tuteeQueue.size() != 0 && tutorPool.size() != 0) {
            Tutee currentTutee = new Tutee();
            while (currentTutee != null) {
                currentTutee = tuteeQueue.poll();
                if (currentTutee != null) {
                    for (int i = 0; i < tutorPool.size(); i++) {
                        Tutor currentTutor = tutorPool.get(i);
                        if (currentTutor.getTeachable().contains(currentTutee.getSubject())) {
                            for (int j = 0; j < currentTutor.getAvailability().length; j++) {
                                if (currentTutor.getAvailability()[j] && currentTutee.getAvailability()[j]) {
                                    currentTutor.setHasTutee(true);
                                    currentTutee.setHasTutor(true);
                                    currentTutor.setAssignment(currentTutee);
                                    tutorPool.remove(currentTutor);
                                    switch (j) {
                                        case 0:
                                            mondaySession.add(currentTutor);
                                            break;
                                        case 1:
                                            tuesdaySession.add(currentTutor);
                                            break;
                                        case 2:
                                            wednesdaySession.add(currentTutor);
                                            break;
                                        case 3:
                                            thursdaySession.add(currentTutor);
                                            break;
                                        case 4:
                                            fridaySession.add(currentTutor);
                                            break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (currentTutee.getHasTutor()) {
                            break;
                        }
                    }
                }
                if (currentTutee != null) {
                    if (!currentTutee.getHasTutor()) {
                        unmatched.addLast(currentTutee);
                    }
                }
            }
            tuteeQueue.addAll(FIRST_ELEMENT_OF_COLLECTION, unmatched);
            return true;
        }
        return false;
    }

    /**
     * Populates the database of tutor and tutees from a comma seperated value file.
     * 
     * @param filePath the path of csv file containing tutee and tutor data<br>
     * <i>precondition:</i> the csvFile must be formatted as such:<br>
     * <i>for tutors</i> "Tutor,family name, given name, grade, avialabilty for monday to friday (true or false,
     *                 5 seperate values), teachable subjects"<br>
     * <i>for tutors</i> "Tutor,family name, given name, grade, avialabilty for monday to friday (true or false,
     *                 5 seperate values), subject to be taught"
     * @return the success of the method call 
     */
    public boolean populateFromFile(String filePath) {
        String data = "";
        int endOfAvailability = 9;
        String splitBy = ",";
        int startOfAvailability = 4;
        filePath = filePath.replace("\\", "/");
        try {
            BufferedReader console = new BufferedReader(new FileReader(filePath));
            data = console.readLine();
            do {
                String[] studentData = data.split(splitBy);
                if (studentData[FIRST_ELEMENT_OF_COLLECTION].toUpperCase().equals(TUTOR)) {
                    Tutor newTutor = new Tutor(new boolean[DAYS_IN_SCHOOL_WEEK], studentData[1], studentData[2],
                            Integer.parseInt(studentData[3]), false, new ArrayList<>(), new Tutee());
                    for (int i = startOfAvailability; i < endOfAvailability; i++) {
                        if (studentData[i].toUpperCase().equals("TRUE")) {
                            newTutor.setSpecificAvailability(true, i - 2);
                        }
                    }
                    for (int i = endOfAvailability; i < studentData.length; i++) {
                        newTutor.addSubject(studentData[i]);
                    }
                    tutorPool.add(newTutor);
                } else if (studentData[FIRST_ELEMENT_OF_COLLECTION].toUpperCase().equals(TUTEE)) {
                    Tutee newTutee = new Tutee(new boolean[DAYS_IN_SCHOOL_WEEK], studentData[1], studentData[2],
                            Integer.parseInt(studentData[3]), false, studentData[endOfAvailability]);
                    for (int i = startOfAvailability; i < endOfAvailability; i++) {
                        if (studentData[i].toUpperCase().equals("TRUE")) {
                            newTutee.setSpecificAvailability(true, i - 2);
                        }
                    }
                    tuteeQueue.addLast(newTutee);
                }
                data = console.readLine();
            }
            while (data != null);
            return true;
        } catch (FileNotFoundException error) {
            System.out.println("The file path was invalid");
            return false;
        } catch (IOException error) {
            return false;
        }
    }

    /**
     * Adds a tutee to the queue.
     * 
     * @param tutee the tutee to be added to the queue<br>
     * <i>precondition:</i> <code>tutee</code> cannot be null
     * @return the success of method call 
     */
    public boolean queueTutee(Tutee tutee)
    {
        if (tutee != null) {
            tuteeQueue.addLast(tutee);
            return true;
        }
        return false;
    }

    /**
     * Returns a scheduler retrieved from a file.
     * 
     * @param savedScheduler the file to be retrieved from 
     * @return the retrieved scheduler
     */
    public Scheduler readFromFile(File savedScheduler) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(savedScheduler));
            try {
                return (Scheduler) input.readObject();
            } catch (EOFException error) {
            } catch (ClassCastException error) {
                System.out.println("Invalid file type!");
            }
            input.close();
        } catch (IOException error) {
        } catch (ClassNotFoundException error) {
        }
        return null;
    }

    /**
     * Removes a tutee from the database.
     * 
     * @param tutee the tutee to be removed<br>
     * <i>precondition:</i> <code>tutor</code> cannot be null
     * @return the success of the method call 
     */
    public boolean removeTutee(Tutee tutee) {
        if (tutee != null) {
            if (tuteeQueue.contains(tutee)) {
                tuteeQueue.remove(tutee);
                return true;
            } else if (tutee.getHasTutor()) {
                for (int i = 0; i < tutee.getAvailability().length; i++) {
                    if (tutee.getAvailability()[i]) {
                        Iterator<Tutor> iterator;
                        Tutor toBeRemoved = new Tutor();
                        switch (i) {
                            case 0:
                                iterator = mondaySession.iterator();
                                while (iterator.hasNext()) {
                                    Tutor tutor = iterator.next();
                                    if (tutor.getAssignment().equals(tutee)) {
                                        toBeRemoved = tutor;
                                        tutor.setAssignment(new Tutee());
                                        tutorPool.add(tutor);
                                    }
                                }
                                mondaySession.remove(toBeRemoved);
                                break;
                            case 1:
                                iterator = tuesdaySession.iterator();
                                while (iterator.hasNext()) {
                                    Tutor tutor = iterator.next();
                                    if (tutor.getAssignment().equals(tutee)) {
                                        toBeRemoved = tutor;
                                        tutor.setAssignment(new Tutee());
                                        tutorPool.add(tutor);
                                    }
                                }
                                tuesdaySession.remove(toBeRemoved);
                                break;
                            case 2:
                                iterator = wednesdaySession.iterator();
                                while (iterator.hasNext()) {
                                    Tutor tutor = iterator.next();
                                    if (tutor.getAssignment().equals(tutee)) {
                                        toBeRemoved = tutor;
                                        tutor.setAssignment(new Tutee());
                                        tutorPool.add(tutor);
                                    }
                                }
                                wednesdaySession.remove(toBeRemoved);
                                break;
                            case 3:
                                iterator = thursdaySession.iterator();
                                while (iterator.hasNext()) {
                                    Tutor tutor = iterator.next();
                                    if (tutor.getAssignment().equals(tutee)) {
                                        toBeRemoved = tutor;
                                        tutor.setAssignment(new Tutee());
                                        tutorPool.add(tutor);
                                    }
                                }
                                thursdaySession.remove(toBeRemoved);
                                break;
                            case 4:
                                iterator = fridaySession.iterator();
                                while (iterator.hasNext()) {
                                    Tutor tutor = iterator.next();
                                    if (tutor.getAssignment().equals(tutee)) {
                                        toBeRemoved = tutor;
                                        tutor.setAssignment(new Tutee());
                                        tutorPool.add(tutor);
                                    }
                                }
                                fridaySession.remove(toBeRemoved);
                                break;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a tutor from the database. 
     * 
     * @param tutor the tutor to be removed<br>
     * <i>precondition:</i> <code>tutor</code> cannot be null
     * @return the success of the method call
     */
    public boolean removeTutor(Tutor tutor) {
        if (tutor != null) {
            if (tutorPool.contains(tutor)) {
                tutorPool.remove(tutor);
                return true;
            } else if (tutor.getHasTutee()) {
                tuteeQueue.addLast(tutor.getAssignment());
                for (int i = 0; i < tutor.getAvailability().length; i++) {
                    if (tutor.getAvailability()[i]) {
                        switch (i) {
                            case 0:
                                mondaySession.remove(tutor);
                                break;
                            case 1:
                                tuesdaySession.remove(tutor);
                                break;
                            case 2:
                                wednesdaySession.remove(tutor);
                                break;
                            case 3:
                                thursdaySession.remove(tutor);
                                break;
                            case 4:
                                fridaySession.remove(tutor);
                                break;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a student with the given name.
     * 
     * @param name the name to be searched.<br>
     * <i>precondition:</i> <code>name</code> cannot be null
     * @return the students with the specified name
     */
    public ArrayList<Student> searchByName(String name) {
        if (name != null) {
            ArrayList<Student> searchResult = new ArrayList<>();
            name = name.toUpperCase();
            ArrayList<Tutor> weekSession = new ArrayList<>();
            weekSession.addAll(FIRST_ELEMENT_OF_COLLECTION, mondaySession);
            weekSession.addAll(FIRST_ELEMENT_OF_COLLECTION, tuesdaySession);
            weekSession.addAll(FIRST_ELEMENT_OF_COLLECTION, wednesdaySession);
            weekSession.addAll(FIRST_ELEMENT_OF_COLLECTION, thursdaySession);
            weekSession.addAll(FIRST_ELEMENT_OF_COLLECTION, fridaySession);

            for (Tutee tutee : tuteeQueue) {
                if (tutee.getGivenName().equals(name) || tutee.getFamilyName().equals(name)) {
                    searchResult.add(tutee);
                }
            }

            for (Tutor tutor : tutorPool) {
                if (tutor.getGivenName().equals(name) || tutor.getFamilyName().equals(name)) {
                    searchResult.add(tutor);
                }
            }

            for (Tutor tutor : weekSession) {
                if (tutor.getGivenName().equals(name) || tutor.getFamilyName().equals(name)) {
                    searchResult.add(tutor);
                }

                if (tutor.getAssignment().getFamilyName().equals(name) ||
                        tutor.getAssignment().getGivenName().equals(name)) {
                    searchResult.add(tutor.getAssignment());
                }
            }
            return searchResult;
        }
        return new ArrayList<>();
    }

    /**
     * Sets the date of this scheduler.
     * 
     * @param date the date of this scheduler<br>
     * <i>precondition:</i> <code>date</code> cannot be null
     * @return the success of the method call
     */
    public boolean setDate(String date) {
        if (date != null) {
            this.date = date;
            return true;
        }
        return false;
    }

    /**
     * Sets the list of tutors teaching on Fridays.
     * 
     * @param fridaySession the list of tutors teaching on Fridays<br>
     * <i>precondition:</i> <code>fridaySession</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setFridaySession(ArrayList<Tutor> fridaySession) {
        if (fridaySession != null) {
            this.fridaySession = fridaySession;
            return true;
        }
        return false;
    }

    /**
     * Sets the list of tutors teaching on Mondays.
     * 
     * @param mondaySession the list of tutors teaching on Mondays<br>
     * <i>precondition:</i> <code>mondaySession</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setMondaySession(ArrayList<Tutor> mondaySession) {
        if (mondaySession != null) {
            this.mondaySession = mondaySession;
            return true;
        }
        return false;
    }

    /**
     * Sets the list of tutors teaching on Thursdays.
     * 
     * @param thursdaySession the list of tutors teaching on Thursdays<br>
     * <i>precondition:</i> <code>thursdaySession</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setThursdaySession(ArrayList<Tutor> thursdaySession) {
        if (thursdaySession != null) {
            this.thursdaySession = thursdaySession;
            return true;
        }
        return false;
    }

    /**
     * Sets the list of tutors teaching on Tuesdays.
     * 
     * @param tuesdaySession the list of tutors teaching on Tuesdays<br>
     * <i>precondition:</i> <code>tuesdaySession</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setTuesdaySession(ArrayList<Tutor> tuesdaySession) {
        if (tuesdaySession != null) {
            this.tuesdaySession = tuesdaySession;
            return true;
        }
        return false;
    }

    /**
     * Sets the queue of tutees.
     * 
     * @param tuteeQueue the queue of tutees<br>
     * <i>precondition:</i> <code>tuteeQueue</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setTuteeQueue(LinkedList<Tutee> tuteeQueue) {
        if(tuteeQueue != null) {
            this.tuteeQueue = tuteeQueue;
            return true;
        }
        return false;
    }

    /**
     * Sets the pool of available tutors.
     * 
     * @param tutorPool the pool of available tutors<br>
     * <i>precondition:</i> <code>tutorPool</code> cannot be null
     * @return the success of the method call
     */
    public boolean setTutorPool(ArrayList<Tutor> tutorPool) {
        if(tutorPool != null) {
            this.tutorPool = tutorPool;
            return true;
        }
        return false;
    }

    /**
     * Sets the list of tutors teaching on Wednesdays.
     * 
     * @param wednesdaySession the list of tutors teaching on Wednesdays<br>
     * <i>precondition:</i> <code>wednesdaySession</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setWednesdaySession(ArrayList<Tutor> wednesdaySession) {
        if(wednesdaySession != null) {
            this.wednesdaySession = wednesdaySession;
            return true;
        }
        return false;
    }

    /**
     * Writes this scheduler to a file.
     * 
     * @param file the file to be written to
     */
    public void writeToFile(File file) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(this);
            output.close();
        }
        catch (IOException error) {}
    }
}