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
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj instanceof Entity) {
	    return this.id == ((Entity) obj).getId();
	} else {
	    return false;
	}
    }
}
