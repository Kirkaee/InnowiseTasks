package by.innowise.tasks.service;

import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.SubjectMapper;
import by.innowise.tasks.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    public static final String NOT_FOUND_BY_ID = "There is no such subject with id: %s";

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectMapper subjectMapper;

    public SubjectDto saveSubject(SubjectDto subjectDto) {
        return subjectMapper
                .toSubjectDto(subjectRepository
                        .save(subjectMapper
                                .toSubject(subjectDto)));
    }

    public List<SubjectDto> getAllSubjects() {
        return subjectRepository
                .findAll()
                .stream()
                .map(subjectMapper::toSubjectDto)
                .toList();
    }

    public SubjectDto getSubjectById(Long id) {
        return subjectRepository
                .findById(id)
                .map(subjectMapper::toSubjectDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteSubject(Long id) {
        subjectRepository
                .deleteById(id);
    }

    public SubjectDto updateSubject(Long id, SubjectDto subjectDto) {
        subjectDto.setId(id);
        return subjectMapper
                .toSubjectDto(subjectRepository
                        .save(subjectMapper
                                .toSubject(subjectDto)));
    }
}
