package gukbi.bookplybackend.mypage.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.mypage.dao.MyPageMapper;
import gukbi.bookplybackend.mypage.service.MyBookApplService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyBookApplServiceImpl implements MyBookApplService {

  private final MyPageMapper myPageMapper;

  @Override
  @Transactional
  public ResponseDTO getHopeBookCnt(String mem_no) {
    ResponseDTO res = new ResponseDTO();
    int totalCnt = myPageMapper.getHopeBookCnt(mem_no);

    if (totalCnt >= 0) {
      res.setResCode(200);
      res.setResMsg("희망 도서 수 조회");
      res.setData("totalCnt", totalCnt);
    } else {
      res.setResCode(300);
      res.setResMsg("희망 도서 수 조회 실패");
    }
    return res;
  }

  @Override
  @Transactional
  public ResponseDTO getHopeBookList(Map<String, Object> pageMap) { // 희망도서 조회
    ResponseDTO res = new ResponseDTO();
    List<Map<String, String>> hopeBookList = myPageMapper.getHopeBookList(pageMap);

    res.setResCode(200);
    res.setResMsg("희망 도서 리스트 조회");
    res.setData("hopeBookList", hopeBookList);

    return res;
  }

  @Override
  @Transactional
  public ResponseDTO cancelHopeBook(Map<String, String> reqBody) { // 희망도서 취소
    ResponseDTO res = new ResponseDTO();
    int updateRow = myPageMapper.cancelHopeBook(reqBody);

    if (updateRow > 0) {
      res.setResCode(200);
      res.setResMsg("희망 도서취소 성공");
    } else {
      res.setResCode(300);
      res.setResMsg("희망 도서취소 실패");
    }

    return res;
  }

  @Override
  @Transactional
  public ResponseDTO bookApplReg(Map<String, Object> reqBody) {
    ResponseDTO res = new ResponseDTO();
    int insertRow = myPageMapper.bookApplReg(reqBody);

    if (insertRow > 0) {
      res.setResCode(200);
      res.setResMsg("도서거래 등록 성공");
    } else {
      res.setResCode(300);
      res.setResMsg("도서거래 등록 실패");
    }
    return res;
  }
}
