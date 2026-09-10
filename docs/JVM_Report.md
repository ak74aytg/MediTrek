# MediTrack JVM Report

## JDK, JRE and JVM

These three terms are related, but they are not the same:

| Term | Meaning |
| --- | --- |
| JDK | Used to develop Java programs. It includes tools such as `javac`, `jar`, and `javadoc`. |
| JRE | Contains the libraries and runtime needed to run Java programs. |
| JVM | The virtual machine that loads and runs Java bytecode. |

For MediTrack, the JDK is needed because the source files have to be compiled.
When the program is started with the `java` command, the JVM loads the compiled
classes and runs the `main` method.

The basic flow is:

```text
Main.java --javac--> Main.class --java--> JVM --program output
```

The `.class` file contains bytecode. It is not machine code for only one
specific computer.

## Class Loader

The class loader makes Java classes available to the JVM. It does this in
stages:

1. **Loading:** finds the class file and creates a `Class` object.
2. **Linking:** checks the bytecode, prepares memory for static fields, and
   connects references used by the class.
3. **Initialization:** runs static field initializers and static blocks.

Java normally uses parent delegation. This means a class loader asks its parent
to load a class first.

The main loaders are:

- The **bootstrap class loader**, which loads core Java classes such as
  `java.lang` classes.
- The **platform class loader**, which loads other standard Java platform
  classes.
- The **application class loader**, which loads application classes.

For MediTrack, the application class loader loads
`com.airtribe.Main` from the compiled classes in `target/classes`.

A class is identified by its full class name and by the class loader that
loaded it. This is why two classes with the same name loaded by different
class loaders are not necessarily treated as the same class.

## JVM Runtime Data Areas

### Heap

The heap is shared by all threads. Objects and arrays created with `new` are
stored there. The garbage collector removes objects that are no longer
reachable.

For example, MediTrack objects such as patients, doctors, and appointments
will normally be stored on the heap. If the heap runs out of space, the JVM can
throw `OutOfMemoryError`.

### Stack

Each thread has its own JVM stack. Every method call creates a stack frame.
The frame contains local variables, an operand stack, and information needed
when the method returns.

A local variable can contain a primitive value or a reference to an object.
The reference and the object are different things: the reference is kept in
the frame, while the object is normally on the heap.

Too many nested method calls can fill a thread's stack and cause
`StackOverflowError`.

### Method Area

The method area is shared between threads. It stores information about loaded
classes, including method data, field data, bytecode, and the runtime constant
pool.

The method area is part of the JVM specification. In HotSpot JVMs, class
metadata is generally stored in an area called Metaspace.

Static fields belong to the class, not to each object. Static initialization is
also handled when the class is initialized. This will matter later when
MediTrack has static counters or application-wide settings.

### PC Register

Each thread has its own program counter (PC) register. It keeps track of the
next bytecode instruction for that thread.

If the thread is running a native method instead of Java bytecode, the PC value
is handled differently by the JVM.

### Native Method Stack

The JVM can also use a native method stack when Java code calls native code,
for example through JNI. The exact details depend on the JVM implementation.

## Execution Engine

After a class has been loaded, the execution engine runs its bytecode.

The JVM has an interpreter and a JIT compiler:

- The **interpreter** reads bytecode and executes it instruction by
  instruction. It helps the program start quickly.
- The **JIT compiler** looks for code that runs often. It compiles that code
  into native instructions for the current computer and reuses the compiled
  version.

A short-lived method may only be interpreted. A method that runs many times,
such as a loop or a frequently used service method, is a good candidate for
JIT compilation.

The JVM can also undo an optimization if its assumptions stop being true. This
is called deoptimization.

## JIT compiler and interpreter

| Interpreter | JIT compiler |
| --- | --- |
| Starts executing bytecode immediately | Spends time compiling frequently used code |
| Has less startup work | Can make repeated code run faster |
| Good for code that runs only a few times | Good for hot methods and loops |
| Executes bytecode | Executes compiled native instructions |

Both can be used in the same application. The JVM does not have to interpret
everything or compile everything.

## Write Once, Run Anywhere

Java source code is compiled into bytecode. The bytecode can run on different
operating systems if a compatible JVM is available.

```text
Java source --javac--> bytecode
                          |
              ---------------------------
              |            |            |
          JVM on Linux  JVM on macOS  JVM on Windows
              |            |            |
          native code   native code   native code
```

This is the idea behind “Write Once, Run Anywhere.” The same Java bytecode does
not need to be compiled separately for every operating system.

There are still some limits. Native libraries, file paths, environment
variables, permissions, character encodings, and Java version differences can
cause platform-specific problems.

## What the screenshots show

The Java screenshot shows:

```text
java version "21.0.5" 2024-10-15 LTS
javac 21.0.5
```

So Java 21.0.5 is installed in that environment. The Maven screenshot does not
show any command output, so it does not prove which Maven version is installed
or which Java version Maven is using.

The project is currently configured for Java 23 in `pom.xml`. Java 21 will not
compile a project whose target release is 23. The compiler would report:

```text
error: invalid target release: 23
```

JDK 23 needs to be selected before the Maven build can be considered complete.


