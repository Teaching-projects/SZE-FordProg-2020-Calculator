

package com.company;
import com.company.Calculator;



public class Parser {
	public static final int _EOF = 0;
	public static final int _ident = 1;
	public static final int _pre = 2;
	public static final int _act = 3;
	public static final int _ans = 4;
	public static final int _ANS = 5;
	public static final int maxT = 8;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	Calculator calculator;


/*---------------------------------------------------*/


	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	void Calculator() {
		double buff=0.0;
		if (la.kind == 1 || la.kind == 4 || la.kind == 5) {
			double x = Expression();
			buff+=x;
		} else if (la.kind == 6) {
			double y = ParenthesisExpression();
			buff+=y;
			while (la.kind == 2 || la.kind == 3 || la.kind == 6) {
				String s = Operation();
				double y1 = ParenthesisExpression();
				if(s==null) buff=buff*y1;
				                        else if(s.equals("+")) buff+=+y1;
				                                       else if(s.equals("-")) buff-=y1;
				                                      else if(s.equals("/")) buff=buff/y1;
				                                      else if(s.equals("*")) buff=buff*y1;
				                                      else buff=buff*y1;
				                                           
			}
		} else SynErr(9);
		calculator.showResult(buff);
	}

	double  Expression() {
		double  ret;
		double n1; double n2; String s=null;ret=0.0;
		if (la.kind == 1 || la.kind == 4) {
			n1 = Ident();
			ret=n1;
			while (StartOf(1)) {
				s = Operation();
				n2 = Ident();
				if(s==null) ret=n1+n2;
				else if(s.equals("+")) ret=ret+n2;
				else if(s.equals("-")) ret=ret-n2;
				else if(s.equals("/")) ret=ret/n2;
				else if(s.equals("*")) ret=ret*n2;
				else ret=ret+n2;
			}
		} else if (la.kind == 5) {
			Get();
			ret=calculator.getAns();
		} else SynErr(10);
		return ret;
	}

	double  ParenthesisExpression() {
		double  ret;
		Expect(6);
		double x = Expression();
		ret=x;
		Expect(7);
		return ret;
	}

	String  Operation() {
		String  s;
		if (la.kind == 2 || la.kind == 3) {
			if (la.kind == 2) {
				Get();
			} else {
				Get();
			}
		}
		s=t.val;
		return s;
	}

	double  Ident() {
		double  n;
		n=0.0;
		if (la.kind == 1) {
			Get();
			n = Double.parseDouble(t.val);
		} else if (la.kind == 4) {
			Get();
			n = calculator.getAns();
		} else SynErr(11);
		return n;
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Calculator();
		Expect(0);

	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x},
		{_x,_T,_T,_T, _T,_x,_x,_x, _x,_x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "ident expected"; break;
			case 2: s = "pre expected"; break;
			case 3: s = "act expected"; break;
			case 4: s = "ans expected"; break;
			case 5: s = "ANS expected"; break;
			case 6: s = "\"(\" expected"; break;
			case 7: s = "\")\" expected"; break;
			case 8: s = "??? expected"; break;
			case 9: s = "invalid Calculator"; break;
			case 10: s = "invalid Expression"; break;
			case 11: s = "invalid Ident"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
