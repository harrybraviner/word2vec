package braviner.word2vec;

import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Optional;

public class CorpusParserTests {

    @Test
    public void ParseSingleSentence() {

        String[] oneSentence = new String[] {"The quick brown fox jumped over the lazy fox."};

        CorpusParser parser = CorpusParser.buildFromSentenceStream(Arrays.stream(oneSentence));

        Assert.assertEquals(7, parser.getWordCount());
        Assert.assertEquals("the", parser.getWordFromIndex(0));
        Assert.assertEquals("fox", parser.getWordFromIndex(3));
        Assert.assertEquals(1, parser.getIndexFromWord("quick"));
    }

    @Test
    public void Normalizer() {
        Assert.assertEquals(Optional.empty(), Normalizer.normalize("-"));
        Assert.assertEquals(Optional.of("foobar"), Normalizer.normalize("foo-bar"));
        Assert.assertEquals(Optional.of("wont"), Normalizer.normalize("won't"));
        Assert.assertEquals(Optional.of("apple"), Normalizer.normalize("ApPle"));
        Assert.assertEquals(Optional.of("word2vec"), Normalizer.normalize("word2Vec"));
        Assert.assertEquals(Optional.of("[NUM]"), Normalizer.normalize("123"));
    }

    @Test
    public void ParserMultipleSentences() {
        // These strings include some punctuation that we wish to be stripped out
        String[] multipleSentences = new String[] {
                "Foo bar baz - the future fox",
                "The quick brown fox jumped over the lazy fox.",
                "Never give up; never surrender!"
        };

        CorpusParser parser = CorpusParser.buildFromSentenceStream(Arrays.stream(multipleSentences));

        Assert.assertEquals(15, parser.getWordCount());
        Assert.assertEquals("the", parser.getWordFromIndex(3));
        Assert.assertEquals("surrender", parser.getWordFromIndex(14));
        Assert.assertEquals(2, parser.getIndexFromWord("baz"));
    }
}
