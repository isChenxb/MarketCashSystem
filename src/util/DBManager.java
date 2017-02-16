package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * ����ʵ��JDBC�ķ�װ����Ҫʵ�ִ��������
 * ��������ĳ�����̣���Ա��������Ա��������
 * ���ݿ�ķ��ʲ��裺
 * ��1�������������������ӣ����������󣨷�װ�ڹ��췽���У�
 * ��2������insert��delete��update֮��Ĳ�����������Ӱ��ļ�¼����
 * ��>0�����ʾ�����ɹ��������ʾ����ʧ�ܣ���Ҫʵ��һ������
 * ��3������select�Ĳ��������ص��ǲ�ѯ���ļ�¼������Ҫʵ��һ������
 * ��4�������ݿ����Ӷ���Ĺرգ����ͷ���Դ�������Դ����ʱ���ͷţ�ϵͳ�������
 * ��out of memory���Ĵ��󣬵���ϵͳ����
 * 
 */
public class DBManager {
	//��Ա�ĳ�Ա����
	private Connection con;
	private Statement stm;
	private ResultSet rs;
	
	//����Ϊ��װ�ĳ�Ա����
	/*
	 * 1.���췽����ʵ�ּ����������������ӣ�����������
	 */
	public DBManager() throws  IOException{
		//��xml�ĵ��л�ȡ���ݿ���Ϣ
		
		//����һ��DocumentBuilderFactory�Ķ���
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try {
			//����һ��DocumentBuilder�Ķ���
			DocumentBuilder db=dbf.newDocumentBuilder();
			//ͨ��DocumentBuilder�����parser��������DBMessage.xml�ļ�����ǰĿ¼��
     		Document document =db.parse(".//DBMessage.xml");
     		//��ȡ����DBMessage�ڵ㼯��
     		NodeList dbmList=document.getElementsByTagName("DBMessage");
     		//ͨ��item()������ȡDBMessage�ڵ�
     		Node dbm=dbmList.item(0);
     		//����DBMessage�ڵ���ӽڵ�
     		NodeList childNodes=dbm.getChildNodes();
     		//����childNodes��ȡÿ���ڵ�Ľڵ����ͽڵ�ֵ
     		ArrayList<String> strList=new ArrayList<String>();
     		for (int i=0;i<childNodes.getLength();i++){
     			//���ֳ�text���͵�node�Լ�element���͵�node
     			if (childNodes.item(i).getNodeType()==Node.ELEMENT_NODE){
     				strList.add(childNodes.item(i).getTextContent());
     			}
     		}
	
		
		   
     		// 1.��������
			String driverName = strList.get(1); // mysql
															// jdbc����������,ʵ���Ͼ���driver���ڰ��е�����·��
			Class.forName(driverName);

			// 2.���������ݿ������
			String url = strList.get(2);         // ���ݿ������ַ�����һ��ʹ��ͳһ��Դ��λ����url������ʽ

			String user = strList.get(3);  // �������ݿ�ʱ���û�
			String password = strList.get(4);  // �������ݿ�ʱ������
			con = DriverManager.getConnection(url, user, password);

			//3.����������
			stm = con.createStatement();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 2.����insert��delete��update֮��Ĳ���,�����쳣�ĳ��������Բ�ȡ���ַ�ʽ
	 * ��1��ʹ��try��catch
	 * ��2)ֱ���׳���ʹ��thows�������������߽��д���
	 */
	public boolean executeUpdate(String sql) throws SQLException{
		boolean ret=false;
		int i=stm.executeUpdate(sql);
		if(i>0){
			ret=true;
		}
		
		return ret;		
	}
	
	/*
	 * 3.����select�Ĳ���
	 */
	
	public ResultSet executeQuery(String sql) throws SQLException{
		rs=stm.executeQuery(sql);
		return rs;
	}
	
	
	/*
	 * 4.��Դ���ͷ�
	 */
	
	public void close(){
		try {
			if (rs != null) {

				rs.close();

			}

			if (stm != null) {
				stm.close();
			}

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public Connection getConnection(){
		return con;
	}
	
	
	

}

