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
* ​