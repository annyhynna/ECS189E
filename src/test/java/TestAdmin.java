import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by annyhsu on 3/7/17.
 */
public class TestAdmin {
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    /*
    Test createClass function
     */
    @Test
    public void testValidCreateClass() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 15);
        assertTrue(this.admin.classExists("ECS122A", 2017));
    }


    // if the same class & year is created twice, should adopt the updated information
    @Test
    public void testValidCreateClass3() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 15);
        this.admin.createClass("ECS122A", 2017, "Sean Davis", 15);
        assertTrue(this.admin.getClassInstructor("ECS122A", 2017).equals("Sean Davis"));
    }

    @Test
    public void testInvalidCreateSameClass() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 15);
        this.admin.createClass("ECS122A", 2017, "Sean Davis", 15);
        assertFalse(this.admin.getClassInstructor("ECS122A", 2017).equals("Rob Gysel"));
    }

    @Test
    public void testInvalidClassName() {
        this.admin.createClass(null, 2017, "Rob Gysel", 15);
        assertFalse(this.admin.classExists(null, 2017));
    }

    @Test
    public void testInvalidClassName2() {
        this.admin.createClass("", 2017, "Rob Gysel", 15);
        assertFalse(this.admin.classExists("", 2017));
    }

    @Test
    public void testInvalidYear() {
        this.admin.createClass("ECS122A", 2016, "Rob Gysel", 15);
        assertFalse(this.admin.classExists("ECS122A", 2016));
    }

    @Test
    public void testInvalidInstructorName() {
        this.admin.createClass("ECS122A", 2017, null, 15);
        assertNotNull(this.admin.getClassInstructor("ECS122A", 2017));
    }

    @Test
    public void testInvalidInstructorName2() {
        this.admin.createClass("ECS122A", 2017, "", 15);
        assertFalse(this.admin.getClassInstructor("ECS122A", 2017).isEmpty());
    }

    @Test
    public void testInvalidCapacity() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 0);
        assertFalse(this.admin.getClassCapacity("ECS122A", 2017) <= 0);
    }

    @Test
    public void testInvalidCapacity2() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", -10);
        assertFalse(this.admin.getClassCapacity("ECS122A", 2017) <= 0);
    }

    /*
    Test changeCapacity function
     */

    @Test
    public void testValidChangeCapacity() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 100);
        this.admin.changeCapacity("ECS122A", 2017, 200);
        assertTrue(this.admin.getClassCapacity("ECS122A", 2017) == 200);
    }

    @Test
    public void testValidChangeCapacity2() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 5);
        IStudent student1 = new Student();
        student1.registerForClass("Patty Liu", "ECS122A", 2017);
        IStudent student2 = new Student();
        student1.registerForClass("Anny Hsu", "ECS122A", 2017);
        this.admin.changeCapacity("ECS122A", 2017, 3);

        assertTrue(this.admin.getClassCapacity("ECS122A", 2017) >= 2);
    }

    // if the new capacity is the same as original, it's okay, it's just silly
    @Test
    public void testValidChangeCapacity3() {
        int originalCapacity = 100;
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", originalCapacity);
        this.admin.changeCapacity("ECS122A", 2017, 100);
        assertTrue(this.admin.getClassCapacity("ECS122A", 2017) == originalCapacity);
    }

    // Changing capacity for a class that doesn't exist
    @Test
    public void testInvalidChangeCapacity() {
        this.admin.changeCapacity("ECS122A", 2017, 200);
        assertTrue(this.admin.classExists("ECS122A", 2017));
    }

    // Changing capacity to a number lower than the enrolled students
    @Test
    public void testInvalidChangeCapacity2() {
        this.admin.createClass("ECS122A", 2017, "Rob Gysel", 5);
        IStudent student1 = new Student();
        student1.registerForClass("Patty Liu", "ECS122A", 2017);
        IStudent student2 = new Student();
        student2.registerForClass("Anny Hsu", "ECS122A", 2017);
        IStudent student3 = new Student();
        student3.registerForClass("Han Chen", "ECS122A", 2017);
        this.admin.changeCapacity("ECS122A", 2017, 2);

        assertTrue(this.admin.getClassCapacity("ECS122A", 2017) >= 3);
    }
}
