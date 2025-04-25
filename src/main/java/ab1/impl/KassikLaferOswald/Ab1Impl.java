package ab1.impl.KassikLaferOswald;

import ab1.Record;
import ab1.Ab1;

import java.util.Arrays;

public class Ab1Impl implements Ab1 {

    //region QuickSort (mit Dutch National Flag Partitionierung) und InsertionSort - (Oswald)

    /**
     * Sortiert ein Array von {@link Record}-Objekten mithilfe einer Kombination
     * aus QuickSort (mit Dutch National Flag Partitionierung) und InsertionSort.
     * Für Arrays mit weniger als 10 Elementen ist InsertionSort effizienter, weil es weniger Overhead hat.
     * Für größere Arrays ist Quicksort in der Regel performanter (Divide and Conquer).
     * Die Sortierung erfolgt in-place, d.h. ohne zusätzlichen Speicher.
     *
     * @param array Das zu sortierende Array.
     */
    @Override
    public void sort(Record[] array) {
        if (array == null || array.length <= 1) return;    // Leeres oder triviales Array → bereits sortiert
        quicksort(array, 0, array.length - 1);    // QuickSort auf gesamtem Array starten
    }

    /**
     * Führt für Arrays mit mehr als 10 Elementen einen QuickSort mit "Dutch National Flag"-Modifikation durch.
     * </p>
     * Quicksort ist ein schneller, rekursiver, nicht-stabiler Sortieralgorithmus,
     * der nach dem Prinzip Teile und herrsche arbeitet.
     * </p>
     * Die Dutch National Flag Partitionierung ist eine Modifikation bei dem das zu sortierende Array nicht mehr in 2,
     * sondern in 3 Teile (kleiner, gleich und größer) geteilt wird.
     * </p>
     * Für kleine Arrays (unter 10 Elemente) wird InsertionSort verwendet.
     *
     * @param array      Das zu sortierende Array
     * @param startIndex Der Startindex des Teilarrays
     * @param endIndex   Der Endindex des Teilarrays
     */
    private void quicksort(Record[] array, int startIndex, int endIndex) {
        // Wenn das Array oder Teilarray bereits weniger als 10 Elemente umfasst verwende "InsertionSort"
        if (endIndex - startIndex < 9) {
            insertionSort(array, startIndex, endIndex);
        } else {
            // Wenn das Array oder Teilarray mehr als 10 Elemente umfasst verwende "QuickSort"
            Record pivotElement = array[endIndex];      // Pivot-Element ist das letzte im betrachteten Bereich
            int indexKleiner = startIndex;                // Grenze für "kleiner als Pivot"
            int indexGroesser = endIndex;                // Grenze für "größer als Pivot"
            int laufIndex = startIndex;                    // Aktueller Index, der durchläuft

            while (laufIndex <= indexGroesser) {
                int ergebnisVergleich = compare(array[laufIndex], pivotElement);   // Vergleiche aktuelles Element mit dem Pivot
                if (ergebnisVergleich < 0) {                    // Das Element ist kleiner
                    swap(array, indexKleiner++, laufIndex++);   // DasElement nach vorne tauschen
                } else if (ergebnisVergleich > 0) {                // Das Element ist größer
                    swap(array, laufIndex, indexGroesser--);    // nach hinten tauschen
                } else {
                    laufIndex++;                                // Das Element ist gleich groß
                }
            }

            // Rekursiv die einzelnen Bereiche des Arrays sortieren
            quicksort(array, startIndex, indexKleiner - 1);   // Linker Bereich < Pivot
            quicksort(array, indexGroesser + 1, endIndex);   // Rechter Bereich > Pivot
        }
    }

    /**
     * Führt einen stabilen InsertionSort auf einem kleinen oder Teil-Array durch.
     * <p>
     * Insertionsort ist ein einfaches stabiles Sortierverfahren.
     * Die Reihenfolge von Elementen mit gleichem Schlüsselwert bleibt unverändert.
     * Es ist leicht zu implementieren, effizient bei kleinen oder bereits teilweise sortierten Eingabemengen.
     * Außerdem benötigt Insertionsort keinen zusätzlichen Speicherplatz, da der Algorithmus in-place arbeitet.
     *
     * @param array Das zu sortierende Array
     * @param start Startindex des Teilarrays
     * @param end   Endindex des Teilarrays
     */
    private void insertionSort(Record[] array, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            Record current = array[i];          // Das aktuelle Element, das einsortiert werden soll
            int j = i - 1;
            // Solange der Anfang nicht erreicht ist und das Element zur linken größer ist als das gewählte Element
            while (j >= start && compare(array[j], current) > 0) {
                array[j + 1] = array[j];        // Element nach rechts verschieben
                j--;
            }
            array[j + 1] = current;             // Element an passender Stelle einsetzen
        }
    }


    /**
     * Vergleicht zwei {@link Record}-Objekte:
     * - Zuerst nach Primärschlüssel (key)
     * - Bei Gleichheit nach Sekundärschlüssel (subkey)
     *
     * @param a Erstes Record-Objekt
     * @param b Zweites Record-Objekt
     * @return Negativ wenn a < b, 0 wenn gleich, positiv wenn a > b
     */
    private int compare(Record a, Record b) {
        // Wenn sich die Primärschlüssel unterscheiden
        if (a.key != b.key) {
            // Vergleich nach Primärschlüssel
            return Integer.compare(a.key, b.key);
        } else {
            // Vergleich nach Sekundärschlüssel
            return Integer.compare(a.subkey, b.subkey);
        }
    }

    /**
     * Vertauscht zwei Elemente im Array.
     *
     * @param array Das Array, in dem getauscht wird
     * @param i     Index des ersten Elements
     * @param j     Index des zweiten Elements
     */
    private void swap(Record[] array, int i, int j) {
        Record tmp = array[i];     // Temporärspeicher
        array[i] = array[j];       // Element i ← j
        array[j] = tmp;            // Element j ← tmp (ursprünglich i)
    }
    //endregion

    @Override
    public Record[] merge(Record[] h1, Record[] h2) {
        // Schritt 1: Wandle beide Heaps (unsortiert, aber mit Heapstruktur) in sortierte Arrays um.
        Record[] sorted1 = heapToSortedArray(h1);
        Record[] sorted2 = heapToSortedArray(h2);

        // Schritt 2: Merge der beiden sortierten Arrays wie bei Merge-Sort.
        Record[] result = new Record[sorted1.length + sorted2.length];
        int i = 0, j = 0, k = 0;

        // Solange beide Arrays noch Elemente enthalten, wähle jeweils das kleinere der aktuellen Einträge.
        while (i < sorted1.length && j < sorted2.length) {
            if (compare(sorted1[i], sorted2[j]) <= 0) {
                result[k++] = sorted1[i++]; // nimm Element aus sorted1
            } else {
                result[k++] = sorted2[j++]; // nimm Element aus sorted2
            }
        }

        // Wenn noch Reste in sorted1 sind, füge sie dem Ergebnis hinzu.
        while (i < sorted1.length) {
            result[k++] = sorted1[i++];
        }

        // Wenn noch Reste in sorted2 sind, füge sie dem Ergebnis hinzu.
        while (j < sorted2.length) {
            result[k++] = sorted2[j++];
        }

        // Gib das finale, vollständig sortierte Array zurück.
        return result;

    }

    private void heapifyDown(Record[] heap, int size, int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            // Vergleiche mit linkem Kind
            if (left < size && compare(heap[left], heap[smallest]) < 0) {
                smallest = left;
            }

            // Vergleiche mit rechtem Kind
            if (right < size && compare(heap[right], heap[smallest]) < 0) {
                smallest = right;
            }

            // Wenn Wurzel schon das kleinste Element ist, sind wir fertig
            if (smallest == i) {
                break;
            }

            // Tausche Element an Position i mit dem kleineren Kind
            Record temp = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = temp;

            // Fahre mit dem kleineren Kind weiter
            i = smallest;
        }
    }

    private Record[] heapToSortedArray(Record[] heap) {
        // Erstelle eine Kopie, damit das Original nicht verändert wird.
        Record[] copy = new Record[heap.length];
        System.arraycopy(heap, 0, copy, 0, heap.length);

        // Ergebnisarray, das am Ende sortiert zurückgegeben wird.
        Record[] sorted = new Record[heap.length];

        // Aktuelle Heapgröße (wird nach jedem Entfernen reduziert)
        int size = heap.length;

        // Wiederhole so oft wie es Elemente gibt:
        // Hole immer das kleinste Element (Wurzel des Heaps), speichere es,
        // ersetze es durch das letzte Element und repariere dann den Heap.
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = copy[0];             // Wurzel = Minimum
            copy[0] = copy[size - 1];        // Ersetze Wurzel durch letztes Element
            size--;                          // "Entferne" letztes Element
            heapifyDown(copy, size, 0);      // Stelle Heap-Eigenschaft wieder her
        }

        return sorted;
    }



    @Override
    public byte[] karatsuba(byte[] a, byte[] b) {
        //TODO: implement
        return null;
    }
}
