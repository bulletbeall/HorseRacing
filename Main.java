import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;


public class Main {
	static String globalFileName = System.getProperty("user.dir")+"/temp.csv";
	static File myGlobalFile = new File(globalFileName);
	public static StringBuilder globalBuilder = new StringBuilder();
	static double wonLost = 0;
	static double globalAverage = 0;
	static double globalNo5Average = 0;
	static double globalNo4Average = 0;
	static double globalNo3Average = 0;
	static double globalNoShowAverage = 0;
	static double globalNoPlaceAverage = 0;
	static double globalNoWinAverage = 0;
	static double globalNoExactaAverage = 0;
	static double globalNoTriAverage = 0;
	static double globalNoDDAverage = 0;
	static double globalNoSuperAverage = 0;
	static int averageCounter = 0;
	static int maxRaces = 130;
	static String[] raceDates = new String[maxRaces];

	public static void main(String[] args) throws NumberFormatException, IOException, ParseException, InterruptedException {
		PrintWriter  globalPw = new PrintWriter(myGlobalFile);
		populateRaceDates();

		for(int i=0;i<raceDates.length;i++){
			getRaceData(raceDates[i]);
			averageCounter++;
			System.out.println("Done " + (i+1) + " of " + raceDates.length);
		}		

		globalPw.write(globalBuilder.toString());
		globalPw.close();

		printAveragesAtTop();
	}

	public static void populateRaceDates() throws InterruptedException{
		String GTSURL = "https://www.guaranteedtipsheet.com/12-Laurel_Park_Tips";

		URL url = null; 
		URLConnection con = null;
		InputStream is = null;
		TimeUnit.MILLISECONDS.sleep(100);
		try {
			url = new URL(GTSURL);
		} catch (MalformedURLException e){
			System.out.println("URLgts");
			System.exit(0);
		}

		try { 
			con = url.openConnection();
		} catch (IOException e) {
			System.out.println("congts");
			System.exit(0);
		}

		try {
			is = con.getInputStream();
		} catch (IOException e) {
			System.out.println("isgts");
			System.exit(0);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = "";
		try {
			String arr[];
			int i=0;
			while ((line = br.readLine()) != null) {
				if(maxRaces <= i ){
					break;
				}
				//	line = line.replace("<img src='gts_", "");
				if(line.contains("<td width=\"200\" align=\"left\"><font face=\"Verdana\">")) {
					line=line.replace(" ", "");
					line=line.replace("</font></td>", "");
					line=line.replace("<tdwidth=\"200\"align=\"left\"><fontface=\"Verdana\">", "");
					arr = line.split("/");
					line = arr[2]+arr[0]+arr[1];
					//System.out.println(line);
					raceDates[i] = line;
					i++;
				}
			}
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		//System.out.println("done!");

	}

	public static void printAveragesAtTop() throws InterruptedException, FileNotFoundException{
		File inputFile = new File(System.getProperty("user.dir")+"/temp.csv");
		String fileName = System.getProperty("user.dir")+"/global_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			builder.append(",All Bets Average,15760," + (globalAverage/averageCounter)+ ",," + ((globalAverage/averageCounter)-15760) + "\n");
			//System.out.println(globalAverage/averageCounter);
			builder.append(",No Pick 5 Average,5520," + (globalNo5Average/averageCounter)+ ",," + ((globalNo5Average/averageCounter)-5520) + "\n");
			//System.out.println(globalNo5Average/averageCounter);
			builder.append(",No Pick 4 Average,2448," + (globalNo4Average/averageCounter)+ ",," + ((globalNo4Average/averageCounter)-2448) + "\n");
			//System.out.println(globalNo4Average/averageCounter);
			builder.append(",No Pick 3 Average,1552," + (globalNo3Average/averageCounter)+ ",," + ((globalNo3Average/averageCounter)-1552) + "\n");
			//System.out.println(globalNo3Average/averageCounter);
			builder.append(",No Show Average,2376," + (globalNoShowAverage/averageCounter)+ ",," + ((globalNoShowAverage/averageCounter)-2376) + "\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			builder.append(",No Win Average,2376," + (globalNoWinAverage/averageCounter)+ ",," + ((globalNoWinAverage/averageCounter)-2376) + "\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			builder.append(",No Place Average,2376," + (globalNoPlaceAverage/averageCounter)+ ",," + ((globalNoPlaceAverage/averageCounter)-2376) + "\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			builder.append(",No Exacta Average,2232," + (globalNoExactaAverage/averageCounter)+ ",," + ((globalNoExactaAverage/averageCounter)-2232) + "\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			builder.append(",No Superfecta Average,2016," + (globalNoSuperAverage/averageCounter)+ ",," + ((globalNoSuperAverage/averageCounter)-2016) + "\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			builder.append(",No Trifecta Average,2016," + (globalNoTriAverage/averageCounter)+ ",," + ((globalNoTriAverage/averageCounter)-2016) + "\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			builder.append(",No Daily Double Average,2192," + (globalNoDDAverage/averageCounter)+ ",," + ((globalNoDDAverage/averageCounter)-2192) + "\n\n");
			//System.out.println(globalNoShowAverage/averageCounter);
			while ((line = br.readLine()) != null) {
				builder.append(line+"\n");
			}
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		inputFile.delete();
		pw.write(builder.toString());
		pw.close();
		//System.out.println("done!");
	}

	public static void getRaceData(String raceDate) throws InterruptedException, FileNotFoundException{

		String GTSURL = "https://www.guaranteedtipsheet.com/history2.asp?pick=1&track=12&racedate=" + raceDate;
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);

		StringBuilder builder = new StringBuilder();

		//System.out.println(ffsURLPost);
		URL url = null; 
		URLConnection con = null;
		InputStream is = null;
		TimeUnit.MILLISECONDS.sleep(100);
		try {
			url = new URL(GTSURL);
		} catch (MalformedURLException e){
			System.out.println("URLgts");
			System.exit(0);
		}

		try { 
			con = url.openConnection();
		} catch (IOException e) {
			System.out.println("congts");
			System.exit(0);
		}

		try {
			is = con.getInputStream();
		} catch (IOException e) {
			System.out.println("isgts");
			System.exit(0);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			while ((line = br.readLine()) != null) {
				line = line.replace("<img src='gts_", "");
				line = line.replace("3.jpg' width=12 height=12 border=0> ", "");
				line = line.replace("<br>", "");
				line = line.replace("<br />", "");
				line = line.replace(",", "");

				if(line.contains("<strong>$")) {
					line = line.replace("<strong><font color=green>TOTAL PAYOUTS</font></strong>:&nbsp;&nbsp;<strong>", raceDate + ",All Bets, $15760, ");
					line = line.replace("</strong>&nbsp;&nbsp;", "");
					//System.out.println(line.split(",")[3].substring(2));
					globalBuilder.append(line+",,"+(Double.parseDouble(line.split(",")[3].substring(2))-15760) + "\n");
					globalAverage = globalAverage+Double.parseDouble(line.split(",")[3].substring(2));
					builder.append(line+"\n\n");
				}
				//WIN
				if(line.contains("<font color=#008000")) {
					line = line.replace("<font color=#008000>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[0] + "," + arr[1] + "," + arr[2]; 
					printNow = printNow.replace(" ", "");
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				//PLACE
				if(line.contains("<font color=#FF9900")) {
					line = line.replace("<font color=#FF9900>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[0] + "," + arr[1] + "," + arr[2];
					printNow = printNow.replace(" ", "");
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				//SHOW
				if(line.contains("<font color=#FF0000")) {
					line = line.replace("<font color=#FF0000>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[0] + "," + arr[1] + "," + arr[2]; 
					printNow = printNow.replace(" ", "");
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				//Ex,Tri,Super
				if(line.contains("<font color=#000080")) {
					line = line.replaceFirst("<font color=#000080>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[0] + "," + arr[1] + "," + arr[2];
					arr2 = printNow.split(" ");
					printNow = arr2[1] + arr2[0];
					arr2 = printNow.split(",");
					printNow = arr2[0] + "," + arr2[2] + "," + arr2[1]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				if(line.contains("<font color=#000080")) {
					line = line.replaceFirst("<font color=#000080>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[0] + "," + arr[1] + "," + arr[2];
					arr2 = printNow.split(" ");
					printNow = arr2[1] + arr2[0];
					arr2 = printNow.split(",");
					printNow = arr2[0] + "," + arr2[2] + "," + arr2[1]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				if(line.contains("<font color=#000080")) {
					line = line.replaceFirst("<font color=#000080>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[0] + "," + arr[1] + "," + arr[2];
					arr2 = printNow.split(" ");
					printNow = arr2[1] + arr2[0];
					arr2 = printNow.split(",");
					printNow = arr2[0] + "," + arr2[2] + "," + arr2[1]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				//DD
				if(line.contains("<font color=blue")) {
					line = line.replaceFirst("<font color=blue>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[1] + "," + arr[0] + "," + arr[2]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				if(line.contains("<font color=blue")) {
					line = line.replaceFirst("<font color=blue>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[1] + "," + arr[0] + "," + arr[2]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				if(line.contains("<font color=blue")) {
					line = line.replaceFirst("<font color=blue>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[1] + "," + arr[0] + "," + arr[2]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				if(line.contains("<font color=blue")) {
					line = line.replaceFirst("<font color=blue>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[1] + "," + arr[0] + "," + arr[2]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
				if(line.contains("<font color=blue")) {
					line = line.replaceFirst("<font color=blue>", ",");
					line = line.replace(" - ", ",");
					line = line.replace("</font>", ",");
					arr = line.split(",");
					printNow = arr[1] + "," + arr[0] + "," + arr[2]; 
					//System.out.println(printNow);
					builder.append(printNow+"\n");
					if(arr.length > 3){
						line = arr[3];
						for(int i=4;i<arr.length;i++){
							line = line + "," + arr[i];
						}
					}
				}
			}

		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		removePick5(fileName,raceDate);
		//System.out.println("done!");

	}

	public static void removePick5(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noPick5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains(",")){
					if(!line.contains("PICK 5")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Pick 5, $5520, $" + total + ",," + (total-5520) +"\n");
			globalNo5Average = globalNo5Average+total;
			builder.append("\n" + raceDate + ",No Pick 5, $5520, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		removePick4(fileName,raceDate);
		//System.out.println("done!");

	}

	public static void removePick4(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noPick4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 5")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("PICK 4")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Pick 4, $2448, $" + total + ",," + (total-2448) + "\n");
			globalNo4Average = globalNo4Average+total;
			builder.append("\n" + raceDate + ",No Pick 4, $2448, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		removePick3(fileName,raceDate);
		removeShow(fileName,raceDate);
		removePlace(fileName,raceDate);
		removeWin(fileName,raceDate);
		removeExacta(fileName,raceDate);
		removeSuper(fileName,raceDate);
		removeTri(fileName,raceDate);
		removeDD(fileName,raceDate);
		//System.out.println("done!");

	}

	public static void removePick3(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noPick3or4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("PICK 3")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Pick 3, $1552, $" + total + ",," + (total-1552) + "\n");
			globalNo3Average = globalNo3Average+total;
			builder.append("\n" + raceDate + ",No Pick 3, $1552, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//System.out.println("done!");

	}
	
	public static void removeShow(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noShowor4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("show")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Show, $2376, $" + total + ",," + (total-2376) + "\n");
			globalNoShowAverage = globalNoShowAverage+total;
			builder.append("\n" + raceDate + ",No Show, $2376, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

	public static void removePlace(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noPlaceor4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("place")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Place, $2376, $" + total + ",," + (total-2376) + "\n");
			globalNoPlaceAverage = globalNoPlaceAverage+total;
			builder.append("\n" + raceDate + ",No Place, $2376, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

	public static void removeWin(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noWinor4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("win")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Win, $1480, $" + total + ",," + (total-1480) + "\n");
			globalNoWinAverage = globalNoWinAverage+total;
			builder.append("\n" + raceDate + ",No Win, $1480, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

	public static void removeExacta(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noExactaor4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("EXACTA")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Exacta, $2232, $" + total + ",," + (total-2232) + "\n");
			globalNoExactaAverage = globalNoExactaAverage+total;
			builder.append("\n" + raceDate + ",No Exact, $2232, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

	public static void removeSuper(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noSuperor4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("SUPERFECTA")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Super, $2016, $" + total + ",," + (total-2016) + "\n");
			globalNoSuperAverage = globalNoSuperAverage+total;
			builder.append("\n" + raceDate + ",No Super, $2016, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

	public static void removeTri(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noTrior4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("TRIFECTA")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No Trifecta, $2016, $" + total + ",," + (total-2016) + "\n");
			globalNoTriAverage = globalNoTriAverage+total;
			builder.append("\n" + raceDate + ",No Trifecta, $2016, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

	public static void removeDD(String csvFile, String raceDate) throws FileNotFoundException{
		File inputFile = new File(csvFile);
		String fileName = System.getProperty("user.dir")+"/"+raceDate+"_noDDor4or5_payouts.csv";
		File myFile = new File(fileName);
		myFile.delete();
		PrintWriter  pw = new PrintWriter(myFile);
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

		String line = "";
		try {
			String arr[];
			String arr2[];
			String printNow = "";
			double total = 0;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				//WIN
				if(line.contains("Pick 4")){
					//do nothing
				}else if(line.contains(",")){
					if(!line.contains("D.DOUBLE")) {
						builder.append(line+"\n");
						arr = line.split(",");
						arr[2] = arr[2].replace("$","");
						//System.out.println(arr[2]);
						total = total + Double.parseDouble(arr[2]);										
					}
				}
			}
			globalBuilder.append(raceDate + ",No DD, $2192, $" + total + ",," + (total-2192) + "\n");
			globalNoDDAverage = globalNoDDAverage+total;
			builder.append("\n" + raceDate + ",No DD, $2192, $" + total);
		} catch (IOException e) {
			System.out.println("whileGTS");
			System.exit(0);
		}
		pw.write(builder.toString());
		pw.close();
		//removeShow(fileName,raceDate);
		//System.out.println("done!");
	}

}
