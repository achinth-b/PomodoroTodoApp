package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTaskPhase5 {
    // TODO: design tests for new behaviour added to Task class

    private Task testTask;
    private Tag testTag1;
    private Tag testTag2;

    @BeforeEach
    void runBefore() {
        try {
            testTask = new Task("testTask ## tomorrow;");
            testTag1 = new Tag("testTag1");
            testTag2 = new Tag("testTag2");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void TestConstructorWithNoExceptions() {
        assertEquals(testTask.getDescription(), "testTask ");
        testTask.setDueDate(null);
        assertTrue(testTask.getTags().isEmpty());
        assertNull(testTask.getDueDate());
        assertFalse(testTask.getPriority().isUrgent() && testTask.getPriority().isImportant());
        assertTrue(testTask.getStatus() == Status.TODO);
        assertEquals(0, testTask.getProgress());
        assertEquals(0, testTask.getEstimatedTimeToComplete());
    }

    @Test
    void testConstructorWithExceptions() {
        try {
            testTask = new Task(null);
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Exception caught!");
        } finally {
            try {
                testTask = new Task("");
                fail();
            } catch (EmptyStringException e) {
                System.out.println("Exception caught!");
            }
        }
    }

    @Test
    void testAddTag() {
        testTask.addTag(testTag1);
        assertFalse(testTask.containsTag(testTag2));
        assertTrue(testTask.containsTag(testTag1));

        Tag testTag3 = new Tag("testTag1");
        testTask.addTag((testTag3));
        assertTrue(testTask.containsTag(testTag3));
    }

    @Test
    void testRemoveTag() {
        testTask.addTag(testTag1);
        testTask.addTag(testTag2);

        assertTrue(testTask.containsTag(testTag2));
        assertTrue(testTask.containsTag(testTag1));

        Tag testTag3 = new Tag("testTag3");
        testTask.addTag((testTag3));

        testTask.removeTag(testTag2);
        testTask.removeTag(testTag1);
        assertFalse(testTask.containsTag(testTag2));
        assertFalse(testTask.containsTag(testTag1));

        assertTrue(testTask.containsTag(testTag3));
        testTask.removeTag("testTag3");
        assertFalse(testTask.containsTag(testTag3));

    }

    @Test
    void testGetTags() {
        Set<Tag> setTags = new HashSet<>();
        setTags.add(testTag1);
        setTags.add(testTag2);

        Tag testTag3 = new Tag("testTag1");
        testTask.addTag((testTag1));
        testTask.addTag(testTag2);
        testTask.addTag(testTag3);

        assertEquals(testTask.getTags(), setTags);

    }

    @Test
    void testSetAndGetPriority() {
        Priority p1 = new Priority(2);
        testTask.setPriority(p1);
        assertFalse(testTask.getPriority() == new Priority(4));
        assertTrue(testTask.getPriority() == p1);

    }

    @Test
    void testSetAndGetStatus() {
        testTask.setStatus(Status.TODO);
        testTask.setStatus(Status.UP_NEXT);
        assertEquals(testTask.getStatus(), Status.UP_NEXT);
        testTask.setStatus(Status.DONE);
        assertEquals(testTask.getStatus(), Status.DONE);
    }

    @Test
    void testSetDescriptionsWithExceptions() {
        try {
            testTask.setDescription(null);
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Exception caught!");
        } finally {
            try {
                testTask.setDescription("");
                fail();
            } catch (EmptyStringException e) {
                System.out.println("Exception caught!");
            }
        }
    }

    @Test
    void testSetAndGetDescriptionsWithoutExceptions() {
        assertEquals(testTask.getDescription(), "testTask ");
        testTask.setDescription("hello");
        assertEquals(testTask.getDescription(), "hello");
    }

    @Test
    void testSetAndGetDueDate() {
        assertNotNull(testTask.getDueDate());
        DueDate d1 = new DueDate();
        d1.postponeOneDay();
        testTask.setDueDate(d1);
        assertEquals(testTask.getDueDate(), d1);
    }

    @Test
    void testContainsTagWithExceptions() {
        try {
            testTask.containsTag("");
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Exception Caught!");
        } finally {
            try {
                String hehe = null;
                testTask.containsTag(hehe);
                fail();
            } catch (EmptyStringException e) {
                System.out.println("ree");
            }
        }

        try {
            Tag h = null;
            testTask.containsTag(h);
            fail();
        } catch (NullArgumentException e) {
            System.out.println("pranked");
        }
    }

    @Test
    void testContainsTagWithoutExceptions() {
        testTask.addTag(testTag1);
        Tag testTag3 = new Tag("testTag1");
        testTask.addTag(testTag3);
        assertTrue(testTask.containsTag(testTag3));
        assertTrue(testTask.containsTag(testTag1));
        assertFalse(testTask.containsTag(testTag2));
        testTask.containsTag(new Tag("testTag1"));

    }

    @Test
    void testToString() {
        testTask.addTag(testTag1);
        testTask.addTag(testTag2);
        assertEquals("\n{" +
                "\n\tDescription: testTask " +
                "\n\tDue date: " + testTask.getDueDate().toString() +
                "\n\tStatus: TODO" +
                "\n\tPriority: DEFAULT"
                + "\n\tTags: #testTag2, #testTag1"
                + "\n}", testTask.toString());
    }

    @Test
    void testEquals() {
        Task testTask2 = new Task("testTask ## tomorrow;");
    }

    @Test
    void testHashCode() {
        Task testTask2 = new Task("testTask");
        testTask2.setDueDate(null);
        DueDate h = new DueDate(Calendar.getInstance().getTime());
        Task testTask3 = new Task("dude this is fake");
        testTask3.setDueDate(h);
        testTask3.setStatus(Status.DONE);
        assertFalse(testTask3.hashCode() == testTask.hashCode());

    }

    @Test
    void uh() {
        Date h = new Date();
        System.out.println(h.getHours());
    }

    @Test
    void testNewProgressAndETCSetters() {
        testTask.setEstimatedTimeToComplete(5);
        assertTrue(testTask.getEstimatedTimeToComplete() == 5);
        testTask.setProgress(5);
        assertEquals(testTask.getProgress(), 5);
    }

    @Test
    void testAddTagAndRemoveTag() {
        testTask.addTag("hehe");
        assertTrue(testTask.containsTag(new Tag("hehe")));
        testTask.removeTag("hehe");
        assertFalse(testTask.containsTag("hehe"));
    }

    @Test
    void catchStatusException() {
        try {
            testTask.setStatus(null);
            fail();
        } catch (NullArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testETCExceptionMethods() {
        try {
            testTask.setEstimatedTimeToComplete(-5);
            fail();
        } catch (NegativeInputException e) {
            e.printStackTrace();
        }

    }


    @Test
    void testParseDescription() {
        testTask = new Task("buy bread ## today; groceries;");
        System.out.println(testTask.getDueDate());
        assertEquals("\n{" +
                "\n\tDescription: buy bread " +
                "\n\tDue date: " + testTask.getDueDate().toString() +
                "\n\tStatus: TODO" +
                "\n\tPriority: DEFAULT"
                + "\n\tTags: #groceries"
                + "\n}", testTask.toString());
    }

    @Test
    void forCoverage() {
        testTask.setDueDate(null);
        System.out.println(testTask.getTags().size());
        assertEquals("\n{" +
                "\n\tDescription: testTask " +
                "\n\tDue date: " +
                "\n\tStatus: TODO" +
                "\n\tPriority: DEFAULT"
                + "\n\tTags:  "
                + "\n}", testTask.toString());
        System.out.println(testTask.toString());
        try {
            throw new NegativeInputException();
        } catch (NegativeInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testNullStatus() {
        try {
            testTask.setStatus(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);

        }
    }

    @Test
    void testBothNullAddTags() {
        try {
            String name = null;
            testTask.addTag(name);
            fail();
        } catch (EmptyStringException e) {
            System.out.println(e);
        }

        try {
            Tag tag = null;
            testTask.addTag(tag);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
        }
    }

    @Test
    void testBothNullRemoveTags() {
        try {
            String name = null;
            testTask.removeTag(name);
            fail();
        } catch (EmptyStringException e) {
            System.out.println(e);
        }

        try {
            Tag tag = null;
            testTask.removeTag(tag);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);
        }
    }

    @Test
    void testInvalidProgressException() {
        try {
            testTask.setProgress(1000000000);
            fail();
        } catch (InvalidProgressException e) {
            e.printStackTrace();
        }

        try {
            testTask.setProgress(-19348);
            fail();
        } catch (InvalidProgressException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEmptyStringAddRemoveTag() {
        try {
            testTask.addTag("");
            fail();
        } catch (EmptyStringException e) {
            e.printStackTrace();
        }

        try {
            testTask.removeTag("");
            fail();
        } catch (EmptyStringException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEqualsWithDifferentDueDate() {
        DueDate due = new DueDate();
        due.postponeOneWeek();
        Task testTask2 = new Task("testTask ");
        assertFalse(testTask2.equals(testTask));

    }

    @Test
    void testEqualsWithDifferentPriorities() {
        Task testTask2 = new Task("testTask ## tomorrow;");
        testTask.setPriority(new Priority(3));
        testTask.setPriority(new Priority(2));
        assertFalse(testTask2.equals(testTask));

    }

    @Test
    void testEqualsWithDifferentSttatus() {
        Task testTask2 = new Task("testTask ## tomorrow;");
        testTask.setPriority(new Priority(3));
        testTask.setPriority(new Priority(3));
        testTask2.setStatus(Status.TODO);
        testTask.setStatus(Status.UP_NEXT);
        assertFalse(testTask2.equals(testTask));
    }

    @Test
    void testAllEquals(){
        Task testTask2 = new Task("testTask ## tomorrow;");
        testTask2.setPriority(new Priority(3));
        testTask.setPriority(new Priority(3));
        testTask2.setStatus(Status.UP_NEXT);
        testTask.setStatus(Status.UP_NEXT);
        assertTrue(testTask2.equals(testTask));

    }
}





