package com.ap.enotes_api_service.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.catalina.mapper.Mapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.dto.NotesDto.CategoryDto;
import com.ap.enotes_api_service.dto.NotesDto.FilesDto;
import com.ap.enotes_api_service.dto.NotesResponseDto;
import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.entity.FileDetails;
import com.ap.enotes_api_service.entity.Notes;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.repository.CategoryRepository;
import com.ap.enotes_api_service.repository.FileRepository;
import com.ap.enotes_api_service.repository.NotesRepository;
import com.ap.enotes_api_service.service.NotesService;
import com.ap.enotes_api_service.utils.CommonUtil;
import com.ap.enotes_api_service.utils.Validation;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private Validation validation;
	@Value("${file.upload.path}")
	private String uploadPath;
	@Autowired
	private FileRepository fileRepository;
	
	@Override
	public Boolean saveNotes(String notes, MultipartFile multipartFile) throws Exception {
		// TODO Auto-generated method stub
		
		ObjectMapper objMp = new ObjectMapper();
		NotesDto notesDto = objMp.readValue(notes, NotesDto.class);
		
		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);
		
		Integer notesId = notesDto.getId();
		
		if(!ObjectUtils.isEmpty(notesId)) {
			updateNotes(notesDto, multipartFile);
		}
		
		//Notes Validation
		
		validation.NotesValidation(notesDto);
		
		/*
		 * //Notes Validation.....validating the category coming in notes, we validate
		 * if the Category with given Id does not exist.
		 */		
		
		checkExistingCategory(notesDto.getCategory());
		Notes mappedNotes = modelMapper.map(notesDto, Notes.class);
		
		//Save File
		FileDetails fileDeails = saveFileDetails(multipartFile);
		
		if(!ObjectUtils.isEmpty(fileDeails)) {
			mappedNotes.setFileDetails(fileDeails);
		}
		else {
			if(ObjectUtils.isEmpty(notesId)) {
				mappedNotes.setFileDetails(null);
			}
		}
		
		//Save Note
		Notes savedNote =  notesRepository.save(mappedNotes);
		if(!ObjectUtils.isEmpty(savedNote)) {
			return true;
		}
		
		return false;
	}  


	private void updateNotes(NotesDto notesDto, MultipartFile multipartFile) throws Exception {
		// TODO Auto-generated method stub
		Notes existingNotes = notesRepository.findById(notesDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid Notes Id = " + notesDto.getId()));
		
		if(ObjectUtils.isEmpty(multipartFile)) {
			notesDto.setFileDetails(modelMapper.map(existingNotes.getFileDetails(), FilesDto.class));
		}
		
	}


	private FileDetails saveFileDetails(MultipartFile multipartFile) throws IOException {
		// TODO Auto-generated method stub
		
		if(!ObjectUtils.isEmpty(multipartFile) && !multipartFile.isEmpty()) {
			
			String originalFileName = multipartFile.getOriginalFilename();
			String randomString = UUID.randomUUID().toString();
			String uploadFileName = randomString+"."+FilenameUtils.getExtension(originalFileName);
			
			File saveFile = new File(uploadPath);
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			
			String storagePath = uploadPath.concat(uploadFileName);
			long upload = Files.copy(multipartFile.getInputStream(), Paths.get(storagePath));
		
			if(upload!=0) {
				FileDetails fileDetails = new FileDetails();
				fileDetails.setOriginalFileName(originalFileName);
				fileDetails.setDisplayFileName(getDisplayName(originalFileName));
				fileDetails.setUploadFileName(uploadFileName);
				fileDetails.setFileSize(multipartFile.getSize());
				fileDetails.setPath(storagePath);
				FileDetails savedFile = fileRepository.save(fileDetails);
				return savedFile;
			}
		}
		return null;
	}


	private String getDisplayName(String originalFileName) {
		// TODO Auto-generated method stub
		
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFileName);
		
		if(fileNameWithoutExtension.length() > 8) {
			fileNameWithoutExtension = fileNameWithoutExtension.substring(0, 7); 
		}
		
		fileNameWithoutExtension = fileNameWithoutExtension + "." + extension;
		
		return fileNameWithoutExtension;
	}


	/*
	 * //this function checks if the category id passed in a note is valid or
	 * not....if the category with the id exists or not...
	 */	
	
	private void checkExistingCategory(CategoryDto category) throws ResourceNotFoundException {
		
		categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid category ID = " + category.getId()));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		// TODO Auto-generated method stub
		
		/*
		 * Here we are extracting a list of Notes from database and then by using
		 * stream.map, we are taking one Note at a time then the one Note we get
		 * from stream.map, we are mapping its data from Note to NotesDto one by
		 * one. then we take another Note and do the same using mapper. Hence, the
		 * stream.map gives one Note at a time from list of Notes, then
		 * mapper.map maps data of each Note to NotesDto.
		 */
		
		return notesRepository.findAll().stream().map(note -> modelMapper.map(note, NotesDto.class)).toList();

	}


	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		// TODO Auto-generated method stub
				
		InputStream io =  new FileInputStream(fileDetails.getPath());
		
		byte[] byteDataOfFile = StreamUtils.copyToByteArray(io);
		
		return byteDataOfFile;
	}


	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		// TODO Auto-generated method stub
		FileDetails fileDetails = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File Not Available with ID = " + id));
		return fileDetails;
	}


	@Override
	public NotesResponseDto getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		/*
		 * //Pagination m kitny page dikhany h or ek page m kitny notes dikhany
		 * h-------(1,5)---2nd page m 5 notes dikhany h. page index0 sy start hota h.
		 */		
		
		Pageable pagable = PageRequest.of(pageNo, pageSize);
		Page<Notes> notesList = notesRepository.findAllByCreatedByAndIsDeletedFalse(userId, pagable);
		
		List<NotesDto> notesDtoList = notesList.get().map(note -> modelMapper.map(note, NotesDto.class)).toList();
		
		NotesResponseDto notesResponse = NotesResponseDto.builder()
				.notes(notesDtoList)
				.pageNumber(notesList.getNumber())
				.pageSize(notesList.getSize())
				.totalElements(notesList.getTotalElements())
				.totalPages(notesList.getTotalPages())
				.isFirst(notesList.isFirst())
				.isLast(notesList.isLast())
				.build();
		return notesResponse;
	}


	@Override
	public void softDeleteNotes(Integer id) throws Exception {

		Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found with Id = "+id));
		notes.setIsDeleted(true);
		notes.setDeletedOn(new Date());
		notesRepository.save(notes);
	}


	@Override
	public void restoreNotes(Integer id) throws Exception {
		Notes notes = notesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found with Id = "+id));
		notes.setIsDeleted(false);
		notes.setDeletedOn(null);
		notesRepository.save(notes);
	}


	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		
		List<Notes> recycleBinNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
		List<NotesDto> notesDtoList = recycleBinNotes.stream().map(note -> modelMapper.map(note, NotesDto.class)).toList();
		
		return notesDtoList;
	}

}
