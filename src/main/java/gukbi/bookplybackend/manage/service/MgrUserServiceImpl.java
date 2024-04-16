package gukbi.bookplybackend.manage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.manage.dao.MgrMapper;

@Service
public class MgrUserServiceImpl implements MgrUserService{

  @Autowired
  MgrMapper manageMapper;

  @Autowired
  ObjectMapper objectMapper;

  @Override
  @Transactional // 총 회원 개수 가져오기 
  public ResponseDTO getUserCount(Map<String, String> sqlData) {
    ResponseDTO res = new ResponseDTO();
    int userCount = manageMapper.getUserCount(sqlData);

    if(userCount >= 0) {
      res.setResCode(200);
      res.setResMsg("총 회원 개수 조회");
      res.setData("userCount", userCount);
    } else {
      res.setResCode(300);
      res.setResMsg("회원 개수 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 현재 페이지 회원목록 가져오기
  public ResponseDTO getUserList(Map<String, Object> pageData) {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> userList = manageMapper.getUserList(pageData);

    if(!userList.isEmpty()) {
      res.setResCode(200);
      res.setResMsg("회원 리스트 조회");
      res.setData("userList", userList);
    } else {
      res.setResCode(300);
      res.setResMsg("회원 리스트 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 총 회원 대여/연체정보 개수 가져오기
  public ResponseDTO getRentCount(Map<String, String> sqlData) {
    ResponseDTO res = new ResponseDTO();
    int rentCount = manageMapper.getUserRentCount(sqlData);

    if(rentCount >= 0) {
      res.setResCode(200);
      res.setResMsg("총 회원 대여/연체정보 개수 조회");
      res.setData("rentCount", rentCount);
    } else {
      res.setResCode(300);
      res.setResMsg("회원 대여/연체정보 개수 조회에 실패했습니다.");
    }

    return res;
  }

  @Override
  @Transactional // 회원 대여/연체정보 가져오기
  public ResponseDTO getUserInfo(Map<String, Object> pageData) {
    ResponseDTO res = new ResponseDTO();
    List<Map<String, Object>> userInfo = manageMapper.getUserInfo(pageData);

    if(userInfo != null) {
      res.setResCode(200);
      res.setResMsg("회원 대여/연체정보 조회");
      res.setData("userInfo", userInfo);
    } else {
      res.setResCode(300);
      res.setResMsg("회원 대여/연체정보 조회에 실패했습니다.");
    }
    
    return res;
  }

  @Override
  @Transactional // 회원 연체정보 개수 가져오기
  public ResponseDTO getOverdueCount(String memNo) {
    ResponseDTO res = new ResponseDTO();
    int overdueCount = manageMapper.getOverdueCount(memNo);

    if(overdueCount >= 0) {
      res.setResCode(200);
      res.setResMsg("회원 연체정보 개수 조회");
      res.setData("overdueCount", overdueCount);
    } else {
      res.setResCode(300);
      res.setResMsg("회원 연체정보 개수 조회에 실패했습니다.");
    }

    return res;
  }
}