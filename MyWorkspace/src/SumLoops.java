
public class SumLoops {

	public static int sum(int num) {
		int total = 0;
		while (num > 0) {
			total = total + num;
			num--;
		}
		return total;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(sum(10));
	}

}
