package com.tripleaims.exhibition.service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

	/** 날짜, 제목에 대해서 모든 전시회를 검색합니다. */
	public Map<String, Object> selectExhibition(Map<String, Object> paramMap) {
		// Get Parameters
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", dao.selectExhibition(paramMap));
		
		return resultMap;
	}

	/** 전시회를 추가합니다. */
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

	/** 전시회 내용을 변경합니다. */
	public Map<String, Object> editExhibition(Map<String, Object> paramMap) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		
		System.out.println("editExhibition(): " + paramMap);
		
		boolean isEdit = dao.updateExhibition((ExhibitionDTO)paramMap.get("exhibition"));
		resultMap.put("isEdit", isEdit);
		
		return resultMap;
	}

	/** 전시회의 대표이미지를 변경하거나 추가합니다. */
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
	 * status의 값이 'true'이면 전시회에 등록된 작품들을 조회하고
	 * 아니면 해당 작가의 해당 전시회에 등록되지 않은 작품들을 조회한다.
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
	
	/** 전시회 정보 가져오기 */
	public ExhibitionDTO exhibitionInfo(String exhibitionNo) {
		return dao.exhibitionInfo(exhibitionNo);
	}
	
	/** 해당 검색&페이지에 대한 오픈된 전시회 목록을 불러옵니다. */
	public List<ExhibitionDTO> crrentList(PagingParam dto) {
		return dao.crrentList(dto);
	}
	
	/** 해당 검색 결과에 대한 오픈된 전시회 개수를 가져옵니다. */
	public int  currentCount(PagingParam pram) {
		return dao.currentCount(pram);
	}
	
	/** 해당 검색&페이지에 대한 전시 대기중인 전시회 목록을 가져옵니다. */
	public List<ExhibitionDTO> pastList(PagingParam dto) {
		return dao.pastList(dto);
	}
	
	/** 해당 검색&페이지에 대한 전시 대기중인 전시회 개수를 가져옵니다. */
	public int  pastCount(PagingParam pram) {
		return dao.pastCount(pram);
	}
	
	/** 해당 전시회의 작품들을 가져옵니다. */
	public List<ArtworkDTO> exArtwowrk(String exhibitionNo) {
		return dao.exArtwowrk(exhibitionNo);
	}

	/** 전시회에 작품을 추가합니다. */
	public Map<String, Object> addArtworks(Map<String, Object> paramMap) {
		// Get Parameters
		String exhibitionNo = (String)paramMap.get("exhibitionNo");
		String[] artworkNo = (String[])paramMap.get("artworkNo");
		
		// Set ResultMap
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		resultMap.put("result", false);
		resultMap.put("message", null);
		resultMap.put("ExceptionMessage", null);
		
		// Get Exhibition's Info (artworks)
		List<ArtworkDTO> artworks = dao.selectExhibitionArtworks(exhibitionNo, null);
		
		// Setting artworksNo
		Set<String> regArtworkNo = new HashSet<String>();
		for (ArtworkDTO artwork : artworks) {
			regArtworkNo.add(artwork.getArtworkNo());
		}
		
		// insert ExhibitionArtwork
		int orderNo = artworks.size() + 1;
		try {
			for (String no : artworkNo) {
				if(!regArtworkNo.contains(no)) {
					ExhibitionArtworkDTO dto = new ExhibitionArtworkDTO();
					String exhibitionArtworkNo = String.format("%s%03d", exhibitionNo, orderNo);
					dto.setExhibitionArtworkNo(exhibitionArtworkNo);
					dto.setExhibitionNo(exhibitionNo);
					dto.setArtworkNo(no);
					dto.setOrderNo(orderNo);

					dao.insertExhibitionArtwork(dto);
					
					orderNo++;
				}
			}
			
			resultMap.put("result", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			resultMap.put("message", "등록 중 오류가 발생했습니다.");
			resultMap.put("ExceptionMessage", e.getMessage());
		}
		
		// return result
		return resultMap;
	}

	
	/** 전시회에 등록된 작품을 제외합니다. */
	public Map<String, Object> removeArtworks(Map<String, Object> paramMap) {
		// Get Parameters
		String exhibitionNo = (String)paramMap.get("exhibitionNo");
		String[] artworkNo = (String[])paramMap.get("artworkNo");

		// Set ResultMap
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		resultMap.put("result", false);
		resultMap.put("message", null);
		resultMap.put("ExceptionMessage", null);
		
		// Delete exhibition_artwork
		try {
			for (String no : artworkNo) {
				dao.deleteExhibitionArtwork(exhibitionNo, no);
			}

			resultMap.put("result", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			resultMap.put("message", "삭제 중 오류가 발생했습니다.");
			resultMap.put("ExceptionMessage", e.getMessage());
			
		}
		
		return resultMap;
	}

	/** 전시회 작품 순서를 변경합니다. */
	public Map<String, Object> replaceOrder(Map<String, Object> paramMap) {
		// Log
		System.out.println("ExhibitionService:replaceOrder()");
		
		// Get Parameters
		HttpServletRequest request = (HttpServletRequest)paramMap.get("request");
		String exhibitionNo = (String)paramMap.get("exhibitionNo");
		
		// Set ResultMap
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Connect", true); // Connect 확인
		resultMap.put("result", false);
		resultMap.put("message", null);
		resultMap.put("ExceptionMessage", null);

		// Get Exhibition's Info (artworks)
		List<ArtworkDTO> artworks = dao.selectExhibitionArtworks(exhibitionNo, null);
		
		// Delete Exhibition's Info (artworks)
		dao.deleteAllExhibitionArtwork(exhibitionNo);
		
		// Order Artwork For Registing Exhibition
		Map<String, Integer> orderMap = new HashMap<>();
		for (ArtworkDTO dto : artworks) {
			String artworkNo = dto.getArtworkNo();
			if(request.getParameter(artworkNo) != null) {
				int orderNo = Integer.parseInt((String)request.getParameter(artworkNo));
				orderMap.put(artworkNo, orderNo);
			}
		}
		List<Map.Entry<String, Integer>> entryList = new LinkedList<Map.Entry<String,Integer>>(orderMap.entrySet());
		entryList.sort(Map.Entry.comparingByValue());
		
		System.out.println("ExhibitionNo:" + exhibitionNo);
		System.out.println("orderMap:" + orderMap);
		
		// Insert Exhibition_Artwork
		int orderNo = 0;
		int successCnt = 0;
		for (Map.Entry<String, Integer> entry : entryList) { 
			String artworkNo = entry.getKey();
			orderNo++;
			
			System.out.println(artworkNo + ": order-" + orderNo);
			
			ExhibitionArtworkDTO dto = new ExhibitionArtworkDTO();
			dto.setExhibitionArtworkNo(String.format("%s%03d", exhibitionNo, orderNo));
			dto.setExhibitionNo(exhibitionNo);
			dto.setArtworkNo(artworkNo);
			dto.setOrderNo(orderNo);
			
			boolean isInsert = dao.insertExhibitionArtwork(dto);
			if(isInsert) successCnt++;
		}
		
		// Check Result
		if(successCnt == artworks.size()) {
			resultMap.put("result", true);
			resultMap.put("message", "성공");
			resultMap.put("ExceptionMessage", null);
		} else {
			resultMap.put("result", false);
			resultMap.put("message", "등록되지 못한 작품이 있습니다. 확인이 필요합니다.");
			resultMap.put("ExceptionMessage", null);
		}
		
		// Return Result
		return resultMap;
		
	}
	
	
}
