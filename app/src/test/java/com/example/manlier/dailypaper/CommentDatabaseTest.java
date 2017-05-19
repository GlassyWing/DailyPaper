package com.example.manlier.dailypaper;

import com.example.manlier.dailypaper.modules.comments.model.CommentDbHelper;
import com.example.manlier.dailypaper.modules.main.widget.MainActivity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by manlier on 2017/5/16.
 */

public class CommentDatabaseTest {

    @Test
    public void testInsert() {
        CommentDbHelper helper = new CommentDbHelper(MainActivity.getContext());
        boolean in = helper.insertData("manlier", "不错", "2017/5/16", "T01");
        Assert.assertTrue(in);
    }
}
