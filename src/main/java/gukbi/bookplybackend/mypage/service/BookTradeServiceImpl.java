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
public class BookTradeServiceImpl implements BookTradeService{
    
    private final MyPageMapper myPageMapper;

    @Override
    @Transactional
    public ResponseDTO getTradeList(String mem_no) {
        ResponseDTO res = new ResponseDTO();
        
        List<Map<String,String>> tradeList = myPageMapper.getTradeList(mem_no);

        if(null != tradeList){
            res.setResCode(200);
            res.setResMsg("내도서거래 조회 성공");
            res.setData("tradeList", tradeList);
        } else{
            res.setResCode(300);
            res.setResMsg("내도서거래 조회 실패");
        }

        return res;
    }

    @Override
    public ResponseDTO deleteTrade(Map<String, Object> reqBody) {
        ResponseDTO res = new ResponseDTO();

        int updateRow = myPageMapper.deleteTrade(reqBody);

        if(updateRow > 0){
            res.setResCode(200);
            res.setResMsg("도서거래 게시글 삭제 성공");
        }else{
            res.setResCode(300);
            res.setResMsg("도서거래 게시글 삭제 실패");
        }

        return res;
    }
}