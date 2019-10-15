package player;

import java.io.File;
import sploder12.json.JSON;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;

import com.Render;


public class Player0 {
	public String name;
	private JSON json = new JSON();
	public double cash = 0, bank = 0;
	public int x = 0, y = 0;
	public int worldx = 0, worldy = 0;
	public float[] earnings = {0.0F};
	public float salary = 0.0f;
	
	public byte skin = 3, hair = 0, hat = 0, shirt = 1, body = 0, face = 1;
	public Color haircolor = new Color(95,50,25);
	public Color eyecolor = new Color(50,50,255);
	public int[][] inventory = new int[8][4];
	public int[][] invenCount = new int[8][4];
	
	public int helditm = 0;
	
	public float health = 100;
	public float energy = 100;
	public float thirst = 100;
	public float hunger = 100;
	
	public Image bodyimg, hairimg, faceimg, headimg;
	
	
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
	
	
	public Image coloring(BufferedImage img, Color color){
		
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				if(img.getRGB(i, j) == -2){ //#fffffe
					img.setRGB(i, j, color.getRGB());
				}
			}
		}
		Image out = img.getScaledInstance((int)(48*Render.xScale), -1, Image.SCALE_SMOOTH);
		
		return out;
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
		face = json.getByteValueOfDict(inpt, json.locateStringEnd(inpt, "face"));
		
		
		
		haircolor = new Color(json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "hColor")));
		eyecolor = new Color(json.getIntValueOfDict(inpt, json.locateStringEnd(inpt, "eColor")));
		
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

		
		bodyimg = coloring(Render.Bbodi[body],Render.skins[skin]);
		headimg = coloring(Render.Bhead, Render.skins[skin]);
		
		faceimg = coloring(Render.Bfaces[face], eyecolor);
		
		hairimg = coloring(Render.Bhair[hair], haircolor);
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
	        output.append("sal: " + salary + ',');
	        
	        output.append("skin: " + skin + ',');
	        output.append("hair: " + hair + ',');
	        output.append("hat: " + hat + ',');
	        output.append("shirt: " + shirt + ',');
	        output.append("body: " + body + ','); 
	        output.append("face: " + face + ',');
	        output.append("hColor: " + haircolor.getRGB() + ',');
	        output.append("eColor: " + eyecolor.getRGB() + ',');
	        
	        for(byte i = 0; i < 8; i++){
	        	for(byte j = 0; j < 4; j++){
		        	output.append("invI" + i + j +": " + inventory[i][j] + ',');
		        	output.append("invC" + i + j +": " + invenCount[i][j] + ',');
	        	}
	        }
	        
	        output.append("hp: " + health + ',');
	        output.append("stam: " + energy + ',');
	        output.append("thir: " + thirst +',');
	        output.append("hung: " + hunger + ',');
	        
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
