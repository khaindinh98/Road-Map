import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.io.*;
public class abc{
	public static void main (String [] str){//Scanner sc,FileReader r){
			String pat="T(?<Value>\\d+)\\s+(?<Style>.*?):\\s+.*";
			try{
				BufferedReader fr1=new BufferedReader(new InputStreamReader(new FileInputStream(str[0]), "UTF-8"));
				BufferedReader fr2=new BufferedReader(new InputStreamReader(new FileInputStream(str[1]), "UTF-8"));
				BufferedReader fr3=new BufferedReader(new InputStreamReader(new FileInputStream(str[2]), "UTF-8"));
				BufferedWriter fw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str[3]), "UTF-8"));
				String line="";
				while((line =fr1.readLine()) != null){
					if(line.trim().isEmpty()) continue;
					Pattern pattern = Pattern.compile(pat);
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()){	
						String a=""+matcher.group("Style")+ "\n";
						System.out.print(a);
						fw.write(a);
					}
				}
				while((line =fr2.readLine()) != null){
					if(line.trim().isEmpty()) continue;
					Pattern pattern = Pattern.compile(pat);
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()){	
						String a=""+matcher.group("Style")+ "\n";
						System.out.print(a);
						fw.write(a);
					}
				}
				while((line =fr3.readLine()) != null){
					if(line.trim().isEmpty()) continue;
					Pattern pattern = Pattern.compile(pat);
					Matcher matcher = pattern.matcher(line);
					while(matcher.find()){	
						String a=""+matcher.group("Style")+ "\n";
						System.out.print(a);
						fw.write(a);
					}
				}
				fr1.close();
				fr2.close();
				fr3.close();
				fw.close();
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}
			
		}
}