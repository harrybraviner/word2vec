package braviner.word2vec;

import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;

public class CorpusParserTests {

    @Test
    public void ParseSingleSentence() {

        String[] oneSentence = new String[] {"The quick brown fox jumped over the lazy fox."};

        CorpusParser parser = CorpusParser.buildFromSentenceStream(Arrays.stream(oneSentence));

        Assert.assertEquals(8, parser.getWordCount());
        Assert.assertEquals("the", parser.getWordFromIndex(0));
        Assert.assertEquals("fox", parser.getWordFromIndex(3));
        Assert.assertEquals(1, parser.getIndexFromWord("quick"));
    }
}
