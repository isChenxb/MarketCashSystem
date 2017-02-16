package util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Tools {
	//获取流水号
	public String generateLsh() throws SQLException, IOException{
		String lsh="";
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String today=df.format(new Date());
		String sql="select max(right(sale_no,4)) from tsale_detail  where left(sale_no,8)='"+today+"'";
		DBManager db=new DBManager(); 
		ResultSet rs=db.executeQuery(sql);
		String lastLsh="";
			if (rs.next()){
				lastLsh=rs.getString(1);
			}
			if (lastLsh==null){
				//System.out.println("当天无销售记录");
				lastLsh="0000";
			}
			rs.close();
			db.close();
			
			//生成下一笔销售记录的流水号
			int number=Integer.parseInt(lastLsh);
            number++;
            String newLastLsh=String.valueOf(number);
            int zeroNum=4-newLastLsh.length();
            for (int i=0;i<zeroNum;i++){
				newLastLsh="0"+newLastLsh;
			}
			lsh=today+newLastLsh;
			
		
		return lsh;
	}
	
	
	//检查条形码是否在数据库中存在
	public boolean isProductExist(String no) throws SQLException, IOException{
		boolean flag=false;
		DBManager db=new DBManager();
		String sql="SELECT product_no FROM tproduct WHERE product_no='"+no+"'";
		ResultSet rs=db.executeQuery(sql);
		while (rs.next()){
			flag=true;
		}
		return flag;
	}
	

}
