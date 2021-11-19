package by.innowise.tasks.service;

import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.FacultyMapper;
import by.innowise.tasks.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    public static final String NOT_FOUND_BY_ID = "There is no such faculty with id: %s";

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    public FacultyDto saveFaculty(FacultyDto facultyDto) {
        return facultyMapper
                .toFacultyDto(facultyRepository
                        .saveAndFlush(facultyMapper
                                .toFaculty(facultyDto)));
    }

    @Transactional(readOnly = true)
    public List<FacultyDto> getAllFaculties() {
        return facultyRepository.getAll()
                .map(facultyMapper::toFacultyDto)
                .toList();
    }

    public FacultyDto getFacultyById(Long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toFacultyDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public void updateFaculty(FacultyDto facultyDto) {
        facultyRepository.save(facultyMapper.toFaculty(facultyDto));
    }
}
