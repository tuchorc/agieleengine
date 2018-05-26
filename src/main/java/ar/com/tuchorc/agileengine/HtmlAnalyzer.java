package ar.com.tuchorc.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class HtmlAnalyzer {

	private static String CHARSET_NAME = "utf8";
	private static final Logger log = LoggerFactory.getLogger(HtmlAnalyzer.class);

	public String analyze(String pathToDocument, String elementId) {
		File htmlFile = new File(pathToDocument);
		Document doc;
		try {
			doc = Jsoup.parse(
					htmlFile,
					CHARSET_NAME,
					htmlFile.getAbsolutePath());
		} catch (IOException e) {
			log.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
			return "Unexpected Exception";
		}

		try {
			Optional<Element> result = Optional.of(doc.getElementById(elementId));
			if (result.isPresent()) {
				Element target = result.get();
				return HtmlAnalyzer.getElementPath(target);
			}
		} catch (NullPointerException ex) {
			// element not found bu id
		}

		return searchDocumentForBestMatch(doc, elementId);
	}

	private String searchDocumentForBestMatch(Document doc, String elementId) {
		Elements elements = doc.body().select("*");
		List<ScoredElement> scoringList = new ArrayList<>(elements.size());

		for (Element e : elements) {
			scoringList.add(new ScoredElement(e, HtmlAnalyzer.score(e, elementId)));
		}
		scoringList.sort((e1, e2) -> e2.getScore().compareTo(e1.getScore()));

		ScoredElement result = scoringList.get(0);
		if (result.getScore() == 0) {
			return "No results found";
		}

		return getElementPath(result.getElement());
	}

	private static Double score(Element e, String targetId) {
		String[] words = targetId.split("-");
		int totalWords = words.length;
		Double score = 0.0;
		for (Attribute a : e.attributes()) {
			String value = a.getValue();
			int foundWords = 0;
			for (String word : words) {
				if (value.contains(word)){
					foundWords++;
				}
			}
			if (foundWords > 0) {
				Double partial = foundWords * 100.0 / totalWords;
				int distance = LevenshteinDistance.computeLevenshteinDistance(value, targetId);
				score += partial / distance;
			}
		}
		return score;
	}

	private static String getElementPath(Element element) {
		String path = element.toString();

		while (element.hasParent()) {
			element = element.parent();
			path += " > " + element.nodeName();
		}

		return path;
	}

	class ScoredElement {

		private Element element;
		private Double score;

		public ScoredElement(Element element, Double score) {
			this.element = element;
			this.score = score;
		}

		public Element getElement() {
			return element;
		}

		public Double getScore() {
			return score;
		}

	}

}
