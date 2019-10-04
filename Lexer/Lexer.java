/*
Project #1: Lexer
Receives contents of .txt file for input, processes & parses contents of input file to output tokens.
Tommy Van Pham (#012977339), tvpham92@gmail.com
CECS444-01
Last updated: 9/28/2019
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Lexer 
{	
	public static void main(String[] args) throws FileNotFoundException 
	{
		int lineNum = 0, wordPos = 1;
		//File Scanner reads .txt file into string as for input
		//NECESSARY CHANGE: change "klown" to name of local user, and ensure file scanner is reading correct input file
		Scanner scanner = new Scanner(new File("C:\\Users\\klown\\Desktop\\444-p2_TVP\\input3.txt"));
		//files provided include: input1.txt, input2.txt, input3.txt
		
		while (scanner.hasNextLine()) 			//loop tracks line number, passes to lex function
		{
		   String line = scanner.nextLine();
		   // process the line
		   lineNum++;
		   line = line.replace("(", " ( ");
		   line = line.replace(";", " ;");
		   line = line.replace("\t", "");
		   lex(line, lineNum, wordPos);
		}
		//end of input
		outputToken("",lineNum+1,wordPos);
		scanner.close();
	}
	
	public static void lex(String s, int ln, int wp)			//lex function recursively iterates through input string, tracks word position on line
	{
		//Define indices for next word
		String lexString = s, remainingString;
		int lineNumber = ln, wordFront = 0, wordBack = 0, wordPosition = wp, space = 1, lexLen = lexString.length();
		//find ending index of next word
		
		//is this word a quoted string?
		if (lexString.indexOf('\"') == 0)
		{
			lexString = lexString.substring(wordFront + 1);
			wordBack = lexString.indexOf('\"');
		}
		else
		{
			wordBack = lexString.indexOf(' ');			//end of string returns -1
		}
		
		if(wordBack == -1)								
		{												//if at end of string:
			wordBack = lexLen;							//set back index at string length max
			space = 0;									//don't increment word index
		}
		String word = lexString.substring(wordFront, wordBack);		//uses indices to store chunk of string as word 
		
		//checks for comments, removes them
		if (word.contentEquals("//"))
		{
			word = "";
			remainingString = "";
		}
		else
		{
			remainingString = lexString.substring(wordBack+space);	//indexes remaining string following the space after word, unless at the end of string
		}
		//if word exists, call outputToken function
		if(word.length() > 0)
		{
			outputToken(word,ln,wp);
			wordPosition = wordPosition + word.length() + space;
		}
		//call lex function again until there is no more characters left in the string
		if (remainingString.length() != 0)
		{
			lex(remainingString, lineNumber, wordPosition);
		}
	}
	
	public static class Token			//token class stores attributes
	{
		String word, key;
		int ID, lineNum, linePos;
	}
	
	public static void outputToken(String s, int ln, int lp)	//creates token, passes values to class attributes, outputs token info
	{
		Token result = new Token();
		result.word = s;
		result.lineNum = ln;
		result.linePos = lp;
		String numVal = "";
		
		//Unpaired Delimiters
		if (result.word.contentEquals(","))
		{
			result.ID = 6;
		}
		else if (result.word.contentEquals(";"))
		{
			result.ID = 7;
		}
		//Keywords
		else if (result.word.contentEquals("prog"))
		{
			result.ID = 10;
		}
		else if (result.word.contentEquals("main"))
		{
			result.ID = 11;
		}
		else if (result.word.contentEquals("fcn"))
		{
			result.ID = 12;
		}
		else if (result.word.contentEquals("class"))
		{
			result.ID = 13;
		}
		else if (result.word.contentEquals("float"))
		{
			result.ID = 15;
		}
		else if (result.word.contentEquals("int"))
		{
			result.ID = 16;
		}
		else if (result.word.contentEquals("string"))
		{
			result.ID = 17;
		}
		else if (result.word.contentEquals("if"))
		{
			result.ID = 18;
		}
		else if (result.word.contentEquals("elseif"))
		{
			result.ID = 19;
		}
		else if (result.word.contentEquals("else"))
		{
			result.ID = 20;
		}
		else if (result.word.contentEquals("while"))
		{
			result.ID = 21;
		}
		else if (result.word.contentEquals("input"))
		{
			result.ID = 22;
		}
		else if (result.word.contentEquals("print"))
		{
			result.ID = 23;
		}
		else if (result.word.contentEquals("new"))
		{
			result.ID = 24;
		}
		else if (result.word.contentEquals("return"))
		{
			result.ID = 25;
		}
		else if (result.word.contentEquals("var"))
		{
			result.ID = 26;
		}
		//Paired delimiters
		else if (result.word.contentEquals("<"))
		{
			result.ID = 31;
		}
		else if (result.word.contentEquals(">"))
		{
			result.ID = 32;
		}
		else if (result.word.contentEquals("{"))
		{
			result.ID = 33;
		}
		else if (result.word.contentEquals("}"))
		{
			result.ID = 34;
		}
		else if (result.word.contentEquals("["))
		{
			result.ID = 35;
		}
		else if (result.word.contentEquals("]"))
		{
			result.ID = 36;
		}
		else if (result.word.contentEquals("("))
		{
			result.ID = 37;
		}
		else if (result.word.contentEquals(")"))
		{
			result.ID = 38;
		}
		//Other punctuation characters
		else if (result.word.contentEquals("*"))
		{
			result.ID = 41;
		}
		else if (result.word.contentEquals("^"))
		{
			result.ID = 42;
		}
		else if (result.word.contentEquals(":"))
		{
			result.ID = 43;
		}
		else if (result.word.contentEquals("."))
		{
			result.ID = 44;
		}
		else if (result.word.contentEquals("="))
		{
			result.ID = 45;
		}
		else if (result.word.contentEquals("-"))
		{
			result.ID = 46;
		}
		else if (result.word.contentEquals("+"))
		{
			result.ID = 47;
		}
		else if (result.word.contentEquals("/"))
		{
			result.ID = 48;
		}
		else if (result.word.contentEquals("&"))
		{
			result.ID = 49;
		}
		//Multi-char operators
		else if (result.word.contentEquals("->"))
		{
			result.ID = 51;
		}
		else if (result.word.contentEquals("=="))
		{
			result.ID = 52;
		}
		else if (result.word.contentEquals("!="))
		{
			result.ID = 53;
		}
		else if (result.word.contentEquals("<="))
		{
			result.ID = 54;
		}
		else if (result.word.contentEquals(">="))
		{
			result.ID = 55;
		}
		else if (result.word.contentEquals("<<"))
		{
			result.ID = 56;
		}
		else if (result.word.contentEquals(">>"))
		{
			result.ID = 57;
		}
		else if (result.word.contentEquals(""))
		{
			result.ID = 0;
		}
		else if (result.word.matches("^[-+]?\\d+$"))
		{
			result.ID = 3;
			numVal = " int= " + result.word;
		}
		else if (result.word.matches("-?\\d+(\\.\\d+)?"))
		{
			result.ID = 4;
			numVal = " flo= " + result.word;
		}
		else if (result.word.matches("[a-zA-Z0-9]*"))
		{
			result.ID = 2;
		}
		else if (result.word.matches("[a-zA-Z0-9 >=]*"))
		{
			result.ID = 5;
		}
		else
		{
			result.ID = 99;
		}
		//print token
		System.out.println("(Tok:\t" + result.ID + "\tlin= " + result.lineNum + "," + result.linePos + "\tstr= \"" + result.word + "\"" + numVal + ")");
	}
	
}