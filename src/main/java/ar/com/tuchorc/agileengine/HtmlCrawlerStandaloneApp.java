package ar.com.tuchorc.agileengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HtmlCrawlerStandaloneApp implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(HtmlCrawlerStandaloneApp.class);

	@Autowired
	@Qualifier("htmlAnalyzer")
	HtmlAnalyzer helper;

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(HtmlCrawlerStandaloneApp.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	//access command line arguments
	@Override
	public void run(String... args) throws Exception {
		String document = (args.length > 0) ? args[0] : "samples/sample0/sample0.html";
		String elementID = (args.length > 1) ? (args[1]) : "make-everything-ok-button";
		String source = (args.length > 2) ? (args[2]) : "LOCAL";

		try {
			log.info("Analyzing file: " + document);
			log.info("Looking for element ID: " + elementID);

			String result = helper.analyze(document, elementID, source);

			log.info("Result element path: " + result);
		} catch (Exception e) {
			log.error("XXXXXXXXXXXXXX == Unexpected exception == XXXXXXXXXXXXXX ");
			log.error(e.getLocalizedMessage());
		}
	}
}