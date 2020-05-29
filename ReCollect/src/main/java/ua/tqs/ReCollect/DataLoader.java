package ua.tqs.ReCollect;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import ua.tqs.ReCollect.model.Location;
import ua.tqs.ReCollect.service.LocationService;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private LocationService locationService;

    static final Logger logger = Logger.getLogger(DataLoader.class);

    @Autowired
    private ResourceLoader rl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (locationService.getAll().size() != 308) {
            locationService.deleteAll();
            String fileName = "cdpt.json";
            InputStream inputStream = rl.getResource("classpath:static/"+fileName).getInputStream();
            String cdpt = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
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