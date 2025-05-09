import java.util.List;

public class EvenSum {
    public static int sumOfEvenNumbers(List<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            if (number % 2 == 0) {
                sum += number;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int result = sumOfEvenNumbers(numbers);
        System.out.println("Sum of even numbers: " + result);
    }
}
