package com.ubbys.admin.qna.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ubbys.admin.qna.service.SelectQnaService;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.board.vo.Pagination;

@WebServlet("/admin/qna/*")
public class SelectQnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI(); // 식별가능한 요청주소 	 ex) /ubbys/admin/qna/list
		String contextPath = request.getContextPath(); // 최상위 주소 ex) ubbys
		String command = uri.substring( (contextPath + "/admin/qna/").length() ); // list만 남음
		// uri에서 contextPath + "/admin/qna/" 만큼을 앞에서부터 잘라낸 나머지는 command에 저장
		
		String path = null; // 응답화면 주소 또는 경로
		RequestDispatcher view = null; // 요청 위임 객체 저장 참조 변수
		
		try {
			SelectQnaService service = new SelectQnaService();
			
			// 현재 페이지 저장 (int형 String을 반환)
			// 삼항 연산자를 이용해서 cp가 없으면 1, 있으면 int형태로 파싱한 cp값을 저장
			int cp = request.getParameter("cp") == null ? 1 : Integer.parseInt( request.getParameter("cp") );
			
			// 검색 조건
			String searchQnaCond = request.getParameter("searchQnaCond") == null ? "" : request.getParameter("searchQnaCond");
			String searchQnaCondText = request.getParameter("searchQnaCondText") == null ? "" : request.getParameter("searchQnaCondText");
			String searchOrder = request.getParameter("searchOrder") == null ? "" : request.getParameter("searchOrder");
			
			Qna qna = new Qna();
			qna.setSearchQnaCond(searchQnaCond);
			qna.setSearchQnaCondText(searchQnaCondText);
			qna.setSearchOrder(searchOrder);
			
			if(command.equals("list")) {
				// 페이징 처리를 위한 여러 정보를 담고있는 객체 Pagination 생성
				Pagination pagination = service.getPagination(cp, qna);
				
				List<Qna> qnaList = service.selectQnaList(pagination, qna);
				
				// pagination, boardList를 request에 속성으로 추가한 후 boardList.jsp로 forward
				request.setAttribute("pagination", pagination);
				request.setAttribute("qnaList", qnaList);
				request.setAttribute("qna", qna);
				
				path = "/WEB-INF/views/admin/qna/qnaList.jsp";
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
