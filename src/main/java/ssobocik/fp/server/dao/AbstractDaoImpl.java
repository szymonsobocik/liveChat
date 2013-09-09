package ssobocik.fp.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Base class for dao layer
 *
 * @author szymon.sobocik
 */
public class AbstractDaoImpl {


    @PersistenceContext
    protected EntityManager em;

    /**
     * Helper method replacing em.getSingleResult() because it throws silent exceptions
     */
    public <T> T getSingleResult(List<T> resultList) {
        if (!resultList.isEmpty()){
            return resultList.get(0);
        }
        return null;
    }

    public void detach(Object o){
        em.detach(o);
    }
}

