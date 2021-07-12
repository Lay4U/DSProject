# 크롤링을 통한 단어 수 세기
## 분산및 병렬 처리 시스템

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

HTML의 파일에 있는 내용 중 태그들을 제외한 단어들을 카운팅 하기 위한 시스템으로써 사이트를 크롤링한 후 HTML의 태그를 제거한 단어들을  카운팅한 결과를 가지고 단어 들의 갯수가 몇개인지 이 단어 몇 번 쓰여졋는지 카운팅 하기위한 프로그램이다.

- 수업때 배웠던 분산및 벙렬 처리 시스템을 활용하여 게시판을 구현하는것이 목표
- 4명 구성원 (컴퓨터과학과) 2개월 작업.

## Features

- 회원 관리 시스템 구현
- 게시판 글 CRUD 구현
- 포인트및 랭킹 시스템 구현
- 쪽지 기능 구현
- 친구 기능 구현

## Tech

- AWT, Swing
   - 사용자에게 결과물을 출력하기 위해 AWT와 Swing을 이용하여 GUI를 구현하였다.
- jsoup 
    - 크롤링을 위한 자바 라이브러리로 이를 통해 여러개의 사이트에서 단어를 추출 가능토록 하였다.
- word-count MySQL, JDBC
    - 크롤링을 통해 DB에 저장된 데이터를 불러와서 태그를 제거한 유의미한 단어들을 추출하여 단어의 개수를 Counting하였다.

- Distribution System
    - 총 4개의 DB가 실행되도록 각 하이퍼바이저 구동 후 자바에서 4개의 DB를 모두 연결한 후 전체 데이터를 4등분 하여 각자의 DB에 쿼리를 날리는 작업을 하였다. 작업이 끝나면 결과값이 출력되도록 프로그램을 구현하였다.
    

## Review
DB를 통해 분산 처리 시스템을 구현하였다. 가상 머신에서 돌아갈 수 있도록 처음 세팅하는것이 힘들었지만 한번 설정 된 후에는 큰 어려움 없이 진행 되었다. SQL문을 이용하여 단어의 counting을 진행하였고 jsoup으로 크롤링 및  구문 분석을 할 수 있었다. 이 라이브러리가 없었으면 수작업으로 단어를 추출했어야 했는데, 큰 어려움 없이 프로젝트를 진행 할 수 있었다. 분산 처리에는 한계가 있었다. 너무 많은 웹사이트를 크롤링 할 시에는 VM에서 동작중인 DB가 뻗는 경우가 있었다. 때문에 slave에서 일정 작업량 이상에서는 DB가 열심히 일을 하고 있을때 master에서 계속해서 작업을 넘기지 않고 stop-and-wait할 수 있는 조건을 달아주어야 프로그램이 죽지 않고 가용성을 유지할 수 있었다. 분산 처리 개수가 많아 질수록 당연히 처리속도가 빨라졌다 물론 1/N은 아니지만 꽤 효율적인 결과를 볼 수 있었다. 

## Result
![result](./reult.jpg)
## License

MIT



[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
