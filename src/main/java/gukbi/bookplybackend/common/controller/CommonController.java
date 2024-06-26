package gukbi.bookplybackend.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.common.service.CommonService;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
public class CommonController { // 공통으로 처리가 필요한 기능들 추가

  private static final int recordPage = 10;

  @Autowired
  CommonService commonService;

  // 대메뉴 가져오기
  @GetMapping("/sidebar/largeMenu/{level}")
  public ResponseDTO getLargeMenu(@PathVariable(value = "level") int level) {
    ResponseDTO res = commonService.getLargeMenu(level);
    return res;
  }

  // 소메뉴 가져오기
  @GetMapping("/sidebar/smallMenu")
  public ResponseDTO getSmallMenu() {
    ResponseDTO res = commonService.getSmallMenu();
    return res;
  }

  // 추천도서 등록정보 가져오기
  @GetMapping(value = "/main/sugBookInfo")
  public ResponseDTO sugBookInfo() {
    ResponseDTO res = commonService.sugBookInfo();
    return res;
  }

  // 도서 테이블 총 개수
  @GetMapping(value = "/main/bookCount")
  public ResponseDTO getBookCount(@RequestParam Map<String, String> sqlData) {
    ResponseDTO res = commonService.getBookCount(sqlData);
    return res;
  }

  // 도서전체목록 데이터 받기
  @GetMapping(value = "/main/bookList/{currentPage}")
  public ResponseDTO getBookList(@PathVariable(value = "currentPage") int currentPage,
      @RequestParam Map<String, String> sqlData) {
    Map<String, Object> pageData = new HashMap<String, Object>();
    pageData.put("recordPage", recordPage);
    pageData.put("currentPage", (currentPage - 1) * 10);
    pageData.put("search", sqlData.get("search"));

    ResponseDTO res = commonService.getBookList(pageData);
    return res;
  }

  // 도서 상세정보 가져오기
  @GetMapping(value = "/main/bookInfo/{isbn}")
  public ResponseDTO getBookInfo(@PathVariable(value = "isbn") String isbn) {
    ResponseDTO res = commonService.getBookInfo(isbn);
    return res;
  }

  // 도서 책소개 정보 가져오기
  @GetMapping(value = "/main/descript/{isbn}")
  public ResponseDTO getDescript(@PathVariable(value = "isbn") String isbn) {
    ResponseDTO res = commonService.getDescript(isbn);
    return res;
  }

  // 카테고리 도서 테이블 총 개수
  @GetMapping(value = "/main/catCount")
  public ResponseDTO getCatCount(@RequestParam Map<String, String> sqlData) {
    ResponseDTO res = commonService.getCatCount(sqlData);
    return res;
  }

  // 카테고리 도서전체목록 데이터 받기
  @GetMapping(value = "/main/catList/{currentPage}")
  public ResponseDTO getCatList(@PathVariable(value = "currentPage") int currentPage,
      @RequestParam Map<String, String> sqlData) {
    Map<String, Object> pageData = new HashMap<String, Object>();
    pageData.put("recordPage", recordPage);
    pageData.put("currentPage", (currentPage - 1) * 10);
    pageData.put("menuName", sqlData.get("menuName"));

    ResponseDTO res = commonService.getCatList(pageData);
    return res;
  }

  // 최신 공지사항 목록 가져오기
  @GetMapping(value = "/main/notiList")
  public ResponseDTO getNotiList() {
    ResponseDTO res = commonService.getNotiList();
    return res;
  }

  // 북플리 추천 목록 가져오기
  @GetMapping(value = "/main/bookPly/{favorite}")
  public ResponseDTO getBookPly(@PathVariable(value = "favorite") String favorite) {
    ResponseDTO res = commonService.getBookPly(favorite);
    return res;
  }

  // 책바구니에 담기
  @PostMapping(value = "/main/bookInfo/basket")
  public ResponseDTO basket(@RequestBody Map<String, Object> sqlData) {
    ResponseDTO res = commonService.basket(sqlData);
    return res;
  }

  // 책바구니에 빼기
  @DeleteMapping(value = "/main/bookInfo/basketDelete")
  public ResponseDTO basketDelete(@RequestParam Map<String, Object> sqlData) {
    ResponseDTO res = commonService.basketDelete(sqlData);
    return res;
  }

  // 책바구니에 전체 빼기
  @DeleteMapping(value = "/main/bookInfo/basketDeleteAll/{memNo}")
  public ResponseDTO basketDeleteAll(@PathVariable(value = "memNo") String memNo) {
    ResponseDTO res = commonService.basketDeleteAll(memNo);
    return res;
  }

  // 책바구니 목록 가져오기
  @GetMapping(value = "/main/bookInfo/basketList/{memNo}")
  public ResponseDTO basketList(@PathVariable(value = "memNo") String memNo) {
    ResponseDTO res = commonService.basketList(memNo);
    return res;
  }

  // 책바구니 내역 대여
  @PostMapping(value = "/main/userInfo/bookRent")
  public ResponseDTO bookRent(@RequestBody Map<String, Object> sqlData) {
    List<Map<String, Object>> bookData = (List<Map<String, Object>>) sqlData.get("basket");
    for (Map<String, Object> rent : bookData) {
      rent.put("memNo", sqlData.get("memNo"));
    }

    ResponseDTO res = commonService.bookRent(bookData);
    return res;
  }

  // 도서대여내역 조회
  @GetMapping(value = "/main/bookInfo/status/{bookNo}")
  public ResponseDTO getBookStatus(@PathVariable(value = "bookNo") String bookNo) {
    ResponseDTO res = commonService.getBookStatus(bookNo);
    return res;
  }

  // 카테고리 목록 조회
  @GetMapping(value = "/main/category")
  public ResponseDTO getCategory() {
    ResponseDTO res = commonService.getCategory();
    return res;
  }

  // 예약내역 조회
  @GetMapping(value = "/main/bookInfo/reservation")
  public ResponseDTO getReservation() {
    ResponseDTO res = commonService.getReservation();
    return res;
  }

  // 특정 시간에 연체 내역에 대한 이메일 전송
  @Scheduled(cron = "0 57 16 * * ?")
  public void sendMail() {
    commonService.sendMail();
  }

  @Scheduled(cron = "0 0 09 * * ?")
  public void updateRentalList() { // 매일 아침마다 대여내역을 최신화
    commonService.updateRentalList();
  }

  // 리뷰내역 조회
  @GetMapping(value = "/main/bookInfo/review/{isbn}")
  public ResponseDTO getReview(@PathVariable(value = "isbn") String isbn) {
    ResponseDTO res = commonService.getReview(isbn);
    return res;
  }
}
