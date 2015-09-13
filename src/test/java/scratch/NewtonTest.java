package scratch;

import static java.lang.Math.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class NewtonTest {
	static class Newton {
		private static final double TOLERANCE = 1E-16;// can access this field from `NewtonTest`.
		public static double squareRoot(double n) {
			double approx = n;
			while (abs(approx - n / approx) > TOLERANCE * approx) {// abs(1 - n/Xn^2) > TOLERANCE
				// Xn+1 = (n / Xn + Xn) / 2;
				//      = (n + Xn^2) / 2Xn
				//      = Xn - (Xn^2 - 2)/Xn
				approx = (n / approx + approx) / 2.0;
			}
			return approx;
		}
	}

	@Test
	public void squareRoot() {
		double result = Newton.squareRoot(250.0);
		assertThat(result * result, closeTo(250.0, Newton.TOLERANCE));
	}

	@Test
	public void squareRootByVerificationUsingMathSqrt() {
		double result = Newton.squareRoot(1969.0);
		assertThat(result, closeTo(sqrt(1969.0), Newton.TOLERANCE));
	}
}
