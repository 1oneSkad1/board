// 게시글 추가 버튼
const $addBtn = document.getElementById('addBtn');
$addBtn.addEventListener('click', evt=>{
  location.href = '/boards/add';
});
// 게시글 조회
const $rows = document.getElementById('rows');
$rows.addEventListener('click',evt=>{
  const $row = evt.target.closest('.row');
  const boardId = $row.dataset.boardId;
  location.href = `/boards/${boardId}/detail`;
});
// 로그아웃
const $logOutBtn = document.getElementById('logOutBtn');
$logOutBtn.addEventListener('click',evt=>{
      fetch('/logout',{method:'POST',body:null})
  .then(res=>res.text())
  .then(res=>{
    if(res==='OK'){
      location.href='/';
    }
   })
})
