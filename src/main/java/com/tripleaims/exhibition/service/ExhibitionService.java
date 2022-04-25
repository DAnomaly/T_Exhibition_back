package com.tripleaims.exhibition.service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dao.ExhibitionDAO;
import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionArtworkDTO;
import com.tripleaims.exhibition.dto.ExhibitionDTO;
import com.tripleaims.exhibition.dto.PagingParam;
import com.tripleaims.exhibition.util.ExhibitionConfig;

@Service
@Transactional
public class ExhibitionService {

	@Autowired
	ExhibitionDAO dao;

	// 전시회 목록 검색
	public Map<String, Object> selectExhibition(Map<String, Object> paramMap) {
		// Get Parameters
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", dao.selectExhibition(paramMap));
		
		return resultMap;
	}

	// 전시회 추가
	public String addExhibition(Map<String, Object> paramMap) {
		
		// Get now
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		
		// Get Parameters
		ExhibitionDTO dto = (ExhibitionDTO)paramMap.get("exhibition");
		String[] artworks = ((String)paramMap.get("artworks")).split(",");
		
		// Parameter Check
		/*
		System.out.println("dto: " + dto.toString());
		System.out.print("artworks: ");
		for (String str : artworks) {
			System.out.print(str + " ");
		}
		System.out.println(); 
		*/
		
		// Setting exhibitionNo 
		String maxExhibitionNo = dao.selectMaxExhibitionNo();
		String exhibitionNo = "";
		if(maxExhibitionNo == null || maxExhibitionNo.isEmpty()) {
			exhibitionNo += "110" + df.format(now) + "00001";
		} else {
			if(maxExhibitionNo.indexOf(df.format(now)) != -1) {
				exhibitionNo += (Long.parseLong(maxExhibitionNo) + 1); 
			} else {
				exhibitionNo += "110" + df.format(now) + "00001";
			}
		}
		dto.setExhibitionNo(exhibitionNo);
		
		// Insert Exhibition
		dao.insertExhibition(dto);
		
		// Insert ExhibitionArtwork
		for (int i = 0; i < artworks.length; i++) {
			String artworkNo = artworks[i];
			ExhibitionArtworkDTO tempDTO = new ExhibitionArtworkDTO();
			tempDTO.setExhibitionArtworkNo(String.format("%s%03d", exhibitionNo, i + 1));
			tempDTO.setExhibitionNo(exhibitionNo);
			tempDTO.setArtworkNo(artworkNo);
			tempDTO.setOrderNo(i + 1);
			
			dao.insertExhibitionArtwork(tempDTO);
		}
		
		// Redirect : exhibition-view.html
		StringBuilder returnSb = new StringBuilder();
		returnSb.append("<script>\n");
		returnSb.append("location.replace('" + ExhibitionConfig.REFERER_URL + "/admin/exhibition-view.html?exhibitionNo=" + exhibitionNo + "');");
		returnSb.append("</script>\n");
		
		return returnSb.toString();
	}

	// 전시회 내용 변경
	public Map<String, Object> editExhibition(Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		
		System.out.println("editExhibition(): " + paramMap);
		
		boolean isEdit = dao.updateExhibition((ExhibitionDTO)paramMap.get("exhibition"));
		resultMap.put("isEdit", isEdit);
		
		return resultMap;
	}

	// 전시회 이미지 변경
	public Map<String, Object> changeExhibitionImage(Map<String, Object> paramMap) {
		// Get Parameter
		String exhibitionNo = (String)paramMap.get("exhibitionNo");
		MultipartFile originalFile = (MultipartFile)paramMap.get("mainImage");
		
		// Set ResultMap
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		resultMap.put("ExceptionMessage", null);
		
		// Generate Image File
		resultMap.put("Generate", false);
		resultMap.put("GenerateMessage",null);
		
		String filePath = "", fullFilePath = "", ext = "", fileName = "", fullFileName = "";
		if(originalFile.getSize() == 0) {
			resultMap.put("Generate", false);
			resultMap.put("GenerateMessage", "getSize(): 0");
		} else {
			try {
				filePath =  "/assets/exhibition/mainImage";
				fullFilePath = ExhibitionConfig.FRONT_PATH + filePath;
				ext = originalFile.getOriginalFilename().substring(originalFile.getOriginalFilename().lastIndexOf(".") + 1);
				fileName = "EMI_" + exhibitionNo;
				fullFileName = fileName + "." + ext;

				File folder = new File(fullFilePath);
				if(!folder.exists()) folder.mkdirs();

				File file = new File(folder, fullFileName);
				originalFile.transferTo(file);

				resultMap.put("Generate", true);
				resultMap.put("GenerateMessage", "파일저장성공");
			} catch (Exception e) {
				resultMap.put("Generate", false);
				resultMap.put("GenerateMessage", "파일저장실패");
				resultMap.put("ExceptionMessage", e.getMessage());
			}
		}
		
		// Update DB data
		resultMap.put("InsertDB", false);
		resultMap.put("InsertDBMessage",null);
		if((boolean)resultMap.get("Generate")) {
			try {
				boolean isUpdate = dao.updateExhibitionMainImage(filePath + "/" + fullFileName, exhibitionNo);
				if(isUpdate) {
					resultMap.put("InsertDB", true);
					resultMap.put("InsertDBMessage", "변경내용수정됨");
				} else {
					resultMap.put("InsertDB", false);
					resultMap.put("InsertDBMessage", "변경내용없음");
				}
			} catch (Exception e) {
				resultMap.put("InsertDB", false);
				resultMap.put("InsertDBMessage", "변경내용없음");
				resultMap.put("ExceptionMessage", e.getMessage());
			}
		}
		
		// Show Log
		System.out.println("changeExhibitionImage(): exhibitionNo=" + exhibitionNo + ", fileName=" + originalFile.getName());
		System.out.println("changeExhibitionImage(): " + resultMap.toString());
		
		// Return ResultMap
		return resultMap;
	}


	/**
	 * <b>전시회 작품 조회</b><br/>
	 * status의 값이 'true'이면 전시회에 등록된 작품들을 조회
	 * 아니면 해당 작가의 해당 전시회에 등록되지 않은 작품들을 조회한다.
	 * 
	 * @param paramMap : 파라미터Map
	 * @return 응답Map
	 */
	public Map<String, Object> artworkList(Map<String, Object> paramMap) {
		// Get Parameters
		String exhibitionNo = (String)paramMap.get("exhibitionNo");
		String artistNo = (String)paramMap.get("artistNo");
		boolean status = ((String)paramMap.get("status")).equals("true");

		// Set ResultMap
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		resultMap.put("ExceptionMessage", null);

		// Get Artwork List
		List<ArtworkDTO> artworkList = null;
		resultMap.put("isWork", false);
		resultMap.put("artworkList", null);
		try {
			
			if(status == true) {
				artworkList = dao.selectExhibitionArtworks(exhibitionNo, artistNo);
			} else {
				artworkList = dao.selectNotExhibitionArtworks(exhibitionNo, artistNo);
			}
			resultMap.put("isWork", true);
			resultMap.put("artworkList", artworkList);
			
		} catch (Exception e) {
			resultMap.put("isWork", false);
			resultMap.put("ExceptionMessage", e.getMessage());
		}

		// Show Log
		System.out.println("artworkList(): " + paramMap.toString());

		// Return ResultMap
		return resultMap;
	}
	
	public ExhibitionDTO exhibitionInfo(String exhibitionNo) {
		return dao.exhibitionInfo(exhibitionNo);
	}
	
	public List<ExhibitionDTO> crrentList(PagingParam dto) {
		return dao.crrentList(dto);
	}
	
	public int  currentCount(PagingParam pram) {
		return dao.currentCount(pram);
	}
	
	public List<ExhibitionDTO> pastList(PagingParam dto) {
		return dao.pastList(dto);
	}
	
	public int  pastCount(PagingParam pram) {
		return dao.pastCount(pram);
	}
	
	
	public List<ArtworkDTO> exArtwowrk(String exhibitionNo) {
		return dao.exArtwowrk(exhibitionNo);
	}

}
