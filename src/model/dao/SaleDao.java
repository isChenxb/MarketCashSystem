package model.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import model.vo.UserVo;
import util.DBManager;
import util.Tools;
import util.Validate;

public class SaleDao {
	
	//��������
	public void getMoney(UserVo user) throws SQLException, IOException{
		DBManager db=new DBManager();
		Tools ts=new Tools();
		Scanner input=new Scanner(System.in);
		String date;
		String lsh,sale_men,sale_time;
		String product_no="";
		String product_name="";
		Float product_price=0.f;
		int sale_sum;
		while(true){
		System.out.println("��������Ʒ�����루6λ�����ַ�����");
		Validate va=new Validate();
		date=input.next();
		if (va.isCode(date)){        //����������ʽ�Ƿ���ȷ
			if (ts.isProductExist(date)){       //����������Ƿ���ڶ�Ӧ��Ʒ
				break;            
			}else{
				System.out.println("�������벻���ڣ�����������");
			}
		}
		}
		    System.out.println("��������Ʒ������");
			sale_sum=input.nextInt();
			String sql="SELECT * FROM tproduct WHERE product_no='"+date+"'";
			ResultSet rs=db.executeQuery(sql);
			while (rs.next()){
				product_no=rs.getString(1);
				product_name=rs.getString(2);
				product_price=rs.getFloat(3);
			}
			rs.close();
			//���ù��ߵõ���ˮ��
			lsh=ts.generateLsh();
			//��ȡ��ǰ��¼�û�������
			sale_men=user.getChrName();
			//��ȡ��ǰʱ��
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
			sale_time=df.format(new Date());
			
			//��������Ϣд�����ݱ�
			String sql2="INSERT INTO tsale_detail VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pst=db.getConnection().prepareStatement(sql2);
			pst.setString(1, lsh);
			pst.setString(2, product_no);
			pst.setString(3, product_name);
			pst.setFloat(4, product_price);
			pst.setInt(5, sale_sum);
			pst.setString(6, sale_men);
			pst.setString(7, sale_time);
			int iCount=pst.executeUpdate();
			if (iCount>0){
				System.out.println("�ɹ�����һ����������");
			}else{
				System.out.println("������������ʧ�ܣ�");
			}
			pst.close();
		    db.close();
	}
	
	
	//��ѯͳ�ƹ���
	public void forQuery() throws SQLException, IOException{
		Validate va=new Validate();
		Scanner input=new Scanner(System.in);
		DBManager db=new DBManager();
		String date;
		while(true){
			System.out.println("�������������ڣ�yyyy-mm-dd��:");
			date=input.next();
			if (va.isDate(date)){
				break;
			}
		}
		String[] arrays=date.split("-");
		date=arrays[0]+arrays[1]+arrays[2];
		String sql="SELECT * FROM tsale_detail WHERE LEFT(sale_no,8)='"+date+"'";
		ResultSet rs=db.executeQuery(sql);
		int allSum=0;     //��������
		int allProduct=0;       //��Ʒ�ܼ�
		int allSaleMoney=0;    //�����ܽ��
		System.out.println(arrays[0]+"��"+arrays[1]+"��"+arrays[2]+"����������");
		System.out.println("��ˮ��	          ��Ʒ����       ����	      ����	      ���	          ʱ��	           ����Ա");
		System.out.println("=====	      =======	 =====	  ====	      ===		      ====	           =====");
		while (rs.next()){
			System.out.print(rs.getString(1)+"    ");
			System.out.print(rs.getString(3)+"    ");
			System.out.print(rs.getFloat(4)+"    ");
			System.out.print(rs.getInt(5)+"    ");
			System.out.print(rs.getFloat(4)*rs.getInt(5)+"    ");
			System.out.print(rs.getString(7)+"    ");
			System.out.println(rs.getString(6));
			allSum++;
			allProduct+=rs.getInt(5);
			allSaleMoney+=rs.getFloat(4)*rs.getInt(5);
		}
		System.out.println("=====	=======	=====	====	===		====	=====");
		System.out.println("����������"+allSum+"��  ��Ʒ�ܼ���"+allProduct+"��  �����ܽ�"+allSaleMoney+"Ԫ");
		System.out.println("���������ⰴ������������");
		String str=input.next();
		if (str!=null){
			return;
		}
	}
	
	//���������ݵ�����xls�ļ���
	public void exportToExcel() throws IOException, SQLException, RowsExceededException, WriteException{
		DBManager db=new DBManager();
		//��ȡ��ǰʱ��
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String date=df.format(new Date());
		
		WritableWorkbook writebook=Workbook.createWorkbook(
				new File(".//exportFile//saleDetail"+date+".xls"));
		WritableSheet writeSheet=writebook.createSheet("���ۼ�¼", 0);
		//��ѯ���۱��е����ۼ�¼������������
		String sql="SELECT * FROM tsale_detail";
		ResultSet rs=db.executeQuery(sql);
		//��ʼд������
		String[] title={"��ˮ��","������","��Ʒ����","����","����","����Ա","����ʱ��"};
		//д�����
		for (int col=0;col<7;col++){
			//���ñ��������ʽ
			WritableFont arial11font = new WritableFont(WritableFont.ARIAL, 11,WritableFont.BOLD);
			WritableCellFormat arial11format = new WritableCellFormat (arial11font);
			arial11format.setAlignment(jxl.format.Alignment.CENTRE);  //ˮƽ����
			arial11format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);  //�߿���ʽ
			arial11format.setBackground(Colour.RED); //������ɫ
			Label label=new Label(col,0,title[col],arial11format);
			writeSheet.addCell(label);
		}
		//д����Ʒ��Ϣ
		int row=1;
		while(rs.next()){
			String sale_no=rs.getString(1);
			String product_no=rs.getString(2);
			String product_name=rs.getString(3);
			String product_price=String.valueOf(rs.getFloat(4));
			String sale_sum=String.valueOf(rs.getInt(5));
			String sale_men=rs.getString(6);
			String sale_time=rs.getString(7);
			String[] message={sale_no,product_no,product_name,product_price,sale_sum,sale_men,sale_time};
			for(int col=0;col<7;col++){
				WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
				WritableCellFormat arial10format = new WritableCellFormat (arial10font);
				arial10format.setAlignment(jxl.format.Alignment.CENTRE);  //ˮƽ����
				arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);  //�߿���ʽ
				Label label=new Label(col,row,message[col],arial10format);
				writeSheet.addCell(label);
			}
			row++;
		}
		System.out.println("�ɹ�����"+(row-1)+"���������ݵ�excel�ļ���");
		writebook.write();
		writebook.close();
		rs.close();
		db.close();
	}
	
	
	//���������ݵ�����txt�ļ���
	public void exportToTxt() throws IOException, SQLException{
		//��ȡ��ǰʱ��
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
	    String date=df.format(new Date());
	    
	    DBManager db=new DBManager();
	    File file=new File(".//exportFile//saleDetail"+date+".txt");
	    FileWriter wri=new FileWriter(file);
	    PrintWriter prn=new PrintWriter(wri);
	  //��ѯ���۱��е����ۼ�¼������������
	    String sql="SELECT * FROM tsale_detail";
	    ResultSet rs=db.executeQuery(sql);
	    prn.println("��ˮ��	 ������        ��Ʒ����   	����	  ����	����Ա      ����ʱ��");
	    prn.println("=====	=======	=====	====	===		====	=====");
	    //д��������Ϣ
	    int sum=0;
	    while (rs.next()){
	    String sale_no=rs.getString(1);
		String product_no=rs.getString(2);
		String product_name=rs.getString(3);
		String product_price=String.valueOf(rs.getFloat(4));
		String sale_sum=String.valueOf(rs.getInt(5));
		String sale_men=rs.getString(6);
		String sale_time=rs.getString(7);
		prn.print(sale_no+"      ");
		prn.print(product_no+"      ");
		prn.print(product_name+"      ");
		prn.print(product_price+"      ");
		prn.print(sale_sum+"      ");
		prn.print(sale_men+"      ");
		prn.println(sale_time+"      ");
		sum++;
	    }
	    prn.close();
	    System.out.println("�ɹ�����"+sum+"���������ݵ��ı��ļ���");
	}

}