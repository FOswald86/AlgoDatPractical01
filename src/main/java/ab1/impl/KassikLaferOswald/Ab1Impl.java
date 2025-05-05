package ab1.impl.KassikLaferOswald;

import ab1.Record;
import ab1.Ab1;

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

    //region merge

    /**
     * Diese Methode merged zwei Heaps in ein gemeinsames sortiertes Array.
     * Dabei werden beide Heaps zunächst sortiert und dan zusammengefügt
     *
     * @param h1 erste Heap
     * @param h2 zweite Heap
     * @return h1 mit h2 zusammengeführt
     */
    @Override
    public Record[] merge(Record[] h1, Record[] h2) {
        // Wandelt beide Heaps in vollständig sortierte Arrays um
        Record[] sorted1 = heapToSortedArray(h1);
        Record[] sorted2 = heapToSortedArray(h2);

        // Erstellt ein neues Array mit Platz für alle Elemente aus beiden Arrays
        Record[] result = new Record[sorted1.length + sorted2.length];

        // Index i für sorted1, j für sorted2, k für das Ergebnis-Array
        int i = 0;
        int j = 0;
        int k = 0;

        // Solange beide Arrays noch nicht leer sind, vergleichen und das kleinere einfügen
        while (i < sorted1.length && j < sorted2.length) {
            // compare gibt negativen Wert zurück, wenn sorted1[i] < sorted2[j]
            if (compare(sorted1[i], sorted2[j]) <= 0) {
                result[k++] = sorted1[i++];  // Nächstes Element aus sorted1 nehmen
            } else {
                result[k++] = sorted2[j++];  // Nächstes Element aus sorted2 nehmen
            }
        }

        // Falls in sorted1 noch Elemente übrig sind, alle anhängen
        while (i < sorted1.length) {
            result[k++] = sorted1[i++];
        }

        // Dasselbe für sorted2
        while (j < sorted2.length) {
            result[k++] = sorted2[j++];
        }

        // Das finale, komplett sortierte Array wird zurückgegeben
        return result;
    }


    /**
     * Diese Methode sorgt dafür, dass die Heapeigenschaft nach unten wiederhergestellt wird
     *
     * @param heap Das Array, das den Heap repräsentiert
     * @param size Die tatsächliche Größe des Heaps
     * @param i    Der Index, bei dem die Überprüfung starten soll
     */
    private void heapifyDown(Record[] heap, int size, int i) {
        while (true) {
            // Berechne die Indizes der linken und rechten Kindknoten
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i; // nehme an, das aktuelle Element ist das kleinste

            // Wenn das linke Kind existiert und kleiner ist als das aktuelle kleinste,
            // dann aktualisiere den Index des kleinsten Elements
            if (left < size && compare(heap[left], heap[smallest]) < 0) {
                smallest = left;
            }

            // Dasselbe mit dem rechten Kind
            if (right < size && compare(heap[right], heap[smallest]) < 0) {
                smallest = right;
            }

            // Wenn das aktuelle Element schon das kleinste ist, ist der Heap korrekt
            if (smallest == i) {
                break;
            }

            // Ansonsten tausche das aktuelle Element mit dem kleineren Kind
            Record temp = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = temp;

            // Fahre mit dem kleineren Kind weiter, da dort jetzt evtl. ein Fehler vorliegt
            i = smallest;
        }
    }


    /**
     * Wandelt ein Min-Heap-Array in ein sortiertes Array um (aufsteigend)
     * Dabei wird das Original nicht verändert – es wird mit einer Kopie gearbeitet
     *
     * @param heap Der ursprüngliche Heap
     * @return Ein sortiertes Array mit denselben Elementen
     */
    private Record[] heapToSortedArray(Record[] heap) {
        // Erstelle eine Kopie vom Heap, damit das Original nicht verändert wird
        Record[] copy = new Record[heap.length];
        // Array, in dem das sortierte Ergebnis gesammelt wird
        Record[] sortedArray = new Record[heap.length];

        /*
          System.arraycopy Erklärung:
          heap: Quell-Array
          0: Startindex im Quell-Array
          copy: Ziel-Array
          0: Startindex im Ziel-Array
          heap.length: Anzahl der Elemente, die kopiert werden
         */
        System.arraycopy(heap, 0, copy, 0, heap.length);

        // size ist die aktuelle Heapgröße – wird nach jedem Entfernen kleiner
        int size = heap.length;

        // Wiederhole so oft, wie es Elemente im Heap gibt
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = copy[0];              // Nimm das kleinste Element (Wurzel)
            copy[0] = copy[size - 1];         // Ersetze die Wurzel mit dem letzten Element im Heap
            size--;                           // "Entferne" das letzte Element aus dem Heap
            heapifyDown(copy, size, 0);    // Stelle die Heapeigenschaft wieder her (ab Wurzel)
        }

        // Gib das vollständig sortierte Array zurück
        return sortedArray;
    }



    //endregion merge

    //region Karatsuba Oktal Multiplikation

    //region Karatsuba Oktal Multiplikation (Lafer)

    /**
     * Führt eine Multiplikation zweier Oktalzahlen durch, die als Byte-Arrays
     * repräsentiert sind. Nutzt das Karatsuba-Verfahren zur effizienten
     * Multiplikation großer Zahlen.
     * Beispiel: [1, 3] (Oktal 13 = Dezimal 11) * [2, 1] (Oktal 21 = Dezimal 17)
     * ergibt [2, 7, 3] (Oktal 273 = Dezimal 187)
     */
    @Override
    public byte[] karatsuba(byte[] a, byte[] b) {
        // Eingabevalidierung
        if (!isValidOctal(a) || !isValidOctal(b)) {
            throw new IllegalArgumentException("Nur Oktalziffern (0–7) erlaubt.");
        }

        // Entferne führende Nullen
        a = trimLeadingZeros(a);
        b = trimLeadingZeros(b);

        int n = Math.max(a.length, b.length); // maximale Länge ermitteln

        // Basisfall: beide Arrays sind einstellig
        // Beispiel: [3] * [2] = [6], [5] * [5] = 25 → Oktal 31 → [3, 1]
        if (n == 1) {
            int result = a[0] * b[0];

            // Java führt alle Rechnungen dezimal aus – auch bei Oktalwerten.
            // Daher ist das Ergebnis hier eine Dezimalzahl und muss in Oktal zerlegt werden.
            if (result < 8) {
                return new byte[]{(byte) result};
            }

            // Beispiel: 25 → 25 / 8 = 3, 25 % 8 = 1 → [3, 1]
            return new byte[]{
                    (byte) (result / 8),
                    (byte) (result % 8)
            };
        }

        // Padding auf gleiche Länge
        if (a.length < n) a = padLeft(a, n);
        if (b.length < n) b = padLeft(b, n);

        // Falls n ungerade ist, auf gerade erhöhen
        if (n % 2 != 0) {
            a = padLeft(a, n + 1);
            b = padLeft(b, n + 1);
            n++;
        }

        int half = n / 2;

        // Zerlegung in High/Low
        byte[] aHigh = subArray(a, 0, half);
        byte[] aLow = subArray(a, half, n);
        byte[] bHigh = subArray(b, 0, half);
        byte[] bLow = subArray(b, half, n);

        // Karatsuba-Teilschritte
        byte[] ac = karatsuba(aHigh, bHigh);
        byte[] bd = karatsuba(aLow, bLow);
        byte[] abcd = karatsuba(add(aHigh, aLow), add(bHigh, bLow));
        byte[] adbc = subtract(subtract(abcd, ac), bd);

        // Stellenverschiebung im Oktalsystem
        byte[] part1 = shiftLeft(ac, 2 * (n - half));
        byte[] part2 = shiftLeft(adbc, n - half);

        // Endergebnis zusammensetzen
        return add(add(part1, part2), bd);
    }

    /**
     * Prüft, ob alle Ziffern gültige Oktalwerte (0–7) sind.
     * Beispiel: [1, 5, 0] → gültig, [1, 8] → ungültig
     */
    private static boolean isValidOctal(byte[] arr) {
        for (byte b : arr) {
            if (b < 0 || b > 7) return false;
        }
        return true;
    }

    /**
     * Addiert zwei Oktalzahlen.
     * Beispiel: [1, 6] + [2, 1] = [3, 7]
     */
    private static byte[] add(byte[] a, byte[] b) {
        int maxLen = Math.max(a.length, b.length);
        byte[] result = new byte[maxLen + 1];
        int carry = 0;

        for (int i = 0; i < result.length; i++) {
            int ai = getDigit(a, a.length - 1 - i);
            int bi = getDigit(b, b.length - 1 - i);
            int sum = ai + bi + carry;
            result[result.length - 1 - i] = (byte) (sum % 8);
            carry = sum / 8;
        }

        return trimLeadingZeros(result);
    }

    /**
     * Subtrahiert b von a.
     * Voraussetzung: a ≥ b
     * Beispiel: [5, 2] - [3, 1] = [2, 1]
     */
    private static byte[] subtract(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        int borrow = 0;

        for (int i = 0; i < result.length; i++) {
            int ai = getDigit(a, a.length - 1 - i);
            int bi = getDigit(b, b.length - 1 - i);
            int diff = ai - bi - borrow;

            if (diff < 0) {
                diff += 8;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result[result.length - 1 - i] = (byte) diff;
        }

        return trimLeadingZeros(result);
    }

    /**
     * Fügt führende Nullen hinzu.
     * Beispiel: padLeft([3, 4], 4) → [0, 0, 3, 4]
     */
    private static byte[] padLeft(byte[] arr, int length) {
        byte[] result = new byte[length];
        System.arraycopy(arr, 0, result, length - arr.length, arr.length);
        return result;
    }

    /**
     * Entfernt führende Nullen.
     * Beispiel: [0, 0, 5, 3] → [5, 3]
     */
    private static byte[] trimLeadingZeros(byte[] arr) {
        int i = 0;
        while (i < arr.length - 1 && arr[i] == 0) i++;
        byte[] result = new byte[arr.length - i];
        System.arraycopy(arr, i, result, 0, result.length);
        return result;
    }

    /**
     * Verschiebt Array nach links (Oktalstellen).
     * Beispiel: shiftLeft([2, 3], 2) → [2, 3, 0, 0]
     */
    private static byte[] shiftLeft(byte[] arr, int zeros) {
        byte[] result = new byte[arr.length + zeros];
        System.arraycopy(arr, 0, result, 0, arr.length);
        return result;
    }

    /**
     * Gibt Ziffer an Position index zurück oder 0, wenn ungültig.
     * Beispiel: getDigit([1, 2, 3], -1) → 0
     */
    private static int getDigit(byte[] arr, int index) {
        if (index < 0 || index >= arr.length) return 0;
        return arr[index];
    }

    /**
     * Gibt ein Teilarray zurück.
     * Beispiel: subArray([1, 2, 3, 4], 1, 3) → [2, 3]
     */
    private static byte[] subArray(byte[] arr, int from, int to) {
        byte[] result = new byte[to - from];
        System.arraycopy(arr, from, result, 0, result.length);
        return result;
    }

    //endregion

}
