# MVC, MVP, MVVM

![MVC, MVP, MVVM](https://github.com/SoojongHwang/LessSmartEditor/blob/master/Study/images/mvc_mvp_mvvm.png)





## 1. MVC

### Controller

* 사용자의 입력이 직접 들어옴
* 사용자에 입력에 대응되는 view를 선택해야 하기때문에 View들을 가지고 있어야함( C와 V 는 1:m 관계가 됨)
* 입력에 따라서 Model이 가진 Data를 조작함

### View 

* Controller를 가지고 있지 않음 ( Controller 로직에 대해 알지 못함)
* (Controller가 조작하여 변경된) Model이 변경되었음을 알리면 자신을 업데이트함

### Model

* Controller에 의해 데이터가 변경되면 간접 참조하던 View에게 자신이 변경되었음을 알림(M과 결합되어있음)



그림에서는 Model 이 View 를 참조하고있지만 구현하기에 따라 다를 수 있다. 위에서 설명한 바와 같이 Model 이 View에게 알리는 방법이 있을 수도있고, View에서 변경된 Model을 알아차리는 방법이 있음.

두 방법 모두 View와 Model 둘중 하나가 다른 하나에게 의존할 수 밖에 없음.



> 안드로이드에선 Activity 나 Fragment 가 View 의 특성을 모두 가지고 있기 때문에 View 와 Controller의 역할을 분리하기가 힘듦



