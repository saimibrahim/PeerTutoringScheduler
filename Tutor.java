import java.util.ArrayList;

/**
 * A tutor which knows what subjects it can teach, if it has a tutee, and who that tutee is. 
 */
public class Tutor extends Student implements java.io.Serializable
{
    // instance variables 
    private Tutee assignment;
    private boolean hasTutee;
    private ArrayList<String> teachable;

    /**
     * Constructs a tutor with default values.
     */
    public Tutor() {
        super();
        assignment = new Tutee();
        hasTutee = false;
        teachable = new ArrayList<>();
    }

    /**
     * Constructs a tutor with the specified values.
     * 
     * @param availability the array storing the availability of this tutee over the course of a 5 day school week<br>
     * <i>precondition:</i> the size of <code>availability</code> must be equal to 5
     * @param givenName the given name of this tutee<br>
     * <i>precondition:</i> <code>givenName</code> cannot be null 
     * @param familyName the family name of this tutee<br>
     * <i>precondition:</i> <code>familyName</code> cannot be null
     * @param grade the grade of this tutee<br>
     * <i>precondition:</i> <code>grade</code> must be either 9, 10, 11, or 12 
     * @param hasTutee whether or not this tutee has a tutor
     * @param teachable the subjects which this tutor can teach<br>
     * <i>precondition:</i> <code>teachable</code> cannot be null
     * @param assignment the tutee assigned to this tutor<br>
     * <i>precondition:</i> <code>assignment</code> cannot be null
     */
    public Tutor(boolean[] availability, String givenName, String familyName, int grade, boolean hasTutee,
                 ArrayList<String> teachable, Tutee assignment) {
        super(availability, givenName, familyName, grade);
        this.hasTutee = hasTutee;
        if (teachable != null) {
            this.teachable = teachable;
        } else {
            this.teachable = new ArrayList();
        }

        if (assignment != null) {
            this.assignment = assignment;
        } else {
            this.assignment = new Tutee();
        }
    }

    /**
     * Compares this tutor to the specified tutor.
     * 
     * @param object the object to which this tutor is compared
     * @return true if this tutor is the same as object, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Tutor) {
            Tutor specifiedTutor = (Tutor) object;
            return super.equals(specifiedTutor) &&
                    this.assignment.equals(specifiedTutor.getAssignment()) &&
                    this.teachable.equals(specifiedTutor.getTeachable()) &&
                    this.hasTutee == specifiedTutor.getHasTutee();
        }
        return false;
    }

    /**
     * Returns the tutee assigned to this tutor.
     * 
     * @return the tutee assigned to this tutor
     */
    public Tutee getAssignment() {
        return assignment;
    }

    /**
     * Returns whether or not this tutor has a tutee.
     * 
     * @return whether or not this tutor has a tutee
     */
    public boolean getHasTutee() {
        return hasTutee;
    }

    /**
     * Returns the subjects which this tutor can teach.
     * 
     * @return the subjects which this tutor can teach
     */
    public ArrayList<String> getTeachable() {
        return teachable;
    }
    
    /**
     * Returns whether or not the given object is a tutor.
     * 
     * @param object the object being tested
     * @return true if the object is a tutor, otherwise false
     */
     public static boolean isTutor(Object object) {
         return object instanceof Tutor;
     }

    /**
     * Returns a string representation of this tutor.
     * 
     * @return a string representing this tutor
     */
    @Override
    public String toString() {
        return
                super.toString()
                        + "["
                        + "Assignment: " + assignment
                        + ", Has Tutee: " + hasTutee
                        + ", Teachables: " + teachable
                        + "]";
    }

    /**
     * Adds a subject which this tutor can teach, denoted by the course code.
     * 
     * @param subject a subject this tutor can teach<br>
     * <i>precondition:</i> <code>subject</code> cannot be null
     * @return the success of the method call
     */
    public boolean addSubject(String subject) {
        if (subject != null) {
            teachable.add(subject.toUpperCase());
            return true;
        }
        return false;
    }

    /**
     * Sets the assinged tutee of this tutor.
     * 
     * @param assignment the tutee assigned to this tutor<br>
     * <i>precondition:</i> <code>assignment</code> cannot be null
     * @return the success of the method call 
     */
    public boolean setAssignment(Tutee assignment) {
        if (assignment != null) {
            this.assignment = assignment;
            return true;
        }
        return false;
    }

    /**
     * Sets whether or not this tutor has a tutee.
     * 
     * @param hasTutee whether or not this tutor has a tutee
     */
    public void setHasTutee(boolean hasTutee) {
        this.hasTutee = hasTutee;
    }

    /**
     * Sets the arraylist storing the subject this tutor can teach.
     * 
     * @param teachable the subjects which this tutor can teach<br>
     * <i>precondition:</i> <code>teachable</code> cannot be null
     * @return the success of the method call
     */
    public boolean setTeachable(ArrayList teachable) {
        if (teachable != null) {
            this.teachable = teachable;
            return true;
        }
        return false;
    }
}