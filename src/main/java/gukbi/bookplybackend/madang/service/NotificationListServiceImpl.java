package gukbi.bookplybackend.madang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gukbi.bookplybackend.common.dto.ResponseDTO;
import gukbi.bookplybackend.madang.dao.MadangMapper;

@Service
public class NotificationListServiceImpl implements NotificationListService {

    @Autowired
    MadangMapper madangMapper;

    @Override
    @Transactional
    public ResponseDTO notiListCnt(Map<String, Object> reqBody) {
        ResponseDTO res = new ResponseDTO();

        int totalCnt = madangMapper.notiListCnt(reqBody);

        if (totalCnt > 0) {
            res.setResCode(200);
            res.setResMsg("공지사항 게시글 수 조회 성공");
            res.setData("totalCnt", totalCnt);
        } else {
            res.setResCode(300);
            res.setResMsg("공지사항 게시글 수 조회 실패");
        }

        return res;
    }

    @Override
    @Transactional
    public ResponseDTO notiList(Map<String, Object> pageMap) {
        ResponseDTO res = new ResponseDTO();

        List<Map<String, Object>> notiList = madangMapper.notiList(pageMap);

        if(notiList != null){
            res.setResCode(200);
            res.setResMsg("공지사항 조회 성공");
            res.setData("notiList", notiList);
        } else{
            res.setResCode(300);
            res.setResMsg("공지사항 조회 실패");
        }
        return res;
    }
}
