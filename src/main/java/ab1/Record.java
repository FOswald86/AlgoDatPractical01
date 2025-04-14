package ab1;

/**
 * Eine einfache Datenstruktur mit zwei Feldern:
 * - key: der Primärschlüssel, der zum Sortieren verwendet wird
 * - subkey: der Sekundärschlüssel, der verwendet wird, wenn die
 *           Primärschlüsselwerte gleich sind
 */
public class Record {
    public final int key;
    public final int subkey;

    /**
     * Konstruktor zum Initialisieren beider Felder.
     *
     * @param key   Der Primärschlüssel
     * @param subkey Der Sekundärschlüssel
     */
    public Record(int key, int subkey) {
	this.key = key;
	this.subkey = subkey;
    }

    @Override
    public String toString() {
	return "(" + key + ", " + subkey + ")";
    }
}


