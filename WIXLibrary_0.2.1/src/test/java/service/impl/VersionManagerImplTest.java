package service.impl;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import data.Version;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/beans-service.xml", "classpath:/META-INF/spring/beans-dao.xml"})
public class VersionManagerImplTest {
	
	@Autowired
	private VersionManagerImpl versionManager;
	
	@Test
	public void initTest() {
		assertThat(versionManager, is(notNullValue()));
		Version version = new Version();		
		try {
			versionManager.newGitRepos("junit");
		} catch (GitAPIException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		try {
			copyTransfer("src/main/resources/test.wix", "/tmp/junit/test.wix");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			versionManager.add();
		} catch (NoFilepatternException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		try {
			version.setRevisionNum(versionManager.commit("commit from junit test."));
		} catch (NoHeadException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (NoMessageException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (UnmergedPathsException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	/*
	@Test
	public void diffTest() {
		String diffStr = versionManager.getDifference("junit");
		System.out.println(diffStr);
	}
	*/
	
	private void copyTransfer(String srcPath, String destPath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(srcPath);
		FileChannel srcChannel = fileInputStream.getChannel();
		FileOutputStream fileOutputStream = new FileOutputStream(destPath);
		FileChannel destChannel = fileOutputStream.getChannel();
		try {
			srcChannel.transferTo(0, srcChannel.size(), destChannel);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fileInputStream.close();
			fileOutputStream.close();
		}
	}
}
