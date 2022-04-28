package com.tripleaims.exhibition.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dto.ArtworkImageDTO;

/**
 * 작품이미지 생성 클래스
 * 
 * @author 박세환
 */
public class ArtworkFileUtil {

	private final static String ESSETS_FOLDER_NAME = ExhibitionConfig.ASSETS_PATH.substring(ExhibitionConfig.ASSETS_PATH.lastIndexOf("/") + 1);  // "assets"
	private final static String ARTWORK_FOLDER_NAME = "artwork";
	private final static String ARTWORK_PATH = ExhibitionConfig.ASSETS_PATH + "/" + ARTWORK_FOLDER_NAME; 
	
	/**
	 * 작품의 이미지를 등록합니다.
	 * @return Map형식으로 반환합니다.<br/>
	 * <table>
	 * 	<tr>
	 * 		<th>Key</th> <th>Value</th> <th>Comment</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>result</td> <td>true/false</td> <td>성공여부</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>exMessage</td> <td>e.getMessage</td> <td>에러메세지</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>fileName</td> <td></td> <td>ARTWORK_IMAGE.FILE_NAME</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>thumb1Name</td> <td></td> <td>ARTWORK_IMAGE.THUM1_NAME</td>
	 * 	</tr>
	 * </table>
	 */
	public static Map<String, Object> registImage(String artistNo, String artworkNo, int orderNo, MultipartFile originalFile) {
		
		String ext = originalFile.getOriginalFilename().substring(originalFile.getOriginalFilename().lastIndexOf(".") + 1);
		String filename = "ART_" + artistNo + "_" + artworkNo + "_" + String.format("%03d", orderNo);
		String realPath = ARTWORK_PATH + "/" + artistNo + "/" + artworkNo;
		String regPath = "/" + ESSETS_FOLDER_NAME + "/" + ARTWORK_FOLDER_NAME + "/" + artistNo + "/" + artworkNo;

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("result", false);
		returnMap.put("exMessage", null);
		
		File realFile = null;
		try {
			realFile = createOriginalFile(realPath, filename, ext, originalFile);
		} catch (Exception e) {
			returnMap.put("exMessage", e.getMessage());
		}
		
		File thumbnailFile1 = null;
		if(realFile != null) {
			try {
				thumbnailFile1 = createThumbnailFile1(realPath, filename, ext, realFile);
			} catch (Exception e) {
				returnMap.put("exMessage", e.getMessage());
			}
		}
		
		if(thumbnailFile1 != null && realFile != null) {
			returnMap.put("result", true);
			returnMap.put("exMessage", null);
			returnMap.put("fileName", regPath + "/" + realFile.getName());
			returnMap.put("thumb1Name", regPath + "/" + thumbnailFile1.getName());
		}
		
		return returnMap;
	}
	
	/** 작품 원본을 생성합니다. */
	private static File createOriginalFile(String realPath, String filename, String ext, MultipartFile originalFile) throws IllegalStateException, IOException {
		String regFilename = filename + "_R." + ext;

		File regFolder = new File(realPath);
		if(!regFolder.exists()) regFolder.mkdirs();
		
		File regFile = new File(regFolder, regFilename);
		originalFile.transferTo(regFile);
		
		return regFile;
	}
	
	/** 작품 썸네일을 생성합니다. */
	private static File createThumbnailFile1(String realPath, String filename, String ext, File realFile) throws IOException {
		int dw = 270, dh = 270; // 사이즈
		
		BufferedImage srcImg = ImageIO.read(realFile); 
		
		int ow = srcImg.getWidth(); 
		int oh = srcImg.getHeight(); 
		
		int nw = ow; 
		int nh = (ow * dh) / dw; 
		
		if(nh > oh) { 
			nw = (oh * dw) / dh; 
			nh = oh; 
		}
		
		BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);
		BufferedImage destImg = Scalr.resize(cropImg, dw, dh);

		File regFolder = new File(realPath);
		if(!regFolder.exists()) regFolder.mkdirs();
		
		String regFilename = filename + "_TH1." + ext;
		File thumbFile = new File(regFolder, regFilename);
		
		ImageIO.write(destImg, ext, thumbFile);
		
		return thumbFile;
	}
	
	/**
	 * 해당 작품의 이미지를 삭제합니다.
	 * @return 성공여부
	 */
	public static boolean deleteFile(ArtworkImageDTO dto) {
		String fileName = dto.getFileName();
		String thumb1Name = dto.getThum1Name();
		
		File realFile = new File(ExhibitionConfig.FRONT_PATH + "/" + fileName);
		boolean isDeleteRF = false;
		if(realFile.exists()) isDeleteRF = realFile.delete();
		
		File thumbnailFile1 = new File(ExhibitionConfig.FRONT_PATH + "/" + thumb1Name);
		boolean isDeleteTHF1 = false;
		if(thumbnailFile1.exists()) isDeleteTHF1 = thumbnailFile1.delete();
		
		return isDeleteRF && isDeleteTHF1;
	}
	
	
}
