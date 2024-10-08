package tot.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import tot.exception.FileUploadDirectoryNotFoundException;
import tot.exception.FileUploadInvalidFilenameException;
import tot.util.FileUtil;

public class FileUtilTest {

	@InjectMocks
	private FileUtil fileUtil;

	@Mock
	private MultipartFile mockFile;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		// 테스트용 업로드 디렉토리 설정
		ReflectionTestUtils.setField(fileUtil, "uploadDir", "test/upload");
		new File("test/upload").mkdirs(); // 테스트 디렉토리 생성
	}

	/**
	 * 유효한 파일을 업로드할 때 이미지가 정상적으로 저장되는지 테스트
	 */
	@Test
	public void testSaveImage_WithValidFile() throws IOException {
		String originalFilename = "testImage.png";
		Path resourcePath = Paths.get("src/test/resources");

		// 리소스 디렉토리가 없으면 생성
		if (Files.notExists(resourcePath)) {
			Files.createDirectories(resourcePath);
		}

		File tempFile = new File(resourcePath.toFile(), originalFilename);

		// 기존 파일이 존재하는 경우 삭제
		if (tempFile.exists()) {
			Files.delete(tempFile.toPath());
		}

		// 테스트용으로 임시 파일 생성
		Files.createFile(tempFile.toPath());

		// Mock 설정
		when(mockFile.getOriginalFilename()).thenReturn(originalFilename);
		when(mockFile.getInputStream()).thenReturn(Files.newInputStream(tempFile.toPath()));
		when(mockFile.getSize()).thenReturn(1024L);

		// 테스트 실행
		String result = fileUtil.saveImage(mockFile);

		// UUID가 포함된 파일 이름을 확인
		assertTrue(result.startsWith("/upload/"));
		assertTrue(result.endsWith(".png"));

		// 저장된 파일 경로 확인
		String savedFileName = result.substring("/upload/".length());
		Path savedFilePath = Paths.get(fileUtil.getUploadDir(), savedFileName);
		assertTrue(Files.exists(savedFilePath));

		// 테스트 후 파일 삭제
		Files.delete(savedFilePath);
		Files.delete(tempFile.toPath());
	}

	/**
	 * 파일 이름이 null인 경우 예외가 발생하는지 테스트
	 */
	@Test
	public void testSaveImage_WithNullFilename() {
		when(mockFile.getOriginalFilename()).thenReturn(null);

		assertThrows(FileUploadInvalidFilenameException.class, () -> fileUtil.saveImage(mockFile));
	}

	/**
	 * 업로드 디렉토리가 비어 있는 경우 예외가 발생하는지 테스트
	 */
	@Test
	public void testSaveImage_WithEmptyUploadDir() {
		ReflectionTestUtils.setField(fileUtil, "uploadDir", ""); // 업로드 디렉토리를 비워둠

		assertThrows(FileUploadDirectoryNotFoundException.class, () -> fileUtil.saveImage(mockFile));
	}

	/**
	 * 파일 이름이 null인 경우 유효성 검증 메서드가 예외를 발생시키는지 테스트
	 */
	@Test
	public void testValidateFilename_WithNullFilename() {
		assertThrows(FileUploadInvalidFilenameException.class, () -> fileUtil.saveImage(mockFile));
	}
}
