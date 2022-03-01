package com.capstone.NetflixHelper.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstone.NetflixHelper.Exception.ShowAlreadyExistsException;
import com.capstone.NetflixHelper.Model.Shows;
import com.capstone.NetflixHelper.Repository.ShowsRepository;

@SpringBootTest
class ShowsServiceImplTest {

	@MockBean
	private ShowsRepository showsRepository;
	
	private ShowsServiceImpl showsServiceImpl;
	
	Shows show;
	
	@Autowired
	public ShowsServiceImplTest(ShowsServiceImpl showsServiceImpl)
	{
		this.showsServiceImpl = showsServiceImpl;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		show = new Shows("S101","Demo Show","Show for testing demo","Movie",null,null,null);
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}
	
	@Test
	void testGetAllShowsSuccess() 
	{
		when(showsRepository.findAll()).thenReturn(Stream.of(show)
				.collect(Collectors.toList()));
		assertEquals(1, showsServiceImpl.getAllShows().size());
	}

	@Test
	void testGetAllUsersFailure() 
	{
		when(showsRepository.findAll()).thenReturn(null);
		assertEquals(null, showsServiceImpl.getAllShows());
	}
	
	@Test
	void testFindShowByIdSuccess() throws ShowAlreadyExistsException
	{
		when(showsRepository.findById("S101")).thenReturn(Optional.of(show));
		assertEquals(show, showsServiceImpl.findShowById("S101"));
	}
	
	@Test
	void testFindShowByIdFailure() throws ShowAlreadyExistsException
	{
		when(showsRepository.getOne("S101")).thenReturn(null);
		assertEquals(null, showsServiceImpl.findShowById("S101"));
	}
	
	@Test
	void testSaveShowSuccess() throws ShowAlreadyExistsException 
	{
		when(showsRepository.getOne("S101")).thenReturn(null);
		when(showsRepository.save(show)).thenReturn(show);
		assertEquals(show, showsServiceImpl.saveShow(show));
	}
	
	@Test
	void testSaveShowFailure() throws ShowAlreadyExistsException 
	{
		when(showsRepository.findById("S101")).thenReturn(Optional.of(show));
		when(showsRepository.save(show)).thenReturn(null);
		Shows testShow;
		try
		{
			testShow = showsServiceImpl.saveShow(show);
		}
		catch(ShowAlreadyExistsException showAlreadyExistsException)
		{
			testShow = null;
		}
		assertEquals(null, testShow);
	}

}
