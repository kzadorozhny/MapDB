package org.mapdb;


import org.junit.Test;

import java.io.File;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueuesTest {



    @Test public void stack_persisted(){
        File f = Utils.tempDbFile();
        DB db = DBMaker.newFileDB(f).journalDisable().cacheDisable().asyncWriteDisable().make();
        Queue<Object> stack = db.getStack("test");
        stack.add("1");
        stack.add("2");
        stack.add("3");
        stack.add("4");

        db.close();
        db = DBMaker.newFileDB(f).journalDisable().cacheDisable().asyncWriteDisable().deleteFilesAfterClose().make();
        stack = db.getStack("test");

        assertEquals("4",stack.poll());
        assertEquals("3",stack.poll());
        assertEquals("2",stack.poll());
        assertEquals("1",stack.poll());
        assertNull(stack.poll());
        db.close();
    }


    @Test public void queue_persisted(){
        File f = Utils.tempDbFile();
        DB db = DBMaker.newFileDB(f).journalDisable().cacheDisable().asyncWriteDisable().make();
        Queue<Object> queue = db.getQueue("test");
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");

        db.close();
        db = DBMaker.newFileDB(f).journalDisable().cacheDisable().asyncWriteDisable().deleteFilesAfterClose().make();
        queue = db.getQueue("test");

        assertEquals("1", queue.poll());
        assertEquals("2", queue.poll());
        assertEquals("3", queue.poll());
        assertEquals("4", queue.poll());
        assertNull(queue.poll());
        db.close();
    }
}
