package service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.impl.VersionDaoImpl;
import data.Constant;

import service.VersionManager;

@Service
public class VersionManagerImpl implements VersionManager {

	private File reposDir;
	
	private Logger logger;
	
	private Git git;
	
	@Autowired
	private VersionDaoImpl versionDao;
	
	public VersionManagerImpl() throws IOException {
		this.logger = Logger.getLogger(VersionManagerImpl.class);
	}
	
	@Override
	public String getDifference(String author, String fileName) {
		OutputStream out = new ByteArrayOutputStream();
		List<DiffEntry> diff = null;
		try {
			diff = git.diff().setOutputStream(out).setPathFilter(PathFilter.create(fileName)).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		if ( diff.size() != 0 ) {
			logger.debug("Get diff string : " 
					+ " diffEntryNum = " + diff.size() 
					+ " changeType = " + diff.get(0).getChangeType() 
					+ " oldPath = " + diff.get(0).getOldPath() 
					+ " newPath = " + diff.get(0).getNewPath());
		}
		String diffStr = out.toString();
		return diffStr;
	}

	@Override
	public void newGitRepos(String author) throws GitAPIException, IOException, JGitInternalException {
		this.reposDir = new File(Constant.WIX_FILE_MANAGEMENT_DIR.getValue() + "/" + author + "/");
		boolean isSuccessful = this.reposDir.mkdir();
		if ( isSuccessful ) {
			Git.init().setDirectory(this.reposDir).call();
			logger.debug("Git-Init : "
					+ "directoryPath = " + this.reposDir.getAbsolutePath());
		}
		this.git = Git.open(this.reposDir);
	}

	@Override
	public void add() throws NoFilepatternException, GitAPIException {
		AddCommand add = this.git.add();
		add.addFilepattern(".").call();
	}

	@Override
	public String commit(String message) throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, GitAPIException {
		CommitCommand commit = this.git.commit();
		commit.setMessage(message).call();
		Iterable<RevCommit> log = null;
		
		log = this.git.log().call();
		
		Iterator<RevCommit> iterator = log.iterator();
		RevCommit rev = iterator.next();
		logger.debug("Commit info : "
				+ " message = " + message 
				+ " hash = " + rev.getId()
				+ " sha = " + rev.getName());
		return rev.getName();
	}

	public void createBranch(String branchName) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, GitAPIException {
		Ref ref = this.git.checkout().setCreateBranch(true).setName(branchName).call();
		logger.debug("Brach-Created : "
				+ " ref = " + ref
				+ " name = " + ref.getName()
				+ " objectId = " + ref.getObjectId().getName());
	}
	
	public void rebase(String branchName) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException {
		this.git.checkout().setCreateBranch(false).setName(Constant.MASTER_BRANCH.getValue()).call();
		this.git.rebase().setUpstream(branchName).call();
		List<String> list = this.git.branchDelete().setBranchNames(branchName).call();
		for ( int i = 0; i < list.size(); i++ ) {
			logger.debug("delete branch list info : " + list.get(i));
		}
	}
	
	public void deleteBranch(String branchName) throws NotMergedException, CannotDeleteCurrentBranchException, GitAPIException {
		this.git.checkout().setName(Constant.MASTER_BRANCH.getValue()).setCreateBranch(false).call();
		//mergeせずにbranchDeleteするのでsetForce(true)
		List<String> list = this.git.branchDelete().setForce(true).setBranchNames(branchName).call();
		for ( int i = 0; i < list.size(); i++ ) {
			logger.debug("delete branch list info : " + list.get(i));
		}
	}
	
	public void revert(String branchName) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		// TODO : hash値でcheckout
		//int oldHash = versionDao.findOldHash(version);
		//this.git.checkout().setCreateBranch(false).setName(branchName).call();
	}
	
}
