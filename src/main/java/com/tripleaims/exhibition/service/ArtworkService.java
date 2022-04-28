package com.tripleaims.exhibition.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dao.ArtworkDAO;
import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;
import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ArtworkImageDTO;
import com.tripleaims.exhibition.util.ArtworkFileUtil;
import com.tripleaims.exhibition.util.ExhibitionConfig;

@Service
@Transactional
public class ArtworkService {

	@Autowired
	ArtworkDAO dao;
	
	public Map<String, Object> selectAllArtwork(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<>();
		List<ArtworkDTO> list = dao.selectAllArtwork(paramMap);
		resultMap.put("result", list);
		
		return resultMap; 
	}
	
	public Map<String, Object> selectAllArtworkCategory() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<ArtworkCategoryDTO> list = dao.selectAllArtworkCategroy();
		resultMap.put("result", list);
		
		return resultMap;
	}

	public String insertArtwork(Map<String, Object> paramMap) {
		StringBuilder resultStr = new StringBuilder();
		
		ArtworkDTO artworkDTO = (ArtworkDTO)paramMap.get("artworkDTO");
		@SuppressWarnings("unchecked")
		List<MultipartFile> images = (List<MultipartFile>)paramMap.get("images");
		
		// 작품 DB 등록
		String maxArtworkNo = dao.selectMaxArtworkNo();
		String artworkNo;
		if(maxArtworkNo != null && !maxArtworkNo.isEmpty()) {
			artworkNo = "" + (Long.parseLong(maxArtworkNo) + 1);
		} else {
			Date now = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			artworkNo = "120" + dateFormat.format(now) + "00001";
		}
		artworkDTO.setArtworkNo(artworkNo);
		
		if(dao.insertArtwork(artworkDTO) == false) {
			resultStr.append("<script>\n");
			resultStr.append("alert('작품 등록 실패');\n");
			resultStr.append("history.back();\n");
			resultStr.append("</script>\n");
			
			return resultStr.toString();
		}
		
		// 이미지 등록
		if(images != null && images.size() != 0) {
			for (int i = 0; i < images.size(); i++) {
				MultipartFile image = images.get(i);

				Map<String, Object> imageMap = ArtworkFileUtil.registImage(artworkDTO.getArtistNo(), artworkNo, i+1, image);
				if((boolean)imageMap.get("result") == false) {
					System.out.println((String)imageMap.get("exMessage"));
					continue;
				}
				
				ArtworkImageDTO artworkImage = new ArtworkImageDTO();
				String artworkImageNo, maxArtworkImageNo = dao.selectMaxArtworkImageNo();
				if(maxArtworkImageNo != null && !maxArtworkImageNo.isEmpty()) {
					artworkImageNo = "" + (Long.parseLong(maxArtworkImageNo) + 1);
				} else {
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMM");
					Date now = new Date(System.currentTimeMillis());
					artworkImageNo = "121" + dateFormat2.format(now) + "00001";
				}
				artworkImage.setArtworkImageNo(artworkImageNo);
				artworkImage.setArtworkNo(artworkNo);
				artworkImage.setGroupOrder(i + 1);
				artworkImage.setFileName((String)imageMap.get("fileName"));
				artworkImage.setThum1Name((String)imageMap.get("thumb1Name"));
				
				// DB 등록
				dao.insertArtworkImage(artworkImage);
			}
			
		}
		
		resultStr.append("<script>\n");
		resultStr.append("location.replace('" + ExhibitionConfig.REFERER_URL + "/admin/artwork-view.html?artworkNo=" + artworkNo +"');\n");
		resultStr.append("</script>");
		
		return resultStr.toString();
	}

	public Map<String, Object> selectArtworkFromArr(String[] arr) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String param = "";
		
		if(arr == null || arr.length == 0) { // arr 없음
			resultMap.put("result", null);
			return resultMap;
		}
		
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			
			if(i == 0) {
				param += str;
			} else {
				param += "," + str;
			}
		}

		resultMap.put("result", dao.selectArtworkFromArr(param));
		
		return resultMap;
	}

	public Map<String, Object> selectOneArtwork(String artworkNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("artwork", dao.selectOneArtwork(artworkNo));
		resultMap.put("artworkImages", dao.selectArtworkImage(artworkNo));
		resultMap.put("category", dao.selectAllArtworkCategroy());
		
		return resultMap;
	}
	
	public String updateArtwork(ArtworkDTO artworkDTO) {
		StringBuilder contentBuilder = new StringBuilder();
		boolean isSuccess = dao.updateArtworkConfig(artworkDTO);
		
		if(isSuccess) {
			// 성공
			contentBuilder.append("<script>\n");
			contentBuilder.append("location.replace('" + ExhibitionConfig.REFERER_URL + "/admin/artwork-view.html?artworkNo=" + artworkDTO.getArtworkNo() + "');");
			contentBuilder.append("</script>");
		} else {
			// 실패
			contentBuilder.append("<body>");
			contentBuilder.append("ERROR");
			contentBuilder.append("</body>");
		}
		
		return contentBuilder.toString();
	}
	
	
	public List<ArtworkDTO> artistArtwork(String artistNo) {
		return dao.artistArtwork(artistNo);
	}


	
	public List<ArtworkImageDTO> artworkImage(String artworkNo) {
		return dao.artworkImage(artworkNo);
	}
	
	public ArtworkDTO artworkInfo(String artworkNo) {
		return dao.artworkInfo(artworkNo);
	}
	
	
}
