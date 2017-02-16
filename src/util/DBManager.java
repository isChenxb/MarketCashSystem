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
 * 该类实现JDBC的封装，主要实现代码的重用
 * 核心是类的抽象过程（成员变量，成员方法），
 * 数据库的访问步骤：
 * （1）加载驱动，建立连接，创建语句对象（封装在构造方法中）
 * （2）对于insert，delete，update之类的操作，返回受影响的记录条数
 * 若>0，则表示操作成功，否则表示操作失败，需要实现一个方法
 * （3）对于select的操作，返回的是查询到的记录集，需要实现一个方法
 * （4）对数据库连接对象的关闭，以释放资源，如果资源不及时的释放，系统将会出现
 * “out of memory”的错误，导致系统奔溃
 * 
 */
public class DBManager {
	//成员的成员变量
	private Connection con;
	private Statement stm;
	private ResultSet rs;
	
	//以下为封装的成员方法
	/*
	 * 1.构造方法，实现加载驱动，建立连接，创建语句对象
	 */
	public DBManager() throws  IOException{
		//从xml文档中获取数据库信息
		
		//创建一个DocumentBuilderFactory的对象
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try {
			//创建一个DocumentBuilder的对象
			DocumentBuilder db=dbf.newDocumentBuilder();
			//通过DocumentBuilder对象的parser方法加载DBMessage.xml文件到当前目录下
     		Document document =db.parse(".//DBMessage.xml");
     		//获取所有DBMessage节点集合
     		NodeList dbmList=document.getElementsByTagName("DBMessage");
     		//通过item()方法获取DBMessage节点
     		Node dbm=dbmList.item(0);
     		//解析DBMessage节点的子节点
     		NodeList childNodes=dbm.getChildNodes();
     		//遍历childNodes获取每个节点的节点名和节点值
     		ArrayList<String> strList=new ArrayList<String>();
     		for (int i=0;i<childNodes.getLength();i++){
     			//区分出text类型的node以及element类型的node
     			if (childNodes.item(i).getNodeType()==Node.ELEMENT_NODE){
     				strList.add(childNodes.item(i).getTextContent());
     			}
     		}
	
		
		   
     		// 1.加载驱动
			String driverName = strList.get(1); // mysql
															// jdbc驱动描述符,实际上就是driver类在包中的完整路径
			Class.forName(driverName);

			// 2.建立与数据库的连接
			String url = strList.get(2);         // 数据库连接字符串，一般使用统一资源定位符（url）的形式

			String user = strList.get(3);  // 连接数据库时的用户
			String password = strList.get(4);  // 连接数据库时的密码
			con = DriverManager.getConnection(url, user, password);

			//3.创建语句对象
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
	 * 2.对于insert，delete，update之类的操作,对于异常的出来，可以采取两种方式
	 * （1）使用try，catch
	 * （2)直接抛出，使用thows，即交给调用者进行处理
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
	 * 3.对于select的操作
	 */
	
	public ResultSet executeQuery(String sql) throws SQLException{
		rs=stm.executeQuery(sql);
		return rs;
	}
	
	
	/*
	 * 4.资源的释放
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

