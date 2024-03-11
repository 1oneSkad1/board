package com.kh.web.web;

import com.kh.web.domain.comments.svc.CommentsSVC;
import com.kh.web.domain.entity.Comments;
import com.kh.web.web.api.ApiResponse;
import com.kh.web.web.api.ResCode;
import com.kh.web.web.req.content.ResUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class ApiCommentsController {

  private final CommentsSVC commentsSVC;

  //댓글 등록
  @PostMapping("/add")
  public ApiResponse<?> addComments(@RequestBody Comments comments) {
    log.info("comments={}",comments);
    ApiResponse<?> res = null;
    Long commentsId = commentsSVC.saveComments(comments);
    log.info("commentsId={}", commentsId);

    if (commentsId != null) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), commentsId);
    } else {
      res = ApiResponse.createApiResponse(ResCode.NOK.getCode(), ResCode.NOK.name(), null);
    }
    return res;
  }

  //댓글 목록
  @GetMapping("/{bid}/list")
  public ApiResponse<?> list(@PathVariable("bid") Long boardId) {

    List<Comments> list = commentsSVC.findCommentsAll(boardId);

    ApiResponse<List<Comments>> res = null;
    if (list.size() > 0) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      res.setTotalCnt(commentsSVC.totalCnt());
    } else {
      res = ApiResponse.createApiResponseDetail(
          ResCode.OK.getCode(), ResCode.OK.name(), "댓글이 없습니다.", list);
    }
    return res;
  }

  //댓글 삭제
  @DeleteMapping("/{cid}/delete")
  public ApiResponse<?> delete(@PathVariable("cid") Long cid) {
    int deletedCnt = commentsSVC.deleteByCommentsId(cid);
    ApiResponse<ResUpdate> res = null;

    if(deletedCnt == 1){
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    }else{
      res = ApiResponse.createApiResponse(ResCode.NOK.getCode(), ResCode.NOK.name(), null);
    }
    return res;
  }

  //댓글 수정
  @PatchMapping("/{cid}/update")
  public ApiResponse<?> update(@RequestBody Comments comments) {
    int updatedCnt = commentsSVC.updateByCommentsId(comments);
    ApiResponse<ResUpdate> res = null;

    if (updatedCnt == 1) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.NOK.getCode(), ResCode.NOK.name(), null);
    }
    return res;
  }

}
