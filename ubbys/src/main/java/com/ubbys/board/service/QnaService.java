package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.QnaDAO;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaCategory;
import com.ubbys.board.vo.QnaPagination;

public class QnaService {
	
	private QnaDAO dao = new QnaDAO();

	/** 카테고리 목록 조회 Service
	 * @return qnaCategory
	 * @throws Exception
	 */
	public List<QnaCategory> selectQnaCategory() throws Exception {
		
		Connection conn = getConnection();
		
		List<QnaCategory> qnaCategory = dao.selectQnaCategory(conn);
		
		close(conn);
		
		return qnaCategory;
	}

	/** qna 글 삽입 Service
	 * @param qna
	 * @return result
	 * @throws Exception
	 */
	public int insertQna(Qna qna) throws Exception {
		
		Connection conn = getConnection();
		
		int qnaPostId = dao.nextQnaPostId(conn);
		
		int result = 0;
		
		if(qnaPostId>0) {
			qna.setQnaPostId(qnaPostId);
			qna.setQnaContent(replaceParameter(qna.getQnaContent()));
			qna.setQnaTitle(replaceParameter(qna.getQnaTitle()));
			qna.setQnaContent(qna.getQnaContent().replace("\r\n", "<br>"));
			
			result = dao.insertQna(conn, qna);
			
			if(result>0) {
				commit(conn);
				result = qnaPostId;
			} else {
				rollback(conn);
			}
		
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	/** XSS 방지 메소드
	 * @param param
	 * @return result
	 */
	private String replaceParameter(String param) {
		
		String result = param;
		if(param != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		return result;
	}

	/** qna 수정 Service
	 * @param qna
	 * @return result
	 * @throws Exception
	 */
	public int updateQna(Qna qna) throws Exception {
		
		Connection conn = getConnection();
		
		qna.setQnaContent(replaceParameter(qna.getQnaContent()));
		qna.setQnaTitle(replaceParameter(qna.getQnaTitle()));
		qna.setQnaContent(qna.getQnaContent().replace("\r\n", "<br>"));
		
		int result = dao.updateQna(conn, qna);
		
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}

}
