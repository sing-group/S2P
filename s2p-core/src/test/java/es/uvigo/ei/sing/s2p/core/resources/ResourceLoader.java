package es.uvigo.ei.sing.s2p.core.resources;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singleton;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Set;

/**
 * Helper class to use files and directories from the project classpath as
 * regular files and directories. This class extracts or copies the files
 * and/or directories needed to the temporal directory of the system to allow
 * using then as regular files.
 * 
 * @author Miguel Reboiro-Jato
 *
 */
public final class ResourceLoader {
	private ResourceLoader() {}
	
	public final static File loadResource(String path) {
		try {
			final URI container = ResourceLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			if (container.toString().endsWith("jar")) {
				final URI containerJar = URI.create("jar:" + container);
				try (final FileSystem jarSystem = FileSystems.newFileSystem(containerJar, emptyMap())) {
					final Path filePath = jarSystem.getPath(path.substring(1));
					
					final Path tmpFile;
					if (Files.isDirectory(filePath)) {
						tmpFile = Files.createTempDirectory("laimages");
						ResourceLoader.copyDirectory(filePath, tmpFile);
					} else {
						tmpFile = Files.createTempFile("laimages", "test");
						Files.copy(filePath, tmpFile, StandardCopyOption.REPLACE_EXISTING);
					}
					
					return tmpFile.toFile();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				final URI uri = TestResources.class.getClassLoader().getResource(path.substring(1)).toURI();
				
				return new File(uri.getPath());
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static void copyDirectory(Path source, Path target)
	throws IOException {
		if (!Files.isDirectory(target))
			throw new IllegalArgumentException(target + " must be a directory");

		final String sourceFileName = source.getFileName().toString();
		
		final Path dest = target.resolve(sourceFileName);

		final Set<FileVisitOption> options = singleton(FileVisitOption.FOLLOW_LINKS);
		final DirectoryTreeCopier copier = new DirectoryTreeCopier(source, dest);
		
		Files.walkFileTree(source, options, Integer.MAX_VALUE, copier);
	}
	
	private static class DirectoryTreeCopier implements FileVisitor<Path> {
		private final Path source;
		private final Path target;

		public DirectoryTreeCopier(Path source, Path target) {
			this.source = source;
			this.target = target;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
			try {
				final String relativeSourcePath = source.relativize(dir).toString();
				
				Files.copy(dir, target.resolve(relativeSourcePath));
			} catch (FileAlreadyExistsException x) {
				x.printStackTrace();
			} catch (IOException x) {
				x.printStackTrace();
				
				return SKIP_SUBTREE;
			}
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			final FileSystem sourceFileSystem = source.getFileSystem();
			final String fileName = file.getFileName().toString();
			final Path sourcePath = sourceFileSystem.getPath(fileName);
			
			final String relativeSourcePath = source.relativize(sourcePath).toString();
			
			copyFile(file, target.resolve(relativeSourcePath));
			
			return CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
			if (exc == null) {
				final String relativeSourcePath = source.relativize(dir).toString();
				
				final Path newDirectory = target.resolve(relativeSourcePath);
				try {
					final FileTime time = Files.getLastModifiedTime(dir);
					
					Files.setLastModifiedTime(newDirectory, time);
				} catch (IOException x) {
					x.printStackTrace();
				}
			}
			
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			exc.printStackTrace();
			
			return CONTINUE;
		}
	}

	private static void copyFile(Path source, Path target) {
		if (Files.notExists(target)) {
			try {
				Files.copy(source, target, new CopyOption[] { REPLACE_EXISTING });
			} catch (IOException x) {
				x.printStackTrace();
			}
		}
	}
}
