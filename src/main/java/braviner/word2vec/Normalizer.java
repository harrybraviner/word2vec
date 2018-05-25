package braviner.word2vec;

import java.util.Optional;
import java.util.regex.Pattern;

public class Normalizer {

    private static Pattern numberRegex = Pattern.compile("\\d+");

    /**
     * Normalizes the string.
     *
     * @param word Input word, unnormalized.
     * @return An optional containing the normalized word. If the normalization
     *         is to discard this string no value will be present.
     */
    public static Optional<String> normalize (String word) {
        // Strip out any non-alphanumeric characters
        word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        if (word.length() == 0) {
            return Optional.empty();
        }

        if (numberRegex.matcher(word).matches()) {
            // word is simply a number, return a special number token
            return Optional.of("[NUM]");
        }

        return Optional.of(word);
    }
}
