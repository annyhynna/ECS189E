import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by annyhsu on 3/7/17.
 */
public class TestInstructor {
    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();

        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 100);
    }

    /*
    Test addHomework function
     */

    @Test
    public void testValidAddHomework() {
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW1", "Dynamic Programming");
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW1"));
    }

    @Test
    public void testValidAddHomework2() {
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW1", "Dynamic Programming");
        assertEquals("Rob Gysel", this.admin.getClassInstructor("ECS122A", 2017));
    }

    // Instructor adds homework when he/she is not the instructor for that class
    @Test
    public void testInvalidAddHomework() {
        this.instructor.addHomework("Sean Davis", "ECS122A",2017, "HW1", "Dynamic Programming");
        assertEquals("Sean Davis", this.admin.getClassInstructor("ECS122A", 2017));
    }

     /*
    Test assignGrade function
     */
    @Test
    public void testValidAssignGrade() {
        this.student.registerForClass("Anny Hsu", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW2", "Divide and Conquer");
        this.student.submitHomework("Anny Hsu", "HW2", "Correct answer.", "ECS122A", 2017);
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW2", "Anny Hsu", 100);
        assertEquals(new Integer(100), this.instructor.getGrade("ECS122A", 2017, "HW2", "Anny Hsu"));
    }

    @Test
    public void testValidAssignGrade2() {
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW2", "Divide and Conquer");
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW2", "Anny Hsu", 100);
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017).equals("Rob Gysel"));
    }

    @Test
    public void testValidAssignGrade3() {
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW2", "Divide and Conquer");
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW2", "Anny Hsu", 100);
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW2"));
    }

    @Test
    public void testValidAssignGrade4() {
        this.student.registerForClass("Anny Hsu", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW2", "Divide and Conquer");
        this.student.submitHomework("Anny Hsu", "HW2", "Correct answer.", "ECS122A", 2017);
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW2", "Anny Hsu", 100);
        assertTrue(this.student.hasSubmitted("Anny Hsu", "HW2", "ECS122A", 2017));
    }

    // Instructor assigns grade to a class that he/she is not teaching
    @Test
    public void testInvalidAssignGrade() {
        this.instructor.assignGrade("Sean Davis", "ECS122A", 2017, "HW2", "Anny Hsu", 100);
        assertEquals("Sean Davis", this.admin.getClassInstructor("ECS122A", 2017));
    }

    // Instructor assigns grade to a homework that doesn't exist
    @Test
    public void testInvalidAssignGrade2() {
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW3", "Anny Hsu", 100);
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW3"));
    }

    // Instructor assigns grade to a homework that student did not submit
    @Test
    public void testInvalidAssignGrade3() {
        this.student.registerForClass("Anny Hsu", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW3", "Divide and Conquer");
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW3", "Anny Hsu", 100);
        assertTrue(this.student.hasSubmitted("Anny Hsu", "HW3", "ECS122A", 2017));
    }

    @Test
    public void testValidGradeOverHundred() {
        this.student.registerForClass("Anny Hsu", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW2", "Divide and Conquer");
        this.student.submitHomework("Anny Hsu", "HW2", "Correct answer.", "ECS122A", 2017);
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW2", "Anny Hsu", 130);
        assertTrue(this.instructor.getGrade("ECS122A", 2017, "HW2", "Anny Hsu") > 100);
    }

    @Test
    public void testInvalidGradeNegative() {
        this.student.registerForClass("Anny Hsu", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A",2017, "HW2", "Divide and Conquer");
        this.student.submitHomework("Anny Hsu", "HW2", "Correct answer.", "ECS122A", 2017);
        this.instructor.assignGrade("Rob Gysel", "ECS122A", 2017, "HW2", "Anny Hsu", -100);
        assertFalse(this.instructor.getGrade("ECS122A", 2017, "HW2", "Anny Hsu") < 0);
    }
}
