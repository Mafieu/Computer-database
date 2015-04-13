package com.excilys.malbert.persistence.model;

/**
 * Class above Computer and Company, setting the id to a default -1
 * 
 * @author excilys
 *
 */
public abstract class Entity {
    protected long id = -1;

    /**
     * @return
     */
    public long getId() {
	return id;
    }

    /**
     * Comparison with the id
     * 
     * @param obj
     * @return
     */
    public boolean equals(Entity obj) {
	return this.id == obj.getId();
    }
}
