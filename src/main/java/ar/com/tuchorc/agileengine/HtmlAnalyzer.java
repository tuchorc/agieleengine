package ar.com.tuchorc.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class HtmlAnalyzer {

	private static String CHARSET_NAME = "utf8";
	private static final Logger log = LoggerFactory.getLogger(HtmlAnalyzer.class);

	public Optional<Element> analyze(String pathToDocument, String elementId, String source) throws IOException {
		Document doc;
		if (source.equalsIgnoreCase("WEB")) {
			doc = Jsoup.connect(pathToDocument).get();
		} else {
			File htmlFile = new File(pathToDocument);
			doc = Jsoup.parse(
					htmlFile,
					CHARSET_NAME,
					htmlFile.getAbsolutePath());
		}
		Optional<Element> result = Optional.ofNullable(null);
		try {
			result = Optional.of(doc.getElementById(elementId));
		} catch (Exception e) {
		}

		if (! result.isPresent())
			result = searchDocumentForBestMatch(doc, elementId);

		return result;
	}

	private Optional<Element> searchDocumentForBestMatch(Document doc, final String elementId) {
		return doc.body().select("*").parallelStream()
				.map(e -> new ScoredElement(e, HtmlAnalyzer.score(e, elementId)))
				.max((o1, o2) -> o1.getScore().compareTo(o2.getScore()))
				.filter(se -> se.getScore().compareTo(0.0D) > 0)
				.map(se -> se.getElement());
	}

	private static Double score(Element e, String targetId) {
		final List<String> words = Arrays.asList(targetId.split("-"));
		final int totalWords = words.size();
		return e.attributes().asList().stream().mapToDouble(a -> {
			String value = a.getValue();
			int foundWords = (int) words.stream().filter(value::contains).count();
			if (foundWords > 0) {
				Double partial = foundWords * 100.0 / totalWords;
				int distance = LevenshteinDistance.computeLevenshteinDistance(value, targetId);
				return partial / (distance + 1);
			}
			return 0;
		}).sum();
	}

	public static String getElementPath(Optional<Element> element) {
		return element.map(e -> {
			StringBuilder sb = new StringBuilder();
			sb.append(e.toString());
			getParent(sb, e.parent());
			return sb.toString();
		}).orElse("No data found");
	}

	private static void getParent(StringBuilder sb, Element element) {
		sb.append(" > ");
		sb.append(element.nodeName());
		if (element.hasParent())
			getParent(sb, element.parent());
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
