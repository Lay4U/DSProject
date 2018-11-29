import java.net.*;

import org.jsoup.Jsoup;

import java.io.*;

public class PreProcessing {
	public static void main(String[] args) throws Exception {
		PrintWriter out = new PrintWriter("filename.txt");
		
		URL url = new URL("https://en.wikipedia.org/wiki/Distributed_computing");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine = "";
		String input = null;
		
		while ((inputLine = in.readLine()) != null)
		{
			input += inputLine;
//			System.out.println(inputLine);
		}
		in.close();
		
		String parse=Jsoup.parse(input).text();
		parse.replaceAll("\n", "");
		parse.replaceAll("\\p{P}", "");
//		System.out.println(input);
		System.out.println(parse);
		out.println(parse);
//		inputLine.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		
	}

}
