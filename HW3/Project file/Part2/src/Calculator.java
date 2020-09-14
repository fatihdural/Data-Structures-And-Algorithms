import java.io.*;
import java.util.StringTokenizer;
/**
 *  this class evaluates an infix expression, first convert expression to postfix and evaluate
 *  the postfix expression with a stack based algoritm.
 */
public class Calculator {       // it takes a file path and calculate expression in the file.
    private StringBuilder values = new StringBuilder(); // it is for variables and values of them.
    /**
     *  constructor takes a string file name with path.
     */
    public Calculator(String filename){
        try{
            BufferedReader br = new BufferedReader( new FileReader(filename) );
            String line;
            while( ( line = br.readLine() )  != null ){ // reads up to end of file
                if( getLine(line) ){        // if line has no = operator
                    if( line.contains("//") )    // if line is a comment, turn the start of loop ( extra control )
                        continue;
                    String [] expression = infix_to_postfix(line); // convert infix to postfix
                    Stack result = processExpression(expression);   // process postfix expression.
                    printResult( result );                          // print the result.
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
    }
    /**
     *  convert infix to postfix method. it takes a line.
     *  return string []. all string element is a postfix tokens.
     */
    private String [] infix_to_postfix(String line){    // convert infix to postfix expression in given line
        Stack<String> stack = new Stack<String>();
        String [] expression = new String[0];
        StringTokenizer token = new StringTokenizer(line);
        while( token.hasMoreElements() ){       // finding the tokens in line.
            String token2 = token.nextToken();  // string token2 take the next token.
            if( isOperator(token2) || token2.equals("(") ){// if token is operator
                stack.push(token2);         // push the stack
            }
            else if( isFunc(token2) ){  // if token is operator
                stack.push("(");        // push them to the stack
                stack.push(token2.substring(0,3));
            }

            // *Slides contain this algoritm*
            else if( token2.equals(")") ){  // token is close paranthesis
                while( true ){
                    if( !stack.peek().equals("(") ){    // when top of stack is not "("
                        String [] temp = new String[expression.length + 1];
                        for( int i = 0; i < expression.length; i++){
                            temp[i] = expression[i];
                        }
                        temp[expression.length] = stack.peek();
                        expression = temp;          // put to array the top of stack
                        stack.pop();
                    }
                    else{   // when top of stack is "(", pop and break the loop.
                        stack.pop();
                        break;
                    }
                }
            }

            else {
                String [] temp = new String[expression.length + 1];
                for( int i = 0; i < expression.length; i++){
                    temp[i] = expression[i];
                }
                temp[expression.length] = token2;   // put to array the top of stack
                expression = temp;
            }
        }

        while( !stack.empty() ){        // no paranthesis in stack, free the stack to array.
            String [] temp = new String[expression.length + 1];
            for( int i = 0; i < expression.length; i++){
                temp[i] = expression[i];
            }
            temp[expression.length] = stack.pop();
            expression = temp;
        }
        return expression;
    }
    /**
     *  process the given postfix expression.
     */
    private Stack processExpression(String [] expression){
        Stack<Double> result = new Stack<>();   // result stack
        for( int i = 0; i < expression.length; i++){
            if( isVar(expression[i]) ){             // if token is variable
                result.push( valueOfVar( expression[i] ));
            }
            else if( isNumeric( expression[i] ) ){         // if token is numeric value ( 1, 3, -10 etc.)
                result.push(  Double.parseDouble(expression[i]) );
            }
            else if( isOperator(expression[i]) ){          // if token is operator ( + - * / )
                String sum = "+";
                String subt = "-";
                String mult = "*";
                String divi = "/";

                double right = result.pop();
                double left = result.pop();
                if( sum.equals( expression[i] ) ){      // if operator is +
                    result.push( left + right );
                }

                else if( subt.equals( expression[i] ) ){        // if operator is -
                    result.push( left - right );
                }

                else if( mult.equals( expression[i] ) ){    // if operator is *
                    result.push( left * right );
                }

                else if( divi.equals( expression[i] ) ){        // if operator is (/)
                    result.push( left / right );
                }
            }

            else if( isFunc( expression[i] ) ){         // if token is a function
                if( expression[i].equals( "cos" ) ){
                    result.push(  cos( result.pop() ) ) ;
                }
                else if( expression[i].equals( "sin" ) ){
                    result.push( sin( result.pop() ) ) ;
                }
                else if( expression[i].equals( "abs" ) ){
                    result.push( abs( result.pop() ) ) ;
                }
            }
        }
        return result;
    }
    /**
     *  print the infix expression's result on screen.
     */
    private void printResult(Stack result){
        while(!result.empty()){
            System.out.printf("%s %.2f\n", "The infix expression's result is :", result.pop());
        }
    }
    /**
     *  it takes a line, control it has assignment operator or not.
     *  if there are variables, assign them to instance variable that StringBuilder values.
     */
    private boolean getLine(String line){    // function find the variables and assign them to values array.
        int count = 0;
        for( int i = 0; i < line.length(); i++){
            if( line.charAt(i) == '=' ){            // assignments must be like that x = 5, y = 10.
                values.append(line.charAt(i - 2));
                count++;
            }
            else if( count != 0 &&  line.charAt(i) != ' ' && line.charAt(i) != '\n'){   // values of variables.
                values.append(line.charAt(i));
            }
        }
        if( count == 0 )        // if there is no assignment operator (=), return true.
            return true;
        return false;
    }
    /**
     *  variable control for given token.
     */
    private boolean isVar(String token){         // variable control.
        if( !token.equals("-") ){       // variables are not be "-" operator but, values can contain -.
            for( int i = 0; i < values.length(); i++){
                String temp = String.valueOf( values.charAt(i) );
                if( token.equals( temp )){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     *  find the values of variables.
     */
    private double valueOfVar(String token){     // finding the variable value ( x = 5, y = 10 etc.)
        int start = 0;
        int end = 0;
        for( int i = 0; i < values.length(); i++){
            String temp = String.valueOf( values.charAt(i) );
            if( token.equals( temp ) ){         // finding variable
                i++;                // next element is digit first ( for 150 -> 1. )
                start = i;
                i++;
                while( i  < values.length() && Character.isDigit( values.charAt(i) )   ){
                    i++;                                        // if variable has more than one digit.
                }                                   // for 150 -> 5, 0.
                end = i;
                break;
            }
        }
        return Double.parseDouble(String.valueOf( values.substring(start,end) ));   // return the digit
    }
    /**
     *  token is a numberic or not control.
     */
    private boolean isNumeric(String str) { // numeric control.
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    /**
     *  operator control in given token
     */
    private boolean isOperator(String token){   // operator control.
        if( token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") ){
            return true;
        }
        return false;
    }
    /**
     *  function control ( sin - cos - abs )
     */
    private boolean isFunc(String token){          // function control.
        if( token.equals("sin(") || token.equals("cos(") || token.equals("abs(") || token.equals("sin") || token.equals("cos") || token.equals("abs")){
            return true;
        }
        return false;
    }

    /*
        sin() function and its helper functions radians() factorial() and power() taken from
        https://stackoverflow.com/questions/22416710/in-c-finding-sinx-value-with-taylors-series
    */
    /**
     *  convert degree to radians.
     */
    private double radians(double degrees)  // converts degrees to radians
    {
        double radians;
        double pi = 3.14159265358979323846;
        radians = (pi/180)*degrees;
        return radians;
    }
    /**
     *  find factorial
     */
    private double factorial(int x)  //calculates the factorial
    {
        double fact = 1;
        for(; x >= 1 ; x--)
        {
            fact = x * fact;
        }
        return fact;
    }
    /**
     *  find power of given number
     */
    private double power(double x,double n)  //calculates the power of x
    {
        double output = 1;
        while(n > 0)
        {
            output =( x*output);
            n--;
        }
        return output;
    }
    /**
     *  find the sinus of given degree.
     */
    private double sin(double degree)  //value of sine by Taylors series
    {
        double radians = radians(degree);
        if( degree == 180 ) // it doesnt work for 180 degree. I give the value manually.
           radians = 0;
        double a,b,c;
        double result = 0;
        for(int y=0 ; y!=9 ; y++)
        {
            a = power(-1,y);
            b = power(radians,(2*y)+1);
            c = factorial((2*y)+1);
            result = result + (a*b)/c;
        }
        return result;
    }
    /**
     *  finding cosinus with sin() method
     */
    private double cos(double degree){      // finding cosinus with sin() function
        double result = 0;
        if( degree <= 90 ){
            result = sin(90.0 - degree);
        }
        else if( degree <= 360){
            result = -1 * sin(90 - (180 - degree));
            if( result == 0)
                result *= -1;
        }
        return result;
    }
    /**
     *  returning absoulute value of element, so for negative elements, convert them to positive.
     */
    private double abs(double item){        // return absoulute value of element
        if( item < 0 ){
            item *= -1;
        }
        return item;
    }
}
