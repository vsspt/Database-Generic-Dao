package com.github.vsspt.common.db.impl.hibernate.tests;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.github.vsspt.common.db.dao.api.IGenericDAO;
import com.github.vsspt.common.db.dao.api.search.GenericSearch;
import com.github.vsspt.common.db.dao.api.util.PagingList;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;

@ContextConfiguration(locations = {"classpath:daoServicesContext.xml"})
@Test
public class GenericDaoObjTest extends AbstractTestNGSpringContextTests {

  private static final Logger LOG = LoggerFactory.getLogger(GenericDaoObjTest.class);

  @Resource(name = "idDAO")
  private IGenericDAO<GenericDaoObj, Long> dao;

  public void testAnd() throws ParseException {

    final long expectedResult = 1L;
    final long id = 1L;
    final Date aDate = DateUtils.parseDate("2012-10-10", "yyyy-MM-dd");
    final Search search = new Search();

    search.addFilterAnd(Filter.equal("ID", id), Filter.equal("aDate", aDate));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testAnd - Found [{}] of [{}] records with [ID = {} AND aDate = {}], values [{}].", new Object[] {nbrRecords, total, id, aDate, page.getList()});

    assert nbrRecords == expectedResult;
  }

  public void testBetween() throws ParseException {

    final Date beginDate = DateUtils.parseDate("2012-10-10", "yyyy-MM-dd");
    final Date endDate = DateUtils.parseDate("2012-10-12", "yyyy-MM-dd");
    final int expectedResult = 4;
    final Search search = new Search();

    search.addFilterAnd(Filter.greaterOrEqual("aDate", beginDate), Filter.lessThan("aDate", endDate));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testBetween - Found [{}] of [{}] records with dates [{} <= DATE < {}], values [{}].", new Object[] {nbrRecords, total, beginDate, endDate, page.getList()});

    assert page.getTotalRecords() == expectedResult;
  }

  @Test
  public void testDisjunction() {
    final Long id_1 = 1L;
    final Long id_2 = 2L;
    final Long id_5 = 5L;
    final int expectedResult = 3;
    final Search search = new Search();
    search.setDisjunction(Boolean.TRUE);

    search.addFilter(Filter.equal("ID", id_1));
    search.addFilter(Filter.equal("ID", id_2));
    search.addFilter(Filter.equal("ID", id_5));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testDisjunction - Found [{}] of [{}] records with with ID = [{} OR {} OR {}], values [{}].", new Object[] {nbrRecords, total, id_1, id_2, id_5, page.getList()});

    assert page.getTotalRecords() == expectedResult;
  }

  @Test
  public void testFirstRecords() {
    final int first = 0;
    final int nrRecords = 5;
    final Search search = new Search();
    search.setFirstResult(first);

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testFirstRecords - Found [{}] of [{}] records, First [{}], values [{}].", new Object[] {nbrRecords, total, first, page.getList()});

    assert nbrRecords == nrRecords;
  }

  @Test
  public void testMaxRecords() {
    final int max = 3;
    final int expectedResult = max;
    final Search search = new Search();
    search.addSort(Sort.desc("ID"));
    search.setMaxResults(max);

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testMaxRecords - Found [{}] of [{}] records, Max Records [{}], values [{}].", new Object[] {nbrRecords, total, max, page.getList()});

    assert nbrRecords == expectedResult;
  }

  public void testOr() throws ParseException {

    final long expectedResult = 2;
    final long id = 1L;
    final long id2 = 2L;
    final Search search = new Search();
    search.addFilterOr(Filter.equal("ID", id), Filter.equal("ID", id2));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testOr - Found [{}] of [{}] records with ID = [{} OR {}], values [{}].", new Object[] {nbrRecords, total, id, id2, page.getList()});

    assert nbrRecords == expectedResult;
  }

  @Test
  public void testPaging() {
    final int nrRecords = 5;
    final int max = 2;
    int lastID = -1;

    final Search search_1 = new Search();
    search_1.addSort(Sort.asc("aInt"));
    search_1.setMaxResults(max);
    LOG.debug("testPaging() - begin");
    final PagingList<GenericDaoObj> page_1 = dao.get(getGenericSearch(search_1));
    final int page_1_nbr = page_1.getList().size();
    LOG.debug("Records [1 to {} of {}], values [{}].", new Object[] {page_1_nbr, page_1.getTotalRecords(), page_1.getList()});

    lastID = page_1.getList().get(1).getaInt();
    final Search search_2 = new Search();
    search_2.addSort(Sort.asc("ID"));
    search_2.setMaxResults(max);
    search_2.setFirstResult(lastID);
    final PagingList<GenericDaoObj> page_2 = dao.get(getGenericSearch(search_2));
    final int page_2_nbr = page_2.getList().size();
    LOG.debug("Records [{} to {} of {}], values [{}].", new Object[] {lastID + 1, lastID + page_2_nbr, page_2.getTotalRecords(), page_2.getList()});

    lastID = page_2.getList().get(1).getaInt();
    final Search search_3 = new Search();
    search_3.addSort(Sort.asc("ID"));
    search_3.setMaxResults(max);
    search_3.setFirstResult(lastID);
    final PagingList<GenericDaoObj> page_3 = dao.get(getGenericSearch(search_3));
    final int page_3_nbr = page_3.getList().size();
    LOG.debug("Records [{} to {} of {}], values [{}].", new Object[] {lastID + 1, lastID + page_3_nbr, page_3.getTotalRecords(), page_3.getList()});
    LOG.debug("testPaging() - end");

    assert page_1_nbr + page_2_nbr + page_3_nbr == nrRecords;
  }

  @Test
  public void testSearchGe() {
    final Long ge = 2L;
    final int expectedResult = 4;
    final Search search = new Search();
    search.addFilterGreaterOrEqual("ID", ge);

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testSearchGe - Found [{}] of [{}] records with ID >= [{}], values [{}].", new Object[] {nbrRecords, total, ge, page.getList()});

    assert nbrRecords == expectedResult;
  }

  @Test
  public void testSearchGt() {
    final Long gt = 2L;
    final int expectedResult = 3;
    final Search search = new Search();
    search.addFilterGreaterThan("ID", gt);
    search.addSort(Sort.desc("ID"));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testSearchGt - Found [{}] of [{}] records with ID > [{}], values [{}].", new Object[] {nbrRecords, total, gt, page.getList()});

    assert nbrRecords == expectedResult;
  }

  @Test
  public void testSearchIDEqual() {
    final long id = 3;
    final long expectedResult = id;

    final Search search = new Search();
    search.addSort(Sort.asc("ID"));
    search.addFilter(Filter.equal("ID", id));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testSearchIDEqual - Found [{}] of [{}] records with ID = [{}], values [{}].", new Object[] {nbrRecords, total, id, page.getList()});

    final GenericDaoObj obj = page.getList().get(0);

    assert obj.getID() == expectedResult;
  }

  @Test
  public void testSearchLe() {
    final Long le = 3L;
    final int expectedResult = 3;
    final Search search = new Search();
    search.addFilterLessOrEqual("ID", le);

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testSearchLe - Found [{}] of [{}] records with with ID <= [{}], values [{}].", new Object[] {nbrRecords, total, le, page.getList()});

    assert page.getTotalRecords() == expectedResult;
  }

  @Test
  public void testSearchLt() {
    final Long lt = 3L;
    final int expectedResult = 2;
    final Search search = new Search();
    search.addFilterLessThan("ID", lt);
    search.addSort(Sort.desc("ID"));

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testSearchLt - Found [{}] of [{}] records with ID < [{}], values [{}].", new Object[] {nbrRecords, total, lt, page.getList()});

    assert page.getTotalRecords() == expectedResult;
  }


  @Test
  public void testLike() {
    final String like_value = "Registo";
    final int expectedResult = 5;
    final String property = "aString";
    final Search search = new Search();
    search.addFilterLike(property, like_value);

    final PagingList<GenericDaoObj> page = dao.get(getGenericSearch(search));

    final int total = page.getTotalRecords();
    final int nbrRecords = page.getList().size();

    LOG.debug("testLike - Found [{}] of [{}] records [{} LIKE '{}%'], values [{}].", new Object[] {nbrRecords, total, property, like_value, page.getList()});

    assert page.getTotalRecords() == expectedResult;
  }

  private GenericSearch getGenericSearch(final Search search) {
    return new GenericSearch(search);
  }
}
