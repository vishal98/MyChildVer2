package test

import java.io.Serializable;

import org.grails.datastore.mapping.core.SessionImplementor
import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator

class Author {
Long id
	static hasMany = [books:Book,notes:Notes]
	String name
    static constraints = {
    }
	
}
