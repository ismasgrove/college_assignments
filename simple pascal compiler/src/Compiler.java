import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compiler {
	public static void main(String args[]) throws IOException {
		LexScanner lexScanner = new LexScanner();
		Classifier classifier = new Classifier();
		FileInputStream fstream = new FileInputStream("source.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		lexScanner.setLine(br.readLine());
		lexScanner.tokenize();

	}
}
