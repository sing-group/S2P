package es.uvigo.ei.sing.s2p.core.matcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class HasEqualFileContentMatcher extends TypeSafeDiagnosingMatcher<File> {
	private File expectedFile;

	public HasEqualFileContentMatcher(File expectedFile) {
		this.expectedFile = expectedFile;
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("File: ").appendText(this.expectedFile.getAbsolutePath());
	}
	
	@Override
	protected boolean matchesSafely(File item, Description mismatchDescription) {
		final byte[] expectedContent, actualContent;
		
		try {
			expectedContent = Files.readAllBytes(this.expectedFile.toPath());
		} catch (IOException e) {
			mismatchDescription.appendText("Exception thrown while reading file: ")
				.appendText(this.expectedFile.getAbsolutePath());
			
			return false;
		}
		try {
			actualContent = Files.readAllBytes(item.toPath());
		} catch (IOException e) {
			mismatchDescription.appendText("Exception thrown while reading file: ")
				.appendText(item.getAbsolutePath());
			
			return false;
		}
			
		if (Arrays.equals(expectedContent, actualContent)) {
			return true;
		} else {
			mismatchDescription
				.appendText(item.getAbsolutePath())
				.appendText(" does not have the same content");
			
			return false;
		}
	}

	@Factory
	public static HasEqualFileContentMatcher hasEqualFileContent(File expectedFile) {
		return new HasEqualFileContentMatcher(expectedFile);
	}
}
