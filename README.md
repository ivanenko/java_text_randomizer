# Java text randomizer

Generate random text based on template

## Example

```java
TextRandomizer rnd = new TextRandomizer("{Brown|Red} fox {jump over|dig under} [lazy|crazy] dog", true);
System.out.println(rnd.getText());
```

You will get random text like 'Red fox dig under crazy lazy dog'.