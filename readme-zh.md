# pro.fessional.nevermore

![Maven Central](https://img.shields.io/maven-central/v/pro.fessional/nevermore?color=00DD00)
![Sonatype Snapshots](https://img.shields.io/nexus/s/pro.fessional/nevermore?server=https%3A%2F%2Foss.sonatype.org)
[![Coverage Status](https://coveralls.io/repos/github/trydofor/professional-nevermore/badge.svg)](https://coveralls.io/github/trydofor/professional-nevermore)

> 中文 🇨🇳 | [English 🇺🇸](readme.md)

有点用的Java注解处理器

![nevermore](./nevermore.png)

## 如何使用

``` xml
<dependencies>
    <dependency>
        <groupId>pro.fessional</groupId>
        <artifactId>nevermore</artifactId>
        <version>${nevermore.version}</version>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <parameters>true</parameters>
                <annotationProcessorPaths>
                    <!--  at last -->
                    <path>
                        <dependency>
                            <groupId>pro.fessional</groupId>
                            <artifactId>nevermore-processor</artifactId>
                            <version>${nevermore.version}</version>
                        </dependency>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    <plugins>
<build>
```

## 条件 @SetterIf

为`Pojo`的`protected`字段，在同包下生成具有条件设值的子类，如以下代码，

```java
public interface Outer {
    @SetterIf
    class Pojo {
        protected long id;
        protected boolean active;
        protected Enum<?> status;
        protected List<String> items;
    }
}
```

会为每一个字段，生成如下模式的Setter，

```java
public interface $Outer {

    class Pojo extends Outer.Pojo {
        // conditional setId 
        public void setIdIf(Long id, boolean bool) {/*...*/}
        public void setIdIf(Supplier<Long> id, boolean bool) {/*...*/}
        public void setIdIf(Long id, Predicate<Long> bool) {/*...*/}
        public void setIdIf(Long id, Predicate<Long> bool, Supplier<Long>... ids) {/*...*/}
        public void setIdIfNot(Long id, Predicate<Long> bool) {/*...*/}
        public void setIdIfNot(Long id, Predicate<Long> bool, Supplier<Long>... ids) {/*...*/}
        public void setIdIf(UnaryOperator<Long> id) {/*...*/}

        // other field setters
    }
}
```

生成的子类，除了`$`前缀外，拥有与父类相同的限定名，

* `$Pojo` - 普通类时，自身加前缀
* `$Outer.Pojo` - 内部类时，最顶层类加前置
