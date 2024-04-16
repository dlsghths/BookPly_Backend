package gukbi.bookplybackend.mypage.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.mypage.dao.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{
    
    private final MyPageMapper myPageMapper;

    @Override
    @Transactional
    public ResponseDTO getIdDuplicate(String mem_id) { // 아이디 중복체크
       ResponseDTO res = new ResponseDTO();

       int count = myPageMapper.getIdDuplicate(mem_id);
       boolean isDuplicate = count > 0;

       res.setResCode(200);
       if(isDuplicate){
        res.setResMsg("중복된 아이디입니다.");
       }else{
        res.setResMsg("사용 가능한 아이디입니다.");
       }

       res.setData("isDuplicate", isDuplicate);

       return res;
    }

    @Override
    @Transactional
    public ResponseDTO getCurrentPwd(String mem_no){ // 현재비밀번호 조회
        ResponseDTO res = new ResponseDTO();

        String currentPwd = myPageMapper.getCurrentPwd(mem_no);

        res.setResCode(200);
        res.setResMsg("현재 비밀번호 조회");
        res.setData("currentPwd", currentPwd);

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO pwdUpdate(Map<String, String> reqBody){ // 비밀번호 수정
        ResponseDTO res = new ResponseDTO();

        int updateRow = myPageMapper.pwdUpdate(reqBody);


        if(updateRow > 0){
            res.setResCode(200);
            res.setResMsg("비밀번호 수정 성공");
            
        }else{
            res.setResCode(300);
            res.setResMsg("비밀번호 수정 실패");
        }    

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO accountDelete(Map<String,String> reqBody){ // 회원 탈퇴
        ResponseDTO res = new ResponseDTO();

        // 논리적 삭제 use_flag = 'N'로 update
        int deleteRow = myPageMapper.accountDelete(reqBody);

        if(deleteRow > 0){
            res.setResCode(200);
            res.setResMsg("회원탈퇴 성공");
        }else{
            res.setResCode(300);
            res.setResMsg("회원탈퇴 실패");
        }

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO updateUserInfo(Map<String, String> reqBody){ // 회원정보 수정
        ResponseDTO res = new ResponseDTO();

        int updateRow = myPageMapper.updateUserInfo(reqBody);

        if(updateRow > 0){
            res.setResCode(200);
            res.setResMsg("회원정보 수정 성공");      
        }else{
            res.setResCode(300);
            res.setResMsg("회원정보 수정 실패");
        }

        return res;
    }
}