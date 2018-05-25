package braviner.word2vec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

public class CorpusParser {

    private HashMap<String, Integer> wordToIndex = new HashMap<>();
    private HashMap<Integer, String> indexToWord = new HashMap<>();
    private ArrayList<int[]> sentences = new ArrayList<>();
    private int numDistinctWords = 0;

    private CorpusParser() {

    }

    public int[] getSentenceAsIndices(int index) {
        return sentences.get(index);
    }

    public int getWordCount() {
        return numDistinctWords;
    }

    public String getWordFromIndex(int index) {
        return indexToWord.get(index);
    }

    public int getIndexFromWord(String word) {
        return wordToIndex.get(word);
    }

    private String[] tokenizeSentence(String sentence) {
        return sentence.split(" ");
    }

    /**
     * Returns the normalized version of the string.
     *
     * @param word Un-normalized word.
     * @return Normalized word.
     */
    private String normalize(String word) {
        return word.toLowerCase();
    }

    /**
     * Normalizes the strings of the Stream.
     * @param words A Stream of unnormalized words.
     * @return A Stream, possibly of fewer elements.
     */
    private Stream<String> normalizeStrings(Stream<String> words) {
        return words.map(Normalizer::normalize).filter(Optional::isPresent).map(Optional::get);
    }

    private void addSentenceToCorpus(String sentence) {
        String[] tokenizedSentence = tokenizeSentence(sentence);
        String[] normalizedSentence = normalizeStrings(Arrays.stream(tokenizedSentence)).toArray(String[]::new);

        // Convert the sentence to a representation as indices for compressed storage.
        int[] sentenceAsTokenIndices = new int[normalizedSentence.length];
        for (int i=0; i<normalizedSentence.length; i++) {
            String word = normalizedSentence[i];
            int tokenIndex;
            if (!wordToIndex.containsKey(word)) {
                wordToIndex.put(word, numDistinctWords);
                indexToWord.put(numDistinctWords, word);
                tokenIndex = numDistinctWords;
                numDistinctWords++;
            } else {
                tokenIndex = wordToIndex.get(word);
            }
            sentenceAsTokenIndices[i] = tokenIndex;
        }

        sentences.add(sentenceAsTokenIndices);
    }

    public static CorpusParser buildFromSentenceStream(Stream<String> sentenceStream) {
        CorpusParser parser = new CorpusParser();
        sentenceStream.forEach(parser::addSentenceToCorpus);
        return parser;
    }
}
