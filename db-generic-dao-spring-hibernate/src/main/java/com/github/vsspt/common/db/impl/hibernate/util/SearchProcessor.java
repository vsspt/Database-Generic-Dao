package com.github.vsspt.common.db.impl.hibernate.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.vsspt.common.db.dao.api.search.GenericAlias;
import com.github.vsspt.common.db.dao.api.search.GenericFilterDef;
import com.github.vsspt.common.db.dao.api.search.GenericFilterParam;
import com.github.vsspt.common.db.dao.api.search.GenericSearch;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Sort;

public class SearchProcessor {
  private static final int ZERO = 0;
  private static final Logger LOG = LoggerFactory.getLogger(SearchProcessor.class);

  private void addCriterions(final Criteria criteria, final List<Criterion> criterions) {
    for (final Criterion criterion : criterions) {
      criteria.add(criterion);
    }
  }

  private void addCriterions(final Disjunction disjunction, final List<Criterion> criterions) {
    for (final Criterion criterion : criterions) {
      disjunction.add(criterion);
    }
  }

  private void addFilter(final Criteria criteria, final ISearch search) {

    final List<Filter> filters = search.getFilters();

    final FilterToCriteria helper = new FilterToCriteria(filters);
    final List<Criterion> criterions = helper.getCriterions();

    if (search.isDisjunction()) {
      final Disjunction disjunction = Restrictions.disjunction();
      addCriterions(disjunction, criterions);
      criteria.add(disjunction);
    } else {
      addCriterions(criteria, criterions);
    }
  }

  private void addPaging(final Criteria criteria, final ISearch search) {

    final int firstResult = search.getFirstResult();
    if (firstResult >= ZERO) {
      criteria.setFirstResult(firstResult);
    }

    final int maxResult = search.getMaxResults();
    if (maxResult > ZERO) {
      criteria.setMaxResults(maxResult);
    }
  }

  private void addProjection(final Criteria criteria) {
    criteria.setProjection(Projections.rowCount()).uniqueResult();
  }

  private void addSort(final Criteria criteria, final ISearch search) {

    final List<Sort> sorts = search.getSorts();

    for (final Sort sort : sorts) {
      criteria.addOrder(getOrder(sort));
    }
  }

  private Order getOrder(final Sort sort) {
    if (sort.isDesc()) {
      return Order.desc(sort.getProperty());
    } else {
      return Order.asc(sort.getProperty());
    }
  }

  public Criteria processProjection(final Session session, final Criteria criteria, final GenericSearch search) {

    setReadOnly(criteria);
    addProjection(criteria);

    if (search != null) {
      final ISearch iSearch = search.getSearch();

      enableFilter(session, search);
      addAliases(criteria, search);
      if (iSearch != null) {
        addFilter(criteria, search.getSearch());
      }
      addDistinctRootEntity(criteria, search);
    }

    return criteria;
  }

  private void setReadOnly(final Criteria criteria) {
    criteria.setReadOnly(Boolean.TRUE);
  }

  private void addAliases(final Criteria criteria, final GenericSearch search) {

    if (search.hasAliases()) {
      final List<GenericAlias> aliases = search.getDistinctAliases();
      for (final GenericAlias alias : aliases) {
        criteria.createAlias(alias.getAliasKey(), alias.getAliasValue(), JoinType.LEFT_OUTER_JOIN);
        LOG.debug("Adding {}.", alias.toString());
      }
    }
  }

  private void addDistinctRootEntity(final Criteria criteria, final GenericSearch search) {

    if (search.hasAliases()) {
      criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    }
  }

  public Criteria process(final Session session, final Criteria criteria, final GenericSearch search) {

    setReadOnly(criteria);

    if (search != null) {

      enableFilter(session, search);
      addAliases(criteria, search);

      final ISearch iSearch = search.getSearch();

      if (iSearch != null) {
        addPaging(criteria, iSearch);
        addSort(criteria, iSearch);
        addFilter(criteria, iSearch);
      }

      addDistinctRootEntity(criteria, search);
    }

    return criteria;
  }

  private void enableFilter(final Session session, final GenericSearch search) {
    if (search.hasFilters()) {

      try {
        final List<GenericFilterDef> filters = search.getFiltersDef();
        for (final GenericFilterDef filter : filters) {

          final String filterName = filter.getName();

          final org.hibernate.Filter hibernateFilter = session.enableFilter(filterName);

          LOG.debug("Filter [{}] enabled.", filterName);

          if (filter.hasParameters()) {
            final List<GenericFilterParam> params = filter.getParameters();

            for (final GenericFilterParam param : params) {
              hibernateFilter.setParameter(param.getName(), param.getValue());

              LOG.debug("Parameter {} enabled.", param.getName());
            }
          }
        }
      } catch (final Exception ex) {
        LOG.error("Error on enableFilter", ex);
      }
    }

  }
}
