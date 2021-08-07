import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

public class FixedSizeQueueDemoTest {

	private static FixedSizeQueue fsq;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		fsq = new FixedSizeQueue(8);
		
		fsq.enqueue(1.0f);	fsq.displayQueue();
		fsq.enqueue(2.0f);	fsq.displayQueue();
		fsq.enqueue(3.0f);	fsq.displayQueue();
		fsq.enqueue(4.0f);	fsq.displayQueue();
		fsq.enqueue(5.0f);	fsq.displayQueue();
		fsq.enqueue(6.0f);	fsq.displayQueue();
		fsq.enqueue(7.0f);	fsq.displayQueue();
		fsq.enqueue(8.0f);	fsq.displayQueue();
		fsq.enqueue(9.0f);	fsq.displayQueue();
		fsq.enqueue(10.0f);	fsq.displayQueue();
		fsq.enqueue(11.0f);	fsq.displayQueue();
		fsq.enqueue(12.0f);	fsq.displayQueue();
		
		System.out.println(Arrays.toString(fsq.getArray()));

	}

}
