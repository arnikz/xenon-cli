package nl.esciencecenter.xenon.cli;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

public class ListFilesOutputTest {
    private ListFilesOutput listFilesOutput;

    @Before
    public void setUp() throws Exception {
        listFilesOutput = new ListFilesOutput();
        listFilesOutput.addDirectory("dir1");
        listFilesOutput.addFile("file1");
    }

    @Test
    public void test_toString() {
        String result = listFilesOutput.toString();
        String expected = "dir1\nfile1\n";
        assertEquals(expected, result);
    }

    @Test
    public void toJson() {
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson(listFilesOutput);
        String expected = "{\"objects\":[\"dir1\",\"file1\"],\"files\":[\"file1\"],\"directories\":[\"dir1\"]}";
        assertEquals(expected, result);
    }
}