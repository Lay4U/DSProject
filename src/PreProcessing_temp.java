import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;

import java.io.*;

public class PreProcessing_temp {
	public static void main(String[] args) throws Exception {
		PrintWriter out = new PrintWriter("filename.txt");
		
		URL url = new URL("https://en.wikipedia.org/wiki/Distributed_computing");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine = "";
		String input = "";
		
		while ((inputLine = in.readLine()) != null)
			input += inputLine;
		Document jsoupDoc = Jsoup.parse(input);
		int count = Jsoup.parse(input).text().split(" ").length;
		String temp = jsoupDoc.text().replaceAll("\\.", "")
				.replaceAll("\"", "")
				.replaceAll(",", "")
				.replaceAll(":", "")
				.replaceAll("\\(", "")
				.replaceAll("\\)", "")
				.replaceAll(";", "")
//				.replaceAll("\\[", "")
//				.replaceAll("\\]", "")
				.replaceAll("\\[(.*?)\\]", "");
		String[] result = temp.split(" ");
		for (String word : result) {
			out.println(word);
		}
		
		System.out.println("total word count :"+count);
		in.close();
		
		
//		jsoupDoc.outputSettings(new OutputSettings().prettyPrint(false));
//		String str = jsoupDoc.html().replaceAll("\\\\n", "\n");
//        String strWithNewLines = 
//                Jsoup.clean(str, "", Whitelist.none(), new OutputSettings().prettyPrint(false));
//        strWithNewLines.replaceAll(" ", "\n");
//        System.out.println(strWithNewLines);
//		jsoupDoc.html().replaceAll(" ", "\n");
		
//		OutputSettings settings = new OutputSettings();
//		settings.prettyPrint(false);
//		String parse = Jsoup.clean(input, "", Whitelist.none(), settings);
		
//		parse=Jsoup.clean(parse, Whitelist.basic());
//		String newLine = System.getProperty("line.separator");
//		input.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
//		parse.replace(" ", "\n");
//		parse.replaceAll("\n", "");
//		parse.replaceAll("\\p{P}", "");
//		System.out.println(jsoupDoc);
//		out.println(strWithNewLines);
//		inputLine.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		
	}

}
