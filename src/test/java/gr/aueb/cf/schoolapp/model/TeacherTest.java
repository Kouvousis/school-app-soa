package gr.aueb.cf.schoolapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void constructorAndGettersAndSetters() {
        Teacher teacher = new Teacher();

        teacher.setId(1);
        assertEquals(1, teacher.getId());

        teacher.setFirstname("Jim");
        assertEquals("Jim", teacher.getFirstname());

        teacher.setLastname("Kouvou");
        assertEquals("Kouvou", teacher.getLastname());
    }

    @Test
    void overloadedConstructorAndToString() {
        Teacher teacher = new Teacher(1, "Jim", "Kouvou");
        assertEquals(1, teacher.getId());
        assertEquals("Jim", teacher.getFirstname());
        assertEquals("Kouvou", teacher.getLastname());

        String expected = "id=1, firstname=Jim, lastname=Kouvou";
    }
}