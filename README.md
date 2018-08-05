# Todo list 관리 web service 


### 과제 할일 목록(todo-list) 웹 어플리케이션 구현
### 기능 
* 사용자는 텍스트로 된 할일을 추가할 수 있다.   
* 할일 추가 시 다른 할일들을 참조 걸 수 있다. 
* 참조는 다른 할일의 id를 명시하는 형태로 구현한다. (예시 참고) 
* 사용자는 할일을 수정할 수 있다.
 * 사용자는 할일 목록을 조회할 수 있다.  
* 조회시 작성일, 최종수정일, 내용이 조회 가능하다.  
* 할일 목록은 페이징 기능이 있다.
 * 사용자는 할일을 완료처리 할 수 있다. 
* 완료처리 시 참조가 걸린 완료되지 않은 할일이 있다면 완료처리할 수 없다. (예시 참고)


###
사용 기술 

- front end : handlebars, jquery, bootstrap
- back end : springBoot, java8, hibernate, h2

###
아키텍쳐
- ddd(domain driven development) 기반의 module 구성.
- todo와 link는 entity 기준 oneToMany 관계를 가진다.


###
고려 사항 및 예외 처리
- todo는 자기 자신을 참고 하지 못한다.
- todo 끼리 순환 참조를 할 수 없다.
- 없는 todo Id에 대해 참조 할 수 없다. 
- done은 순환된 참조가 없거나, 참조된 Todo가 done 되어야 가능하다.

###
trouble shooting
- list의 각 row 별 modal을 사용 할 시, 데이터 주입이 어려워 각 수정 버튼에 필요한 data를 명시 함.

###
demo

![Alt Text](https://github.com/kootokoo/todo/raw/master/path/to/demo.gif)
