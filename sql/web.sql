--게시글 테이블

--테이블삭제
drop table comments;
drop table board;
drop table member;

--시퀀스삭제
drop sequence comments_comments_id_seq;
drop sequence board_board_id_seq;
drop sequence member_member_id_seq;
---------
--게시판 생성
--------
create table board(
    board_id         number(10),
    board_title      varchar2(100),
    board_content    clob,
    writer           varchar2(20),
    member_id        number(10),
    cdate            timestamp,   --생성일시
    udate            timestamp    --수정일시
);
--댓글 생성
create table comments(
    board_id         number(10),
    comments_id      number(10),
    comments_content clob,
    writer           varchar2(20),
    member_id        number(10),
    cdate            timestamp,
    udate            timestamp
);
--멤버생성
create table member(
    member_id   number(10),
    email       varchar2(50),
    passwd      varchar2(12),
    nickname    varchar2(30),
    gubun       varchar(11),        --default 'M0101', --회원구분(일반,우수,관리자1,관리자2)--
    pic         blob,               --사진
    cdate       timestamp,          --생성일시, 가입일
    udate       timestamp           --수정일시
);

--기본키
alter table board add constraint board_board_id_pk primary key(board_id);
alter table comments add constraint comments_comments_id_pk primary key(comments_id);
alter table member add Constraint member_member_id_pk primary key(member_id);

--외래키
alter table comments add constraint comments_fk foreign key (board_id) references board;

--시퀀스생성
create sequence board_board_id_seq;
create sequence comments_comments_id_seq;
create sequence member_member_id_seq;

--제약조건
alter table member modify email constraint email unique;

--디폴트
alter table board modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table board modify udate default systimestamp; --운영체제 일시를 기본값으로
alter table comments modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table comments modify udate default systimestamp; --운영체제 일시를 기본값으로
alter table member modify gubun default 'M0101';
alter table member modify cdate default systimestamp; --운영체제 일시를 기본값으로
alter table member modify udate default systimestamp; --운영체제 일시를 기본값으로

--필수 not null
alter table board modify board_title not null;
alter table board modify board_content not null;
alter table board modify writer not null;
alter table comments modify comments_content not null;
alter table comments modify writer not null;
alter table member modify email not null;
alter table member modify passwd not null;
alter table member modify nickname not null;

commit;

