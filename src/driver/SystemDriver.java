package driver;

import model.vo.UserVo;

import controller.Controller;
import view.LoginView;
import view.MenuView;

public class SystemDriver {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LoginView lv=new LoginView();
        Controller con=new Controller();
        //ͳ���û���¼����times
        int times=0;
        while (true){
        	UserVo user=lv.display();
        	if (con.loginRequestProcess(user)){
        		break;     //�û���¼�ɹ�������ѭ��
        	}else{
        		times++;
        		if (times==3){
        			System.out.println("���ֻ�ܳ��������˺�����3�Σ������˳���");
        			return; 
        		}
        		continue;
        	}
        }
        
        //ѭ��������˵�
        MenuView mv=new MenuView();
        while(con.mainMenuRequestProcess(mv.display(con.getUser()))){
        	//���û���ѡ���˳����ִ�����û���������������˵�
        }
       
	}
	    
	    	
	}


