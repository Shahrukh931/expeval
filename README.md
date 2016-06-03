ExpEval
=======
A light weight, customizable expression evaluator.

# Overview
---

- [Key features](#)
- [Quick demo](#)
- [Predefined operators](#)
- [Custom operators and functions](#)
- [Using context](#)

---
## Key features: 


- Variables support.
- Custom operators(unary or binary) and functions can be added at runtime.
- Support for defining a context or scope in which the expression will be evaluated.
- Compiled expression can saved to avoid repetative compilation for future evaluations.
- Exceptions with detailed messages are thrown during expression compilation.
- Functions can be defined with any number of arguments.
- Pre-Defined standard boolean and mathematical operators. 

   The expression is compiled against following grammer: 
   ```html
        E -> E binary_operator T  
        E -> T
        T -> unary_operator T            
        T -> Z
        Z -> ( E )
        Z -> id
        Z -> fid ( )
        Z -> fid ( F )
        F -> F , E
        F -> E
```
 ## Quick demo
 
 ```java
 assertEquals(0d, Expression.parse("-1+1").eval());
 assertEquals(2d, Expression.parse("4/2").eval());
 assertEquals(true, Expression.parse("3.9999999<=4 && 5.0000000001>5").eval());
 asssertEquals(true, Expression.parse("true&&!false").eval());
 
Var x = new Var("x");
x.setValue(10);
Context.getDefault().registerVar(x);

Var myList = new Var("list");
List<Object> l = new ArrayList<Object>();
l.add(1);
l.add(true);
l.add("xyz");
myList.setValue(l);
Context.getDefault().registerVar(myList);

Context.getDefault().registerFunction(new Function("if") {
      @Override
      public Object onEvaluation(List<Object> arguments) {
         if (arguments.size() == 1) {
            return arguments.get(0);
         } else {
            Boolean result = (Boolean) arguments.get(0);
            return result ? arguments.get(1) : arguments.get(2);
         }
      }
});

Context.getDefault().registerBinaryOperator(new BinaryOperator("contains", BinaryOperator.EQ.getPrecedence()) {
      @Override
      public Object onEvaluation(Object leftOperand, Object rightOperand) {
         List<Object> list = (List<Object>) leftOperand;
         for (Object o : list) {
            if ((Boolean) BinaryOperator.EQ.onEvaluation(o, rightOperand)) {
               return true;
            }
         }
         return false;
      }
});

Expression expression = Expression.parse("x+1");
assertEquals(10d,expression.eval());

Context.getDefault().getVar("x").setValue(100);
assertEquals(101d,expression.eval());

assertEquals(3l,Expression.parse("if(2>1,3,4)").eval());
assertEquals(true,Expression.parse("list contains 1").eval());
assertEquals(2d,Expression.parse("1+if(list contains true,1,2)").eval());
```

## Predefined operators

<table>
<tr><th>Operator</th><th>Description</th><th>Precedence</th></tr>
<tr><td>!</td><td>Logical NOT</td><td>51</td></tr>
<tr><td>+</td><td>Unary plus</td><td>51</td></tr>
<tr><td>-</td><td>Unary minus</td><td>51</td></tr>
<tr><td>~</td><td>Bitwise NOT</td><td>51</td></tr>
<tr><td>/</td><td>Division</td><td>50</td></tr>
<tr><td>%</td><td>Modulus</td><td>50</td></tr>
<tr><td>*</td><td>Multiplication</td><td>50</td></tr>
<tr><td>-</td><td>Subtraction</td><td>49</td></tr>
<tr><td>+</td><td>Adddition</td><td>49</td></tr>
<tr><td>&lt;&lt;</td><td>Bitwise left shift</td><td>48</td></tr>
<tr><td>&gt;&gt;</td><td>Bitwise right shift</td><td>48</td></tr>
<tr><td>&lt;</td><td>Less than</td><td>47</td></tr>
<tr><td>&gt;</td><td>Greater than</td><td>47</td></tr>
<tr><td>&lt;=</td><td>Less than or equal</td><td>47</td></tr>
<tr><td>&gt;=</td><td>Greater than or equal</td><td>47</td></tr>
<tr><td>!=</td><td>Not equals</td><td>46</td></tr>
<tr><td>==</td><td>Equals</td><td>46</td></tr>
<tr><td>&</td><td>Bitwise AND</td><td>45</td></tr>
<tr><td>^</td><td>Bitwise XOR</td><td>44</td></tr>
<tr><td>|</td><td>Bitwise OR</td><td>43</td></tr>
<tr><td>&&</td><td>Logical AND</td><td>42</td></tr>
<tr><td>||</td><td>Logical OR</td><td>42</td></tr>
</table>

## Custom operators and functions

Custom operators and functions can registered to any defined context.The identifier or name of custom operator and function should be a valid java identifier, else an exception is thrown during registration.

```java
Context.getDefault().registerUnaryOperator(new UnaryOperator("decBy2", UnaryOperator.NEGATE.getPrecedence()) {
      @Override
      public Object onEvaluation(Object rightOperand) {
         List<Number> list = (List<Number>) rightOperand;
         List<Double> result = new ArrayList<Double>();
         for (Number number : list) {
            result.add(number.doubleValue() - 2);
         }
         return result;
      }
});

 Context.getDefault().registerFunction(new Function("asList") {
      @Override
      public Object onEvaluation(List<Object> arguments) {
         return arguments;
      }
 });
 
 Context.getDefault().registerBinaryOperator(new BinaryOperator("contains", BinaryOperator.EQ.getPrecedence()) {
      @Override
      public Object onEvaluation(Object leftOperand, Object rightOperand) {
         List<Object> list = (List<Object>) leftOperand;
         for (Object o : list) {
            if ((Boolean) BinaryOperator.EQ.onEvaluation(o, rightOperand)) {
               return true;
            }
         }
         return false;
      }
});

assertEquals(true, Expression.parse("decBy2 asList(1,2,3) contains 0").eval());
 
```

## Using Context
> * Context is an environment in which you can register your custom operators,functions or vars.
> * Operators or functions registered in one context is not visible in another context.
> * Every context has all predefined operators mentioned above.

```java
   Context.getDefault().registerFunction(new Function("sin") {
         @Override
         public Object onEvaluation(List<Object> arguments) {
            return null;
         }
   });


   Context context1 = Context.newContext("scope1");
   context1.registerFunction(new Function("cos") {
         @Override
         public Object onEvaluation(List<Object> arguments) {
            return null;
         }
   });
   context1.registerBinaryOperator(new BinaryOperator("add", BinaryOperator.ADD.getPrecedence()) {
         @Override
         public Object onEvaluation(Object leftOperand, Object rightOperand) {
            return null;
         }
   });
        
   assertNotNull(Context.getDefault().getFunction("sin"));
   assertNull(Context.getDefault().getFunction("cos"));
   assertNull(Context.getDefault().getBinaryOperator("add"));


   assertNotNull(Context.get("scope1").getFunction("cos"));
   assertNotNull(Context.get("scope1").getBinaryOperator("add"));
   assertNull(Context.get("scope1").getFunction("sin"));
        
   Context.resetDefault();
        
   assertNull(Context.getDefault().getFunction("sin"));
   assertNull(Context.getDefault().getFunction("cos"));
   assertNull(Context.getDefault().getBinaryOperator("add"));

   assertNotNull(Context.get("scope1").getFunction("cos"));
   assertNotNull(Context.get("scope1").getBinaryOperator("add"));
        
   Context.removeContext("scope1");
   assertNull(Context.get("scope1"));
```












