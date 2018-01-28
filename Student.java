import java.util.Arrays;

/**
 * A student which knows its name, grade, and when it's available. 
 */
public class Student implements java.io.Serializable
{
    // class fields 
    private static final int DAYS_IN_SCHOOL_WEEK = 5;

    // instance variables
    private boolean[] availability; 
    private String familyName;
    private String givenName;
    private int grade;

    /**
     * Constructs a student with default values.
     */
    public Student() {
        availability = new boolean[DAYS_IN_SCHOOL_WEEK];
        familyName = "";
        givenName = "";
        grade = 0;
    }

    /**
     * Constructs a student with the specified values.
     * 
     * @param availability the array storing the availability of this student over the course of a 5 day school week<br>
     * precondition: the size of <code>availability</code> must be equal to 5 and cannot be null
     * @param givenName the given name of this Student<br>
     * precondition: <code>givenName</code> cannot be null 
     * @param familyName the family name of this Student<br>
     * precondition: <code>familyName</code> cannot be null
     * @param grade the grade of this Student<br>
     * precondition: <code>grade</code> must be either 9, 10, 11, or 12 
     */
    public Student(boolean availability[], String familyName, String givenName, int grade) {
        if (availability != null) {
            if (availability.length == DAYS_IN_SCHOOL_WEEK) {
                this.availability = availability;
            }
            else {
                this.availability = new boolean[DAYS_IN_SCHOOL_WEEK];
            }
        }
        else {
            this.availability = new boolean[DAYS_IN_SCHOOL_WEEK];
        }

        if (familyName != null) {
            this.familyName = familyName.toUpperCase();
        }
        else {
            this.familyName = "";
        }

        if (givenName != null) {
            this.givenName = givenName.toUpperCase();
        }
        else {
            this.givenName = "";
        }

        if (grade >= 9 && grade <= 12) {
            this.grade = grade;
        }
        else {
            this.grade = 0;
        }
    }

    /**
     * Compares this student to the specified student.
     * 
     * @param object the object to which this student is compared
     * @return true if this student is the same as object, otherwise false
     */
    @Override
    public boolean equals(Object object)
    {
         if (object instanceof Student) {
             Student specifiedStudent = (Student) object;
             return this.givenName.equals(specifiedStudent.getGivenName()) &&
                     this.familyName.equals(specifiedStudent.getFamilyName()) &&
                     this.grade == specifiedStudent.getGrade() &&
                     Arrays.equals(this.availability, specifiedStudent.getAvailability());
         }
         return false;
    }

    /**
     * Returns the array storing the availability of this student.
     * 
     * @return the array storing the availability of this student
     */
    public boolean[] getAvailability() {
        return availability;
    }

    /**
     * Returns the family name of this student.
     * 
     * @return the family name of this student
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Returns the given name of this student.
     * 
     * @return the given name of this student
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Returns the grade of this student.
     * 
     * @return the grade of this student
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Returns the specific availability, per day of the week, of this student
     * 
     * @param dayOfWeek an intger representing the day of the week<br>
     * precondition: the value of <code>dayOfWeek</code> must be between 2 and 6, inclusively, corrusponding with
     * the days of the school week
     * @return the specific availability of this student
     */
    public boolean getSpecificAvailability(int dayOfWeek) {
        try {
            return availability[dayOfWeek];
        }
        catch (ArrayIndexOutOfBoundsException error) {
             System.err.println("IndexOutOfBoundsException: " + error.getMessage());
             return false;
        }
    }

    /**
     * Returns a string representation of this student.
     * 
     * @return a string representing this student
     */
    @Override
    public String toString() {
        return
                getClass().getName()
                        + "["
                        + "Availability" + availability
                        + ", Family Name " + familyName
                        + ", Given Name: " + givenName
                        + ", Grade: " + grade
                        + "]";
    }

    /**
     * Sets the array storing the availability of this student
     * 
     * @param availability the array storing the availability of this student<br>
     * precondition: the size of <code>availability</code> must be equal to 5
     * @return the success of the method call 
     */
    public boolean setAvailbility(boolean[] availability) {
        if (availability.length == DAYS_IN_SCHOOL_WEEK) {
            this.availability = availability;
            return true;
        }
        return false;
    }

    /**
     * Sets the family name of this student.
     * 
     * @param familyName the new family name of this student<br>
     * precondition: <code>familyName</code> cannot be null  
     * @return the success of the method call 
     */
    public boolean setFamilyName(String familyName) {
        if (familyName != null) {
            this.familyName = familyName.toUpperCase();
            return true;
        }
        return false;
    }

    /**
     * Sets the given name of this student.
     * 
     * @param givenName the new given name of this student<br>
     * precondition: <code>givenName</code> cannot be null 
     * @return the success of the method call 
     */
    public boolean setGivenName(String givenName) {
        if (givenName != null) {
            this.givenName = givenName.toUpperCase();
            return true;
        }
        return false;
    }

    /**
     * Sets the grade of this student.
     * 
     * @param grade the new grade of this student<br>
     * precondition: <code>grade</code> must be either 9, 10, 11, or 12  
     * @return the success of the method call 
     */
    public boolean setGrade(int grade) {
        if (grade >= 9 && grade <= 12) {
            this.grade = grade;
            return true;
        }
        return false;
    }

    /**
     * Sets the specific availability, per day of the week, of this student

     * @param isAvailable the availability of this student
     * @param dayOfWeek an intger representing the day of the week<br>
     * precondition: the value of <code>dayOfWeek</code> must be between 2 and 6, inclusively, corrusponding with Monday to Friday
     * @return the success of the method call 
     */
    public boolean setSpecificAvailability(boolean isAvailable, int dayOfWeek) {
        if (dayOfWeek >= 2 && dayOfWeek <= 6) {
            availability[dayOfWeek - 2] = isAvailable;
            return true;
        }
        return false;
    }
}