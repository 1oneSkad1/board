--테이블삭제
drop table board;

--시퀀스삭제
drop sequence board_board_id_seq;
---------
--게시판 생성
--------
create table board(
    board_id         number(10),
    board_title      varchar2(100),
    board_content    clob,
    writer           varchar2(20),
    cdate            timestamp,   --생성일시
    udate            timestamp    --수정일시
);
--기본키
alter table board add constraint board_board_id_pk primary key(board_id);

--시퀀스생성
create sequence board_board_id_seq;

--디폴트
alter table board modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table board modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table board modify board_title not null;
alter table board modify board_content not null;
alter table board modify writer not null;

commit;
