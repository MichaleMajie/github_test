package web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.CostDao;
import entity.Admin;
import entity.Cost;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import util.ImageUtil;

public class MainServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 获取请求路径
		String path = req.getServletPath();
		System.out.println(path);
		// 根据规范处理路径
		if ("/findcost.do".equals(path)) {
			findCost(req, res);
		} else if ("/toaddcost.do".equals(path)) {
			toAddCost(req, res);
		} else if ("/addcost.do".equals(path)) {
			addCost(req, res);
		} else if ("/toupdatecost.do".equals(path)) {
			String id=req.getParameter("id");
			CostDao dao=new CostDao();
		Cost cost=dao.findById(Integer.valueOf(id));
		req.setAttribute("cost", cost);
			req.getRequestDispatcher("WEB-INF/cost/update.jsp").forward(req, res);
		} else if ("/updatecost.do".equals(path)) {
			updateCost(req, res);
		}else if("/todeletecost.do".equals(path)){
			deleteCost(req,res);
		}
		else if ("/tologin.do".equals(path)) {
			toLogin(req, res);
		} else if ("/toindex.do".equals(path)) {
			toIndex(req, res);
		} else if ("/login.do".equals(path)) {
			login(req, res);
		} else if ("/createimg.do".equals(path)) {
			createImg(req, res);
		}else if("/tologout.do".equals(path)){
			HttpSession session=req.getSession();
			session.invalidate();
			res.sendRedirect("tologin.do");
		}

		else {
			throw new RuntimeException("没有这个资源!");
		}
	}

	// 生成验证码
	protected void createImg(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException {
			//创建验证码及图片

			Object[] objs = ImageUtil.createImage();
			//将验证码存入session
			HttpSession sn = req.getSession();
			sn.setAttribute("imgCode", objs[0]);
			//将图片输出给浏览器
			res.setContentType("image/png");
			OutputStream os = res.getOutputStream();
			ImageIO.write(
				(BufferedImage) objs[1], "png", os);
			os.close();
		}

	// 登录验证
	private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
		String code=req.getParameter("code");
		//检查验证码
		HttpSession session=req.getSession();
		String imgcode=(String)session.getAttribute("imgCode");
		if(code==null||!code.equalsIgnoreCase(imgcode)){
			req.setAttribute("error", "验证码错误！");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
			return;
		}
		// 验证账号和密码
		AdminDao dao = new AdminDao();
		Admin a = dao.findByCode(adminCode);
		if (a == null) {
			req.setAttribute("error", "账号错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
		} else if (!a.getPassword().equals(password)) {
			req.setAttribute("error", "密码错误");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
		} else {
			// 把账号存入cookie
			Cookie c = new Cookie("user", adminCode);
			res.addCookie(c);
			// 将账号存入session
			
			session.setAttribute("user", adminCode);
			// 重定向到主页 ;
			res.sendRedirect("toindex.do");
		}
	}

	private void toIndex(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);

	}

	private void toLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
	}

	// 修改资费数据
	private void updateCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cost_id = req.getParameter("cost_id");
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
		// 保存这条数据
		Cost c = new Cost();
		c.setCostId(Integer.valueOf(cost_id));
		c.setName(name);
		c.setCostType(costType);
		if (baseDuration != null && baseDuration.length() > 0) {
			c.setBaseDuration(Integer.valueOf(baseDuration));
		}
		if (baseCost != null && baseCost.length() > 0) {
			c.setBaseCost(Double.valueOf(baseCost));
		}
		if (unitCost != null && unitCost.length() > 0) {
			c.setUnitCost(Double.valueOf(unitCost));
		}
		c.setDescr(descr);
		new CostDao().updateCost(c);
		// 重定向到查询
		// 当前:/NETCTOSS/addcost.dos
		// 目标:/NETCTOSS/findcost.do
		res.sendRedirect("findcost.do");

	}

	// 保存资费数据
	private void addCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 接受参数
		req.setCharacterEncoding("utf-8");
		String cost_id = req.getParameter("cost_id");

		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
		// 保存这条数据
		Cost c = new Cost();
		c.setCostId(Integer.valueOf(cost_id));

		c.setName(name);
		c.setCostType(costType);
		if (baseDuration != null && baseDuration.length() > 0) {
			c.setBaseDuration(Integer.valueOf(baseDuration));
		}
		if (baseCost != null && baseCost.length() > 0) {
			c.setBaseCost(Double.valueOf(baseCost));
		}
		if (unitCost != null && unitCost.length() > 0) {
			c.setUnitCost(Double.valueOf(unitCost));
		}
		c.setDescr(descr);
		new CostDao().saveCost(c);
		// 重定向到查询
		// 当前:/NETCTOSS/addcost.do
		// 目标:/NETCTOSS/findcost.do
		res.sendRedirect("findcost.do");
	}

	// 查询资费
	private void findCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 1.查询所有的资费
		CostDao dao = new CostDao();
		List<Cost> list = dao.findAll();
		// 2.转发至find.jsp
		// 2.1将数据存入request
		req.setAttribute("costs", list);
		// 2.2转发
		// 当前:/NETCTOSS/findcost.do
		// 目标:/NETCTOSS/WEB-INF/cost/find.jsp
		req.getRequestDispatcher("WEB-INF/cost/find.jsp").forward(req, res);
	}
	private void deleteCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				CostDao dao=new CostDao();
				String id=req.getParameter("id");
				dao.deleteCost(new Integer(id));
				res.sendRedirect("findcost.do");
	}
	protected void toAddCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/cost/add.jsp").forward(req, res);
	}

}
