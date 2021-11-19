package by.innowise.tasks.service;

import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.SubjectMapper;
import by.innowise.tasks.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    public static final String NOT_FOUND_BY_ID = "There is no such subject with id: %s";

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectDto saveSubject(SubjectDto subjectDto) {
        return subjectMapper
                .toSubjectDto(subjectRepository
                        .saveAndFlush(subjectMapper
                                .toSubject(subjectDto)));
    }

    @Transactional(readOnly = true)
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.getAll()
                .map(subjectMapper::toSubjectDto)
                .toList();
    }

    public SubjectDto getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toSubjectDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    public void updateSubject(SubjectDto subjectDto) {
        subjectRepository.save(subjectMapper.toSubject(subjectDto));
    }
}
