package ua.tqs.ReCollect;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.service.LocationService;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private LocationService locationService;

    static final Logger logger = Logger.getLogger(DataLoader.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (locationService.getAll().size() != 308) {
            locationService.deleteAll();
            String fileName = "cdpt.json";
            File resource = new ClassPathResource(fileName).getFile();
            String cdpt = new String(Files.readAllBytes(resource.toPath()));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, ArrayList<String>> map = mapper.readValue(cdpt, Map.class);

            Iterator<String> keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String district = keys.next();
                ArrayList<String> counties = map.get(district);
                for (String county : counties) {
                    locationService.save(new Location(county, district));
                }
            }

            logger.info("Finished loading " + locationService.getAll().size() + " locations");
        }
        else{
            logger.debug("up and running");
        }

    }
}