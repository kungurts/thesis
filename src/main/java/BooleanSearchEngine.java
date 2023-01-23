import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    Map<PageEntry, String> wordsEntry = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
//        wordsEntry = new HashMap<>();
        for (File pdf : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(pdf));
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(i+1));
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }
                for (Map.Entry<String, Integer> wq : freqs.entrySet()) {
                    PageEntry pageEntry = new PageEntry(pdf.getName(), i+1, wq.getValue());
                    wordsEntry.put(pageEntry, wq.getKey());
                }
            }
        }
    }

    public Map<PageEntry, String> getWordsEntry() {
        return wordsEntry;
    }

    @Override
    public List<PageEntry> search(String word){
        List<PageEntry> result = new ArrayList<>();
        for (Map.Entry<PageEntry, String> wq : wordsEntry.entrySet()) {
            if (wq.getValue().equals(word)) {
                result.add(wq.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }
}
