package ua.tqs.ReCollect.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ItemTypes {

    public static String readFile() throws IOException {
        String fileName = "cdpt.json";
        Resource resource = new ClassPathResource("classpath:" + fileName);
        File file = resource.getFile();
        String fileContent=new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

        if (fileContent!= null){
            JSONObject jo= new JSONObject(fileContent);
            String x = jo.toString();
            return x;
        }
        return "lol";


    }
    
}