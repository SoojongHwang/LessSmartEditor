# Less Smart Editor

## Requirement

1. 에디터에서 작성할 수 있는 컴포넌트의 종류는 글, 그림, 지도가 있다.

2. 에디터에 위 컴포넌트 중 하나를 추가할 때에는 <추가>버튼을 누른 후, 나오는 목록 중에 하나를 선택하는 방식으로한다.

3. 컴포넌트를 추가하고 제거하는 것은 RecyclerView를활용하도록 한다.

4. 최초의 작성된 컴포넌트들은 삭제는 가능하나 서로의 위치를 옮기는 등의 기능은 없도록 한다.(단, 글의 경우엔 수정 가능)

5. 지도 컴포넌트는 Retrofit을 사용하여 Naver 지도 Open API와 SDK를활용하도록 한다.

6. 지도와 그림 컴포넌트는 고정된 크기로만 삽입된다.

7. 그림 삽입 시 Glide를 활용한다.

8. 저장 버튼을 누를 시 각 컴포넌트의 위치와 정보가 내장된 데이터베이스에 저장되도록 한다. 이 때 SQLite를 활용하여 구현한다.

9. 불러오기 기능을 통해서 데이터베이스에 저장되어 있는 글들을 불러 올 수 있다.

10. 아래그림의 디자인은 예시이며, 따라할 필요 없이 디자인은 각자 구현한다.단, 동일한 기능의 동작이 가능해야 한다.

   ___

   (06.01 추가 요구사항)

11. 텍스트 컴포넌트 Spannable

12. 이미지 스트립

13. 타이틀이 RecyclerView에 포함

14. 타이틀내에 이미지 넣기

15. 커서 전후로 컴포넌트 나뉘기

![requirement.jpg](https://github.com/SoojongHwang/LessSmartEditor/blob/master/Study/images/requirement.jpg?raw=false)

## 05.20

* Component 설계


* Text, Image, Map 각각의 ViewHolder 를 BaseViewHolder로 추상화



## 05.21

`(BUG_01) 컴포넌트를 추가하거나, 스크롤을 내렸다 올렸을 때 기존의 editText 가 수정되고있음`

`(BUG_02) image 추가 이후에 text 추가할 때 에러발생`



## 05.22

* BUG_01 해결 (TextWatcher로 해결 -> onBindView 이전에 item의 Position을 업데이트 해야함)
* BUG_02 해결 (position으로 인해 발생하던 문제였는데 textwatcher 도입하니 고쳐짐)
* 컴포넌트 삭제, 이동 기능 추가

`(BUG_03) 에디터 초기화 후에는 컴포넌트를 삭제해도 아이템 개수가 유지되고 자리를 못잡음`



## 05.23

`(BUG_04) editText 2개 작성 후 위에를 지우고 아래것에 입력하면 에러 발생 `

* BUG_03 해결 (아이템이 삭제됐을때 list가 변했다는것을 adapter에게 알려주어야함 notify함수로 해결)

* BUG_04 해결 (상동)

* Retrofit 을 이용하여 Naver Map API 호출 및 결과 출력 기능 추가(RecyclerView)

  ​

## 05.24

* Naver Map 구현부분 MVP 패턴 적용하여 설계 및 개발

## 05.25

`(BUG_05) 맨 처음에 지도가 삽입될 시에 지도의 크기가 작게 그려짐`

* Map SDK 사용, SQLite 요구사항 제외하고 완료
* Editor 와 Map 2개의 Activity별로 역할 분리 완료


![GSON](https://github.com/SoojongHwang/LessSmartEditor/blob/master/Study/images/gson.jpg?raw=false)

> Adapter상의 List 를 Json 으로 변환하여 저장하여 다시 복구해주면 저장 및 불러오기가 완료될거라 생각한다.
>
> 하지만 list에 generic type의 Component 들이 들어가있으므로 어떤 Component인지 알기위하여 deSerialize 하는 도중에 downCasting을 해주는 방법을 찾아보아야겠다.



## 05.26

* Json <-> Object 양방향 변환완료 (Superclass 로 up-casting한 자료도 Deserialize 가능)



## 05.27

* Database 기능

* 구조 세분화

* Model의 기능 분리 

  > Model 	- ComponentManager
  >
  > ​		- DatabaseManager




## 05.28



## 05.29

* Database 기능 완료
* method 이름을 좀더 행동과 일치하도록 변경 및 세분화

`BUG_06 Db로부터 불러와서 생긴 recyclerview 에서는 컴포넌트 이동 및 삭제가 적용되질 않음`



> Usecase 별 Test 필요. (요구사항별로 flow 를 따라가며 체크리스트 해보자)



## 05.30

* BUG_06 해결 - ItemTouchHelper를 detach 한 후에 다시 set 해주어야함 (listener 2개가 달려서 이상동작)

DB 이미 존재하면 update, 없으면 새로 추가(select exists query로 가능할듯)



## 05.31

* 제목이 공백일 때는 저장버튼 비활성화되도록 수정
* ComponentAdapter 의 onBindViewHolder 메소드 간소화(abstract method)



## 06.01

* MVP convention, 역할에 맞는 method name으로 변경
* 설계 변경으로 인한 기능 일시 중지
  * 데이터베이스 삭제
  * 제목 저장
* Presenter가 참조하던 DataManager, ComponentManager  Model 들을 하나로 추상화

## 06.02

오늘 할 일

* Layout 변경하여 선택한 컴포넌트별로 footer 보여주기
* 통합 Model 에서 현재 Document의 상태정보 저장(ex. New, Updated ...)


* Title Component 추가 (RecyclerView 에서 하나의 Document 로 보여줌)

## 06.03

* 문서상태를 Empty 와 Updated로 축소

* 빈 문서는 삭제, 새글 작성, 저장이 불가능하도록 수정

  ​

## 06.04

* RecyclerView 의 item 클릭에 따라 선택바(Span Mode, General Mode) 보여주기(RecyclerView.OnItemTouchListener)
* ​