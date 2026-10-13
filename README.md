# lab-manual
OOP lab 

Department : Software Engineering
Full name: Mapara Tanaka
Reg number: H250080A


Appendix B: Viva Questions &Answers 
 
1.	Class vs object, and where each lives? 
➢	A class is the blueprint (fields and methods);  
➢	An object is one concrete instance created with new.  
➢	The class definition is loaded once into the JVM's class-metadata area, along with its static members. 
➢	Objects live on the heap.  
➢	A variable like Student s holds only a reference to the object, and a local reference variable sits on the stack. 
2.	Why no multiple inheritance of classes, and how do interfaces work around it? 
➢	If a class extended two classes that both define the same method or field, Java could not tell which to inherit (the diamond problem), and the constructor chain would be unclear. A class may extend only one class but implement many interfaces, because interfaces hold no instance state.  
3.	Overloading vs overriding?  
➢	Overloading: same method name, different parameters, in the same class, chosen at 
compile time (print(int) and print(String)). 
➢	Overriding: a subclass supplies its own version with the same signature, chosen at run 
time (Cat.sound() replacing Animal.sound()). 
➢	Use @Override so a typo becomes a compile error. 
4.	Superclass has no no-argument constructor and the subclass omits super(...)? 
➢	Compile-time error. The compiler inserts super(); automatically, and no matching constructor exists. Fix by calling super(args) as the first statement of the subclass constructor. 
5.	Abstract class or interface?  
➢	Choose an abstract class when related classes share state or common code, or you need constructors and protected members (Account with a balance and a shared deposit). 
➢	Choose an interface to describe a capability that unrelated classes can have, or when a class must have several such roles (Flyable, Comparable). 
➢	A class can extend only one abstract class but implement many interfaces. 
6.	Why override hashCode whenever you override equals?  
➢	The contract says equal objects must have equal hash codes. Hash collections (HashMap, HashSet) use hashCode to pick a bucket and then equals to confirm.  
➢	If only equals is overridden, two equal objects usually get different hash codes, land in different buckets, and the set stores duplicates or a map lookup fails.  
➢	The reverse is not required: different objects may share a hash code. 
7.	Checked vs unchecked exceptions? 
➢	Checked exceptions extend Exception but not RuntimeException; the compiler forces you to catch or declare them. Examples: IOException, FileNotFoundException.  
➢	Unchecked exceptions extend RuntimeException; the compiler does not force handling, and they usually signal programming mistakes. 
➢	Examples: NullPointerException, ArithmeticException (also 
ArrayIndexOutOfBoundsException, IllegalArgumentException). 
8.	Why no generics with primitives, and what is type erasure?  
➢	Type parameters must be reference types, because after compilation T is replaced by Object (or its bound), and primitives are not objects. 
➢	Use wrappers (List<Integer>) and autoboxing.  
➢	Type erasure means the compiler checks the generic types, inserts casts, then removes 
the type arguments, so at run time List<String> and List<Integer> are the same class.  
➢	This is why you cannot write new T(), instanceof List<String>, or create a generic array.  
➢	It was done to stay compatible with pre-generics code. 
9.	What does final mean? 
➢	On a variable: it can be assigned only once (for a reference, the reference cannot change but the object's contents still can).  
➢	On a method: it cannot be overridden. On a class: it cannot be extended (String is final). 
10.	Dynamic method dispatch; why are fields and static methods not polymorphic? 
➢	With Parent ref = new Child();, a call to an overridden instance method runs the Child version, because the JVM picks the implementation from the actual object at run time. 
The compiler only uses the reference type to check that the method exists.  
➢	Fields, static methods and private methods are resolved at compile time from the reference type, so ref.name and ref.stat() give the Parent versions (they are hidden, not overridden). 
11.	What is a functional interface, and how does a lambda relate?  
➢	An interface with exactly one abstract method (it may also have default and static 
methods). @FunctionalInterface makes the compiler check it. 
➢	A lambda is a short way to write the implementation of that single method, and the 
compiler works out which interface it targets from context. 
➢	Examples: Runnable, Comparator<T>, Predicate<T>, Function<T,R>. 
12.	How does try-with-resources differ from try/finally?  
➢	Resources declared in the parentheses (anything implementing AutoCloseable) are closed automatically in reverse order, even if an exception is thrown. 
➢	With manual try/finally you must write the close code yourself and it is easy to get wrong. 
➢	If both the body and close() throw, try-with-resources keeps the original exception and attaches the other as a suppressed exception (getSuppressed()), whereas an exception from a finally block would replace the original. 
 
