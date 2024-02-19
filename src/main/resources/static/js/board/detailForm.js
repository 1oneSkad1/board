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