package tot.domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class DelegatingMultipartFile implements MultipartFile {

	private final Resource resource;

	public DelegatingMultipartFile(Resource resource) {
		this.resource = resource;
	}

	@Override
	public String getName() {
		return resource.getFilename();
	}

	@Override
	public String getOriginalFilename() {
		return resource.getFilename();
	}

	@Override
	public String getContentType() {
		try {
			return resource.getURL().openConnection().getContentType();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			return resource.contentLength() == 0;
		} catch (IOException e) {
			return true;
		}
	}

	@Override
	public long getSize() {
		try {
			return resource.contentLength();
		} catch (IOException e) {
			return 0;
		}
	}

	@Override
	public byte[] getBytes() throws IOException {
		try (InputStream inputStream = resource.getInputStream()) {
			return inputStream.readAllBytes();
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return resource.getInputStream();
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		try (InputStream inputStream = resource.getInputStream()) {
			Files.copy(inputStream, dest.toPath());
		}
	}
	
}
