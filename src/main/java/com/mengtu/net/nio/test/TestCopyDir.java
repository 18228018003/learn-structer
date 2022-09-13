package com.mengtu.net.nio.test;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestCopyDir {
    @Test
    public void testCopy() throws IOException {
        String source = "C:\\OpenLDAP";
        String target = "C:\\OpenLDAP-aaa";
        Files.walk(Paths.get(source)).forEach(path -> {
            try{
                String targetName = path.toString().replace(source,target);
                //是目录
                if (Files.isDirectory(path)){
                    Files.createDirectory(Paths.get(targetName));
                }
                //是文件
                else if (Files.isRegularFile(path)){
                    Files.copy(path,Paths.get(targetName));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
