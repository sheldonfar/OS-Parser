import java.util.ArrayList;
import java.util.Locale;
import java.sql.*;

public class DataBase {
	int attr_id = 150;
	
	public void addDataToDB(ArrayList<ShopItem> result) {
		try{  
			System.out.println("------------------------Starting working with the database!-------------------------------");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			Locale.setDefault(Locale.ENGLISH);
			
			Connection con=DriverManager.getConnection(  
			"jdbc:oracle:thin:@localhost:1521:xe","system","fargys11");  
			  

			Statement stmt=con.createStatement();  
			
			
			stmt.executeQuery("TRUNCATE TABLE objects");
			stmt.executeQuery("TRUNCATE TABLE params");
			
			
			for(int i = 0; i < result.size(); i++) {
				
				stmt.executeQuery("INSERT INTO objects VALUES(" + (i+1) + ", '" + result.get(i).getName() + "', '" + result.get(i).getHref() + "')");
			    stmt.executeQuery("INSERT INTO params VALUES(" + (i+1) + ", " + (attr_id++) + ", '" + result.get(i).getCurrent_price() + "')");
			    for(int k = 0; k < result.get(i).getDescription().size(); k++) {
			    	stmt.executeQuery("INSERT INTO params VALUES(" + (i+1) + ", " + (attr_id) + ", '" + result.get(i).getDescription().get(k) + "')");
			    }
			    
				
				attr_id = 150;
			}
			System.out.println("------------------------Done working with database!---------------------------------------");  
			
			
			
			System.out.println("------------------------Now parsing the result into xml file!-----------------------------");  
			ResultSet rs6 = stmt.executeQuery("select p.object_id, o.name, o.href, rtrim(xmlagg (xmlelement (e, p.value || ', ')).extract ('//text()'), ', ') AS \"DESCRIPTION\" from params p JOIN objects o ON (p.object_id = o.object_id) group by p.object_id, o.name, o.href");
			JDBCUtil.convertToXml(rs6);
			System.out.println("------------------------Done parsing into xml file!---------------------------------------");  
			
			
			System.out.println("------------------------Now parsing the result into ñsv file!-----------------------------");  
			ResultSet rs7 = stmt.executeQuery("select p.object_id, o.name, o.href, rtrim(xmlagg (xmlelement (e, p.value || ', ')).extract ('//text()'), ', ') AS \"DESCRIPTION\" from params p JOIN objects o ON (p.object_id = o.object_id) group by p.object_id, o.name, o.href");
			JDBCUtil.convertToCsv(rs7);	
			System.out.println("------------------------Done parsing into csv file!---------------------------------------");  
			
			
			con.close();  	  
		}catch(Exception e){ System.out.println(e);} 
	}
}
