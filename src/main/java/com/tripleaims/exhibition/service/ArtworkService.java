package com.tripleaims.exhibition.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dao.ArtworkDAO;
import com.tripleaims.exhibition.dto.ArtworkCategoryDTO;
import com.tripleaims.exhibition.dto.ArtworkDTO;
import com.tripleaims.exhibition.dto.ArtworkImageDTO;
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
			Date now = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
			String path = "/assets/artwork/" + dateFormat.format(now);
			String folderPath = ExhibitionConfig.FRONT_PATH + path;
			File folder = new File(folderPath);
			if(!folder.exists()) folder.mkdirs();
			
			for (int i = 0; i < images.size(); i++) {
				MultipartFile image = images.get(i);
				
				if(image.getSize() == 0) {
					continue;
				}
				
				String ext = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
				String filename = "artwork_" + artworkDTO.getArtworkNo() + "_" + (i + 1);
				String fullFilename = filename + "." + ext;
				
				File file = null;
				int j = 1;
				while(true) {
					String tempFilename = filename;
					if(j != 1) {
						 tempFilename += "(" + j + ")";
						 fullFilename = tempFilename + "." + ext;
					}
					file = new File(folder, fullFilename);
					if(file.exists()) {
						j++;
						continue;
					} else {
						filename = tempFilename;
						break;
					}
				}
				
				String thumbName = "";
				try {
					// 이미지 생성
					image.transferTo(file);
					
					// 썸네일 생성
					BufferedImage srcImg = ImageIO.read(file); 
					
					int dw = 270, dh = 270; 
					
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

					thumbName = "THUMB_" + filename; 
					File thumbFile = new File(folder, thumbName + "." + ext);
					
					ImageIO.write(destImg, ext, thumbFile);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				ArtworkImageDTO artworkImage = new ArtworkImageDTO();
				String maxArtworkImageNo = dao.selectMaxArtworkImageNo();
				String artworkImageNo;
				if(maxArtworkImageNo != null && !maxArtworkImageNo.isEmpty()) {
					artworkImageNo = "" + (Long.parseLong(maxArtworkImageNo) + 1);
				} else {
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMM");
					artworkImageNo = "121" + dateFormat2.format(now) + "00001";
				}
				artworkImage.setArtworkImageNo(artworkImageNo);
				artworkImage.setArtworkNo(artworkNo);
				artworkImage.setGroupOrder(i + 1);
				artworkImage.setFileName(path + "/" + filename + "." + ext);
				artworkImage.setThum1Name(path + "/" + thumbName + "." + ext);
				
				// DB 등록
				dao.insertArtworkImage(artworkImage);
			}
			
		}
		
		resultStr.append("<script>\n");
		resultStr.append("location.replace('" + ExhibitionConfig.REFERER_URL + "/admin/artwork-view.html?artworkNo=" + artworkNo +"');\n");
		resultStr.append("</script>");
		
		return resultStr.toString();
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
	
	
	
	
}
