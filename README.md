# SoaMLparsers

## Introduction:

Here you can find:
* Case class which wraps Interface to be compatible with CRB.
* A Factory that parse WSDL into Cases.
* A Factory that parse OAS into Cases.


## Setup

This projects uses [java-dotenv](https://github.com/cdimascio/java-dotenv) to load environment variables from a .env file, 
so you have to make a copy of `example.env`, name it `.env` and  configure the desire variables

```bash
$ cp example.env .env
```


### Variables

Commented variables (i.e. line starts with `#` ) are set with default value. Uncomment them to set another one.


#### Database Variables:
```
DATABASE_HOST: Host of the Database (default 127.0.0.1)
DATABASE_PORT: Port of the Database (default 27017)

DATABASE_NAME: Name of the Database (default KB)

DROP_DB: Choose if you want to drop the Database before the import (defualt false)
```


#### Swagger Variables
```
OAS_PATH: Path to SwaggerDataset (requiered)
INSERT_OAS_IN_DB: Choose if you want to insert the Cases in the path into de Database (default false)
```


#### SOAP Variables
```
WSDL_PATH: Path to WsdlDataset (requiered)
INSERT_WSDL_IN_DB: Choose if you want to insert the Cases in the path into de Database (default false)
```


#### Runtime Environment Variables
```
VERBOSE: Choose if you want to run it in verbose mode (default false)
```


## Running

If you want to load in the Database only the OAS or the WSDL you must run the main at parsers.SwaggerToSOaML or 
parsers.WsdlToSoaml respectively.

If you want to load them both at once, run the main at schemas.Case.

In both scenarios you must define the paths and the boolean variable to insert them into the DataBase.
 
 
 ## Installation
 
 Add this to your pom.xml
 
 In `repositories` section:
 ```xml
<repository>
  <id>SoaMLparsers-mvn-repo</id>
  <url>https://raw.github.com/rapkyt/SoaMLparsers/mvn-repo/</url>
  <snapshots>
      <enabled>true</enabled>
    <updatePolicy>always</updatePolicy>
  </snapshots>
</repository>
```

In `dependencies` section:
```xml
<dependency>
  <groupId>com.github.rapkyt</groupId>
  <artifactId>SoaMLparsers</artifactId>
  <version>1.0</version>
</dependency>
```
