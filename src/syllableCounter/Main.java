package syllableCounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import stopwatch.Stopwatch;

/**
 * Start the program.
 * @author Supisara Chuthathumpitak
 *
 */
public class Main {

	/**
	 * Start the program.
	 * @param args
	 */
	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		int syllable = 0, wordCount = 0;
		final String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
		String word;
		try {
			stopwatch.start();
			URL url = new URL( DICT_URL );
			InputStream input = url.openStream();
			BufferedReader reader = new BufferedReader( new InputStreamReader(input));
			while( (word = reader.readLine()) != null ){
				wordCount++;
				WordCounter wordCounter = new WordCounter();
				syllable += wordCounter.countSyllables(word);
			}
			stopwatch.stop();
		}catch(IOException e){
			System.out.println("Exception");
		}
		System.out.printf("Reading words from %s\n", DICT_URL);
		System.out.printf("Counted %,d syllables in %,d words\n", syllable, wordCount);
		System.out.printf("Elapsed time: %f sec", stopwatch.getElapsed()); 
	}
}
