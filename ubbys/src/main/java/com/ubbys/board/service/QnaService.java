package com.ubbys.board.service;

import static com.ubbys.common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.List;

import com.ubbys.board.dao.QnaDAO;
import com.ubbys.board.vo.Qna;
import com.ubbys.board.vo.QnaCategory;
import com.ubbys.board.vo.QnaPagination;
import com.ubbys.user.vo.User;

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
	
	
	/** qna 게시글 삭제 접근권한 확인 Service
	 * @param qnaPostId
	 * @return userId
	 * @throws Exception
	 */
	public int postingUserCheck(int qnaPostId) throws Exception {
		
		Connection conn = getConnection();
		
		int userId = dao.postingUserCheck(conn, qnaPostId);
		
		close(conn);
		
		return userId;
	}
	/** qna 게시글 삭제 Service
	 * @param qnaPostId
	 * @return result
	 * @throws Exception
	 */
	public int deleteQna(int qnaPostId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.deleteQna(conn, qnaPostId);
		
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		return result;
	}
	
	/** qna 게시글에 좋아요를 누른 userList Service
	 * @param qnaPostId
	 * @return uList
	 * @throws Exception
	 */
	public List<User> selectUserList(int qnaPostId) throws Exception {
		
		Connection conn = getConnection();
		
		List<User> uList = dao.selectUserList(conn, qnaPostId);
		
		close(conn);
		
		return uList;
	}
	
	
	/** qna 게시글 좋아요 처리 Service
	 * @param qnaPostId
	 * @param userId
	 * @return result
	 * @throws Exception
	 */
	public int qnaLike(int qnaPostId, int userId) throws Exception {
		
		Connection conn = getConnection();
		
		// DB에 qnaPostId, userId 가 일치하는 좋아요 행이 있는지 확인하기
		int result = dao.qnaLikeCheck(conn, qnaPostId, userId);
		
		if(result==0) { // 해당하는 행이 없는 경우이므로 좋아요 insert 후 해당 포스트에 좋아요 수 증가처리
			
			result = dao.qnaLike(conn, qnaPostId, userId);
			if(result>0) {
				result = dao.qnaLikeIncrease(conn, qnaPostId);
				if(result>0) commit(conn);
				else		 rollback(conn);
				result = 1;
			} else {
				rollback(conn);
			}
			
		} else { //좋아요 delete 후 해당 포스트에 좋아요 수 감소처리
			
			result = dao.qnaLikeCancel(conn, qnaPostId, userId);
			if(result>0) {
				result = dao.qnaLikeDecrease(conn, qnaPostId);
				if(result>0) commit(conn);
				else		 rollback(conn);
				result = 0;
			} else {
				rollback(conn);
			}
		}
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
