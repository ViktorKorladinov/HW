/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myhistory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author 2016-e-korladinov
 */
public class MyHistoryTest {

    public MyHistoryTest() {

    }

    @Test
    public void addLine() throws Exception {
        File f = File.createTempFile("testData", ".txt");
        MyHistory h = new MyHistory(f.getAbsolutePath());
        h.addLine("Line 1");
        h.addLine("Line 2");
        h.save();

        assertEquals("Line 1\nLine 2", h.toString());
    }

    @Test
    public void dupl() throws Exception {
        File f = File.createTempFile("testData", ".txt");
        MyHistory h = new MyHistory(f.getAbsolutePath());
        h.addLine("Line 1");
        h.addLine("Line 2");
        h.addLine("Line 1");

        assertEquals("Line 1\nLine 2", h.toString());
    }

    @Test
    public void duplRead() throws Exception {
        File f = File.createTempFile("testData", ".txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("Ahoj\nAhoj\nAhoj");
        bw.close();

        MyHistory h = new MyHistory(f.getAbsolutePath());
        h.read();

        assertEquals("Ahoj", h.toString());
    }

    @Test(expected = IOException.class)
    public void noFile() throws Exception {
        MyHistory h = new MyHistory("C:\\");
        h.read();
    }

    @Test
    public void save() throws Exception {
        Path p = Files.createTempFile(null, null);

        MyHistory h = new MyHistory(p.toString());
        h.read();
        h.addLine("HURA");
        h.save();

        List<String> content = Files.readAllLines(p);
        String all = String.join("\n", content);

        assertEquals(h.toString(), all);
    }

    @Test
    public void saveDupl() throws Exception {
        File f = File.createTempFile("testData", ".txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("Ahoj\nAhoj\nAhoj");
        bw.close();
        MyHistory h = new MyHistory(f.getAbsolutePath());
        h.read();
        h.save();

        List<String> content = Files.readAllLines(Paths.get(f.getAbsolutePath()));
        String noDuplicates = String.join("\n", content);

        assertEquals(h.toString(), noDuplicates);
    }
}