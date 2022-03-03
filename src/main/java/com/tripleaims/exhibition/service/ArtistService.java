package com.tripleaims.exhibition.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tripleaims.exhibition.dao.ArtistDAO;
import com.tripleaims.exhibition.dto.ArtistAddressDTO;
import com.tripleaims.exhibition.dto.ArtistDTO;
import com.tripleaims.exhibition.util.ExhibitionConfig;

@Service
@Transactional
public class ArtistService {
	
	@Autowired
	ArtistDAO dao;
	
	public String insert(Map<String, Object> paramMap){
		
		StringBuilder returnMessage = new StringBuilder();
		LocalDate date = LocalDate.now();
		
		// 폴더생성
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM");
		String folderPath = ExhibitionConfig.PROFILE_PATH + "/" + date.format(formatter1);
		File folder = new File(folderPath);
		if(!folder.exists()) folder.mkdirs();
		
		// 파일등록
		MultipartFile profile = (MultipartFile)paramMap.get("profile");
		String profilePath = "";
		if(profile.getSize() > 0) {
			String realFilename = profile.getOriginalFilename();
			String ext = realFilename.substring(realFilename.lastIndexOf('.')+1);
			String filename = "profile_" + date.toString() + "_" + folder.listFiles().length + "." + ext;
			
			File file = new File(folder, filename);
			boolean isFileUploaded = true;
			try {
				profile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				isFileUploaded = false;
			}
			if(!isFileUploaded) { // 파일등록 실패
				returnMessage.append("<script>\n");
				returnMessage.append("alert(ERROR:'프로필 이미지 등록 실패');\n");
				returnMessage.append("history.back();\n");
				returnMessage.append("</script>");
				return returnMessage.toString();
			}
			profilePath = folderPath + "/" + filename;
			profilePath = profilePath.substring(ExhibitionConfig.FRONT_PATH.length());
		}
		
		// 작가등록
		ArtistDTO artist = new ArtistDTO();
		String maxArtistNo = dao.selectMaxArtistNo();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMM");
		String indexOfStr = "" + date.format(formatter2);
		String artistNo = "";
		if(maxArtistNo != null && !maxArtistNo.isEmpty()) {
			artistNo = "130" + indexOfStr + String.format("%05d",(Integer.parseInt(maxArtistNo.substring(10))+1));
		} else {
			artistNo = "130" + indexOfStr + "00001";
		}
		artist.setArtistNo(artistNo);
		artist.setNameKor((String)paramMap.get("nameKor"));	// nameKor
		artist.setNameEng((String)paramMap.get("nameEng")); // nameEng
		artist.setBirthday((Date)paramMap.get("birthday")); // birthday
		String[] phoneArr = (String[])paramMap.get("phone"); 
		if(phoneArr != null && phoneArr.length != 0) {
			StringBuilder phones = new StringBuilder();
			phones.append("[");
			for (int i = 0; i < phoneArr.length; i++) {
				String phone = phoneArr[i];
				if(phone == null || phone.isBlank()) continue;
				if(i != 0) phones.append(",");
				phones.append("\"").append(phone).append("\"");
			}
			phones.append("]");
			artist.setPhones(phones.toString()); // phones
		}
		String[] emailArr = (String[])paramMap.get("email");
		if(emailArr != null && emailArr.length != 0) {
			StringBuilder emails = new StringBuilder();
			emails.append("[");
			for (int i = 0; i < emailArr.length; i++) {
				String email = emailArr[i];
				if(email == null || email.isBlank()) continue; 
				if(i != 0)emails.append(",");
				emails.append("\"").append(email).append("\"");
			}
			emails.append("]");
			artist.setEmails(emails.toString()); // emails
		}
		artist.setProfile(profilePath); // profile
		artist.setIntroduce((String)paramMap.get("introduce"));
		artist.setHistory((String)paramMap.get("history"));
		artist.setOpenYn((boolean)paramMap.get("openY")?"Y":"N");
		System.out.println("작가등록:" + artist.toString());
		boolean isInsert = dao.insertArtist(artist);
		if(!isInsert) { // DB 작가등록 실패
			returnMessage.append("<script>\n");
			returnMessage.append("alert('ERROR:작가 등록 실패');\n");
			returnMessage.append("history.back();\n");
			returnMessage.append("</script>");
			return returnMessage.toString();
		}
		
		// 주소 등록
		@SuppressWarnings("unchecked")
		List<String[]> addrList = (List<String[]>)paramMap.get("addrList");
		for (String[] addrs : addrList) {
			ArtistAddressDTO address = new ArtistAddressDTO();
			address.setArtistNo(artistNo);
			address.setAddressName(addrs[0]);
			address.setAddressn(addrs[1]);
			address.setAddress1(addrs[2]);
			if(addrs.length-1 >= 3) address.setAddress2(addrs[3]);
			boolean isInsertAddress = dao.insertArtistAddress(address);
			if(!isInsertAddress) { // DB 주소등록 실패
				returnMessage.append("<script>\n");
				returnMessage.append("alert('ERROR:주소 등록 실패');\n");
				returnMessage.append("history.back();\n");
				returnMessage.append("</script>");
				return returnMessage.toString();
			}
		}
		
		// returnMessage
		returnMessage.append("<script>\n");
		returnMessage.append("location.replace('" + ExhibitionConfig.REFERER_URL + "/admin/artist-view.html?artistNo=" + artistNo + "');");
		returnMessage.append("</script>\n");
		
		return returnMessage.toString();
	}
	
	
	public Map<String, Object> selectList(ArtistDTO dto) {
		List<ArtistDTO> resultList = dao.selectListArtists(dto);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", resultList);
		return resultMap;
	}


	public Map<String, Object> selectOne(String artistNo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArtistDTO dto = dao.selectOneArtist(artistNo);
		if(dto == null) {
			resultMap.put("result", false);
			resultMap.put("data", null);
		} else {
			resultMap.put("result", true);
			resultMap.put("data", dto);
		}
		return resultMap;
	}
	
}

