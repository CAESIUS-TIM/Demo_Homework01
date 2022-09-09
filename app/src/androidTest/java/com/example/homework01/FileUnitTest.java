package com.example.homework01;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileUnitTest {
    @Test
    public void delete() throws IOException {
        File file = new File("test.test");

        System.out.println(file.createNewFile());
        System.out.println(file.delete());
        System.out.println(file.delete());
    }
}
