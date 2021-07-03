package com.ubbys.admin.qna.service;

import static com.ubbys.common.JDBCTemplate.close;
import static com.ubbys.common.JDBCTemplate.commit;
import static com.ubbys.common.JDBCTemplate.getConnection;
import static com.ubbys.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.ubbys.admin.qna.dao.QnaDAO;
import com.ubbys.admin.qna.vo.Qna;
import com.ubbys.admin.qna.vo.QnaCategory;

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

	/** qna 삭제 Service
	 * @param qna
	 * @return result
	 * @throws Exception
	 */
	public int updateQnaStatus(Qna qna) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateQnaStatus(conn, qna);
		
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	/** 좋아요 수 반환 Service
	 * @param qnaPostId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLikeCount(int qnaPostId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.qnaLikeCount(conn, qnaPostId);
		
		close(conn);
		
		return result;
	}
}
