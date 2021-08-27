package git;

import cn.hutool.core.io.FileUtil;
import io.jpom.model.enums.GitProtocolEnum;
import io.jpom.model.data.RepositoryModel;
import io.jpom.util.GitUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bwcx_jzy
 * @date 2019/7/9
 **/
public class TestGit {

	@Test
	public void test() {

		//System.out.println(Files.exists(FileUtil.file("D:\\jpom\\server\\data\\temp\\gitTemp\\6323b009deeca9367a1e393acd9f26b8\\objects").toPath(), LinkOption.NOFOLLOW_LINKS));
	}

	@Test
	public void test2() throws IOException {
		String fileOrDirPath = "D:\\jpom\\server\\data\\temp\\";
		List<File> strings = FileUtil.loopFiles(fileOrDirPath);
//		for (File file : strings) {
//			FilePermission filePermission = new FilePermission(file.getAbsolutePath(), "write");
//			System.out.println(filePermission.implies(filePermission));
//		}
		System.out.println(strings.size());
		for (File string : strings) {
			string.setWritable(true);
			try {
				Path path = Paths.get(string.getAbsolutePath());
				Files.delete(path);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		FileUtil.clean(fileOrDirPath);
		Path path = Paths.get(fileOrDirPath);
		Files.delete(path);
//		FilePermission filePermission = new FilePermission(fileOrDirPath, "write");
//		System.out.println(filePermission.implies(filePermission));
//		FileUtil.clean(fileOrDirPath);
//		FileUtil.del(fileOrDirPath);

	}


	public static void main(String[] args) throws GitAPIException, IOException, URISyntaxException {

		Git git = Git.init().setDirectory(new File("D:\\tttt")).call();
		RemoteConfig remoteConfig = Git.open(new File("D:\\tttt")).remoteAdd().setUri(new URIish("https://gitee.com/keepbx/Jpom.git")).call();
		System.out.println(remoteConfig);

		List<RemoteConfig> call = git.remoteList().call();
		System.out.println(call);
		git.pull().call();
		List<Ref> list = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();
		List<String> all = new ArrayList<>(list.size());
		list.forEach(ref -> {
			String name = ref.getName();
			if (name.startsWith(Constants.R_REMOTES + Constants.DEFAULT_REMOTE_NAME)) {
				all.add(name.substring((Constants.R_REMOTES + Constants.DEFAULT_REMOTE_NAME).length() + 1));
			}
		});
		System.out.println(all);
	}


	@Test
	public void testTag() throws Exception {


		//
//		Git.cloneRepository().setTagOption(TagOpt.FETCH_TAGS)

		String uri = "https://gitee.com/dromara/Jpom.git";
		File file = FileUtil.file("~/test/jpomgit");
		String tagName = "v2.5.2";
		String branchName = "stand-alone";

//		Git call = Git.cloneRepository()
//				.setURI(uri)
//				.setDirectory(file)
//				.setCredentialsProvider(credentialsProvider)
//				.call();

		PrintWriter printWriter = new PrintWriter(System.out);
		RepositoryModel repositoryModel = new RepositoryModel();
		repositoryModel.setGitUrl(uri);
		repositoryModel.setRepoType(0);
		repositoryModel.setUserName("a");
		repositoryModel.setPassword("a");
		repositoryModel.setProtocol(GitProtocolEnum.HTTP.getCode());


		GitUtil.checkoutPullTag(repositoryModel, file, branchName, tagName, printWriter);

		//GitUtil.checkoutPull(uri, file, branchName, credentialsProvider, printWriter);


	}


	@Test
	public void testTag2() throws Exception {
		String uri = "https://gitee.com/keepbx/Jpom-demo-case.git";
		File file = FileUtil.file("~/test/jpomgit2");
		String tagName = "1.2";
		String branchName = "master";
		UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("a", "a");

		PrintWriter printWriter = new PrintWriter(System.out);

		RepositoryModel repositoryModel = new RepositoryModel();
		repositoryModel.setGitUrl(uri);
		repositoryModel.setRepoType(0);
		repositoryModel.setUserName("a");
		repositoryModel.setPassword("a");
		String msg = GitUtil.checkoutPullTag(repositoryModel, file, branchName, tagName, printWriter);
		System.out.println(msg);
		//GitUtil.checkoutPull(uri, file, branchName, credentialsProvider, printWriter);


	}

}
