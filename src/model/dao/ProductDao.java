package model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import util.DBManager;
import util.Tools;
import util.Validate;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.vo.ProductVo;

public class ProductDao {
	
	//����ArrayList���󣬽����е�Product����д�����ݿ�
		public void DBimport(ArrayList<ProductVo> proList) throws SQLException, IOException{
			DBManager db=new DBManager();
			int iCount=0;
			for (int i=0;i<proList.size();i++){
				String sql="INSERT INTO tproduct VALUES(?,?,?,?)";
				PreparedStatement pst=db.getConnection().prepareStatement(sql);
				pst.setString(1, proList.get(i).getProduct_no());
				pst.setString(2, proList.get(i).getProduct_name());
				pst.setFloat(3, proList.get(i).getProduct_price());
				pst.setString(4, proList.get(i).getProduct_from());
				iCount+=pst.executeUpdate();
				pst.close();
			}
			System.out.println("�ɹ�����"+iCount+"����Ʒ����");
			db.close();
		}
	
	//��Excel�е�������
	public void importFromExcel() throws BiffException, IOException, SQLException{
		Validate va=new Validate();
		//����һ��ArrayList���	Product����
		ArrayList<ProductVo> proList=new ArrayList<ProductVo>();
		Tools ts=new Tools();
		ProductDao proDao=new ProductDao();
	    //��������������
		Workbook workbook=Workbook.getWorkbook(new File(".//importFile//product.xls"));
		//����һ��sheet
		Sheet sheet=workbook.getSheet(0);
		int passExist=0;
		int passError=0;
		for(int  row=1;row<sheet.getRows();row++){
			Cell[] cell=sheet.getRow(row);
			String product_no,product_name,product_price,product_from;
			product_no=cell[0].getContents();
			product_name=cell[1].getContents();
			product_price=cell[2].getContents();
			product_from=cell[3].getContents();
			float price=Float.valueOf(product_price);
			//���������ʽ��������������Ʒ�������һ��
			if (!va.isCode(product_no)){
				passError++;
				continue;
			}
			//���Ѵ�����ͬ����Ʒ�ţ�����������Ʒ�������һ��
			if (ts.isProductExist(product_no)){
				passExist++;
				continue;
			}
			proList.add(new ProductVo(product_no,product_name,price,product_from));
		}
		//����DBimprot��������Ʒ��Ϣд�����ݿ�
		proDao.DBimport(proList);
		System.out.println("����"+passExist+"���Ѵ�������");
		System.out.println("����"+passError+"���������ʽ��������");
	}
	
	
	//���ı��ļ�������Ʒ��Ϣ
	public void importFromTxt() throws IOException, SQLException{
		Tools ts=new Tools();
		ProductDao proDao=new ProductDao();
		Validate va=new Validate();
		//����һ��ArrayList���	Product����
		ArrayList<ProductVo> proList=new ArrayList<ProductVo>();
		File file=new File(".//importFile//product.txt");
		FileReader read=new FileReader(file);
		BufferedReader buff=new BufferedReader(read);
		String aLine="";
		int passExist=0;
		int passError=0;
		//���ı�������Ϣ������Ʒ��Ϣ����Product������
		while ((aLine=buff.readLine())!=null){
			String[] str=aLine.split("��");
			String product_no=str[0];
			String product_name=str[1];
			String product_from=str[3];
			float product_price=Float.parseFloat(str[2]);
			//���������ʽ��������������Ʒ�������һ��
			if (!va.isCode(product_no)){
				passError++;
				continue;
			}
			//���Ѵ�����ͬ����Ʒ�ţ�����������Ʒ�������һ��
			if (ts.isProductExist(product_no)){
				passExist++;
				continue;
			}
			proList.add(new ProductVo(product_no,product_name,product_price,product_from));
		}
		//����DBimprot��������Ʒ��Ϣд�����ݿ�
		proDao.DBimport(proList);
		System.out.println("����"+passExist+"���Ѵ�������");
		System.out.println("����"+passError+"���������ʽ��������");
	}
	
	
	//�Ӽ���������Ʒ��Ϣ
	public void importFromScanner() throws SQLException, IOException{
		//����һ��ArrayList���	Product����
		ArrayList<ProductVo> proList=new ArrayList<ProductVo>();
		ProductDao proDao=new ProductDao();
		Validate va=new Validate();
		Tools ts=new Tools();
		Scanner input=new Scanner(System.in);
		while (true){
		System.out.println("�밴����Ʒ�����룬��Ʒ���ƣ����ۣ���Ӧ�̡���ʽ������Ʒ��Ϣ��" +
				"���롰000������¼��");
		String str=input.next();
		//���û�����000���������
		if (str.equals("000")){
			break;
		}
		String[] arrays=str.split("��");
		if (arrays.length!=4){
			System.out.println("������ĸ�ʽ��������������");
			continue;
		}
		if (!(va.isCode(arrays[0]))){          //���������ʽ����������
			continue;
		}
		String product_no=arrays[0];
		String product_name=arrays[1];
		float product_price=Float.parseFloat(arrays[2]);
		String product_from=arrays[3];
		//���Ѵ�����ͬ����Ʒ�ţ�����������Ʒ�������һ��
		if (ts.isProductExist(product_no)){
			System.out.println("���������Ѵ��ڣ�����������");
			continue;
		}
		proList.add(new ProductVo(product_no,product_name,product_price,product_from));
		}
		//����DBimprot��������Ʒ��Ϣд�����ݿ�
				proDao.DBimport(proList);
	}
	
	//��Ʒ��ѯ����
	public void productQuery() throws SQLException, IOException{
		DBManager db=new DBManager();
		Scanner input=new Scanner(System.in);
		System.out.println("�������ѯ����Ʒ���ƣ�");
		String product_name=input.next();
		String sql="SELECT * FROM tproduct WHERE product_name LIKE '%"+product_name+"%'";
		ResultSet rs=db.executeQuery(sql);
		
		//��ȡrs�ĳ���
		rs.last();
		int n=rs.getRow();
		
		System.out.println("���������ļ�¼��"+n+"������Ϣ���£�");
		System.out.println("");
		System.out.println("���	������	��Ʒ����	  ����	 ��Ӧ��");
		System.out.println("===		=====	=======	====	=====");
		//���ý����ָ��
		rs.beforeFirst();
		
		//ѭ�������ѯ���
		int i=1;
		while (rs.next()){
			System.out.print(i+"     "+rs.getString(1)+"    ");
			System.out.print(rs.getString(2)+"    ");
			System.out.print(rs.getFloat(3)+"    ");
			System.out.println(rs.getString(4)+"    ");
			i++;
			}
		rs.close();
		db.close();
	}

}
