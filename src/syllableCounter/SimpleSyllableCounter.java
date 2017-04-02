package syllableCounter;

/**
 * Count the syllables but don't look ahead for the next char or look back.
 * @author Supisara Chuthathumpitak
 *
 */
public class SimpleSyllableCounter {
	private int syllables = 0;
	private State state;
	
	/**
	 * Count syllables in a String.
	 * @param word is the word that we want to count syllables.
	 * @return the number of syllables in a word.
	 */
	public int countSyllables(String word){
		syllables = 0;
		char c = ' ';
		state = State.START;
		for(int k = 0 ; k < word.length() ; k++){
			c = word.charAt(k);
			if(c == '\'' || Character.isWhitespace(c)) continue; //ignore apostrophe
			switch (state) {
			case START:
				if(isVowelOrY(c)){
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (isLetterNotVowel(c)){
					state = State.CONSONANT;
				} else {
					state = State.NONWORD;
				}
				break;
			case CONSONANT:
				c = Character.toLowerCase(c);
				if(k+1 == word.length() && c == 'e' && syllables != 0){
					state = State.CONSONANT;
				} else if (isLetterNotVowel(c)){
					/* stay in consonant state */
				} else if (isHyphen(c)){
					state = State.HYPHEN;
				} else if (isVowelOrY(c)){
					state = State.SINGLE_VOWEL;
					syllables++;
				}
				else {
					state = State.NONWORD;
				}
				break;
			case SINGLE_VOWEL:
				if(isVowel(c)) 
					state = State.MULTIVOWEL;
				else if(isHyphen(c))
					state = State.HYPHEN;
				else if(Character.isLetter(c))
					state = State.CONSONANT;
				else 
					state = State.NONWORD;
				break;
			case MULTIVOWEL:
				if (isVowelOrY(c)){
					/* stay in multivowel state */
				} else if (isLetterNotVowel(c)){
					state = State.CONSONANT;
				} else if (isHyphen(c)){
					state = State.HYPHEN;
				} else{
					state = State.NONWORD;
				}
				break;
			case HYPHEN:
				if(isVowelOrY(c)){
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (isLetterNotVowel(c)){
					state = State.CONSONANT;
				} else {
					state = State.NONWORD;
				}
				break;
			default:
				state = State.NONWORD;
				break;
			}
		}
		return syllables;
	}

	/**
	 * That alphabet is letter or not.
	 * @param c is the alphabet that want to check.
	 * @return is it letter or not.
	 */
	private boolean isLetterNotVowel(char c) {
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
		else if ( c == 'y' && state != State.MULTIVOWEL ){ 
			state = State.CONSONANT;
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
	enum State {
		START,
		CONSONANT,
		NONWORD,
		SINGLE_VOWEL,
		MULTIVOWEL,
		HYPHEN;
	}
}
