# Enum 기본
Java에서 Enum은 상수들의 collections들을 위해 사용하는 특별한 클래스이다.

```java
public enum Color {
    WHITE("1", "w"),
    BLACK("2", "b");

    private String code;
    private String text;

    Color(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }
    
    public String getText() {
        return text;
    }
}

```

### Enum iterations
```java
/**
* Returns an array containing the constants of this enum 
* type, in the order they're declared.  This method may be
* used to iterate over the constants as follows:
*
*    for(E c : E.values())
*        System.out.println(c);
*
* @return an array containing the constants of this enum 
* type, in the order they're declared
*/
public static E[] values();
```
compile time에 추가되어지는 values를 통해서 Enum instance들을 얻을 수 있다.
-> output: WHITE, BLACK

### Enum toString, name
```java
/**  
 * Returns the name of this enum constant, exactly as declared in its * enum declaration. * * <b>Most programmers should use the {@link #toString} method in  
 * preference to this one, as the toString method may return * a more user-friendly name.</b> This method is designed primarily for  
 * use in specialized situations where correctness depends on getting the * exact name, which will not vary from release to release. * * @return the name of this enum constant  
 */
 public final String name() {  
    return name;  
 }
 
/**  
 * Returns the name of this enum constant, as contained in the * declaration.  This method may be overridden, though it typically * isn't necessary or desirable.  An enum type should override this * method when a more "programmer-friendly" string form exists. * * @return the name of this enum constant  
 */
 public String toString() {  
    return name;  
}
```
Enum instance의 이름
output : WHITE, BLACK 

### Enum Methods
Java enum에도 methods를 붙일 수 있다. 위에 선언한 methods를 이용하면 COLOR.WHITE.getCode(), COLOR.WHITE.getText()

### Enum Implementing Interface
```java
public enum Color implements CodeEnum, TextEnum {
    WHITE("1", "w"),
    BLACK("2", "b");

    private String code;
    private String text;

    Color(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }
}

```
interface를 구현할 수 있기에, Comparator 같은 것도 implements 하여 sort할 수 있겠다. (Java enums extend the `java.lang.Enum` class implicitly, so can't extend another class)
