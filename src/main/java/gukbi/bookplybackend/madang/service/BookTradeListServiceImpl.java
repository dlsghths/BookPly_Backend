package gukbi.bookplybackend.madang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.madang.dao.MadangMapper;

@Service
public class BookTradeListServiceImpl implements BookTradeListService{
    
    @Autowired
    MadangMapper madangMapper;

    @Override
    @Transactional
    public ResponseDTO getBookTradeCnt(Map<String, Object> reqBody) {
        ResponseDTO res = new ResponseDTO();

        int totalCnt = madangMapper.getBookTradeCnt(reqBody);
        
        if(totalCnt > 0){
            res.setResCode(200);
            res.setResMsg("거래도서 게시글 개수 조회 성공");
            res.setData("totalCnt", totalCnt);
        } else{
            res.setResCode(300);
            res.setResMsg("거래도서 게시글 개수 조회 실패");
        }
        return res;
    }

    @Override
    @Transactional
    public ResponseDTO getBookTradeList(Map<String, Object> pageMap) {
        ResponseDTO res = new ResponseDTO();

        List<Map<String,Object>> tradeList = madangMapper.getBookTradeList(pageMap);

        if(tradeList != null){
            res.setResCode(200);
            res.setResMsg("거래도서 게시글 조회 성공");
            res.setData("tradeList", tradeList);
        } else{
            res.setResCode(300);
            res.setResMsg("거래도서 게시글 조회 실패");
        }
        
        return res;
    }
}