package nvidia1997.movies.app.core.genre;

import nvidia1997.movies.app.core.Domain;

public class GenreDomain extends Domain<Integer> {
    int id;

    int originalId;
    String name;

    public GenreDomain() {
        super();
    }

    public GenreDomain(String name) {
        super();
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
