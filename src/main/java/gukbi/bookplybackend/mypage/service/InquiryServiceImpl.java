package gukbi.bookplybackend.mypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.mypage.dao.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService{
    
    private final MyPageMapper myPageMapper;

    @Override
    @Transactional
    public ResponseDTO getMyInquiry(String mem_no) { // 문의내역 조회
        ResponseDTO res = new ResponseDTO();
        
        List<Map<String, String>> inquiryList = myPageMapper.getMyInquiry(mem_no);

        if(null != inquiryList){
            res.setResCode(200);
            res.setResMsg("개별문의내역 조회 성공");
            res.setData("inquiryList", inquiryList);
        }else{
            res.setResCode(300);
            res.setResMsg("개별문의내역 조회 실패");
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseDTO deleteInquiry(Map<String, String> reqBody) { // 문의내역 삭제
        ResponseDTO res = new ResponseDTO();

        int updateRow = myPageMapper.deleteInquiry(reqBody);

        if(updateRow > 0) {
            res.setResCode(200);
            res.setResMsg("개별문의내역 삭제 성공");
        } else{
            res.setResCode(300);
            res.setResMsg("개별문의내역 삭제 실패");
        }

        return res;
    }
}