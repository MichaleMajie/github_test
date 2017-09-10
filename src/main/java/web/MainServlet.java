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
		// ��ȡ����·��
		String path = req.getServletPath();
		System.out.println(path);
		// ���ݹ淶����·��
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
			throw new RuntimeException("û�������Դ!");
		}
	}

	// ������֤��
	protected void createImg(
			HttpServletRequest req,
			HttpServletResponse res) 
			throws ServletException, IOException {
			//������֤�뼰ͼƬ

			Object[] objs = ImageUtil.createImage();
			//����֤�����session
			HttpSession sn = req.getSession();
			sn.setAttribute("imgCode", objs[0]);
			//��ͼƬ����������
			res.setContentType("image/png");
			OutputStream os = res.getOutputStream();
			ImageIO.write(
				(BufferedImage) objs[1], "png", os);
			os.close();
		}

	// ��¼��֤
	private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String adminCode = req.getParameter("adminCode");
		String password = req.getParameter("password");
		String code=req.getParameter("code");
		//�����֤��
		HttpSession session=req.getSession();
		String imgcode=(String)session.getAttribute("imgCode");
		if(code==null||!code.equalsIgnoreCase(imgcode)){
			req.setAttribute("error", "��֤�����");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
			return;
		}
		// ��֤�˺ź�����
		AdminDao dao = new AdminDao();
		Admin a = dao.findByCode(adminCode);
		if (a == null) {
			req.setAttribute("error", "�˺Ŵ���");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
		} else if (!a.getPassword().equals(password)) {
			req.setAttribute("error", "�������");
			req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
		} else {
			// ���˺Ŵ���cookie
			Cookie c = new Cookie("user", adminCode);
			res.addCookie(c);
			// ���˺Ŵ���session
			
			session.setAttribute("user", adminCode);
			// �ض�����ҳ ;
			res.sendRedirect("toindex.do");
		}
	}

	private void toIndex(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/index.jsp").forward(req, res);

	}

	private void toLogin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/main/login.jsp").forward(req, res);
	}

	// �޸��ʷ�����
	private void updateCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cost_id = req.getParameter("cost_id");
		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
		// ������������
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
		// �ض��򵽲�ѯ
		// ��ǰ:/NETCTOSS/addcost.dos
		// Ŀ��:/NETCTOSS/findcost.do
		res.sendRedirect("findcost.do");

	}

	// �����ʷ�����
	private void addCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// ���ܲ���
		req.setCharacterEncoding("utf-8");
		String cost_id = req.getParameter("cost_id");

		String name = req.getParameter("name");
		String costType = req.getParameter("costType");
		String baseDuration = req.getParameter("baseDuration");
		String baseCost = req.getParameter("baseCost");
		String unitCost = req.getParameter("unitCost");
		String descr = req.getParameter("descr");
		// ������������
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
		// �ض��򵽲�ѯ
		// ��ǰ:/NETCTOSS/addcost.do
		// Ŀ��:/NETCTOSS/findcost.do
		res.sendRedirect("findcost.do");
	}

	// ��ѯ�ʷ�
	private void findCost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 1.��ѯ���е��ʷ�
		CostDao dao = new CostDao();
		List<Cost> list = dao.findAll();
		// 2.ת����find.jsp
		// 2.1�����ݴ���request
		req.setAttribute("costs", list);
		// 2.2ת��
		// ��ǰ:/NETCTOSS/findcost.do
		// Ŀ��:/NETCTOSS/WEB-INF/cost/find.jsp
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
