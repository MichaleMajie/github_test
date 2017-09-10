package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Cost;
import util.DBUtil;

public class CostDao implements Serializable {
			public  List<Cost>  findAll(){
				Connection conn=null;
				try {
					conn=DBUtil.getConnection();
					String sql="select * from cost_mj"
							+ " order by cost_id";
					Statement smt=conn.createStatement();
					ResultSet rs=smt.executeQuery(sql);
					List<Cost>  list=new ArrayList<Cost>();
					while(rs.next()){
						Cost c =new Cost();
						c.setCostId(rs.getInt("cost_id"));
						c.setName(rs.getString("name"));
						c.setBaseDuration(rs.getInt("base_duration"));
						c.setBaseCost(rs.getDouble("base_cost"));
						c.setUnitCost(rs.getDouble("unit_cost"));
						c.setStatus(rs.getString("status"));
						c.setDescr(rs.getString("descr"));
						c.setCreatime(rs.getTimestamp("creatime"));
						c.setStartime(rs.getTimestamp("startime"));
						c.setCostType(rs.getString("cost_type"));
						list.add(c);
					}
					return list;
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("查询资费失败!");
				}finally{
					DBUtil.close(conn);
				}
				
				
			}
			public   void updateCost(Cost c) {
				Connection  conn=null;
				try {
					conn=DBUtil.getConnection();
					String sql="update cost_mj set name=? ,base_duration=?,base_cost=?"
							+ "  ,unit_cost=?,descr=?,cost_type=? where cost_id=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setString(1, c.getName());
					//setInt/setDouble 方法不允许传入null
					//但当前的业务的确可能传入null,并且
					//数据库也允许存null,要想解决问题,
					//需要把这些数据当做普通的对象来看待及传入
					ps.setObject(2, c.getBaseDuration());
					ps.setObject(3, c.getBaseCost());
					ps.setObject(4, c.getUnitCost());
					ps.setString(5, c.getDescr());
					ps.setString(6, c.getCostType());
					ps.setInt(7, c.getCostId());
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("修改资费失败!");
				}finally{
					DBUtil.close(conn);
				}
				
			}
			public void deleteCost(int id){
				Connection conn=null;
				try {
					conn=DBUtil.getConnection();
					String sql="delete from cost_mj where cost_id=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1, id);
					ps.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("删除资费失败!");
				}finally{
					DBUtil.close(conn);
				}
			}
			private Cost createCost(ResultSet rs) 
					throws SQLException {
					Cost c = new Cost();
					c.setCostId(rs.getInt("cost_id"));
					c.setName(rs.getString("name"));
					c.setBaseDuration(rs.getInt("base_duration"));
					c.setBaseCost(rs.getDouble("base_cost"));
					c.setUnitCost(rs.getDouble("unit_cost"));
					c.setStatus(rs.getString("status"));
					c.setDescr(rs.getString("descr"));
					c.setCreatime(rs.getTimestamp("creatime"));
					c.setStartime(rs.getTimestamp("startime"));
					c.setCostType(rs.getString("cost_type"));
					return c;
				}
			public Cost findById(int id){
				Connection conn=null;
				try {
					conn=DBUtil.getConnection();
					String sql="select * from cost_mj where cost_id=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1, id);
					ResultSet rs=ps.executeQuery();
					while(rs.next()){
						return createCost(rs);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("根据ID查询资费失败");
				}finally{
					DBUtil.close(conn);
				}
				return null;
			}
			public void saveCost(Cost c){
				Connection  conn=null;
				try {
					conn=DBUtil.getConnection();
					String sql="insert into cost_mj values(cost_seq_mj.NEXTVAL,?,?,?,?,1,?,sysdate,null,?)";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setString(1, c.getName());
					//setInt/setDouble 方法不允许传入null
					//但当前的业务的确可能传入null,并且
					//数据库也允许存null,要想解决问题,
					//需要把这些数据当做普通的对象来看待及传入
					ps.setObject(2, c.getBaseDuration());
					ps.setObject(3, c.getBaseCost());
					ps.setObject(4, c.getUnitCost());
					ps.setString(5, c.getDescr());
					ps.setString(6, c.getCostType());
					ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("保存资费失败!");
				}finally{
					DBUtil.close(conn);
				}
			}
			public static void main(String[] args) {
				CostDao dao=new CostDao();
				/*List<Cost>  list=dao.findAll();
				for(Cost c:list){
					System.out.println(c.getCostId()+","+c.getName()+","+c.getDescr());
				}*/
				Cost c=new Cost();
				c.setName("66套餐");
				c.setBaseDuration(660);
				c.setBaseCost(66.0);
				c.setUnitCost(0.6);
				c.setDescr("实惠");
				c.setCostType("2");
				dao.saveCost(c);
			}
		
}
