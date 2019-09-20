package player;

import java.io.File;
import sploder12.json.JSON;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Player0 {
	public String name;
	private JSON json = new JSON();
	public double cash = 0, bank = 0;
	public int x = 0, y = 0;
	public int worldx = 0, worldy = 0;
	public float[] earnings = {0.0F};
	public float salary = 0.0f;
	
	public byte skin = 0, hair = 0, hat = 0, shirt = 0, body = 0, shoes = 0;
	public Color haircolor = new Color(200,150,50);
	public int[][] inventory = new int[8][4];
	public int[][] invenCount = new int[8][4];
	
	public float health = 100;
	public float energy = 100;
	public float thirst = 100;
	public float hunger = 100;
	
	public Player0(int x, int y, double cash){
		this.cash = cash;
		this.x = x;
		this.y = y;
	}
	
	public Player0(int x, int y){
		this.cash = 0.0;
		this.x = x;
		this.y = y;
		
	}
		
	public void LoadData(String file){
		
		String inpt = json.convertToString(file);
		
		name = json.getValueOfDict(inpt, json.locateStringEnd(inpt, "name"));
		
		cash = json.getDoubleValueOfDict(inpt, json.locateStringEnd(inpt, "cash"));
		bank = json.getDoubleValueOfDict(inpt, json.locateStringEnd(inpt, "bank"));
		
		x = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "x"));
		y = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "y"));
		
		worldx = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "wx"));
		worldy = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "wy"));
		
		earnings[0] = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "fearn"));
		salary = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "sal"));
		
		skin = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "skin"));
		hair = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "hair"));
		hat = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "hat"));
		shirt = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "shirt"));
		body = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "body"));
		shoes = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "shoes"));
		
		haircolor = new Color(json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "hColor")));
		
		for(byte i = 0; i < 8; i++){
			for(byte j = 0; j < 4; j++){
				inventory[i][j] = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "invI" + i + j));
				invenCount[i][j] = json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "invC" + i + j));
			}
		}
		
		health = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "hp"));
		energy = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "stam"));
		thirst = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "thir"));
		hunger = json.getFloatValueOfDict(inpt, json.locateStringEnd(inpt, "hung"));
	}
	
	public void LocalSaveData(){
		try{
			BufferedWriter output = null;
	        File file = new File("Saves\\"+ name +".pdat");
	        output = new BufferedWriter(new FileWriter(file));
	        output.append('{');
	        output.append("name:"+ '"' + name + '"' +',');
	        output.append("cash:" + cash + ',');
	        output.append("bank:" + bank + ',');
	        output.append("x: " + x + ',');
	        output.append("y: " + y + ',');
	        output.append("wx: " + worldx + ',');
	        output.append("wy: " + worldy + ',');
	        double fearnings = 0.0;
	        for(int i = 0; i < earnings.length; i++){
	        	fearnings += earnings[i];
	        }
	        output.append("fearn:" + fearnings + ',');
	        output.append("sal:" + salary);
	        
	        output.append("skin:" + (char)(skin) + ',');
	        output.append("hair:" + (char)(hair) + ',');
	        output.append("hat:" + (char)(hat) + ',');
	        output.append("shirt:" + (char)(shirt) + ',');
	        output.append("body:" + (char)(body) + ','); 
	        output.append("shoes:" + (char)(shoes) + ',');
	        output.append("hColor:" + haircolor.getRGB() + ',');
	        
	        for(byte i = 0; i < 8; i++){
	        	for(byte j = 0; j < 4; j++){
		        	output.append("invI" + i + j +":" + inventory[i][j] + ',');
		        	output.append("invC" + i + j +":" + invenCount[i][j] + ',');
	        	}
	        }
	        
	        output.append("hp: " + health + ',');
	        output.append("stam:" + energy + ',');
	        output.append("thir:" + thirst +',');
	        output.append("hung:" + hunger + ',');
	        
	        output.append('}');
	        output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*--------Movement-----------*/
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public void moveX(int x){
		this.x += x;
	}
	
	public void moveY(int y){
		this.y += y;
	}
	
	/*------------Cash------------*/
	public void collectCash() { //For Getting Paid
		int earn = earnings.length;
		for(int i = 0; i < earn; i++){
			cash += earnings[i];
		}
	}
	
	public void addSalary(){ //Salary
		int size = earnings.length;
		float[] newearn = new float[size+1];
		for(int i = 0; i < size; i++){
			newearn[i] = earnings[i];
		}
		newearn[size] = salary;
	}
	
	public void setSalary(float salary){
		this.salary = salary;
	}
	
	public void setCash(double cash){
		this.cash = cash;
	}
	
	public void changeCash(double cash){
		if(this.cash + cash < 0){
			System.out.println("Cannot be afforded");
		}else{
			this.cash += cash;
		}
	}
	
	public void deposit(double moni){ //Put Money In Bank
		if(cash >= moni){
			cash -= moni;
			bank += moni;
		}else{
			System.out.println("Not Enough Money!"); //@TODO replace with an event 
		}
	}
	
	public void withdraw(double moni){ //Take Money Out Bank
		if(bank >= moni){
			cash += moni;
			bank -= moni;
		}else{
			System.out.println("Not Enough Money In The Bank!");
		}
	}
	

}
