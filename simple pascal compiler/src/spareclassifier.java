public class spareclassifier {

	static final String id = "\\s*\\w+\\s*";
	static final String factor = "((" + id + "[+]" + id + ")|(" + id + "[x]" + id + ")|(" + id + "))";
	static final String exp = "((" + factor + "[+]" + factor + ")|(" + factor + "[x]" + factor + "))";
	static final String assign = id + "(:=)" + exp;
	static final String read = "(READ)" + id + "([,]" + id + ")*";
	static final String write = "(WRITE)" + id + "([,]" + id + ")*";

	static String indexExp = id + "(:=)\\s*" + exp + "(TO)\\s*" + exp;
	static String For = "\\s*(FOR)" + indexExp + "(DO)";

	static final String progHeader = "\\s*PROGRAM" + id;
	static final String var = "\\s*(VAR)" + id + "([,]" + id + ")*";
	static final String begin = "\\s*(BEGIN)\\s*";
	static final String end = "\\s*(END)\\s*";
	static String stmt = "((" + assign + ")?)|" + "((" + read + ")?)|" + "((" + write + ")?)|" + "((" + For + ")?)|";

	static final int PROG = 0;
	static final int VAR = 1;
	static final int BEGIN = 2;
	static final int END = 3;
	static final int READ = 4;
	static final int WRITE = 5;
	static final int ASSIGN = 6;
	static final int FOR = 7;
	static final int INVALID = -1;

	static int regex(String input) {
		if (input.matches(Classifier.progHeader)) {
			return PROG;
		} else if (input.matches(Classifier.var)) {
			return VAR;
		} else if (input.matches(Classifier.begin)) {
			return BEGIN;
		} else if (input.matches(Classifier.end)) {
			return END;
		} else if (input.matches(Classifier.read)) {
			return READ;
		} else if (input.matches(Classifier.write)) {
			return WRITE;
		} else if (input.matches(Classifier.assign)) {
			return ASSIGN;
		} else if (input.matches(Classifier.For)) {
			return FOR;
		} else {
			return INVALID;
		}
	}
}