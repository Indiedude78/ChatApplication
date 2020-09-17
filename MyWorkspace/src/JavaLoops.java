
public class JavaLoops {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] myList = { 1, 3, 6, 9, 12, 14, 20, 23, 29, 32, 34 };

		System.out.println("Values in the Array:");
		for (int i = 0; i < myList.length; i++) {
			System.out.println(myList[i]);
		}

		System.out.println("\nEven Numbers Only: ");
		for (int value : myList) {
			if (value % 2 == 0) {
				System.out.println(value);
			}
		}
	}
}
