package util;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Tools {
	//��ȡ��ˮ��
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
				//System.out.println("���������ۼ�¼");
				lastLsh="0000";
			}
			rs.close();
			db.close();
			
			//������һ�����ۼ�¼����ˮ��
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
	
	
	//����������Ƿ������ݿ��д���
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
