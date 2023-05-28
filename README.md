

## 访问者模式

    1. 元素固定
    2. 双向绑定
    3. 回调函数：具体老板对员工提供的函数
    4. 具体使用：老板要求员工动手



### 适用场景
    1. 员工固定
    2. 同一个员工，需要它进行**不同的花活**（但都依托该员工原有的东西）
    3. 不同花活的背后是，员工原有的东西作为输入的不同的侧面产品
   

## 计算表达式
    后序遍历ast

/usr/bin/env /Library/Java/JavaVirtualMachines/jdk-18.0.2.1.jdk/Contents/Home/bin/java -XX:+ShowCodeDetailsInExceptionMessages -cp /Users/weilongze/Desktop/java_etc/Lox/bin com.craftinginterpreters.lox.Lox 

/usr/bin/env /Library/Java/JavaVirtualMachines/jdk-18.0.2.1.jdk/Contents/Home/bin/java -XX:+ShowCodeDetailsInExceptionMessages -cp /Users/weilongze/Desktop/java_etc/Lox/bin com.craftinginterpreters.tool.GenerateAst /Users/weilongze/Desktop/java_etc/Lox/src/com/craftinginterpreters/lox