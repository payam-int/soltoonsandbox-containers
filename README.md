# SoltoonSandbox - Containers library

![soltoon containers - soltooncontainer 4](https://user-images.githubusercontent.com/4481808/33730879-96bbcd16-db96-11e7-8207-0f58b4f3235a.png)


## About this document
### Performance
## What are the containers ?
## How to use
### Configuration
#### Environment Variables
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

# Utils

## Server/Client Comminucations
This package helps you make communication between server and clients. It helps you have a secure communication.

### Example Usages

Connect to Server:
```java
ComRemoteConfig comRemoteConfig = new ComRemoteConfig(password, port); // creating remote(client) configuration
Comminucation connect = ComClient.connect(comRemoteConfig, 1000);
```

Connect to clients:
```java
ComServer comServer = ComServer.initiate(remoteInfoList);
Comminucation client = comServer.connect();
Comminucation client = comServer.connect();
//...
```

Read and write Object:
```java
Comminucation connect;
Object o = connect.getObjectInputStream().readObject()

Object w;
connect.getObjectOutputStream().writeObject(w);
```

Read object with timeout:
```java
Comminucation connect;
Object o = connect.getObjectInputStream().readObject(1000);
```

### Serialization/Deserialization
It uses SecureJson to serialize and deserialize objects.

## Time limitations
### Time-Aware Beans
Time-Aware beans are objects with time limit on method calls. `TimeAwareBeanProxy` helps you instantiate this objects and force its methods to run in a limited time. 
#### Features
* Run methods with timeouts.
* Set temporary return values for situations that method could not return in proper time.
* Get remaining time
* Tell method that its time is getting over

#### Simple Usage

##### Object with limited running time (Time-Aware Bean)


You can extend `TimeAwareProxyInterface`

```java
interface SomeIface extends TimeAwareProxyInterface{
    boolean ready();
}

class SomeObject implements SomeIface{
    // you have to implement methods
    
    public boolean ready(){
        // stuff to do
        
        return true;
    }
}
```

or simply extend `DefaultTimeAwareBean`
```java
interface SomeIface extends TimeAwareProxyInterface{
    boolean ready();
}
class SomeObject extends DefaultTimeAwareBean implements SomeIface{
    public boolean ready(){
        // stuff to do
            
        return true;
    }
}
```
##### Instantiate

```java
class Example{
    public static void main(String[] args){
      SomeObject someObject = new SomeObject();
      ProxyTimeLimit proxyTimeLimit = new ProxyTimeLimit();
      SomeIface timelimited = TimeLimitedBeanProxy.createBean(someObject, SomeIface.class, proxyTimeLimit);
    }
}
```

##### Call with timeout
```java
class Example{
    public static void main(String[] args){
      SomeObject someObject = new SomeObject();
      ProxyTimeLimit proxyTimeLimit = new ProxyTimeLimit();
      SomeIface timelimited = TimeLimitedBeanProxy.createBean(someObject, SomeIface.class, proxyTimeLimit);
      
      proxyTimeLimit.setTimeLimit(3000); // in milis
      proxyTimeLimit.setExtraTimeLimit(1000);
      if(timelimited.ready()){
          // do stuff
      }
      
      proxyTimeLimit.setTimeLimit(0); // no time limit
      if(timelimited.ready()){
          // do stuff
      }
    }
}
```


#### Time limit
![TimeAwareBean Proxy](https://user-images.githubusercontent.com/4481808/33693342-a89a6eb4-db07-11e7-9d50-963d7d33790d.png)

##### Extra time
You can give method an extra time and notify it when it reached.

Example of _Handling extra time operations_
```java
class TimeAwareBean extends DefaultTimeAwareBean {
    public void method(){
        // doing heavy calculcations
        
        if(getDurationType() == TimeAwareBean.EXTRA_TIME){
            // final calculations
        }else{
            // continue calculations
        }
    }
}
```




#### Temporary Return
```java
class TimeAwareBean extends DefaultTimeAwareBean {
    public void method(){
        // ...
        returnTemporary(obj);
        // ...
    }
}
```

#### Exceptions
You can choose what happens when method call is timed out. if you set `timeoutPolicy` to `THROW_EXCEPTION` it throws a `ir.pint.soltoon.utils.clients.exceptions.ProxyTimeoutException` exception on timeouts otherwise it returns null.

#### Performance
**Good.** My testings show that it makes about 0.001ms overhead on method calls.

## Comminucations with Sandbox
### ResultStorage
It provides a structure for storing result of the containers. This result might be used by `SoltoonSandbox` or `SoltoonWeb`.

#### Data Types
| Name | Type | Description |
| ---- | ---- | ----------- |
| Event | EventLog | Any data that is an event and happening time is important. |
| Exception | Exception | Any exception |
| Meta | MetaLog | Any data that is not quite important |
| Misc | <String, Object> | a key-value property for general use. |

#### Usage
##### Initialization
By default, `ResultStorage` tries to find result file from environment variable `RESULT_STORAGE` if it cannot it uses `System.out`.

##### PreDestruction
Before you exit the program, you have to call `ResultStorage.save()` to write result data on the given `OutputStream`.

##### Examples

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


## Serialization/Deserialization
### SecureJson
It provides secure Serialization/Deserialization using JSON.
#### Usage
The primary class to use is `SecureJson`. with static `encode` and `decode` methods you convert your objects to JSON string. `SecureJson` also have a simple security check, it only serializes or deserializes trusted classes.

##### Encode and decode
```java
@Secure
class SecureClass{
    public String text = "hello";
}
```

**Encode:**
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

**Decode:**
```java
class Decode{
    public static void main(String[] args){
      String json; // JSON String
      SecureClass secureClass = SecureJson.decode(json, SecureClass.class);
    }
}
```

#### Trusted Classes
A class(or interface) is trusted if one of these conditions happen:
1. It is primitive 
2. It is a Map, an Array or a List 
3. It is annotated with `@Secure`
4. Its superclass or implemented interfaces are trusted.

##### Define trusted class:
```java
@ir.pint.soltoon.utils.shared.facades.json.Secure
class SecureClass{
}
```
* Classes and interfaces inherit `@Secure` annotation.
* You can use `@Secure` annotation on interfaces.

#### Performance

| Method | Description |
|--------|-------------|
| `isSecure` | First call ~ 0.1s <br> First call on new class ~ 0.3ms <br> Duplicate call ~ 0.01ms |