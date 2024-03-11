const bid = document.getElementById('boardId').value;      //게시글 번호

const $deleteBtn = document.getElementById('deleteBtn');  //삭제 버튼
const $delModal = document.getElementById('delModal');    //모달 그 잡채
const $delItemBtn = document.getElementById('delItemBtn');  //모달 속 삭제 확인 버튼

// 모달창 오픈
$deleteBtn.addEventListener('click', evt=>{
  $delModal.showModal();
});

// 모달창에서 삭제 확인
$delItemBtn.addEventListener('click', evt=>{
  $delModal.close('ok');
});

//삭제 확인 환료
$delModal.addEventListener('close', evt=>{
    if($delModal.returnValue == 'ok'){
      location.href = `/boards/${bid}/del`;       //GET http://서버주소 or 서버도메인/boards/게시글번호/del
    }
});

//댓글 기능
//목록 가져오기
list(bid);
//목록
async function list(bid) {
  // const reqPage = pagination.currentPage;   //요청 페이지
  // const reqCnt = pagination.recordsPerPage; //페이지당 레코드수

  //const url = `http://localhost:9080/api/products?reqPage=${reqPage}&reqCnt=${reqCnt}`;
  const url = `http://localhost:9080/comment/${bid}/list`;
  const option = {
    method:'GET',
    headers:{
      accept:'application/json'
    }
  };
  try {
    const res = await fetch(url,option);
    if(!res.ok) return new Error('서버응답오류')
    const result = await res.json(); //응답메세지 바디를 읽어 json포맷 문자열=>js객체
    if(result.header.rtcd == '00'){
//      console.log(result.body);
      const str = result.body.map(reply=>
                                    `<li>
                                      <span style = "display:none" class="cid">${reply.commentsId}</span>
                                      <span style = "display:none" class="mid">${reply.memberId}</span>
                                      <span>${reply.commentsContent}</span>
                                      <div>
                                        <span>${reply.writer}</span>
                                        <button class="updBtn">수정</button>
                                        <button class="delBtn">삭제</button>
                                      </div>
                                    </li>`).join('');
      $ul = document.querySelector('.comments-wrap ul'); 
      $ul.innerHTML = str;
      $updBtns = $ul.querySelectorAll('.updBtn');
      $delBtns = $ul.querySelectorAll('.delBtn');
      //수정
      Array.from($updBtns).forEach(btn=>btn.addEventListener('click',evt=>{
        // 부모 요소인 <li>를 찾아서
        const liElement = btn.closest('li');

        // liElement 내부의 cid와 mid 값을 가져옴
        const cid = liElement.querySelector('.cid').innerText;
        const mid = liElement.querySelector('.mid').innerText;

        console.log(cid);   //댓글 번호
        console.log(mid);   //작성자 번호
        const comment = {
          'commentsId' : cid,
          'memberId' : mid,
          'commentsContent' : commentForm.querySelector('textarea').value
        }
        //자신이 등록한 댓글만 수정 및 삭제 기능
        if(comment.memberId == commentForm.querySelector('.memberId').textContent){
            update(comment);
        } else {
            alert('자신이 등록한 댓글만 수정 및 삭제 가능');
        }
      }));
      //삭제
      Array.from($delBtns).forEach(btn=>btn.addEventListener('click',evt=>{
        // 부모 요소인 <li>를 찾아서
        const liElement = btn.closest('li');
        // liElement 내부의 cid와 mid 값을 가져옴
        const cid = liElement.querySelector('.cid').innerText;
        const mid = liElement.querySelector('.mid').innerText;
        console.log(cid);   //댓글 번호
        console.log(mid);   //작성자 번호
        //자신이 등록한 댓글만 수정 및 삭제 기능
        if(mid == commentForm.querySelector('.memberId').textContent){
            del(cid);
        } else {
            alert('자신이 등록한 댓글만 수정 및 삭제 가능');
        }
      }));

      // 총 레코드 건수
      // pagination.setTotalRecords(result.totalCnt);
      // pagination.displayPagination(list);

    }else{
      new Error('목록 불러오기 실패');
    }
  }catch(err){
    console.error(err.message);
  }
}

//댓글 등록
async function addComment(comment) {
  const url = `/comment/add`;
  const payload = comment;

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      accept: 'application/json',
    },
    body: JSON.stringify(payload)   // js객체=>json포맷 문자열
  };
  console.log(payload);
  try {
    const response = await fetch(url, options);
    const data = await response.json();
    list(bid);
    // 성공적으로 추가된 경우에 대한 처리
    console.log('코멘트가 성공적으로 추가되었습니다:', data);

  } catch (error) {
    // 오류가 발생한 경우에 대한 처리
    console.error('코멘트 추가 중 오류 발생:', error);
  }
}

commentForm.querySelector('.addBtn').addEventListener('click',evt=>{
  const comment = {
    'boardId' : boardId.value,
    'commentsContent' : commentForm.querySelector('textarea').value,
    'writer' : commentForm.querySelector('.writer').textContent,
    'memberId' : commentForm.querySelector('.memberId').textContent
  }
  addComment(comment);
})

//댓글삭제
async function del(cid) {
  const url = `http://localhost:9080/comment/${cid}/delete`;
  const option = {
    method: 'DELETE',
    headers: {
      'accept': 'application/json',
    }
  }
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); // 응답 메세지 바디를 읽어 json포맷 문자열 => js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);
      list(bid);    //목록 출력
    } else {
      new Error('삭제 실패');
    }
    console.log(result);
  } catch (err) {
    console.err(err.message);
  }
}

//수정
async function update(comment) {
    const url = `http://localhost:9080/comment/${comment.cid}/update`;
    const payload = comment
    const option = {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',  // 요청메세지 바디의 데이터포맷 타입
        accept: 'application/json',          // 응답메세지 바다의 데이터포맷 타입
      },
      body: JSON.stringify(payload), // js객체=>json포맷 문자열
    };
    try {
      const res = await fetch(url, option);
      if (!res.ok) return new Error('서버응답오류');
      const result = await res.json(); //응답메세지 바디를 읽어 json포맷 문자열=>js객체
      if (result.header.rtcd == '00') {
        console.log(result.body);
        list(bid);
      } else {
        new Error('수정 실패!');
      }
    } catch (err) {
      console.error(err.message);
    }
}