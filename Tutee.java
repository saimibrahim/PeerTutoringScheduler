/**
 * A tutee which knows what subject it needs help with and whether it has a tutor or not.
 */
public class Tutee extends Student implements java.io.Serializable
{
    // instance variables 
    private boolean hasTutor;
    private String subject;

    /**
     * Constructs a tutee with default values.
     */
    public Tutee() {
        super();
        hasTutor = false;
        subject = "";
    }

    /**
     * Constructs a tutee with the specified values.
     * 
     * @param availability the array storing the availability of this tutee over the course of a 5 day school week<br>
     * precondition: the size of <code>availability</code> must be equal to 5
     * @param givenName the given name of this tutee<br>
     * precondition: <code>givenName</code> cannot be null 
     * @param familyName the family name of this tutee<br>
     * precondition: <code>familyName</code> cannot be null
     * @param grade the grade of this tutee<br>
     * precondition: <code>grade</code> must be either 9, 10, 11, or 12 
     * @param hasTutor whether or not this tutee has a tutor
     * @param subject the subject to be taught to this tutee<br>
     * precondition: <code>subject</code> cannot be null 
     */
    public Tutee(boolean[] availability, String givenName, String familyName, int grade, boolean hasTutor,
                 String subject) {
        super(availability, givenName, familyName, grade);
        this.hasTutor = hasTutor;
        if (subject != null) {
            this.subject = subject.toUpperCase();
        } else {
            this.subject = "";
        }
    }

    /**
     * Compares this tutee to the specified tutee.
     * 
     * @param object the object to which this tutee is compared
     * @return true if this tutee is the same as object, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Tutee) {
            Tutee specifiedTutee = (Tutee) object;
            return super.equals(specifiedTutee) && this.subject.equals(specifiedTutee.getSubject()) &&
                    this.hasTutor == specifiedTutee.getHasTutor();
        }
        return false;
    }

    /**
     * Returns whether or not this tutee has a tutor.
     * 
     * @return whether or not his tutee has a tutor
     */
    public boolean getHasTutor() {
        return hasTutor;
    }

    /**
     * Returns the subject to be thaught to this tutee.
     * 
     * @return the subject to be thaught to this Tutee
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns whether or not the given object is a tutee.
     * 
     * @param object the object being tested
     * @return true if the object is a tutee, otherwise false
     */
    public static boolean isTutee(Object object) {
        try {
            Tutee tutee = (Tutee) object;
            return true;
        } catch (ClassCastException error) {
            return false;
        }
    }

    /**
     * Returns a string representation of this tutee.
     * 
     * @return a string representing this tutee
     */
    @Override
    public String toString() {
        return
                super.toString()
                        + "["
                        + "Has Tutor: " + hasTutor
                        + ", Subject: " + subject
                        + "]";
    }

    /**
     * Sets whether or not this tutee has a tutor.
     * 
     * @param hasTutor whether or not this tutee has a tutee
     */
    public void setHasTutor(boolean hasTutor) {
        this.hasTutor = hasTutor;
    }

    /**
     * Sets the subject of his Tutee.
     * 
     * @param subject the subject to be thaught to this Tutee<br>
     * precondition: <code>subject</code> cannot be null 
     * @return the success of the method call 
     */
    public boolean setSubject(String subject) {
        if (subject != null) {
            this.subject = subject.toUpperCase();
            return true;
        }
        return false;
    }
}