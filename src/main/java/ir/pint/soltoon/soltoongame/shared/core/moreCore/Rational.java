package ir.pint.soltoon.soltoongame.shared.core.moreCore;

public class Rational {
	
	private int nominator;
	private int denominator;
	
	public Rational(int nominator, int denominator) {
		super();
		this.nominator = nominator;
		this.denominator = denominator;
	}
	
	public int getNominaRtor() {
		return nominator;
	}
	public void setNominator(int nominator) {
		this.nominator = nominator;
	}
	public int getDenominator() {
		return denominator;
	}
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public boolean less(int n) {
		return nominator < n * denominator;
	}
	
}
