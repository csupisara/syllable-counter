package syllableCounter;

/**
 * 
 * @author Supisara Chuthathumpitak
 *
 */
public class WordCounter {
	private final State START = new StartState();
	private final State SINGLEVOWEL = new SingleVowelState();
	private final State MULTIVOWEL = new MultiVowelState();
	private final State NONWORD = new NonWordState();
	private final State CONSONANT = new ConsonantState();
	private final State HYPHEN = new HyphenState();
	private State state; // the current state
	private int syllableCount = 0;
	private int index = 0;
	private String word = "";
	
	/**
	 * Change to a new state.
	 * @param newstate is the state that want to chage to.
	 */
	public void setState( State newstate ){
		state = newstate;
	}

	/**
	 * Count syllables in a String.
	 * @param word is the word that we want to count syllables.
	 * @return the number of syllables in a word.
	 */
	public int countSyllables ( String word ) {
		syllableCount = 0;
		char c = ' ';
		this.word = word;
		setState(START);
		for(int k = 0 ; k < word.length() ; k++){
			index = k;
			c = word.charAt(k);
			if(c == '\'' || Character.isWhitespace(c)) continue; //ignore apostrophe
			state.handleChar(c);
		}
		return syllableCount;
	}

	/**
	 * That alphabet is letter or not.
	 * @param c is the alphabet that want to check.
	 * @return is it letter or not.
	 */
	private boolean isLetterNotVowel( char c ) {
		if (Character.isLetter(c) && !isVowelOrY(c)){
			return true;
		}
		return false;
	}

	/**
	 * That alphabet is vowel or not (Include 'y').
	 * @param c is the alphabet that want to check.
	 * @return is it vowel or not. (Include 'y')
	 */
	private boolean isVowelOrY(char c) {
		c = Character.toLowerCase(c);
		if ( c =='a' || c =='e' || c == 'i' || c == 'o' || c == 'u' ){ return true; }
		else if ( c == 'y' && state != MULTIVOWEL ){ 
			setState(CONSONANT);
			return true;
		}
		return false;
	}

	/**
	 * That alphabet is vowel or not.
	 * @param c is the alphabet that want to check.
	 * @return is it vowel or not.
	 */
	private boolean isVowel(char c){
		c = Character.toLowerCase(c);
		if ( c =='a' || c =='e' || c == 'i' || c == 'o' || c == 'u' ){ return true; }
		return false;
	}

	/**
	 * Check whether that char is hyphen or not.
	 * @param c is the alphabet that want to check.
	 * @return hyphen or not
	 */
	private boolean isHyphen(char c) {
		if (c == '-') return true;
		return false;
	}

	/**
	 * The state that are possible for count syllables in a String.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	abstract class State {
		public abstract void handleChar(char c);
		public void enterState() { }
	}

	/**
	 * Start of all state.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	class StartState extends State {

		/*
		 * (non-Javadoc)
		 * @see syllableCounter.WordCounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if(isVowelOrY(c)){
				setState(SINGLEVOWEL);
				enterState();
			} else if (isLetterNotVowel(c)){
				setState(CONSONANT);
			} else {
				setState(NONWORD);
			}
		}

		/**
		 * The count the syllable.
		 */
		public void enterState() { 
			syllableCount++;
		}
	}

	/**
	 * Find the vowel.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	class SingleVowelState extends State {
		
		/*
		 * (non-Javadoc)
		 * @see syllableCounter.WordCounter.State#handleChar(char)
		 */
		@Override
		public void handleChar( char c){
			if(isVowel(c)) 
				setState(MULTIVOWEL);
			else if(isHyphen(c))
				setState(HYPHEN);
			else if(Character.isLetter(c))
				setState(CONSONANT);
			else 
				setState(NONWORD);
		}
	}

	/**
	 * Find the multi-vowel.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	class MultiVowelState extends State {
		
		/*
		 * (non-Javadoc)
		 * @see syllableCounter.WordCounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)){
				/* stay in multivowel state */
			} else if (isLetterNotVowel(c)){
				setState(CONSONANT);
			} else if (isHyphen(c)){
				setState(HYPHEN);
			} else{
				setState(NONWORD);
			}
		}
	}

	/**
	 * Find the consonant.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	class ConsonantState extends State {
		
		/*
		 * (non-Javadoc)
		 * @see syllableCounter.WordCounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {		
			c = Character.toLowerCase(c);
			if(index+1 == word.length() && c == 'e' && syllableCount != 0){
				setState(CONSONANT);
			} else if (isLetterNotVowel(c)){
				/* stay in consonant state */
			} else if (isHyphen(c)){
				setState(HYPHEN);
			} else if (isVowelOrY(c)){
				setState(SINGLEVOWEL);
				enterState();
			}
			else {
				setState(NONWORD);
			}
		}

		/**
		 * The count the syllable.
		 */
		public void enterState(){
			syllableCount++;
		}
	}

	/**
	 * Find the hyphen.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	class HyphenState extends State {
		
		/*
		 * (non-Javadoc)
		 * @see syllableCounter.WordCounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if(isVowelOrY(c)){
				setState(SINGLEVOWEL);
				enterState();
			} else if (isLetterNotVowel(c)){
				setState(CONSONANT);
			} else {
				setState(NONWORD);
			}
		}
		
		/**
		 * The count the syllable.
		 */
		public void enterState(){
			syllableCount++;
		}
	}

	/**
	 * Find that it contains non-letter, so it is non-word.
	 * @author Supisara Chuthathumpitak
	 *
	 */
	class NonWordState extends State {
		
		/*
		 * (non-Javadoc)
		 * @see syllableCounter.WordCounter.State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			setState(NONWORD);
		}
	}
}
