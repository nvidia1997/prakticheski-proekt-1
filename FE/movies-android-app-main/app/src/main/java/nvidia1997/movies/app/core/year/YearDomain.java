package nvidia1997.movies.app.core.year;

import nvidia1997.movies.app.core.Domain;

public class YearDomain extends Domain<Integer> {
    private int id;

    private int value;

    public YearDomain() {
        super();
    }

    public YearDomain(int value) {
        super();
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

