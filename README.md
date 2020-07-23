# CommandLineCalculator
CLI Calculator. Performs simple calculations using Integer Expression Language (IEL).
  
    Expression | Value
    --- | ---
    add(1, 2) | 3
    add(1, mult(2, 3)) | 7
    mult(add(2, 2), div(9, 3)) | 12
    let(a, 5, add(a, a)) | 10
    let(a, 5, let(b, mult(a, 10), add(b, a))) | 55
    let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))) | 40

### Requirements
1. Java JDK min 1.8
2. Gradle 6.0.1
3. Git

### Project Setup (using CMD or Git Bash)
1. Clone the project into local machine from [github](https://github.com/imAmanRana/CommandLineCalculator).
2. *cd* to the *CommandLineCalculator* folder and run

 ```bash
  gradle jar
 ```
   It will build the project and assemble it in a jar.
 
### Running the Project
1. From the root folder of project(*CommandLineCalculator*), *cd* into *./build/libs*
2. Check if the jar *CommandLineCalculator.jar* exists here.
3. **Usage** : `java -jar  CommandLineCalculator.jar "EXPRESSION_TO_EVALUATE" LOG_LEVEL`
 
 ```bash
	java -jar  CommandLineCalculator.jar "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))" ERROR
	java -jar  CommandLineCalculator.jar "mult(add(2, 2), div(9, 3))" DEBUG
	java -jar  CommandLineCalculator.jar "let(a, 5, add(a, a))"
 ```


###Technical Details
1. **Logging**

   The application uses _java.util.logging_ api.
   If no logging level is specified in the arguments, by default WARNING level is choosen.
   It supports all of the following log levels:
   
     * OFF
     * SEVERE
     * WARNING
     * INFO
     * CONFIG
     * FINE
     * FINER
     * FINEST
     * ALL
     
   Along with these, the application also supports log levels for log4j using proper conversion, as defined [here](https://stackoverflow.com/questions/20795373/how-to-map-levels-of-java-util-logging-and-slf4j-logger).
   
2. **Program Input Expression**
    - Variable names are case-sensitive.
    - Proper number of opening & closing brackets are required.
    - Variable name is not limited to 1 character, it can be of any number of characters. eg: `let(longVariableName,4,sub(10,longVariableName))`
   