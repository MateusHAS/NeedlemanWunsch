package needleman.wunsch.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileLoader {

    
    public String getSequence(String filePath) throws IOException {
    	
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(filePath).getFile());
         
        //File is found
        System.out.println("File Found : " + file.getName());
         
        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        return content;
    }

}
