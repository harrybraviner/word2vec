package braviner.word2vec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class CorpusParser {

    private HashMap<String, Integer> wordToIndex = new HashMap<>();
    private HashMap<Integer, String> indexToWord = new HashMap<>();
    private ArrayList<int[]> sentences = new ArrayList<>();
    private int numDistinctWords = 0;

    private CorpusParser() {

    }

    public void getSentenceAsIndices(int index) {

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
     * Normalizes the strings of the array in-place.
     * @param words An array of un-normalized words.
     */
    private void normalizeStrings(String[] words) {
        for (int i=0; i<words.length; i++) {
            words[i] = normalize(words[i]);
        }
    }

    private void addSentenceToCorpus(String sentence) {
        String[] tokenizedSentence = tokenizeSentence(sentence);
        normalizeStrings(tokenizedSentence);

        // Convert the sentence to a representation as indices for compressed storage.
        int[] sentenceAsTokenIndices = new int[tokenizedSentence.length];
        for (int i=0; i<tokenizedSentence.length; i++) {
            String word = tokenizedSentence[i];
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
