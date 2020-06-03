package ua.tqs.ReCollect;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ReCollectApplication {

	static final Logger logger = Logger.getLogger(ReCollectApplication.class);

	public static void main(String[] args) {
		if (args.length>1 || !args[0].startsWith("--load=")){
			logger.error("Invalid arguments, aborting");
			return;
		}

		SpringApplication.run(ReCollectApplication.class, args);
	}

}
