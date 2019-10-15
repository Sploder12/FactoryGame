package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import sploder12.json.JSON;

public class Config {
	private static JSON json = new JSON();
	//everything is static inorder to be used in main(String[] arge)
	
	public static float svolume = 100;
	
	public static int WIDTH = 800, HEIGHT = 600;
	
	public static byte fpslimit = 63, wantedfps = 60;
	
	public static void loadConfig(){ 
		try{
			String inpt = json.convertToString("Resources\\config.cnf");
			//wantedfps = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "frames"));
			
			svolume = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "volume"));
			WIDTH = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "reswid"));
			HEIGHT = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "resheight"));
			
			
		}catch(Exception e){
			System.out.println("Could Not Find config.cnf, resetting to default");
			saveConfig(); //resets the default if things go wrong
		}
	}
	
	public static void saveConfig(){
		try{
			BufferedWriter output = null;
	        File file = new File("Resources\\config.cnf");
	        output = new BufferedWriter(new FileWriter(file));
	        output.append('{');
	        
	        output.append("frames:"+wantedfps + ',');
	        output.append("volume:" + svolume + ',');
	        
	        output.append("reswid: " + WIDTH + ',');
	        output.append("resheight: " + HEIGHT + ',');
	        
	        output.append('}');
	        
	        output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
