package ar.com.tuchorc.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class HtmlAnalyzer {

	private static String CHARSET_NAME = "utf8";
	private static final Logger log = LoggerFactory.getLogger(HtmlAnalyzer.class);

	public String analyze(String pathToDocument, String elementId, String source) {
		Document doc;
		try {
			if (source.equalsIgnoreCase("WEB")) {
				doc = Jsoup.connect(pathToDocument).get();
			} else {
				File htmlFile = new File(pathToDocument);
				doc = Jsoup.parse(
						htmlFile,
						CHARSET_NAME,
						htmlFile.getAbsolutePath());
			}

		} catch (Exception e) {
			return "Unexpected Exception";
		}

		try {
			Optional<Element> result = Optional.of(doc.getElementById(elementId));
			if (result.isPresent()) {
				return HtmlAnalyzer.getElementPath(result.get());
			}
		} catch (NullPointerException ex) {
			// element not found bu id
		}

		return searchDocumentForBestMatch(doc, elementId);
	}

	private String searchDocumentForBestMatch(Document doc, final String elementId) {
		AtomicReference<ScoredElement> result = new AtomicReference<>();
		doc.body().select("*").stream()
				.map(e -> new ScoredElement(e, HtmlAnalyzer.score(e, elementId)))
				.max((o1, o2) -> o1.getScore().compareTo(o2.getScore()))
				.ifPresent(e -> result.set(e));

		if (result.get() == null || result.get().getScore().equals(0.0D)) {
			return "No results found";
		}

		return getElementPath(result.get().getElement());
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

	private static String getElementPath(Element element) {
		StringBuilder sb = new StringBuilder(element.toString());
		getParent(sb, element.parent());
		return sb.toString();
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
