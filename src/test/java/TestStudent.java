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
public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();

        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 5);
    }

    /*
    Test registerForClass function
     */
    @Test
    public void testValidRegisterClass() {
        this.student.registerForClass("Anny", "ECS122A", 2017);
        assertTrue(this.student.isRegisteredFor("Anny", "ECS122A", 2017));
    }

    @Test
    public void testValidRegisterClass2 () {
        this.student.registerForClass("Anny", "ECS122A", 2017);
        assertTrue(this.admin.classExists("ECS122A", 2017));
    }

    @Test
    public void testValidRegisterClass3 () {
        this.student.registerForClass("Anny", "ECS122A", 2017);
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.student.registerForClass("Brandon", "ECS122A", 2017);
        assertTrue(this.admin.getClassCapacity("ECS122A", 2017) >= 3);
    }

    @Test
    public void testInvalidRegisterClass() {
        this.student.registerForClass(null, "ECS122A", 2017);
        assertFalse(this.student.isRegisteredFor(null, "ECS122A", 2017));
    }

    @Test
    public void testInvalidRegisterClass2() {
        this.student.registerForClass("", "ECS122A", 2017);
        assertFalse(this.student.isRegisteredFor("", "ECS122A", 2017));
    }

    // Register for a class that doesn't exist
    @Test
    public void testInvalidRegisterClass3() {
        this.student.registerForClass("Anny", "ECS999", 2017);
        assertTrue(this.student.isRegisteredFor("Anny", "ECS999", 2017));
    }

    // Register for a class that doesn't exist
    @Test
    public void testInvalidRegisterClass4() {
        this.student.registerForClass("Anny", "ECS999", 2017);
        assertTrue(this.admin.classExists("ECS999", 2017));
    }

    // Register for a class that doesn't have enough capacity
    @Test
    public void testInvalidRegisterClass5 () {
        this.student.registerForClass("Anny", "ECS122A", 2017);
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.student.registerForClass("Brandon", "ECS122A", 2017);
        this.student.registerForClass("Andy", "ECS122A", 2017);
        this.student.registerForClass("Han", "ECS122A", 2017);
        this.student.registerForClass("Sahana", "ECS122A", 2017);
        assertTrue(this.admin.getClassCapacity("ECS122A", 2017) >= 6);
    }

    /*
    Test dropClass function
     */
    @Test
    public void testValidDropClass() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.student.dropClass("Patty", "ECS122A", 2017);
        assertFalse(this.student.isRegisteredFor("Patty", "ECS122A", 2017));
    }

    /*
    Test submitHomework function
     */
    @Test
    public void testValidSubmitHomework() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "HW1", "hello world", "ECS122A", 2017);
        assertTrue(this.student.hasSubmitted("Patty", "HW1", "ECS122A", 2017));
    }

    @Test
    public void testValidSubmitHomework2() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "HW1", "hello world", "ECS122A", 2017);
        assertTrue(this.student.hasSubmitted("Patty", "HW1", "ECS122A", 2017));
    }

    @Test
    public void testValidSubmitHomework3() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "HW1", "hello world", "ECS122A", 2017);
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW1"));
    }

    @Test
    public void testValidSubmitHomework4() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "HW1", "hello world", "ECS122A", 2017);
        assertTrue(this.student.isRegisteredFor("Patty", "ECS122A", 2017));
    }

    // Homework name is null
    @Test
    public void testInvalidSubmitHomework() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", null, "hello world", "ECS122A", 2017);
        assertFalse(this.student.hasSubmitted("Patty",null, "ECS122A", 2017));
    }

    // Homework name is empty
    @Test
    public void testInvalidSubmitHomework2() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "", "hello world", "ECS122A", 2017);
        assertFalse(this.student.hasSubmitted("Patty","", "ECS122A", 2017));
    }

    // Submit a homework with "answerString" being null
    @Test
    public void testInvalidSubmitHomework3() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "HW1", null, "ECS122A", 2017);
        assertFalse(this.student.hasSubmitted("Patty", "HW1", "ECS122A", 2017));
    }

    // Submit a homework that does not exist
    @Test
    public void testInvalidSubmitHomework4() {
        this.student.registerForClass("Patty", "ECS122A", 2017);
        this.student.submitHomework("Patty", "HW1", "hello world", "ECS122A", 2017);
        assertTrue(this.instructor.homeworkExists("ECS122A", 2017, "HW1"));
    }

    // Submit a homework of a class that the student is not registered for
    @Test
    public void testInvalidSubmitHomework5() {
        this.instructor.addHomework("Rob Gysel", "ECS122A", 2017, "HW1", "Coin change");
        this.student.submitHomework("Patty", "HW1", "hello world", "ECS122A", 2017);
        assertTrue(this.student.isRegisteredFor("Patty", "ECS122A", 2017));
    }
}
