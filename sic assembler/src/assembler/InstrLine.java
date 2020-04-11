package assembler;

import java.util.StringTokenizer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.io.BufferedWriter;

public class InstrLine {
	private String instruction;
	public HashMap<String, String> map;
	private StringTokenizer t;
	private int counter=0;
	private BufferedWriter bw;
	private int x;
	private boolean comment;
	
	public InstrLine(String instruction){
        map = new HashMap<>();
        this.instruction =instruction;
        this.Defactor();
        }
	public void changeinst(String instruction){
        this.instruction = instruction;
        this.Defactor();
        }
	public void Defactor () {
        t = new StringTokenizer(instruction);
        map = new HashMap<>();
        x = t.countTokens();

		if (x>=3){
			map.put("column1", t.nextToken());
			map.put("column2", t.nextToken());
			map.put("column3", t.nextToken());
		} else if (x==2) {
			map.put("column1", "#");
			map.put("column2", t.nextToken());
			map.put("column3", t.nextToken());
		} else if (x==1 && t.nextToken().startsWith(".")) {
			comment = true;
			map.put("column1", "#");
			map.replace("column2", t.nextToken());
			map.put("column3", "#");
		} else if (x==1) {
			map.put("column1", "#");
			map.replace("column2", t.nextToken());
			map.put("column3", "#");
		}
}

	public boolean writeToIntermediate(File f) throws IOException {
		counter+=5;
		if (!f.exists()) return false;
		if (bw!=null)
			bw = new BufferedWriter(new FileWriter(f));
		if (comment) {
			bw.write("$");
		}
		bw.write(counter + '\n');
		bw.write(map.get("column1") + '\n' + map.get("column2") + '\n' +
				map.get("column3") + '\n' + Assembler.LCTCTR + '\n');
		if (comment) {
			bw.write("$");
		}
		return true;
	}
	
	public HashMap<String, String> getMap(){
		return map;
	}
        
}
