public class WarmOrCold {

    int secretNumber = 100;

    /**
     * _Part 1: Implement this method._
     * <p>
     * Given the specified guess, determine if it's close or not to your secret
     * number.
     *
     * @param guess a number being guessed
     * @return EXACTLY_RIGHT if the number is correct, WARM if it's within 10, o/w COLD
     */
    public int warmOrCold(int guess) {
        if (guess == secretNumber) {
            return Temperature.EXACTLY_RIGHT;
        }
        if (Math.abs(guess - secretNumber) < 10) {
            return Temperature.WARM;
        } else {
            return Temperature.COLD;
        }
    }
}
