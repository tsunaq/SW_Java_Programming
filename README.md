# SW_Java_Programming

## 1. 설계목적
Java Programming의 GUI를 이용하여 그림판을 직접 구현해본다.

## 2. 요구사항
1) 기본적인 메뉴
2) 펜으로 그리기
3) 선 긋기
4) 타원 만들기
5) 사각형 만들기
6) 삼각형
7) 오각형
8) 지우개
9) 색채우기
10) 글자입력

## 3. 참고사항
![image](https://user-images.githubusercontent.com/58457978/70211142-bdbc4200-1777-11ea-97c3-e2272aa54d4d.png)
<전체화면 구성도>

## 4. 프로그램 설명
### a. Draw 클래스
Draw 클래스는 고유한 필드를 가지며 이 필드들을 PicturePanel 클래스에서  각각 그린 점, 선, 도형들이 벡터 vc 필드로 저장된다.

### b. PicturePanel 클래스
메뉴바, 체크박스, 버튼, 컬러 다이얼로그 등 여러 가지 필드들이 존재하고, Draw 클래스의 객체를 Vector<Draw>형 객체인 vc에 하나씩 저장한다.
  
### c. ExamOne 클래스
PicturePanel 클래스의 객체 pp를 생성하여 위에 구현한 클래스들을 실행한다.

## 5. 결과
![1](https://user-images.githubusercontent.com/58457978/70211809-50111580-1779-11ea-98e6-a75aa8c682ff.png)

![2](https://user-images.githubusercontent.com/58457978/70211810-50111580-1779-11ea-8738-2965a897ed1f.png)

![3](https://user-images.githubusercontent.com/58457978/70211811-50111580-1779-11ea-876e-748cb8ca1bae.png)
