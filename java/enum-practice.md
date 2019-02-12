# Enum 활용
```java
public static final int WHITE = 0;
public static final int BLACK = 1;
```

위와 같은 int enum 패턴을 사용하지 말아야 한다.
* java에서 enum은 다른 언어와 다르게 int가 아닌 참조 자료형이다.
	*	즉 일반객체처럼 상태와 행위를 가질 수 있으며 이것을 풍부하게 표현할 수 있다. 거기에다가 불변성을 유지한다.

* enum type은 jvm에서 싱글 인스턴스를 보장하기 때문에 안전하게 == 연산자를 통해서 비교해도 된다.
	* 게다가 == 연산자는 compile-time and run-time safety하다.

```java
if(color.getColor().equals(Color.WHITE)){} // NPE
if(color.getColor() == COLOR.WHITE){}
if(color.getColor().equals(PIZZA.NORMAL)){}
if(color.getColor() == PIZZA.NORMAL){} // compile error 
```

* enum type에 생성자, 메서드, 필드 추가를 통해 enum을 더 좋게 활용할 수 있다.
```java
public class ColorTest {

    private Color c;
    public enum Color {
        BLACK (5){
            @Override
            public String getColorName() {
                return "black";
            }
        },
        WHITE (2){
            @Override
            public String getColorName() {
                return "white";
            }
        },
        BLUE (0){
            @Override
            public String getColorName() {
                return "blue";
            }
        };

        public String getColorName() {return "";}

        private int brightness = 0;

        Color (int brightness) {
            this.brightness = brightness;
        }
    }

    public int getBrightness() {
        return this.getColor().brightness;
    }

    public String getColorName() {
        return this.getColor().getColorName();
    }

    public Color getColor() {
        return this.c;
    }

    public void setColor(Color c) {
        this.c = c;
    }
}
```
```java
    ColorTest test = new ColorTest();
    test.setColor(ColorTest.Color.BLACK);
    System.out.println(test.getColor());
    System.out.println(test.getBrightness());
    System.out.println(test.getColorName());
}
```

