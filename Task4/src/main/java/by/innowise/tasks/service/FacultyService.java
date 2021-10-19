package by.innowise.tasks.service;

import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.FacultyMapper;
import by.innowise.tasks.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    public static final String NOT_FOUND_BY_ID = "There is no such faculty with id: %s";

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyMapper facultyMapper;

    public FacultyDto saveFaculty(FacultyDto facultyDto) {
        return facultyMapper
                .toFacultyDto(facultyRepository
                        .save(facultyMapper
                                .toFaculty(facultyDto)));
    }

    public List<FacultyDto> getAllFaculties() {
        return facultyRepository
                .findAll()
                .stream()
                .map(facultyMapper::toFacultyDto)
                .toList();
    }

    public FacultyDto getFacultyById(Long id) {
        return facultyRepository
                .findById(id)
                .map(facultyMapper::toFacultyDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteFaculty(Long id) {
        facultyRepository
                .deleteById(id);
    }

    public FacultyDto updateFaculty(Long id, FacultyDto facultyDto) {
        facultyDto.setId(id);
        return facultyMapper
                .toFacultyDto(facultyRepository
                        .save(facultyMapper
                                .toFaculty(facultyDto)));
    }
}
