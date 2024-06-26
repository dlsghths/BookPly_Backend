package gukbi.bookplybackend.mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.mypage.service.MyBookApplService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage/bookAppl")
public class MyBookApplController {

  private static final int showCnt = 10;

  @Autowired
  private final MyBookApplService bookApplService;

  // 희망도서 총 게시글 수
  @GetMapping(value = "/hopeBookCnt/{mem_no}")
  public ResponseDTO getHopeBookCnt(@PathVariable("mem_no") String mem_no) {
    ResponseDTO res = bookApplService.getHopeBookCnt(mem_no);
    return res;
  }

  // 희망도서 리스트 조회
  @GetMapping(value = "/hopeBookList/{nowPage}")
  public ResponseDTO hopeBookList(@PathVariable("nowPage") int nowPage, @RequestParam Map<String, String> reqBody) {
    Map<String, Object> pageMap = new HashMap<>();
    pageMap.put("showCnt", showCnt);
    pageMap.put("nowPage", (nowPage - 1) * 10);
    pageMap.put("mem_no", reqBody.get("mem_no"));

    ResponseDTO res = bookApplService.getHopeBookList(pageMap);
    return res;
  }

  // 희망도서 게시글 삭제
  @PostMapping(value = "/cancelHopeBook")
  public ResponseDTO cancelHopeBook(@RequestBody Map<String, String> reqBody) {
    ResponseDTO res = bookApplService.cancelHopeBook(reqBody);
    return res;
  }

  // 도서거래 게시글 등록
  @PostMapping(value = "/bookApplReg")
  public ResponseDTO postMethodName(@RequestBody Map<String, Object> reqBody) {
    ResponseDTO res = bookApplService.bookApplReg(reqBody);
    return res;
  }
}
