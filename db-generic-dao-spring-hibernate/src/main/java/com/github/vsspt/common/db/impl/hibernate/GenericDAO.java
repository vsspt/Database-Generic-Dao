package com.github.vsspt.common.db.impl.hibernate;

import static org.hibernate.criterion.Example.create;
import static com.github.vsspt.common.db.dao.api.constants.Message.DELETE_INSTANCE;
import static com.github.vsspt.common.db.dao.api.constants.Message.DELETE_INSTANCE_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_ALL;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_ALL_PAGED_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_ALL_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_BY_EXAMPLE;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_BY_EXAMPLE_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_BY_ID;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_BY_ID_SUCCESS_FOUND;
import static com.github.vsspt.common.db.dao.api.constants.Message.FIND_BY_ID_SUCCESS_NOT_FOUND;
import static com.github.vsspt.common.db.dao.api.constants.Message.MERGE_INSTANCE;
import static com.github.vsspt.common.db.dao.api.constants.Message.MERGE_INSTANCE_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.NULL_OBJECT;
import static com.github.vsspt.common.db.dao.api.constants.Message.SAVE_INSTANCE;
import static com.github.vsspt.common.db.dao.api.constants.Message.SAVE_INSTANCE_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.SAVE_OR_UPDATE_INSTANCE;
import static com.github.vsspt.common.db.dao.api.constants.Message.SAVE_OR_UPDATE_INSTANCE_SUCCESS;
import static com.github.vsspt.common.db.dao.api.constants.Message.UPDATE_INSTANCE;
import static com.github.vsspt.common.db.dao.api.constants.Message.UPDATE_INSTANCE_SUCCESS;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.vsspt.common.db.dao.api.IGenericDAO;
import com.github.vsspt.common.db.dao.api.search.GenericSearch;
import com.github.vsspt.common.db.dao.api.util.PagingList;
import com.github.vsspt.common.db.dao.api.util.Parameter;
import com.github.vsspt.common.db.impl.hibernate.util.SearchProcessor;

@Repository
public class GenericDAO<T extends Serializable, PK extends Serializable> implements IGenericDAO<T, PK> {
  private static final Logger LOG = LoggerFactory.getLogger(GenericDAO.class);
  private static final int ZERO = 0;
  private final Class<T> classType;
  private final String className;


  @Autowired
  private SessionFactory sessionFactory;

  public GenericDAO(final Class<T> classType) {
    this.classType = classType;
    this.className = classType.getSimpleName();
  }

  private boolean checkObject(final Object object) {
    return object != null;
  }

  private Criteria createCriteria() {
    final Criteria criteria = getSession().createCriteria(classType);
    return criteria;
  }

  @Override
  public void delete(final PK id) {
    LOG.debug(DELETE_INSTANCE.getMsg(), className);

    @SuppressWarnings("unchecked")
    final PK instance = (PK) getSession().get(classType, id);

    if (!checkObject(instance)) {
      throw new IllegalArgumentException(NULL_OBJECT.getMsg());
    }

    getSession().delete(instance);

    LOG.debug(DELETE_INSTANCE_SUCCESS.getMsg(), className, id);

  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAll() {
    LOG.debug(FIND_ALL.getMsg(), className);

    final List<T> results = createCriteria().list();
    LOG.debug(FIND_ALL_SUCCESS.getMsg(), className, results.size());

    return results;

  }

  @Override
  @SuppressWarnings("unchecked")
  public List<T> findByExample(final T instance) {
    LOG.debug(FIND_BY_EXAMPLE.getMsg(), className);

    final List<T> results = createCriteria().add(create(instance)).list();

    LOG.debug(FIND_BY_EXAMPLE_SUCCESS.getMsg(), className, results.size());

    return results;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T findById(final PK id) {

    if (!checkObject(id)) {
      throw new IllegalArgumentException(NULL_OBJECT.getMsg());
    }

    LOG.debug(FIND_BY_ID.getMsg(), className, id);

    final T instance = (T) getSession().get(classType, id);

    LOG.debug(instance == null ? FIND_BY_ID_SUCCESS_NOT_FOUND.getMsg() : FIND_BY_ID_SUCCESS_FOUND.getMsg(), className, id);

    return instance;
  }

  @SuppressWarnings("unchecked")
  @Override
  public PagingList<T> get(final GenericSearch search) {

    LOG.debug(FIND.getMsg(), className);

    final SearchProcessor processor = new SearchProcessor();

    final Criteria criteria = processor.process(getSession(), createCriteria(), search);
    final List<T> results = criteria.list();

    final Criteria criteriaCount = processor.processProjection(getSession(), createCriteria(), search);
    final Number total = (Number) criteriaCount.uniqueResult();

    LOG.debug(FIND_ALL_PAGED_SUCCESS.getMsg(), new Object[] {className, results.size(), total});

    return new PagingList<T>(results, getTotal(total));
  }

  @Override
  public String getClassName() {
    return className;
  }

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public void update(final T detachedInstance) {
    LOG.debug(UPDATE_INSTANCE.getMsg(), className);

    getSession().update(detachedInstance);

    LOG.debug(UPDATE_INSTANCE_SUCCESS.getMsg());
  }

  @Override
  public void merge(final T detachedInstance) {
    LOG.debug(MERGE_INSTANCE.getMsg(), className);

    getSession().merge(detachedInstance);

    LOG.debug(MERGE_INSTANCE_SUCCESS.getMsg());
  }


  @Override
  public void saveOrUpdate(final T detachedInstance) {
    LOG.debug(SAVE_OR_UPDATE_INSTANCE.getMsg(), className);

    getSession().saveOrUpdate(detachedInstance);

    LOG.debug(SAVE_OR_UPDATE_INSTANCE_SUCCESS.getMsg());
  }

  @SuppressWarnings("unchecked")
  @Override
  public PK save(final T transientInstance) {

    if (!checkObject(transientInstance)) {
      throw new IllegalArgumentException(NULL_OBJECT.getMsg());
    }

    LOG.debug(SAVE_INSTANCE.getMsg(), className);

    final PK id = (PK) getSession().save(transientInstance);

    LOG.debug(SAVE_INSTANCE_SUCCESS.getMsg(), className, id);

    return id;

  }

  @Override
  public List<?> executeQuery(final String queryId, final Parameter... params) {
    LOG.debug("Executing on [{}], Query with ID [{}].", className, queryId);

    final Query query = getSession().getNamedQuery(queryId);
    final List<String> parameters = Arrays.asList(query.getNamedParameters());
    for (final Parameter param : params) {
      if (parameters.contains(param.getKey())) {
        query.setParameter(param.getKey(), param.getValue());
      }
    }

    final List<?> results = query.list();
    LOG.debug("Query with ID [{}] returned [{}] results.", queryId, results.size());

    return results;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<T> execute(final String queryId, final Parameter... params) {
    LOG.debug("Executing on [{}], Query with ID [{}].", className, queryId);

    final Query query = getSession().getNamedQuery(queryId);
    final List<String> parameters = Arrays.asList(query.getNamedParameters());
    for (final Parameter param : params) {
      if (parameters.contains(param.getKey())) {
        query.setParameter(param.getKey(), param.getValue());
      }
    }

    final List<T> results = query.list();
    LOG.debug("Query with ID [{}] returned [{}] results.", queryId, results.size());

    return results;
  }

  private Integer getTotal(final Number total) {
    return total == null ? ZERO : total.intValue();
  }

}
