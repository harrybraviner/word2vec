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

}
