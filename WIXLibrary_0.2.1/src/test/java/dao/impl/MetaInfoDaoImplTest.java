package dao.impl;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import data.MetaInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/beans-dao.xml")
public class MetaInfoDaoImplTest {
	@Autowired
	private MetaInfoDaoImpl metaInfoDao;
	
	@Test
	public void insertTest() {
		assertThat(metaInfoDao, is(notNullValue()));
		MetaInfo metaInfo = new MetaInfo();
		metaInfo.setName("test");
		metaInfo.setAuthor("testさん");
		metaInfo.setComment("test desu");
		metaInfo.setDescription("test desu");
		metaInfo.setLanguage("ja");
		metaInfoDao.insert(metaInfo);
		//TODO : getWidの検証
	}
	
}
