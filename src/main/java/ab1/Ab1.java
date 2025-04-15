package ab1;

/**
 * Das zu implementierende Interface
 */
public interface Ab1 {

	/**
	* Sortiert ein Array von Datensatz-Objekten mit QuickSort und Dutch
	* National Flag-Modifikation, sowie Insertion Sort für kurze Arrays.
	* <p>
	* Zu implementieren ist der QuickSort-Algorithmus mit der "Dutch
	* National Flag"-Modifikation (recherchieren Sie eigenständig; es wird
	* bei der Pivotisierung in drei Teilmengen aufgeteilt: Elemente (a)
	* kleiner, (b) gleich und (c) größer als das Pivot-Element).
	* <p>
	* Beinhaltet das (Sub-)Array weniger als 10 Elemente, so soll zum
	* Sortieren Insertion Sort verwendet werden. Achten Sie darauf, dass
	* Insertion Sort ein stabiles Sortierverfahren ist.
	* <p>
	* Die Sortierreihenfolge ergibt sich aus dem Vergleich von
	* Record-Objekten wie folgt:
	*   - Primärschlüssel (key)
	*   - Sekundärschlüssel (subkey), bei gleichem Primärschlüssel
	* <p>
	* Achten Sie darauf, dass Ihre Implementierung "in-place" arbeitet,
	* d.h. nur O(1) zusätzlichen Speicher verwenden darf.
	*
	* @param array Das Array von Record-Objekten, das sortiert werden soll.
	*/
	public void sort(Record[] array);

	/**
	 * Diese Methode verwendet Heaps, um zwei Arrays sortiert miteinander
	 * zu verschmelzen. Der Input sind zwei Arrays, für die die
	 * Min-Heap-Eigenschaft erfüllt ist. Der Algorithmus soll nun immer das
	 * kleinste Element der beiden Heaps in das Output-Array verschieben
	 * und danach den jeweiligen Heap wiederherstellen. Am Ende sind
	 * dadurch alle Elemente aus h1 und h2 sortiert im Output-Array
	 * angeordnet.
	 *
	 * Die Sortierreihenfolge ergibt sich aus dem Vergleich von
	 * Record-Objekten wie folgt:
	 *   - Primärschlüssel (key)
	 *   - Sekundärschlüssel (subkey), bei gleichem Primärschlüssel
	 *
	 * @param h1    der erste Min-Heap
	 * @param h2    der zweite Min-Heap
	 * @return Sortiertes, verschmolzenes Array beider Input-Arrays
	 */
	public Record[] merge(Record[] h1, Record[] h2);

	/**
	* Multipliziert zwei Oktalzahlen mithilfe des Karatsuba-Algorithmus.
	* Diese Methode verwendet zwei Byte-Arrays zur Darstellung der
	* Oktalzahlen, wobei jedes Byte eine Ziffer im Oktalsystem (also zur
	* Basis 8) darstellt, mit der niederwertigsten Ziffer am Ende des
	* Arrays.
	*
	* @param a Das erste Byte-Array, das die erste Oktal-Zahl darstellt.
	* @param b Das zweite Byte-Array, das die zweite Oktal-Zahl darstellt.
	* @return Ein Byte-Array, dass die Ergebniszahl (oktal) darstellt.
	*/
	byte[] karatsuba(byte[] a, byte[] b);
}
