package by.innowise.tasks.service;

import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.entity.Subject;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.SubjectMapper;
import by.innowise.tasks.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static by.innowise.tasks.service.SubjectService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    public static final Long DEFAULT_ID = 0L;
    public static final String DEFAULT_NAME = "name";

    public static final Subject DEFAULT_SUBJECT = Subject.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .build();

    public static final SubjectDto DEFAULT_SUBJECT_DTO = SubjectDto.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .build();

    public static final List<Subject> DEFAULT_SUBJECTS_LIST = List.of(DEFAULT_SUBJECT);

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void saveSubject() {
        given(subjectRepository.saveAndFlush(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT);
        given(subjectMapper.toSubject(DEFAULT_SUBJECT_DTO)).willReturn(DEFAULT_SUBJECT);
        given(subjectMapper.toSubjectDto(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT_DTO);

        assertEquals(DEFAULT_SUBJECT_DTO, subjectService.saveSubject(DEFAULT_SUBJECT_DTO));

        then(subjectRepository).should(only()).saveAndFlush(DEFAULT_SUBJECT);
        then(subjectMapper).should(times(1)).toSubject(DEFAULT_SUBJECT_DTO);
        then(subjectMapper).should(times(1)).toSubjectDto(DEFAULT_SUBJECT);
        then(subjectMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getSubjects() {
        given(subjectRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_SUBJECT));
        given(subjectMapper.toSubjectDto(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT_DTO);

        assertEquals(DEFAULT_SUBJECT_DTO, subjectService.getAllSubjects().get(0));

        then(subjectRepository).should(only()).getAll();
        then(subjectMapper).should(only()).toSubjectDto(DEFAULT_SUBJECT);
    }

    @Test
    void getSubjectById() {
        given(subjectRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_SUBJECT));
        given(subjectMapper.toSubjectDto(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT_DTO);

        assertEquals(DEFAULT_SUBJECT_DTO, subjectService.getSubjectById(DEFAULT_ID));

        then(subjectRepository).should(only()).findById(DEFAULT_ID);
        then(subjectMapper).should(only()).toSubjectDto(DEFAULT_SUBJECT);
    }

    @Test
    void deleteSubject() {
        subjectService.deleteSubject(DEFAULT_ID);

        then(subjectRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateSubject() {
        given(subjectRepository.save(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT);
        given(subjectMapper.toSubject(DEFAULT_SUBJECT_DTO)).willReturn(DEFAULT_SUBJECT);

        subjectService.updateSubject(DEFAULT_SUBJECT_DTO);

        then(subjectRepository).should(only()).save(DEFAULT_SUBJECT);
        then(subjectMapper).should(only()).toSubject(DEFAULT_SUBJECT_DTO);
    }

    @Test
    void getSubjectByIdNotFoundException() {

        given(subjectRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> subjectService.getSubjectById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(subjectRepository).should(only()).findById(DEFAULT_ID);
    }
}