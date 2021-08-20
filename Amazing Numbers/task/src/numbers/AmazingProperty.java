package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AmazingProperty {
    private final String stringLong;
    private final long number;
    private final boolean natural;

    public boolean isNatural() { return natural; }
    public boolean isEven() { return number % 2 == 0; }
    public boolean isOdd() { return !isEven(); }
    public boolean isBuzz() { return number % 7 == 0; }
    public boolean isBuzzEnd() { return number % 10 == 7; }
    public boolean isDuck() { return (stringLong.indexOf("0") > 0); }
    public boolean isPalindromic() {
        String reverseInt = new StringBuffer(stringLong).reverse().toString();
        return stringLong.equals(reverseInt);
    }
    public boolean isGapful() {
        if (stringLong.length() > 2) {
            char[] arr = stringLong.toCharArray();
            String az = "" + arr[0] + arr[arr.length - 1];
            return number % Integer.parseInt(az) == 0;
        } else return false;
    }
    public boolean isSpy() {
        boolean spy;
        if (stringLong.length() == 1) {
            spy = true;
        } else {
            long mult = 1;
            long summ = 0;
            char[] arr = stringLong.toCharArray();
            for (char c: arr) {
                mult *= Long.parseLong(String.valueOf(c));
                summ += Long.parseLong(String.valueOf(c));
            }
            spy = mult == summ;
        }
        return spy;
    }
    private boolean isSquare(int add) {
        double sq = Math.sqrt(number + add);
        return ((sq - Math.floor(sq)) == 0);
    }
    public boolean isSunny() { return isSquare(1); }
    public boolean isSquare() { return isSquare(0); }
    public boolean isJumping() {
        boolean jumping;
        if (stringLong.length() == 1) {
            jumping = true;
        } else {
            char[] arr = stringLong.toCharArray();
            jumping = true;
            for (int i = 1; i < arr.length; i++) {
                if (Math.abs(arr[i - 1] - arr[i]) != 1) {
                    jumping = false;
                    break;
                }
            }
        }
        return jumping;
    }

    List<Long> checked = new ArrayList<Long>();
    public boolean isHappy(Long i) {
        List<Integer> happyArr = new ArrayList<>(Arrays.asList(1,7));
        List<Integer>   sadArr = new ArrayList<>(Arrays.asList(2,3,4,5,6,8,9));
        if (i < 10) {
            int a = Integer.parseInt(i.toString());
            if (happyArr.contains(a))
                return true;
            else if (sadArr.contains(a))
                return false;
        }
        long nextNum = 0;
        char[] arr = i.toString().toCharArray();
        for (char c: arr) {
            long a = Long.parseLong(String.valueOf(c));
            nextNum += Math.pow(a, 2);
        }
        for (Long j : checked) if (i == nextNum)
            return false;
        checked.add(nextNum);
        return isHappy(nextNum);
    }
    public boolean isHappy() {
        checked.clear();
        return isHappy(number);
    }
    public boolean isSad() { return !isHappy(); }

    public AmazingProperty(String stringLong) {
        this.stringLong = stringLong;
        number = Long.parseLong(stringLong);
        natural = number > 0;
    }

    @Override
    public String toString() {
        if (!natural) return "The first parameter should be a natural number or zero.";

        return "Properties of " + number + "\n" +
                "\t even: " + isEven()  + "\n" +
                "\t odd: " + isOdd()  + "\n" +
                "\t buzz: " + (isBuzz() || isBuzzEnd())  + "\n" +
                "\t duck: " + isDuck()  + "\n" +
                "\t gapful: " + isGapful()  + "\n" +
                "\t spy: " + isSpy()  + "\n" +
                "\t sunny: " + isSunny()  + "\n" +
                "\t square: " + isSquare()  + "\n" +
                "\t jumping: " + isJumping()  + "\n" +
                "\t happy: " + isHappy()  + "\n" +
                "\t sad: " + isSad()  + "\n" +
                " palindromic: " + isPalindromic();
    }

    public String quickStr() {
        return number + " is" + (isEven() ? " even" : "") +
                (isOdd() ? " odd" : "") +
                ((isBuzz() || isBuzzEnd()) ? " buzz" : "") +
                (isDuck() ? " duck" : "") +
                (isGapful() ? " gapful" : "") +
                (isSpy() ? " spy" : "") +
                (isSunny() ? " sunny" : "") +
                (isSquare() ? " square" : "") +
                (isJumping() ? " jumping" : "") +
                (isHappy() ? " happy" : "") +
                (isSad() ? " sad" : "") +
                (isPalindromic() ? " palindromic" : "");
    }
}