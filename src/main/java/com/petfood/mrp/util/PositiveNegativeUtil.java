
package com.petfood.mrp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petfood.mrp.model.SolrRecord;

public final class PositiveNegativeUtil {

    private static final Logger log = LoggerFactory.getLogger(PositiveNegativeUtil.class);
    private static final List<WordScore> wordScores;
    private static final Charset UTF8 = Charset.forName("utf-8");
    
    static {
        wordScores = Collections.unmodifiableList(loadWordScore());
        log.info("positive negative word list: {}", wordScores);
    }
    
    
    private PositiveNegativeUtil() {}
    
    public static void setPositiveNegative(final String topicText, final String text, final SolrRecord record) {
        
        String ptext = text;
        if(topicText != null) {
            ptext = topicText + " " + text;
        }
        final int totalLength = ptext.getBytes(UTF8).length;
        double positive = 0;
        double negative = 0;
        TextScore ts;
        double score;
        final List<String> posNegInfo = new ArrayList<>();
        for(final WordScore ws : wordScores) {
            ts = countScore(ptext, ws, totalLength);
            
            score = ts.getScore();
            if(score < 0) {
                negative += score;
            }
            else if (score > 0) {
                positive += score;
            }
            
            if(ts.getCount() > 0) {
                posNegInfo.add(ws.getText() + ":" + ts.getCount() + ":" + score);
            }
            
            ptext = ts.getText();
            
        }
        
        record.setPositive(positive);
        record.setNegative(negative == 0 ? 0 : negative * -1);
        if(!posNegInfo.isEmpty()) {
            record.setPosNegInfo(posNegInfo);
        }
    }
    
    private static TextScore countScore(final String text, final WordScore ws, final int totalLength) {
        
        final String word = ws.getText();
        
        final int count = StringUtils.countMatches(text, word);
        final double score;
        final String finalText;
        if(count == 0) {
            score = 0;
            finalText = text;
        }
        else {
            if(totalLength == 0) {
                score = 0;
            }
            else {
                score = calculateScore(ws, count, totalLength);
            }
            finalText = StringUtils.replace(text, word, " ");
        }
        
        return new TextScore(finalText, score, count);
    }
    
    private static double calculateScore(WordScore wordScore, int count, int totalTextLength) {
        return ((double) (count * wordScore.getScore() * wordScore.getText().getBytes(UTF8).length) / (double) totalTextLength);
    }
    
    private static List<WordScore> loadWordScore() {
        
        final List<String> opPrefix = new ArrayList<>();
        String line;
        
        try(final InputStream is = PositiveNegativeUtil.class.getResourceAsStream("/oppositePrefix.txt");
            final InputStreamReader isr = new InputStreamReader(is, UTF8);
            final BufferedReader in = new BufferedReader(isr)) {
            
            while((line = in.readLine()) != null) {
                
                if(!line.trim().isEmpty()) {
                    opPrefix.add(line.trim());
                }
            }
        }
        catch (Exception e) {
            log.warn("error parsing/loading oppositePrefix.txt", e);
        }
        
        final List<WordScore> wss = new ArrayList<>();
        int lcnt = 1;
        
        try(final InputStream is = PositiveNegativeUtil.class.getResourceAsStream("/positiveNegative.txt");
            final InputStreamReader isr = new InputStreamReader(is, UTF8);
            final BufferedReader in = new BufferedReader(isr)) {
            
            
            String[] wa;
            WordScore ws;
//            int score;
            
            while((line = in.readLine()) != null) {
                
                line = line.trim();
                if(!line.equals("")) {
                    try {
                        wa = StringUtils.split(line, ',');
                        ws = new WordScore(wa[0].trim(), Integer.parseInt(wa[1].trim()));
                        addOppositePhrase(ws, opPrefix, wss);
                        wss.add(ws);
                    }
                    catch (Exception e) {
                        log.warn("parsing error at line " + lcnt, e);
                    }
                    
                }
                else {
                    log.info("line {} is empty, ignored", lcnt);
                }
                ++lcnt;
            }
            
        }
        catch (Exception e) {
            log.warn("error parsing/loading positiveNegative.txt", e);
        }
        
        log.info("parsed {} lines, word list contains {} words", lcnt - 1, wss.size());
        
        Collections.sort(wss, new Comparator<WordScore>() {

            @Override
            public int compare(WordScore o1, WordScore o2) {
                
                return o2.getText().length() - o1.getText().length();
            }
            
        });
        return wss;
    }
    
    private static void addOppositePhrase(WordScore ws, List<String> opPrefix, List<WordScore> wss) {
       
        final double score = -ws.getScore();
        
        for(String op : opPrefix) {
            wss.add(new WordScore(op + ws.getText(), score));
        }
        
    }

    public static class WordScore {

        private final String text;
        private final double score;
        
        public WordScore(String text, double score) {
            this.text = text;
            this.score = score;
        }
        
        public String getText() {
            return text;
        }
        
        
        public double getScore() {
            return score;
        }
        
        @Override
        public String toString() {
            return text + "," + score;
        }
       
    }
    
    public static class TextScore extends WordScore {
        
        private final int count;
        
        public TextScore(String text, double score, int count) {
            
            super(text, score);
            this.count = count;
        }
        
        public int getCount() {
            return count;
        }
        
    }
    
    
    
    public static void main(String... s) {
        setPositiveNegative("1234", "abc123", new SolrRecord());
    }
}
