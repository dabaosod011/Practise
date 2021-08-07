import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FixedSizeQueue {
	private int FixedSize;
	private LinkedList<Float> container;
	
	FixedSizeQueue(int fixedSize){
		if(fixedSize <= 0){
			fixedSize = 8192;
		}
		
		this.FixedSize = fixedSize;
		container = new LinkedList<Float>();
	}
	

    public void enqueue(float data) {
    	if(container.size() >= FixedSize && !container.isEmpty()){
    		container.removeFirst();
    	}
    	container.addLast(data);
    }

    public void dequeue() {
        if(!container.isEmpty())
        	container.removeFirst();

    }

    public void displayQueue() {
    	System.out.println("size = " + container.size() + " : "+Arrays.toString(container.toArray()));
    }

    public boolean isEmpty(){
        return container.isEmpty();
    }
    
    public int size(){
    	return container.size();
    }
    
    public float[] getArray(){
    	float []array = new float[container.size()];
    	for (int i = 0 ; i < container.size(); i ++){
    		array[i] = container.get(i);
    	}
    	
    	return array;
    }
    
}
