package model;

import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static model.Status.DONE;
import static model.Status.UP_NEXT;
import static org.junit.jupiter.api.Assertions.*;

public class TestProjectPhase5 {
    private Project testProject;

    @BeforeEach
    void runBefore() {
        testProject = new Project("el jeffe");
    }

    @Test
    void testConstructor() {
        assertEquals(testProject.getDescription(), ("el jeffe"));
    }

    @Test
    void testAddTask() {
        Task tester = new Task("this is a test");
        Task tester2 = new Task("hi");
        testProject.add(tester2);
        testProject.add(tester);
//        assertTrue(testProject.getTasks().contains(tester));
//        assertTrue(testProject.getTasks().contains((tester2)));
    }

    @Test
    void testContainsAndRemove() {
        Task tester = new Task("this is a test");

        testProject.add(tester);
        testProject.remove(tester);

        // assertTrue(testProject.getTasks().isEmpty());
    }

//    @Test
//    void testGetTasks() {
//        Task test1 = new Task("hi");
//        Task test2 = new Task("jeffe");
//
//        testProject.add(test1);
//        testProject.add(test2);
//
//        assertTrue(testProject.getTasks().contains(test2));
//    }

    @Test
    void testGetProgress() {
        Task test1 = new Task("hi");
        test1.setStatus(DONE);
        Task test2 = new Task("jeffe");
        test2.setStatus(UP_NEXT);

        testProject.add(test1);
        testProject.add(test2);

        System.out.println(testProject.getProgress());

    }

//    @Test
//    void testisCOmpleted() {
//        Task test1 = new Task("hi");
//        test1.setStatus(DONE);
//        Task test2 = new Task("jeffe");
//
//        testProject.add(test1);
//        testProject.add(test2);
//        assertFalse(testProject.isCompleted());
//
//    }

    @Test
    void testWhenEmpty() {
        assertFalse(testProject.isCompleted());
    }

    @Test
    void testGetNumberOfTasks() {
        Task test1 = new Task("hi");
        Task test2 = new Task("jeffe");


    }

    @Test
    void testIterator() {
        Task test1 = new Task("hi");
        Task test2 = new Task("jeffe");

        testProject.add(test1);
        testProject.add(test2);

        Iterator<Todo> iter = testProject.iterator();
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), new Task("hi"));

        assertTrue(iter.hasNext());
        assertEquals(iter.next(), new Task("jeffe"));

        assertFalse(iter.hasNext());

    }

    @Test
    void testSetProgressAndGetProgress() {
        Task p1t1 = new Task("h");
        p1t1.setProgress(100);
        Task p1t2 = new Task("e");
        p1t2.setProgress(20);
        Project p1 = new Project("meme");

        Project p2 = new Project("haha");
        Task p2t1 = new Task("yeet");
        Task p2t2 = new Task("hsadas");
        p2t2.setProgress(50);


        p2.add(p2t1);
        p2.add(p2t2);

        p1.add(p1t1);
        p1.add(p1t2);
        p1.add(p2);

        System.out.println(p2.getProgress());


        testProject.add(p1);
        System.out.println("hi");
        System.out.println(p1.getProgress());
        assertEquals(p1.getProgress(), 48);
        assertEquals(p2.getProgress(), 25);


    }

    @Test
    void testThrowSelfAddException() {
        testProject.add(testProject);
    }

    @Test
    void testGetDescription() {
        assertEquals(testProject.getDescription(), "el jeffe");
    }

    @Test
    void testNullArgumentForPriority() {
        try {
            testProject.setPriority(null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetTasksDeprecated() {
        try {
            testProject.getTasks();
            fail();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testIsCompleted() {
        Task p1t1 = new Task("h");
        p1t1.setProgress(100);
        Task p1t2 = new Task("e");
        p1t2.setProgress(20);
        Project p1 = new Project("meme");

        Project p2 = new Project("haha");
        Task p2t1 = new Task("yeet");
        Task p2t2 = new Task("hsadas");
        p2t2.setProgress(50);


        p2.add(p2t1);
        p2.add(p2t2);

        p1.add(p1t1);
        p1.add(p1t2);
        p1.add(p2);

        testProject.add(p1);

        assertFalse(testProject.isCompleted());

        p1t1.setProgress(100);
        p1t2.setProgress(100);
        p2t1.setProgress(100);
        p2t2.setProgress(100);

        assertTrue(testProject.isCompleted());

    }

    @Test
    void testAddContainsAndRemoveTodo() {
    // we need to test both kinds of //TODO
        testProject.add(new Task("herro"));
        testProject.add(new Project("ree"));

        assertTrue(testProject.contains(new Project("ree")));
        assertTrue(testProject.contains(new Task("herro")));
        assertFalse(testProject.contains(new Task("herro ## REE;")));

        testProject.add(new Task("herro"));
        testProject.remove(new Task("herro"));
        assertFalse(testProject.contains(new Task("herro")));
    }

    @Test
    void testCaughtContainsException() {
        try {
            testProject.add(null);
            fail();
        } catch (NullArgumentException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testEstimatedTimeToComplete() {
        Task p1t1 = new Task("h");
        Task p1t2 = new Task("e");
        Project p1 = new Project("meme");

        Project p2 = new Project("haha");
        Task p2t1 = new Task("yeet");
        Task p2t2 = new Task("hsadas");


        p2.add(p2t1);
        p2.add(p2t2);

        p1.add(p1t1);
        p1.add(p1t2);
        p1.add(p2);

        testProject.add(p1);
        p1t1.setEstimatedTimeToComplete(1);
        p1t2.setEstimatedTimeToComplete(2);
        p2t1.setEstimatedTimeToComplete(3);
        p2t2.setEstimatedTimeToComplete(45);

        assertTrue(testProject.getEstimatedTimeToComplete() == 51);

    }

    @Test
    void forCoverage() {
        assertFalse((new Task("herro")).equals(new Project("herro")));
        assertTrue((new Task("herro")).equals(new Task("herro")));
        assertFalse(new Task("herro").equals(new Task("ree")));
        testProject.remove(new Task("thanks"));
        testProject.equals(this);
    }

    @Test
    void assertContainsException() {
        try {
            testProject.contains(null);
            fail();
        } catch (NullArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void assertHashCode() {
        Project haha = new Project("el jeffe");
        assertTrue(testProject.hashCode() == haha.hashCode());
    }

    @Test
    void catchNullRemove() {
        try {
            Todo t = null;
            testProject.remove(t);
            fail();
        } catch (NullArgumentException e) {
            System.out.println(e);;
        }
    }

    @Test
    void testWhenZeroProjects() {
        assertEquals(testProject.getNumberOfTasks(), 0);
        assertEquals(testProject.getProgress(), 0);
    }

    @Test
    void iteratorToString() {
       Task testTask1 = new Task("h ## important; urgent;");
       Task testTask2 = new Task("a ## important;");
       Task testTask3 = new Task("i ## urgent;");
       Task testTask4 = new Task("r");
       Task testTask6 = new Task("t ## important; urgent;");

       testProject.add(testTask4);
       testProject.add(testTask3);
       testProject.add(testTask2);
       testProject.add(testTask1);
       testProject.add(testTask6);

       Iterator<Todo> iter = testProject.iterator();

       for (int i = 1; i < 6; i++) {
           assertTrue(iter.hasNext());
           iter.next();
       }

       assertFalse(iter.hasNext());


    }
    @Test
    void testMultipleTodosToIterator() {
        Task testTask1 = new Task("h ## important; urgent;");
        Task testTask2 = new Task("a ## important;");
        Task testTask3 = new Task("i ## urgent;");
        Task testTask4 = new Task("r");
        Project testProject5 = new Project("hi");
        Task testTask6 = new Task("t ## important; urgent;");
        testProject5.add(testTask6);

        testProject.add(testTask4);
        testProject.add(testTask3);
        testProject.add(testTask2);
        testProject.add(testTask1);
        testProject5.add(testTask6);
        testProject.add(testProject5);
        testProject.add(new Project("sdfhalkjsdhf"));
        testProject5.setPriority(new Priority(1));

        Iterator<Todo> iter = testProject.iterator();

        for (int i = 0; i < 6; i++) {
            assertTrue(iter.hasNext());
            System.out.println(iter.next());
        }

        assertFalse(iter.hasNext());

    }

    @Test
    void testExceptionCaseForPriorityIterator() {
        Iterator<Todo> iter = testProject.iterator();
        assertFalse(iter.hasNext());
        try {
            iter.next();
            fail();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Test
    void testHasNextWithNext(){
        Task testTask1 = new Task("hello ## today; important; urgent;");
        Project test2 = new Project("ree");
        test2.setPriority(new Priority(2));
        Task testTask3 = new Task("buebuye");

        testProject.add(testTask1);
        testProject.add(test2);
        testProject.add(testTask3);

        Iterator<Todo> iter = testProject.iterator();

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        assertFalse(iter.hasNext());
        try {
            iter.next();
            fail();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }





}
