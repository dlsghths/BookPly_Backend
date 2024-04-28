package gukbi.bookplybackend.common.service;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gukbi.bookplybackend.common.dao.CommonMapper;
import gukbi.bookplybackend.common.dto.ResponseDTO;

@Service
public class CommonServiceImpl implements CommonService {

  @Autowired
  CommonMapper commonMapper;

  @Autowired
  ObjectMapper objectMapper;

  @Value("${app.data4library.url}")
  private String baseUrl;

  @Value("${app.data4library.api-key}")
  private String authKey;

  @Override
  @Transactional // 대메뉴 가져오기
  public ResponseDTO getLargeMenu(int level) {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> menuList = commonMapper.getLargeMenu(level);

    if(!menuList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("대메뉴 리스트 조회");
      res.setData("menuList", menuList);
    } else {
      res.setResCode(300);
      res.setResMsg("대메뉴 리스트 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 소메뉴 가져오기
  public ResponseDTO getSmallMenu() {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> menuList = commonMapper.getSmallMenu();

    if(!menuList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("소메뉴 리스트 조회");
      res.setData("menuList", menuList);
    } else {
      res.setResCode(300);
      res.setResMsg("소메뉴 리스트 조회에 실패했습니다.");
    }

    return res;
  }

	@Override
  @Transactional // 추천도서 정보 가져오기
	public ResponseDTO sugBookInfo() {
		ResponseDTO res = new ResponseDTO();
    Map<String, Object> bookInfo = commonMapper.sugBookInfo();

    if(bookInfo != null) {
      res.setResCode(200);
      res.setResMsg("추천도서 정보 조회");
      res.setData("sugBookInfo", bookInfo);
    } else {
      res.setResCode(300);
      res.setResMsg("추천도서 정보 조회에 실패했습니다.");
    }

    return res;
	}

  @Override
  @Transactional // 총 도서 개수 가져오기 
  public ResponseDTO getBookCount(Map<String, String> sqlData) {
    ResponseDTO res = new ResponseDTO();
    int bookCount = commonMapper.getBookCount(sqlData);

    if(bookCount >= 0) {
      res.setResCode(200);
      res.setResMsg("총 도서 개수 조회");
      res.setData("bookCount", bookCount);
    } else {
      res.setResCode(300);
      res.setResMsg("도서 개수 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 현재 페이지 도서목록 가져오기
  public ResponseDTO getBookList(Map<String, Object> pageData) {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> bookList = commonMapper.getBookList(pageData);

    if(!bookList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("도서 리스트 조회");
      res.setData("bookList", bookList);
    } else {
      res.setResCode(300);
      res.setResMsg("도서 리스트 조회에 실패했습니다.");
    }

    return res;
  }
  
  @Override
  @Transactional // 도서 상세정보 가져오기
  public ResponseDTO getBookInfo(String isbn) {
    ResponseDTO res = new ResponseDTO();
    Map<String, Object> bookInfo = commonMapper.getBookInfo(isbn);

    if(bookInfo != null) {
      res.setResCode(200);
      res.setResMsg("도서 상세정보 조회");
      res.setData("bookInfo", bookInfo);
    } else {
      res.setResCode(300);
      res.setResMsg("도서 상세정보 조회에 실패했습니다.");
    }
    
    return res;
  }

  @Override
  @Transactional // 도서 책소개 정보 가져오기
  public ResponseDTO getDescript(String isbn) {
    ResponseDTO res = new ResponseDTO();
    URI uri = UriComponentsBuilder.fromUriString(baseUrl)
              .queryParam("authKey", authKey)
              .queryParam("isbn13", isbn)
              .queryParam("format", "json")
              .build(true)
              .toUri();

    String jsonString = WebClient.builder().baseUrl(baseUrl)
              .build()
              .get()
              .uri(uri)
              .retrieve()
              .bodyToMono(String.class)
              .block();

    try {
      JsonNode jsonNode = objectMapper.readTree(jsonString).get("response")
                    .get("detail").get(0).get("book").get("description");
      String result = objectMapper.readValue(jsonNode.toString(), String.class);
      res.setResCode(200);
      res.setResMsg("도서 책소개 정보 조회");
      res.setData("descript", result);
    } catch (Exception e) {
      System.out.println("getDescript error: " + e.getMessage());
      res.setResCode(400);
      res.setResMsg("도서 상세정보 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 총 카테고리 도서 개수 가져오기
  public ResponseDTO getCatCount(Map<String, String> sqlData) {
    ResponseDTO res = new ResponseDTO();
    int bookCount = commonMapper.getCatCount(sqlData);

    if(bookCount >= 0) {
      res.setResCode(200);
      res.setResMsg("총 도서 개수 조회");
      res.setData("bookCount", bookCount);
    } else {
      res.setResCode(300);
      res.setResMsg("도서 개수 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 카테고리 현재 페이지 도서목록 가져오기
  public ResponseDTO getCatList(Map<String, Object> pageData) {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> bookList = commonMapper.getCatList(pageData);

    if(!bookList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("도서 리스트 조회");
      res.setData("bookList", bookList);
    } else {
      res.setResCode(300);
      res.setResMsg("도서 리스트 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 최신 공지사항 목록 가져오기
  public ResponseDTO getNotiList() {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> notiList = commonMapper.getNotiList();

    if(!notiList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("공지사항 리스트 조회");
      res.setData("notiList", notiList);
    } else {
      res.setResCode(300);
      res.setResMsg("공지사항 리스트 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 북플리 추천 목록 가져오기
  public ResponseDTO getBookPly() {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> bookPlyList = commonMapper.getBookPly();

    if(!bookPlyList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("북플리 리스트 조회");
      res.setData("bookPlyList", bookPlyList);
    } else {
      res.setResCode(300);
      res.setResMsg("북플리 리스트 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 책바구니에 담기
  public ResponseDTO basket(Map<String, Object> sqlData) {
    try {
      ResponseDTO res = new ResponseDTO();
      int result = commonMapper.basket(sqlData);

      if(result == 1) {
        res.setResCode(200);
        res.setResMsg("책바구니 정보 등록");
        res.setData("basket", result);
      } else {
        res.setResCode(300);
        res.setResMsg("책바구니 정보 등록에 실패했습니다.");
      }

      return res;
    } catch (DataIntegrityViolationException e) {
      ResponseDTO res = new ResponseDTO();
      res.setResCode(500);
      res.setResMsg("이미 등록되어 있는 정보 입니다.");
      return res;
    }
  }

  @Override
  @Transactional // 책바구니에 빼기
  public ResponseDTO basketDelete(Map<String, Object> sqlData) {
    ResponseDTO res = new ResponseDTO();
    int result = commonMapper.basketDelete(sqlData);

    if(result == 1) {
      res.setResCode(200);
      res.setResMsg("책바구니 정보 삭제");
      res.setData("basket", result);
    } else {
      res.setResCode(300);
      res.setResMsg("책바구니 정보 삭제에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 책바구니 목록 가져오기
  public ResponseDTO basketList(String memNo) {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> basketList = commonMapper.basketList(memNo);

    if(!basketList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("책바구니 리스트 조회");
      res.setData("basketList", basketList);
    } else {
      res.setResCode(300);
      res.setResMsg("책바구니 리스트 조회에 실패했습니다.");
    }

    return res;
  }
}
