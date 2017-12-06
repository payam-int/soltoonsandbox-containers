# SoltoonSandbox - Container

## What are containers ?

## How to use

### Configuration

### Environment Variables
| Variable name | Target | Description | Example |
| ------------- | ------ | ----------- | ------- |
| RESULT_STORAGE | Client & Server |  - | result.txt |
| PORT | Client | - | 127.0.0.1 |
| PASSWORD | Client | - | `any string`
| CLIENTS | Server | - | `host:port/password, host:port/password, ...` |


## Resource Limits

### Time limits

#### Command timeout

#### Container timeout

## How it works

## Active maintainers

@payam-int (payam.int@gmail.com)
 
@jalaldoust 

# Utils
## ResultStorage
It provides a structure for storing result of container. this result might be used by `SoltoonServer`.

### Data Types
| Name | Type | Description |
| ---- | ---- | ----------- |
| Event | EventLog | Any data that is an event and happening time is important. |
| Exception | Exception | Any exception |
| Meta | MetaLog | Any data that is not quite important |
| Misc | <String, Object> | a key-value property for general use. |

### Usage
#### Initialization
By default ResultStorage tries to find storage settings from environment variables (`RESULT_STORAGE`) if it cant it uses `System.out`.

#### PreDestruction
Before you exit from program you have to call `ResultStorage.save()` to write result data on given `OutputStream`.

#### Examples

```java
class SomeClass{    
    public static void main(String[] args){
        
        // You can set ResultStorage's OutputStream otherwise when you call save() the json representation of object would be written on System.out.
        ResultStorage.setOutputStream(someOutputStream); 
        
        // ...
        ResultStorage.addEvent(event);
        // ...
        
        // write result on ouputstream
        ResultStorage.save();
    }
    
}
```


## SecureJson
### Usage Example
It provides Serialization/Deserialization services with a simple security check. It only works on [trusted classes](#trusted-classes). 
#### Encode and decode
```java
@Secure
class SecureClass{
    public String text = "hello";
}
```

Encode:
```java
class Encode{
    public static void main(String[] args){
        SecureClass secureClass = new SecureClass();
        String json = SecureJson.encode(secureClass);
        
        System.out.println(json);       
    }
}
```
```
{
    "text": "hello"
    "_class": "[classname]"
}
```

Decode:
```java
class Decode{
    public static void main(String[] args){
      String json; // JSON String
      SecureClass secureClass = SecureJson.decode(json, SecureClass.class);
    }
}
```
### Trusted Classes
A class(or onterface) is trusted if one of these conditions happen:
* It is primitive 
* It is Map, Array or List 
* It is annotated with `@Secure`
* It's superclass or implemented interafces is trusted.

#### Define trusted class:
```java
@ir.pint.soltoon.utils.shared.facades.json.Secure
class SecureClass{
}
```
* Classes and interfaces inherit `@Secure` annotation.
* You can use `@Secure` annotation on interfaces.

### Performance

| Method | Description |
|--------|-------------|
| `isSecure` | First call ~ 0.1s <br> First call on new class ~ 0.3ms <br> Duplicate call ~ 0.01ms |