# SoltoonSandbox - Containers library

![soltoon containers - soltooncontainer 5](https://user-images.githubusercontent.com/4481808/33734875-e17ba540-dba2-11e7-8130-d579b538ff71.png)

## About this document
This document was written on December 9, 2017.
Authors:
* Payam Mohammadi (@payam-int)
## Configuration
### Environment Variables
| Variable name | Target | Description | Example |
| ------------- | ------ | ----------- | ------- |
| RESULT_STORAGE | Client & Server |  - | path/to/result.txt |
| PORT | Client | - | 127.0.0.1 |
| PASSWORD | Client | - | `any string`
| CLIENTS | Server | - | `host:port/password, host:port/password, ...` |

## build
For ease of build, you can use `with-dependency` jar.

## How this helps you
This library provides utilities that help you running containers in a safe manner.

## Active maintainers
* Payam Mohammadi (@payam-int)

# Utils

## Server/Client Comminucations
This package helps you make communication between server and clients. It helps you have a secure communication.

### Input size
Maximum object size to read is 10MB.

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
      SomeIface timelimited = TimeAwareBeanProxy.createBean(someObject, SomeIface.class, proxyTimeLimit);
    }
}
```

##### Call with timeout
```java
class Example{
    public static void main(String[] args){
      SomeObject someObject = new SomeObject();
      ProxyTimeLimit proxyTimeLimit = new ProxyTimeLimit();
      SomeIface timelimited = TimeAwareBeanProxy.createBean(someObject, SomeIface.class, proxyTimeLimit);
      
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
You can choose what happens when method call is timed out. if you set `timeoutPolicy` to `THROW_EXCEPTION` it throws a `ir.pint.soltoon.ir.pint.soltoon.utils.clients.exceptions.ProxyTimeoutException` exception on timeouts otherwise it returns null.

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
Before you exit the program, you have to call `ResultStorage.flush()` to write result data on the given `OutputStream`.

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
### DeSerialzier
It provides type-preserved Serialization/Deserialization using JSON.
#### Usage
The primary class to use is `DeSerializer`. with static `serialize` and `deserialize` methods you convert to/from JSON string.
**Serialize:**
```java
class Serialize{
    public static void main(String[] args){
        MyObject myObject = new MyObject();
        String json = DeSerializer.serialize(myObject);

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

**Deserialize:**
```java
class Deserialize{
    public static void main(String[] args){
      String json; // JSON String
      MyObject myObject = DeSerializer.deserialize(json, MyObject.class);
    }
}
```

#### Serialize as another class
You can serialize your object as it is another object. You can do this by adding the @Secure annotation. This makes serializer checks if object's property exists on the target class. If no, skips it.

```java
class Parent{
    private int included;
}

@SerializeAs(Parent.class)
class Child extends Parent{
    private int notIncluded;
}
```

#### Insecure classes for deserialization
Classes with `@InsecureForDeserialization` annotation would be skipped in serialization or deserialization.