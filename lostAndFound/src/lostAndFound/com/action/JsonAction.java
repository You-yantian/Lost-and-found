package lostAndFound.com.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
import java.sql.*;
//import java.sql.ResultSet;

public class JsonAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest request;
	private String result;

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	/**
	 * ����ajax����
	 * @return SUCCESS
	 */
	public String excuteAjax(){
		String ret = ERROR;
	    Connection conn = null;
	    String columnName, columnValue = null;
	    JSONArray json=new JSONArray();
	    JSONObject element = null;
		try {
			ResultSetMetaData rsmd = null;
			Statement stmt = null;
		    
			//��ȡ����
			String URL = "jdbc:mysql://localhost/webapp";
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(URL, "root", "yantian94");
	        stmt = conn.createStatement();
	        String sql = "SELECT * FROM item inner join user on item.userID=user.userID";
	        ResultSet rs = stmt.executeQuery(sql);
	        rsmd = rs.getMetaData();
			//�����ݴ洢��map���ת����json�������ݣ�Ҳ�����Լ��ֶ�����json��������
	        Map<String,String> map = new HashMap<String,String>();
	        while (rs.next()) {
	        for (int i = 0; i < rsmd.getColumnCount(); i++) {
                columnName = rsmd.getColumnName(i + 1);
                columnValue = rs.getString(columnName);
                map.put(columnName, columnValue);
            }
	        element = JSONObject.fromObject(map);
	        json.add(element);
	        map.clear();
	        }
	       // JSONObject json = JSONObject.fromObject(map);//��map����ת����json��������
			result = json.toString();//��result��ֵ�����ݸ�ҳ��
			ret=SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			ret=ERROR;
		}finally {
	         if (conn != null) {
	             try {
	                conn.close();
	             } catch (Exception e) {
	             }
	          }
	       }
		return ret;
	}
}
