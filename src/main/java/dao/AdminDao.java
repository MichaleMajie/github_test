package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Admin;
import util.DBUtil;

public class AdminDao implements Serializable {
			public Admin findByCode(String code){
				Connection conn=null;
				try {
					conn=DBUtil.getConnection();
					String sql="select * from admin "
							+ "    where name=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setString(1, code);
					ResultSet rs=ps.executeQuery();
					if(rs.next()){
					Admin a=new Admin();
					a.setAdminId(rs.getInt("id"));
					a.setPassword(rs.getString("password"));
					a.setName(rs.getString("name"));
					return a;
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("��ѯ����Աʧ��.",e);
				}finally{
					DBUtil.close(conn);
				}
				return null;
			}
			public static void main(String[] args) {
				AdminDao dao=new AdminDao();
				Admin a=dao.findByCode("caocao");
				System.out.println(a.getName());
			}
}
