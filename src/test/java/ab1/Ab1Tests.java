package ab1;

import ab1.impl.KassikLaferOswald.Ab1Impl;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Ab1Tests {

	private final Random rand = new Random(System.currentTimeMillis());

	private static final Ab1 ab1Impl = new Ab1Impl();

	private static final int NUM_TESTS = 100;
	private static final int ARRAY_SIZE_SMALL = 1000;
	private static final int ARRAY_SIZE_LARGE = 10000;

	@Test
	public void testSort() {
		for (int i = 0; i < NUM_TESTS; i++) {
			Record[] data = getRandomRecordArray(ARRAY_SIZE_SMALL);

			ab1Impl.sort(data);

            assertTrue(isSorted(data));
		}
	}

	@Test
	public void testMerge() {
		for (int i = 0; i < NUM_TESTS; i++) {
			Record[] h1 = getRandomRecordHeap(ARRAY_SIZE_SMALL);
			Record[] h2 = getRandomRecordHeap(ARRAY_SIZE_LARGE);

			Record[] data = ab1Impl.merge(h1, h2);

            assertTrue(isSorted(data));
		}
	}

	@Test
	public void testMultiplication() {
		for (int i = 0; i < NUM_TESTS; i++) {
			long num1 = getRandomNumber();
			long num2 = getRandomNumber();

			byte[] out = ab1Impl.karatsuba(toOctalArray(num1), toOctalArray(num2));

			assertArrayEquals(toOctalArray(num1 * num2), out);
		}
	}

	private long getRandomNumber() {
		return rand.nextInt(2000000000);
	}

	private byte[] toOctalArray(long num) {
		int[] arr = Long.toOctalString(num).chars().map(c -> c - 48).toArray();
		byte[] out = new byte[arr.length];
		for(int i = 0; i < arr.length; ++i) out[i] = (byte)arr[i];
		return out;
	}

	private Record[] getRandomRecordArray(int size) {
		Record[] arr = new Record[size];
		for (int i = 0; i < size; ++i)
			arr[i] = new Record(rand.nextInt(10),
					    rand.nextInt((size / 20)));
		return arr;
	}

	private Record[] getRandomRecordHeap(int size) {
		Record[] arr = new Record[size];
		arr[0] = new Record(rand.nextInt(10),
				    rand.nextInt((size / 10)));
		fillRandomRecordHeap(arr, 1);
		return arr;
	}

	private void fillRandomRecordHeap(Record[] arr, int pos) {
		int key = arr[pos-1].key;
		int subkey = arr[pos-1].subkey;

		if(2 * pos <= arr.length) {
			arr[2 * pos-1] = new Record(key + rand.nextInt(2),
						    subkey + rand.nextInt(3));
			fillRandomRecordHeap(arr, 2 * pos);
		}

		if(2 * pos + 1 <= arr.length) {
			arr[2 * pos] = new Record(key + rand.nextInt(2),
						  subkey + rand.nextInt(3));
			fillRandomRecordHeap(arr, 2 * pos + 1);
		}
	}

	private boolean isSorted(Record[] data) {
		for (int i = 0; i < data.length - 1; i++)
			if (data[i].key > data[i + 1].key
					|| data[i].key == data[i + 1].key
					&& data[i].subkey > data[i + 1].subkey) 
				return false;
		return true;
	}
}
