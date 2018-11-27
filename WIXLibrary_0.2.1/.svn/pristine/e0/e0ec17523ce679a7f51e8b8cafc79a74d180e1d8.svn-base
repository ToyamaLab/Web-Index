package dao.impl;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import data.Entry;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/beans-dao.xml")
public class EntryDaoImplTest {
	@Autowired
	private EntryDaoImpl entryDao;
	
	@Test
	public void insertTest() {
		assertThat(entryDao, is(notNullValue()));
		Entry entry = new Entry();
		entry.setWid(1);
		entry.setKeyword("test");
		entry.setTarget("http://test/test");
		entryDao.insert(entry);
	}
	
}
